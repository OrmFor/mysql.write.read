package JmsTest;

import com.test.springboot.config.jms.UserModel;
import com.test.springboot.config.jms.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJms extends BaseTest {

    @Autowired
    private UserService t;

    @Test
    public void test(){
        long a1 = System.currentTimeMillis();
        for(int i=0;i<2000;i++){
            UserModel um = new UserModel();
            um.setUserId("userId"+i);
            um.setName("name"+i);
            um.setAge(i+5);

            t.save(um);
        }
        long a2 = System.currentTimeMillis();
        System.out.println("time =="+(a2-a1));

    }
}
