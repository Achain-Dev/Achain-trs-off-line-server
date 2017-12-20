package com.ms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
  public static byte[] _512hash(byte[] orig) {
    return _hash(orig, "512");
  }

  public static byte[] _256hash(byte[] orig) {
    return _hash(orig, "256");
  }

  private static byte[] _hash(byte[] orig, String b) {
    try {
      return MessageDigest.getInstance("SHA-" + b).digest(orig);
    } catch (NoSuchAlgorithmException ignored) {
      throw new RuntimeException();
    }
  }
}
