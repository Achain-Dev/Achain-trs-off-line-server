package test.com.ms.data;

import com.ms.data.ACTPrivateKey;
import com.ms.data.CONTRACT;
import com.ms.data.Transaction;

import org.junit.Test;

public class TransactionTest {

  @Test
  public void testTransfer() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("5K7M9e9WyERLBqdsvFUCMNfzmwiJsX7ut4KbthiN8vsJoTdTyms"),
        10000L,
        "CON92cJUVM6qS9qp1ihnJB5DJrf1pP9F2fSB",
        ""
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransfer() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("5Jjxz2UYLfBoWkPgs2tDnC2XPEVfdxyFzACZoYWC7EXPyXG7z3P"),
        CONTRACT.SMC_t,
        "ACT3hzHVhrekqbhdGrC9quUW28nU4r2gBuGm",
        1L,
        1000L
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransferAll() {
    Transaction trx = new Transaction(
        new ACTPrivateKey("5Jjxz2UYLfBoWkPgs2tDnC2XPEVfdxyFzACZoYWC7EXPyXG7z3P"),
        "",
        "ACT3hzHVhrekqbhdGrC9quUW28nU4r2gBuGm",
        "1",
        1000L
    );
    System.out.println(trx.toJSONString());
  }

  @Test
  public void testContractTransaction(){
    Transaction trx = new Transaction(
            new ACTPrivateKey("5JqhMnPsyNpCVDSU79Xa3reoqR2kYWJNAKLNwYkpF7puiwk9e3D"),
            "CONE8Jjh6bA1J5AcDhFrSNv7faH5aSYWin7k",
            10000L,
            1000L
    );
    System.out.println(trx.toJSONString());
  }
}
