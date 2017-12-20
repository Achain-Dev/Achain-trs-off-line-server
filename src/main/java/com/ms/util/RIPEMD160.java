package com.ms.util;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;

public class RIPEMD160 {
  public static byte[] hash(byte[] orig) {
    return hash(orig, orig.length);
  }

  public static byte[] hash(byte[] orig, int length) {
    return hash(orig, 0, length);
  }

  public static byte[] hash(byte[] orig, int offset, int length) {
    RIPEMD160Digest d = new RIPEMD160Digest();
    d.update(orig, offset, length);
    byte[] hashed = new byte[d.getDigestSize()];
    d.doFinal(hashed, 0);
    return hashed;
  }
}
