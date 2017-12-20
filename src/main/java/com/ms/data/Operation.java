package com.ms.data;

import com.alibaba.fastjson.JSONObject;
import com.ms.data.ACTAddress.Type;
import com.ms.util.JSON;
import com.ms.util.MyByte;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ms.data.Operation.OperationType.*;

public class Operation {
  private OperationType operationType; // 8bit
  private byte[] data;
  //
  private byte[] bytes;
  private JSONObject json;

  public byte[] toBytes() {
    if (bytes == null) {
      bytes = MyByte.builder()
                    .copy(operationType._byte)
                    .copyVector(data)
                    .getData();
    }
    return bytes;
  }

  public JSONObject toJSON() {
    return json;
  }

  public static Operation createWithdraw(ACTPrivateKey actPrivateKey, long amount) {
    Operation operation = new Operation();
    operation.setOperationType(WITHDRAW_OP_TYPE);
    byte[] balanceId = new WithdrawCondition(actPrivateKey.getAddress()).getBalanceId();
    operation.setData(
        MyByte.builder()
              .copy(balanceId)
              .copy(amount)
              .padding()
              .getData());
    operation.setJson(
        JSON.build()
            .add("type", WITHDRAW_OP_TYPE.name().toLowerCase())
            .add("data", JSON.build()
                             .add("claim_input_data", "")
                             .add("amount", amount)
                             .add("balance_id", new ACTAddress(balanceId, Type.BALANCE_ID).getAddressStrStartWithSymbol())
                             .get())
            .get());
    return operation;
  }

  public static Operation createDeposit(ACTAddress address, long amount) {
    Operation operation = new Operation();
    operation.setOperationType(DEPOSIT_OP_TYPE);
    WithdrawCondition condition = new WithdrawCondition(address);
    operation.setData(
        MyByte.builder()
              .copy(amount, 8)
              .copy(condition.toBytes())
              .getData());
    operation.setJson(
        JSON.build()
            .add("type", DEPOSIT_OP_TYPE.name().toLowerCase())
            .add("data", JSON.build()
                             .add("amount", amount)
                             .add("condition", JSON.build()
                                                   .add("asset_id", condition.getAssetId())
                                                   .add("slate_id", condition.getSlateId())
                                                   .add("type", condition.getType().name().toLowerCase())
                                                   .add("balance_type", condition.getBalanceType().name().toLowerCase())
                                                   .add("data", JSON.build()
                                                                    .add("owner", address.getAddressStrStartWithSymbol())
                                                                    .get())
                                                   .get())
                             .get())
            .get());
    return operation;
  }

  public static Operation createIMessage(String remark) {
    byte[] remarkBytes = remark.getBytes();
    if (remarkBytes.length > 40) {
      throw new RuntimeException("remark byte length must be smaller than or equal to 40");
    }
    Operation operation = new Operation();
    operation.setOperationType(IMESSAGE_MEMO_OP_TYPE);
    operation.setData(
        MyByte.builder()
              .copyVector(remarkBytes)
              .getData());
    operation.setJson(
        JSON.build()
            .add("type", IMESSAGE_MEMO_OP_TYPE.name().toLowerCase())
            .add("data", JSON.build()
                             .add("imessage", remark)
                             .get())
            .get());

    return operation;
  }

  public static Operation createCallContract(
      ACTPrivateKey actPrivateKey,
      CONTRACT contract,
      String method,
      String args,
      Asset costLimit) {
    Map<byte[], Long> balances = new HashMap<>();
    Asset transactionFee = new Asset(Transaction.requiredFees);
    balances.put(new WithdrawCondition(actPrivateKey.getAddress()).getBalanceId(),
                 costLimit.getAmount() + transactionFee.getAmount());
    Operation operation = new Operation();
    operation.setOperationType(CALL_CONTRACT_OP_TYPE);
    operation.setData(
        MyByte.builder()
              .copy(actPrivateKey.getPublicKey(true))
              .copy(balances)
              .copy(contract.getActAddress().getEncoded())
              .copy(costLimit.toBytes())
              .copy(transactionFee.toBytes())
              .copy(method)
              .copy(args)
              .getData());
    operation.setJson(
        JSON.build()
            .add("type", CALL_CONTRACT_OP_TYPE.name().toLowerCase())
            .add("data", JSON.build()
                             .add("caller", new ACTAddress(actPrivateKey.getPublicKey(true), Type.PUBLIC_KEY)
                                 .getAddressStrStartWithSymbol())
                             .add("balances", mapFlatToList(balances))
                             .add("contract", contract.getActAddress().getAddressStrStartWithSymbol())
                             .add("costlimit", costLimit.toJSON())
                             .add("transaction_fee", transactionFee.toJSON())
                             .add("method", method)
                             .add("args", args)
                             .get())
            .get());
    return operation;
  }

  public static Operation createCallContract(
      ACTPrivateKey actPrivateKey,
      String contractId,
      String method,
      String args,
      Asset costLimit) {
    ACTAddress actAddress = new ACTAddress(contractId, Type.CONTRACT);
    Map<byte[], Long> balances = new HashMap<>();
    Asset transactionFee = new Asset(Transaction.requiredFees);
    balances.put(new WithdrawCondition(actPrivateKey.getAddress()).getBalanceId(),
                 costLimit.getAmount() + transactionFee.getAmount());
    Operation operation = new Operation();
    operation.setOperationType(CALL_CONTRACT_OP_TYPE);
    operation.setData(
        MyByte.builder()
              .copy(actPrivateKey.getPublicKey(true))
              .copy(balances)
              .copy(actAddress.getEncoded())
              .copy(costLimit.toBytes())
              .copy(transactionFee.toBytes())
              .copy(method)
              .copy(args)
              .getData());
    operation.setJson(
        JSON.build()
            .add("type", CALL_CONTRACT_OP_TYPE.name().toLowerCase())
            .add("data", JSON.build()
                             .add("caller", new ACTAddress(actPrivateKey.getPublicKey(true), Type.PUBLIC_KEY)
                                 .getAddressStrStartWithSymbol())
                             .add("balances", mapFlatToList(balances))
                             .add("contract", actAddress.getAddressStrStartWithSymbol())
                             .add("costlimit", costLimit.toJSON())
                             .add("transaction_fee", transactionFee.toJSON())
                             .add("method", method)
                             .add("args", args)
                             .get())
            .get());
    return operation;
  }

  public static Operation createContractTransaction(ACTPrivateKey actPrivateKey, String contractId, Asset costLimit, Asset amount){
    ACTAddress actAddress = new ACTAddress(contractId, Type.CONTRACT);
    Map<byte[], Long> balances = new HashMap<>();
    Asset transactionFee = new Asset(Transaction.requiredFees);
    balances.put(new WithdrawCondition(actPrivateKey.getAddress()).getBalanceId(),
            costLimit.getAmount() + transactionFee.getAmount() + amount.getAmount());
    Operation operation = new Operation();
    operation.setOperationType(TRANSFER_CONTRACT_OP_TYPE);
    operation.setData(
            MyByte.builder()
                    .copy(actPrivateKey.getPublicKey(true))
                    .copy(costLimit.toBytes())
                    .copy(transactionFee.toBytes())
                    .copy(amount.toBytes())
                    .copy(balances)
                    .copy(actAddress.getEncoded())
                    .getData());
    operation.setJson(
            JSON.build()
                    .add("type", TRANSFER_CONTRACT_OP_TYPE.name().toLowerCase())
                    .add("data", JSON.build()
                            .add("from", new ACTAddress(actPrivateKey.getPublicKey(true), Type.PUBLIC_KEY)
                                    .getAddressStrStartWithSymbol())
                            .add("costlimit", costLimit.toJSON())
                            .add("transaction_fee", transactionFee.toJSON())
                            .add("transfer_amount", amount.toJSON())
                            .add("balances", mapFlatToList(balances))
                            .add("contract_id", actAddress.getAddressStrStartWithSymbol())
                            .get())
                    .get());
    return operation;
  }

  private static List<String[]> mapFlatToList(Map<byte[], Long> balances) {
    List<String[]> result = new ArrayList<>();
    balances.forEach((k, v) -> result.add(
        new String[]{
            new ACTAddress(k, Type.BALANCE_ID).getAddressStrStartWithSymbol(),
            v.toString()
        }));
    return result;
  }

  private void setOperationType(OperationType operationType) {
    this.operationType = operationType;
  }

  private void setData(byte[] data) {
    this.data = data;
  }

  private void setJson(JSONObject json) {
    this.json = json;
  }

  public enum OperationType {
    NULL_OP_TYPE(0),

    // BALANCES
    WITHDRAW_OP_TYPE(1),
    DEPOSIT_OP_TYPE(2),

    // ACCOUNTS
    REGISTER_ACCOUNT_OP_TYPE(3),
    UPDATE_ACCOUNT_OP_TYPE(4),
    WITHDRAW_PAY_OP_TYPE(5),

    // ASSETS
    CREATE_ASSET_OP_TYPE(6),
    UPDATE_ASSET_OP_TYPE(7),
    ISSUE_ASSET_OP_TYPE(8),

    // RESERVED
    // RESERVED_OP_1_TYPE         = 10, // SKIP; SEE BELOW
    RESERVED_OP_2_TYPE(11),
    RESERVED_OP_3_TYPE(17),
    DEFINE_SLATE_OP_TYPE(18),

    // RESERVED
    RESERVED_OP_4_TYPE(21),
    RESERVED_OP_5_TYPE(22),
    RELEASE_ESCROW_OP_TYPE(23),
    UPDATE_SIGNING_KEY_OP_TYPE(24),
    UPDATE_BALANCE_VOTE_OP_TYPE(27),

    // ASSETS
    UPDATE_ASSET_EXT_OP_TYPE(30),
    // MEMO
    IMESSAGE_MEMO_OP_TYPE(66),

    CONTRACT_INFO_OP_TYPE(68),

    REGISTER_CONTRACT_OP_TYPE(70),
    UPGRADE_CONTRACT_OP_TYPE(71),
    DESTROY_CONTRACT_OP_TYPE(72),
    CALL_CONTRACT_OP_TYPE(73),
    TRANSFER_CONTRACT_OP_TYPE(74),
    // CONTRACT
    WITHDRAW_CONTRACT_OP_TYPE(80),
    DEPOSIT_CONTRACT_OP_TYPE(82),

    // BALANCES WITHDRAW
    BALANCES_WITHDRAW_OP_TYPE(88),

    TRANSACTION_OP_TYPE(90),
    STORAGE_OP_TYPE(91),

    // EVENT
    EVENT_OP_TYPE(100),

    // ON FUNCTIONS IN CONTRACTS
    ON_DESTROY_OP_TYPE(108),
    ON_UPGRADE_OP_TYPE(109),

    // CONTRACT CALL SUCCESS
    ON_CALL_SUCCESS_OP_TYPE(110),;
    private byte _byte;

    OperationType(int _byte) {
      this._byte = (byte) _byte;
    }
  }
}
