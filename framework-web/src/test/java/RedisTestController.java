import JmsTest.BaseTest;
import com.test.springboot.redis.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;

public class RedisTestController extends BaseTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test(){
        boolean flag = redisUtil.set("hello","world");
        System.out.println(flag);
        String v = (String) redisUtil.get("hello");
        System.out.println(v);

/*        RedisTestController mock = mock( RedisTestController.class);
        mock.redisUtil.set("h","w");*/

    }




}
