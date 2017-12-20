package test.com.achain.data;

import com.achain.data.ACTPrivateKey;
import com.achain.data.CONTRACT;
import com.achain.data.Transaction;

import org.junit.Test;

public class TransactionTest {

  @Test
  public void testTransfer() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("dsfa"),
        10000L,
        "CONfda",
        ""
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransfer() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("fdsa"),
        CONTRACT.SMC_t,
        "ACTfdsa",
        1L,
        1000L
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransferAll() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("fadsf"),
        "",
        "ACTafsdfasd",
        "1",
        1000L
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransaction(){
    Transaction trx = new Transaction(
            new ACTPrivateKey("fsdaf"),
            "CONsadff",
            10000L,
            1000L
    );
    System.out.println(trx.toJSONString());
  }
}
