package com.test.springboot.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class Method {
    public Method() {
    }

    public static String getQuery(Map<String, ?> params) {
        String query = "";
        if (params == null) {
            return query;
        } else {
            Iterator iter = params.keySet().iterator();

            while(iter.hasNext()) {
                String key = (String)iter.next();
                if (!key.equals("page")) {
                    String value = getArray(params, key, (String)null);
                    if (null != value && !"".equals(value)) {
                        try {
                            query = query + "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
                        } catch (UnsupportedEncodingException var6) {
                            var6.printStackTrace();
                        }
                    }
                }
            }

            if (query.length() > 1) {
                query = query.substring(1);
            }

            return query;
        }
    }

    public static String getArray(Map<String, ?> params, String key, String defaultValue) {
        if (params == null) {
            return defaultValue;
        } else if (params.get(key) == null) {
            return defaultValue;
        } else {
            Object o = params.get(key);

            try {
                String[] values = (String[])((String[])o);
                return values[0];
            } catch (Exception var6) {
                try {
                    return String.valueOf(o);
                } catch (ClassCastException var5) {
                    return defaultValue;
                }
            }
        }
    }
}
