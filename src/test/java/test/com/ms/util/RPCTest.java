package test.com.ms.util;

import com.ms.data.ACTPrivateKey;
import com.ms.data.Transaction;
import com.ms.util.RPC;
import org.junit.Test;

public class RPCTest {

  @Test
  public void testNetworkBroadcastTransaction() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("5Jjxz2UYLfBoWkPgs2tDnC2XPEVfdxyFzACZoYWC7EXPyXG7z3P"),
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
