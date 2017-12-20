package com.ms.data;

import com.ms.data.ACTAddress.Type;
import java.math.BigDecimal;

public enum CONTRACT {
  // 正式链
  SMC("2W6PbuBrGcB3EGFTK81sDfJmrMUTyXqta", "SMC_SimonsChainOfficial"),
  LET("DuPQkPuKqD5NM2XwHJCjnZiiTs2GAJDLB", "LET_LinkEyeOfficial"),
  BSC("57ucNVXMqPpdiBiVC72gTLJao4CobmMNy", "BSC_BlackstoneBlockchain"),
  USC("2Z7MrZV2MP794MBX2Z8xVaB2sBj8RK3RH", "USD_Coin"),
  // 测试链
  @Deprecated
  SMC_t("7w5yDZ5K4yxKjPwfn2seQjg8h6KLUwnCj", "SMC"),;


  public static final String TRANSFER_METHOD = "transfer_to";

  private static final int _scale = 6;
  private static final BigDecimal _2bd = new BigDecimal(Math.pow(10, _scale - 1));

  public static String makeTransferArgs(String toAddress, long amount) {
    if (!toAddress.startsWith(Transaction.ACT_SYMBOL) ||
        !ACTAddress.check(toAddress.substring(3), Type.ADDRESS)) {
      throw new RuntimeException("地址错误");
    }
    return toAddress + "|" + new BigDecimal(amount).divide(_2bd, _scale, BigDecimal.ROUND_DOWN)
                                                   .stripTrailingZeros();
  }

  public static String makeTransferArgs(String toAddress, String amount) {
    if (!toAddress.startsWith(Transaction.ACT_SYMBOL) ||
        !ACTAddress.check(toAddress.substring(3), Type.ADDRESS)) {
      throw new RuntimeException("地址错误");
    }
    return toAddress + "|" + amount;
  }

  private String contractName;
  private ACTAddress actAddress;

  CONTRACT(String id, String contractName) {
    this.actAddress = new ACTAddress(id, Type.CONTRACT);
    this.contractName = contractName;
  }

  public ACTAddress getActAddress() {
    return actAddress;
  }

  public String getContractName() {
    return contractName;
  }
}
