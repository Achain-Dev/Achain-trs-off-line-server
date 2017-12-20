package com.ms.util;

import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JSON {
  public static Builder build() {
    return new Builder();
  }

  public static class Builder {
    private JSONObject jsonObject = new JSONObject();

    public Builder add(String key, Object value) {
      jsonObject.put(key, value);
      return this;
    }

    public Builder add(String key, Date value) {
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      sf.setTimeZone(TimeZone.getTimeZone("GMT"));
      jsonObject.put(key, sf.format(value));
      return this;
    }

    public JSONObject get() {
      return jsonObject;
    }
  }
}
