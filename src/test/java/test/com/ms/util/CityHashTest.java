package test.com.ms.util;

import com.ms.util.CityHash;
import com.ms.util.MyByte;
import java.math.BigInteger;
import java.util.Arrays;
import org.junit.Test;

public class CityHashTest {

  @Test
  public void test() {
    byte[] password = MyByte.fromHex("95836531154f5b8cff3f4af7729baa3740b4695814dafb38dc542ec0cf78f7648f94549c66811f29f2c2f9386e55c0c81014b216c0127a6a42ac0d213c852189");
//    password = MyByte.builder()
//                     .copy(password, 56, 8)
//                     .copy(password, 48, 8)
//                     .copy(password, 40, 8)
//                     .copy(password, 32, 8)
//                     .copy(password, 24, 8)
//                     .copy(password, 16, 8)
//                     .copy(password, 8, 8)
//                     .copy(password, 0, 8)
//                     .getData();
    password = MyByte.reverse(password);
    System.out.println(Arrays.toString(password));
    System.out.println(" data: " + MyByte.toHex(password));
    long[] cityHash = CityHash.cityHash128(password, 0, password.length);
    byte[] testCityHash = MyByte.builder().copy(cityHash[0]).copy(cityHash[1]).getData();
    System.out.println("   my: " + MyByte.toHex(testCityHash));
    System.out.println("right: 9a32cf9ace5d95c412ed3eae59d5aa56");
    System.out.println(new BigInteger(1, MyByte.reverse(MyByte.builder().copy(cityHash[0]).getData())));
    System.out.println(new BigInteger(1, MyByte.reverse(MyByte.builder().copy(cityHash[1]).getData())));
  }
}
