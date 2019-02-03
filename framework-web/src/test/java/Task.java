import JmsTest.BaseTest;
import com.test.springboot.domain.SysUser;
import com.test.springboot.service.ISysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CyclicBarrier;

public class Task extends BaseTest implements Runnable  {

    @Autowired
    private ISysUser sysUserService;
    private CyclicBarrier cyclicBarrier;

    private int i = 0;

    public Task(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            // 等待所有任务准备就绪
            cyclicBarrier.await();
            // 测试内容
            if (i % 3 == 0) {
                SysUser condi = new SysUser();
                condi.setId(30);
                System.out.println(sysUserService);
                //测试读
                SysUser bean = sysUserService.getByCondition(condi);
                System.out.println(bean.getUserName());
            } else {
                //测试写
                SysUser sysUser = new SysUser();
                sysUser.setUserName("老吴" + i);

                SysUser condi = new SysUser();
                condi.setId(30);
                int j = sysUserService.updateByCondition(sysUser, condi);
                if (j == 1) {
                    System.out.println("保存成功第" + j + "次");
                    j++;
                }else{
                    int k = 1;
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
