package test.com.ms.data;

import com.ms.data.ACTAddress;
import com.ms.data.ACTAddress.Type;
import com.ms.data.WithdrawCondition;
import com.ms.util.MyByte;
import org.junit.Test;

public class WithdrawConditionTest {

  @Test
  public void testToBytes() {
    ACTAddress address = new ACTAddress("Cd7GRUr3HpGTXBBpW2cWp4mRi38kZnhEo", Type.ADDRESS);
    WithdrawCondition c = new WithdrawCondition(address);
    System.out.println(MyByte.toHex(c.getBalanceId()) + " ->测试摘要算法");
    System.err.println("bf02b44cfe0496c058ecee82307f8aa90cdbf783 ->正确结果");
  }
}
