package com.ms.data;

import com.alibaba.fastjson.JSONObject;
import com.ms.util.JSON;
import com.ms.util.MyByte;

public class Asset {
  private long amount; // c++ __int64 8bit
  private int assetId; // c++ int 4bit
  //
  private byte[] toBytes;
  private JSONObject json;

  public byte[] toBytes() {
    if (toBytes == null) {
      toBytes = MyByte.builder()
                      .copy(amount)
                      .copySize(assetId)
                      .getData();
    }
    return toBytes;
  }

  public JSONObject toJSON() {
    if (json == null) {
      json = JSON.build()
                 .add("amount", amount)
                 .add("asset_id", assetId)
                 .get();
    }
    return json;
  }

  public Asset(long amount) {
    this.amount = amount;
    this.assetId = 0; // ACT assetId
  }

  public long getAmount() {
    return amount;
  }

  public int getAssetId() {
    return assetId;
  }
}
