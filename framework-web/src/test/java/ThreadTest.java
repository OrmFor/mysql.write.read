import JmsTest.BaseTest;
import com.test.springboot.domain.SysUser;
import com.test.springboot.service.ISysUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
* @Author wwy
* @Description 多线程测试，注意多线程测试的时候，spring注入会有坑，自动装配的bean，需要当参数传入task中，
 *             否则task由于多线程关系，不会在同一个上下文中取到bean并装配
* @Date 11:04 2019/2/3
* @Param
* @return
**/
public class ThreadTest extends  BaseTest{
    @Autowired
    private ISysUser sysUserService;
    @Test
    public void test() {
        int count = 100;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        long a = System.currentTimeMillis();
        for (int i = 0; i < count; i++)
            executorService.execute(new task(cyclicBarrier,sysUserService,i));

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long b = System.currentTimeMillis();
        System.out.println((b-a)/1000);
    }

    private class task implements Runnable {

        private ISysUser sysUserService;
        private CyclicBarrier cyclicBarrier;

        private int i = 0;

        public task(CyclicBarrier cyclicBarrier, ISysUser sysUserService,int i) {
            this.cyclicBarrier = cyclicBarrier;
            this.sysUserService = sysUserService;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                // 等待所有任务准备就绪
                cyclicBarrier.await();
                System.out.println(i);
                // 测试内容
                if (i % 3 == 0) {
                    SysUser condi = new SysUser();
                    condi.setId(30);
                    //测试读
                    SysUser bean = sysUserService.getByCondition(condi);
                } else {
                    //测试写
                    SysUser sysUser = new SysUser();
                    sysUser.setUserName("老吴" + i);

                    SysUser condi = new SysUser();
                    condi.setId(30);
                    int k = 1;
                    int j = 1;
                    int l  = sysUserService.updateByCondition(sysUser, condi);
                    if (l == 1) {
                        System.out.println("保存成功第" + j + "次");
                        j++;
                    } else {
                        System.out.println("保存失败" + k + "次");
                        k++;

                    }
                }

                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
