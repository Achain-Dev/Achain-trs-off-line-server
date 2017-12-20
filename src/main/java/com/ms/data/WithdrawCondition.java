package com.ms.data;

import com.ms.util.MyByte;
import com.ms.util.RIPEMD160;
import com.ms.util.SHA;

public class WithdrawCondition {
  private int assetId; // fc::singed_int/int
  private long slateId; // long long
  private WithdrawConditionType type; // uint8_t
  private WithdrawBalanceType balanceType; // uint8_t
  private byte[] data; // vector<char>
  //
  private byte[] id;
  private byte[] bytes;

  public WithdrawCondition(ACTAddress address) {
    this.assetId = 0; // ACT
    this.slateId = 0; // ？？？
    this.type = WithdrawConditionType.WITHDRAW_SIGNATURE_TYPE;
    this.balanceType = WithdrawBalanceType.WITHDRAW_COMMON_TYPE;
    this.data = MyByte.builder()
                      .copy(address.getEncoded())
                      .padding()
                      .getData();
  }

  public byte[] toBytes() {
    if (bytes == null) {
      bytes = MyByte.builder()
                    .copySize(assetId)
                    .copy(slateId)
                    .copy(type._byte)
                    .copy(balanceType._byte)
                    .copyVector(data)
                    .getData();
    }
    return bytes;
  }

  public byte[] getBalanceId() {
    if (id == null) {
      id = RIPEMD160.hash(SHA._512hash(toBytes()));
    }
    return id;
  }

  public int getAssetId() {
    return assetId;
  }

  public long getSlateId() {
    return slateId;
  }

  public WithdrawConditionType getType() {
    return type;
  }

  public WithdrawBalanceType getBalanceType() {
    return balanceType;
  }

  enum WithdrawConditionType {
    WITHDRAW_NULL_TYPE(0),
    WITHDRAW_SIGNATURE_TYPE(1),
    WITHDRAW_MULTISIG_TYPE(3),
    WITHDRAW_ESCROW_TYPE(6),;

    private byte _byte;

    WithdrawConditionType(int _byte) {
      this._byte = (byte) _byte;
    }
  }

  enum WithdrawBalanceType {
    WITHDRAW_COMMON_TYPE(0),
    WITHDRAW_CONTRACT_TYPE(1),
    WITHDRAW_MARGIN_TYPE(2),;

    private byte _byte;

    WithdrawBalanceType(int _byte) {
      this._byte = (byte) _byte;
    }
  }
}