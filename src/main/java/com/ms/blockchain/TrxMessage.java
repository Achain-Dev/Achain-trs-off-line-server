package com.ms.blockchain;

import com.ms.util.MyByte;

public class TrxMessage {
  private int size;
  private int msgType;
  private byte[] data;

  private byte[] bytes;

  public TrxMessage(byte[] data) {
    this.size = data.length;
    this.msgType = 1000;
    this.data = data;
  }

  public byte[] toBytes() {
    if (bytes == null) {
      bytes = MyByte.builder()
                    .copy(size)
                    .copy(msgType)
                    .copy(data)
                    .getData();
    }
    return bytes;
  }
}
