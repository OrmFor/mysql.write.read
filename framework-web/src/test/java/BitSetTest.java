import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BitSetTest extends Serializers.Base {

    @Test
    public void test() {
        Random random = new Random();

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10000000; i++) {
            int randomResult = random.nextInt(100000000);
            list.add(randomResult);
        }
       /* System.out.println("产生的随机数有");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }*/
        BitSet bitSet = new BitSet(100000000);
        for (int i = 0; i < 10000000; i++) {
            bitSet.set(list.get(i));
        }

        System.out.println("0~1亿不在上述随机数中有" + bitSet.size());
       /* for (int i = 0; i < 100000000; i++) {
            if (!bitSet.get(i)) {
                System.out.println(i);
            }
        }*/
    }
}
