package com.test.springboot.config.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
* @Author wwy
* @Description 优化activeMq
* @Date 16:22 2019/1/28
* @Param
* @return
**/
public class ActiveMQPools {
	/**
	 * 队列名称，从spring配置注入
	 */
	private String queueName = "";
	/**
	 * ActiveMQ的连接池工厂，从Spring注入
	 */
	private PooledConnectionFactory pooledConnectionFactory = null;
	/**
	 * 指定缓存几个连接对象，默认是1，可以从spring注入
	 */
	private int connectionMaxCounts = 0;
	/**
	 * 指定为每个连接对象缓存几个Session对象，默认是1，可以从spring注入
	 */
	private int sessionPerConnectionMaxCount = 0;
	/**
	 * 指定了每个Session提交消息的阀值，也是每个Session对象缓存的消息的最大值
	 */
	private int messageCountPerSession2Send = 100;
	/**
	 * 指定Session每隔多长时间就要提交一次，单位是毫秒，主要就是为了防止消息数量少了，
	 * 达不到上面一个参数指定的阀值
	 */
	private int sessionCommitInterval = 1000;
	/**
	 * 指定每个Session对象缓存几个Producer对象，默认为1，基本上1就够了，只是用来发送消息而已
	 */
	private int producerPerSession = 1;
	
	/**
	 * 缓存到目的地的连接对象，key-第几个连接对象，也就是连接对象的编号
	 * value-对应的连接对象
	 */
	private Map<Integer,Connection> mapConnection = new HashMap<Integer,Connection>();
	/**
	 * 表示当前取到第几个连接对象了，默认就是轮循的方式
	 */
	private int mapConnectionCount = 0;
	/**
	 * 缓存操作的Session对象，一个连接对象可以有多个Session
	 */
	private Map<Connection,Map<Integer,Session>> mapSession = new HashMap<Connection,Map<Integer,Session>>();
	/**
	 * 表示当前取到第几个Session对象了，默认就是轮循的方式
	 */
	private int mapSessionCount = 0;
	/**
	 * 缓存操作的Producer对象，一个Session可以有多个Producer，目前是只用1个
	 */
	private Map<Session,Map<String,MessageProducer>> mapProducer = new HashMap<Session,Map<String,MessageProducer>>();
	/**
	 * 表示当前取到第几个Producer对象了，默认就是轮循的方式
	 */
	private int mapProducerCount = 0;
	/**
	 * 缓存每隔Session对象创建的新的ObjectMessage
	 */
	private Map<Session,ObjectMessage> mapSessionObjectMessage = new HashMap<Session,ObjectMessage>();
	
	/**
	 * 缓存每隔session里面等待发送的消息数量
	 */
	private Map<Session,Integer> mapSessionMessage2Send = new HashMap<Session,Integer>();
	/**
	 * 缓存每隔Session对象上次提交发送消息的时间
	 */
	private Map<Session,Long> mapSessionLastCommitTime = new HashMap<Session,Long>();
	/**
	 * 创建按照时间去提交消息的线程对象 
	 */
	private SessionCommitThread sct = new SessionCommitThread();
	/**
	 * 表示sct线程是否已经启动运行中
	 */
	private boolean sctIsRun = false;
	
	/*---------需要Spring注入--------------*/
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public void setPooledConnectionFactory(
			PooledConnectionFactory pooledConnectionFactory) {
		this.pooledConnectionFactory = pooledConnectionFactory;
	}
	public void setConnectionMaxCounts(int connectionMaxCounts) {
		this.connectionMaxCounts = connectionMaxCounts;
	}
	public void setSessionPerConnectionMaxCount(int sessionPerConnectionMaxCount) {
		this.sessionPerConnectionMaxCount = sessionPerConnectionMaxCount;
	}
	public void setMessageCountPerSession2Send(int messageCountPerSession2Send) {
		this.messageCountPerSession2Send = messageCountPerSession2Send;
	}
	public void setSessionCommitInterval(int sessionCommitInterval) {
		this.sessionCommitInterval = sessionCommitInterval;
	}
	/*---------初始化--------------*/
	/**
	 * 初始化连接池  和 创建需要缓存的对象
	 */
	@PostConstruct
	public void init(){
		//检查注入进来的连接池工厂的配置，如果比本实例需要的连接数和Session数少的话
		//需要动态调整连接池工厂的配置
		if(pooledConnectionFactory.getMaxConnections() < this.connectionMaxCounts){
			pooledConnectionFactory.setMaxConnections(this.connectionMaxCounts+1);
		}
		if(pooledConnectionFactory.getMaximumActiveSessionPerConnection() < this.sessionPerConnectionMaxCount){
			pooledConnectionFactory.setMaximumActiveSessionPerConnection(this.sessionPerConnectionMaxCount+1);
		}
		//防止流量控制
		((ActiveMQConnectionFactory)pooledConnectionFactory.getConnectionFactory()).setProducerWindowSize(Integer.MAX_VALUE);
		
		//初始化所有需要缓存的对象
		try {
			initCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//使用了hibernate类似，1级缓存和二级缓存
	private void initCache()throws Exception{
		for(int i=0;i<this.connectionMaxCounts;i++){
			//1级：connection
			Connection connection = pooledConnectionFactory.createConnection();
			//一定要start，否则无法发送和接收消息
			connection.start();
			
			this.mapConnection.put(i+1, connection);
			//2级：session
			Map<Integer,Session> tempMapSession = new HashMap<Integer,Session>();
			for(int j=0;j<this.sessionPerConnectionMaxCount;j++){
				Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
				
				tempMapSession.put(j+1, session);
				//3级：ObjectMessage
				ObjectMessage txtMsg = session.createObjectMessage();
				this.mapSessionObjectMessage.put(session, txtMsg);
				
				//3级：Producer
				Map<String,MessageProducer> tempMapProducer = new HashMap<String,MessageProducer>();
				for(int k=0;k<this.producerPerSession;k++){
					Destination destination = session.createQueue(queueName);
					MessageProducer producer = (MessageProducer)session.createProducer(destination);
					
					tempMapProducer.put(this.queueName+(k+1),producer);
				}
				
				this.mapProducer.put(session, tempMapProducer);
			}
			
			//把session和connection关联起来
			this.mapSession.put(connection, tempMapSession);
		}
	}
	/*---------提供一些方法给发送客户端使用--------------*/
	/**
	 * 获取连接对象，目前采用轮循的方式来获取缓存的对象
	 * @return
	 */
	public synchronized Connection getConnection(){
		++mapConnectionCount;
		
		Connection conn = this.mapConnection.get(mapConnectionCount);
		
		if(this.mapConnectionCount % this.connectionMaxCounts == 0){
			this.mapConnectionCount = 0;
		}
		return conn;
	}
	/**
	 * 获取某个连接对象下的一个缓存的Session对象
	 * @param conn
	 * @return
	 */
	public synchronized Session getSession(Connection conn){
		++this.mapSessionCount;
		
		Session s = this.mapSession.get(conn).get(mapSessionCount);
		
		if(this.mapSessionCount%this.sessionPerConnectionMaxCount==0){
			this.mapSessionCount = 0;
		}
		return s;
	}
	/**
	 * 根据session和队列名称来获取缓存的producer
	 * @param session
	 * @return
	 */
	public synchronized MessageProducer getQueueProducer(Session session){
		++this.mapProducerCount;
		
		MessageProducer mp = this.mapProducer.get(session).get(queueName+this.mapProducerCount);
		
		if(this.mapProducerCount % 1 == 0){
			this.mapProducerCount = 0;
		}
		
		return mp;
	}
	/**
	 * 根据Session获取缓存的ObjectMessage
	 * @param session
	 * @return
	 */
	public synchronized ObjectMessage getSessionObjectMessage(Session session){
		return this.mapSessionObjectMessage.get(session);
	}
	/**
	 * 提交Session，如果Session中需要发送的消息数量达到了要提交的阀值，就提交；
	 * 如果没有达到，就把对应的需要发送的消息计数加1 
	 * @param session
	 */
	public synchronized void sessionCommit(Session session){
		synchronized (this) {
			Object obj = this.mapSessionMessage2Send.get(session);
			if(obj == null){
				this.mapSessionMessage2Send.put(session, 1);
			}else{
				int count = (Integer)obj;
				if(count > 0 && count % this.messageCountPerSession2Send ==0){
					//应该发送了
					this.mapSessionLastCommitTime.put(session, System.currentTimeMillis());
					try {
						session.commit();
					} catch (JMSException e) {
						e.printStackTrace();
					}
					//清空要发送的消息记录
					this.mapSessionMessage2Send.put(session, 0);
				}else{
					this.mapSessionMessage2Send.put(session, ++count);
				}
			}
		}

		//设置sct的运行标志
		if(!this.sctIsRun){
			this.sctIsRun = true;
			if(sct.getState() == Thread.State.NEW){
				sct.start();
			}
		}
	}
	
	/**
	 * 按照时间去检查，并提交Session中的消息的线程
	 * 在第一个调用sessionCommit方法的时候启动
	 * 在检测到所有的session都没有需要提交的消息后，自动终止
	 * 然后再次在有人调用sessionCommit方法的时候启动
	 */
	private class SessionCommitThread extends Thread{
		public void run(){
			boolean flag = true;
			while(flag){
				Set<Session> set = mapSessionObjectMessage.keySet();
				for(Session s : set){
					//根据时间来判断，是否需要提交Session中的消息
					if(mapSessionLastCommitTime.get(s)==null ||
							(System.currentTimeMillis() - mapSessionLastCommitTime.get(s))
						>= sessionCommitInterval	
							){
						//该提交了
						try{
							mapSessionLastCommitTime.put(s, System.currentTimeMillis());
							s.commit();
						}catch(Exception err){
							err.printStackTrace();
						}
						//把session需要提交的消息数量清空
						mapSessionMessage2Send.put(s, 0);
					}
				}
				//判断是否所有的Session中的消息都已经提交了
				Set<Session> sendSet = mapSessionMessage2Send.keySet();
				boolean f = true;
				for(Session s : sendSet){
					if(mapSessionMessage2Send.get(s) > 0 ){
						f = false;
						break;
					}
				}
				if(f){
					//如果所有的session中的消息都已经提交了，那么本线程就可以结束了
					flag = false;
					sctIsRun = false;
					sct = new SessionCommitThread();
				}else{
					//如果不是所有的session中的消息都已经提交，那么休息一下，等下次检查
					try {
						Thread.sleep(sessionCommitInterval);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			}
		}
	}

}
