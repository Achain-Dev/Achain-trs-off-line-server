package test.com.achain.data;

import com.achain.data.ACTPrivateKey;
import com.achain.util.MyByte;

import org.junit.Test;

public class ACTPrivateKeyTest {

  @Test
  public void testCreate() {
    print(new ACTPrivateKey());
  }

  @Test
  public void testFromStr() {
    print(new ACTPrivateKey("fsda"));
  }

  @Test
  public void testFromHex() {
    print(new ACTPrivateKey(MyByte.fromHex("fasdf")));
  }

  private void print(ACTPrivateKey p) {
    System.out.println("prv: " + MyByte.toHex(p.getEncoded()));
    System.out.println("str: " + p.getKeyStr());
    System.out.println("pub: " + MyByte.toHex(p.getPublicKey(true)));
    System.out.println("add: " + p.getAddress().getAddressStr());
  }
}
