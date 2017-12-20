package test.com.achain.util;

import com.achain.data.ACTPrivateKey;
import com.achain.data.Transaction;
import com.achain.util.RPC;

import org.junit.Test;

public class RPCTest {

  @Test
  public void testNetworkBroadcastTransaction() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("fda"),
        1L,
        "ACTCd7GRUr3HpGTXBBpW2cWp4mRi38kZnhEo",
        null
    );
    System.out.println(trx.toJSONString());
    RPC.Response response = RPC.NETWORK_BROADCAST_TRANSACTION.call(trx.toJSONString());
    System.out.println(response);
  }

  @Test
  public void testInfo() {
    System.out.println(RPC.INFO.call());
  }
}
