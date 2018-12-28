package com.test.springboot.util;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BeanConverter {
    public BeanConverter() {
    }

    public static Map<String, Object> toMap(Object javaBean, boolean canNull) {
        if (javaBean instanceof Map) {
            return (Map)javaBean;
        } else {
            Map<String, Object> result = new HashMap<>();
            Method[] methods = javaBean.getClass().getMethods();
            Method[] var4 = methods;
            int var5 = methods.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method method = var4[var6];

                try {
                    if (method.getName().startsWith("get")) {
                        String field = method.getName();
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(javaBean, (Object[])null);
                        if (value instanceof String && com.mysql.jdbc.StringUtils.isNullOrEmpty((String)value) && !canNull) {
                            value = null;
                        }

                        if (value == null) {
                            if (canNull) {
                                result.put(field, (Object)null);
                            }
                        } else {
                            result.put(field, value);
                        }
                    }
                } catch (Exception var10) {
                    ;
                }
            }

            return result;
        }
    }

    public static Map<String, String> toMap(JSONObject jsonObject) {
        Map<String, String> result = new HashMap();
        Set<String> keySets = jsonObject.keySet();
        Iterator<String> iterator = keySets.iterator();
        String key = null;
        String value = null;

        while(iterator.hasNext()) {
            key = (String)iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }

        return result;
    }

    public static Object toJavaBean(Object javabean, Map<String, String> data) {
        Method[] methods = javabean.getClass().getDeclaredMethods();
        Method[] var3 = methods;
        int var4 = methods.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];

            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, data.get(field));
                }
            } catch (Exception var8) {
                ;
            }
        }

        return javabean;
    }

    public static void toJavaBean(Object javabean, String data) throws ParseException {
        JSONObject jsonObject = JSONObject.parseObject(data);
        Map<String, String> datas = toMap(jsonObject);
        toJavaBean(javabean, datas);
    }
}
