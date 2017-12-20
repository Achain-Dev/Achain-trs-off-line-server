package com.ms.util;

import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES {

  private static final Charset STRING_CHARSET;

  static {
    STRING_CHARSET = Charset.forName("UTF-8");
  }

  private static class AESCipher {

    private Cipher cipher;

    AESCipher(int mode, byte[] key, byte[] iv) {
      try {
        cipher = Cipher.getInstance("AES/CBC/NoPadding", new BouncyCastleProvider());
        cipher.init(mode, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    synchronized byte[] doFinal(byte[] content) {
      try {
        return cipher.doFinal(content);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static Encoder createEncoder(byte[] key, byte[] iv) {
    return new Encoder(key, iv);
  }

  public static Decoder createDecoder(byte[] key, byte[] iv) {
    return new Decoder(key, iv);
  }

  public static class Encoder extends AESCipher {

    private Encoder(byte[] key, byte[] iv) {
      super(Cipher.ENCRYPT_MODE, key, iv);
    }

    public byte[] encode(byte[] content) {
      return doFinal(padding(content));
    }

    public byte[] encode(String content) {
      return encode(content.getBytes(STRING_CHARSET));
    }
  }

  private static byte[] padding(byte[] content) {
    return MyByte.builder()
                 .copy(content)
                 .copy(0, 16 - content.length % 16)
                 .getData();
  }


  public static class Decoder extends AESCipher {

    private Decoder(byte[] key, byte[] iv) {
      super(Cipher.DECRYPT_MODE, key, iv);
    }

    public byte[] decode(byte[] content) {
      return MyByte.trim(doFinal(content));
    }

    public String decodeToString(byte[] content) {
      return new String(decode(content), STRING_CHARSET);
    }
  }
}
