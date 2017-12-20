package com.ms.blockchain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Node {
  private static List<Node> nodes = new ArrayList<>();

  private static void add(String host, Integer port) {
    nodes.add(new Node(host, port));
  }

  static {
    add("127.0.0.1", 61696);
    //add("47.91.234.145", 61696); // 794552977
    //add("47.91.243.208", 61696); // 794555344
  }

  private Socket socket;

  private Node(String host, Integer port) {
    try {
      socket = new Socket(host, port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void write(byte[] data) throws IOException {
    getOutputStream().write(data);
  }

  private byte[] read() throws IOException {
    try (ByteArrayOutputStream in = new ByteArrayOutputStream()) {
      while (getInputStream().available() != 0) {
        in.write(getInputStream().read());
      }
      return in.toByteArray();
    }
  }

  private void flush() throws IOException {
    socket.getOutputStream().flush();
  }

  private InputStream getInputStream() throws IOException {
    return socket.getInputStream();
  }

  private OutputStream getOutputStream() throws IOException {
    return socket.getOutputStream();
  }

  public static void sendMessage(TrxMessage trxMessage) {
    nodes.forEach(node -> {
      try {
        node.write(trxMessage.toBytes());
        node.flush();
        System.out.println(new String(node.read()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  private static char[] wrapData(char[] data) {
    char[] wrappedData = new char[16 * (data.length + 15) / 16];
    System.arraycopy(data, 0, wrappedData, 0, data.length);
    return wrappedData;
  }
}
