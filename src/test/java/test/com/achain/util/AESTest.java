package test.com.achain.util;

import com.achain.util.AES;
import com.achain.util.MyByte;

import org.junit.Test;

public class AESTest {

  @Test
  public void test() {
    byte[] key = MyByte.fromHex("fdaf");
    byte[] iv = MyByte.fromHex("9a32cf9ace5d95c412ed3eae59d5aa56");
    AES.Encoder encoder = AES.createEncoder(key, iv);
    AES.Decoder decoder = AES.createDecoder(key, iv);
    byte[] encoded = encoder.encode("test 中文 message");

    System.out.println("encoded: " + MyByte.toHex(encoded));
    System.out.println("decoded: " + MyByte.toHex(decoder.decode(encoded)));
    System.out.println("message: " + decoder.decodeToString(encoded));
  }
}
