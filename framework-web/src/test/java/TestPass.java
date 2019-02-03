import JmsTest.BaseTest;
import com.test.springboot.domain.SysUser;
import com.test.springboot.service.ISysUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestPass extends BaseTest {

    @Autowired
    private ISysUser sysUserService;

    @Test
    public void test(){
        SysUser condi = new SysUser();
        condi.setId(30);
        System.out.println(sysUserService);
        //测试读
        SysUser bean = sysUserService.getByCondition(condi);
        System.out.println(bean.getUserName());
    }
}
