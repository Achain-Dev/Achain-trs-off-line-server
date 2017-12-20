package com.ms.blockchain;

import com.ms.util.MyByte;

public class HelloMessage {
  private int size;
  private int msgType;
  private byte[] data;

  private byte[] bytes;

  //
  private static byte[] userAgent = "act_client".getBytes();
  private static int coreProtocolVersion = 106;

  public HelloMessage() {
    data = MyByte.builder()
                 .copyVector(userAgent)
                 .copy(coreProtocolVersion)
                 .copy(169280039) // ip
                 .copy(SocketServer.PORT)
                 .copy(SocketServer.PORT)
                 .getData();
    size = data.length;
    msgType = 1000;
  }

  public byte[] toBytes() {
    return bytes;
  }
}
