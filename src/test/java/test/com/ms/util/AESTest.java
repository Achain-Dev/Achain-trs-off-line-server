package test.com.ms.util;

import com.ms.util.AES;
import com.ms.util.MyByte;
import org.junit.Test;

public class AESTest {

  @Test
  public void test() {
    byte[] key = MyByte.fromHex("abb5871fd97bf7b4b32baecbdda3cd3e9a6b1b88df4204bf13fbae38b9f505ea");
    byte[] iv = MyByte.fromHex("9a32cf9ace5d95c412ed3eae59d5aa56");
    AES.Encoder encoder = AES.createEncoder(key, iv);
    AES.Decoder decoder = AES.createDecoder(key, iv);
    byte[] encoded = encoder.encode("test 中文 message");

    System.out.println("encoded: " + MyByte.toHex(encoded));
    System.out.println("decoded: " + MyByte.toHex(decoder.decode(encoded)));
    System.out.println("message: " + decoder.decodeToString(encoded));
  }
}
