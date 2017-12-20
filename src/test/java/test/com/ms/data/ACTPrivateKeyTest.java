package test.com.ms.data;

import com.ms.data.ACTPrivateKey;
import com.ms.util.MyByte;
import org.junit.Test;

public class ACTPrivateKeyTest {

  @Test
  public void testCreate() {
    print(new ACTPrivateKey());
  }

  @Test
  public void testFromStr() {
    print(new ACTPrivateKey("5J4TkxWdJT4rM9utF6nGFCDHuQwiRmNd5vfUdoWQB4ozPjXgM7q"));
  }

  @Test
  public void testFromHex() {
    print(new ACTPrivateKey(MyByte.fromHex("202fe82446c0da855e9659b8e4f65c61704f1d85d89435cd6b9ea8102bb28a6d")));
  }

  private void print(ACTPrivateKey p) {
    System.out.println("prv: " + MyByte.toHex(p.getEncoded()));
    System.out.println("str: " + p.getKeyStr());
    System.out.println("pub: " + MyByte.toHex(p.getPublicKey(true)));
    System.out.println("add: " + p.getAddress().getAddressStr());
  }
}
