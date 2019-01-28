package JmsTest;

import com.test.springboot.config.jms.UserModel;
import com.test.springboot.config.jms.UserService;
import com.test.springboot.service.base.jms.JmsProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJmsOld extends BaseTest{

    @Autowired
    private JmsProducer producer;

    @Autowired
    private UserService t;

    @Test
    public void test(){

        long a1 = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            UserModel um = new UserModel();
            um.setUserId("userId"+i);
            um.setName("name"+i);
            um.setAge(i+5);

            producer.sendMsg("testext.error","insert第"+i+"条error,数据回滚");
        }
        long a2 = System.currentTimeMillis();
        System.out.println("time =="+(a2-a1));
    }

}
