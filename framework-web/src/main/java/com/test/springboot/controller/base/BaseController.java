package com.test.springboot.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;


public class BaseController {
    private static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public JSONObject getJsonFromRequest() {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = request.getReader();
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } finally {
                reader.close();
            }
            LOGGER.info("参数：" + builder);
            if (StringUtils.isNullOrEmpty(builder.toString())) {
                builder.append("{}");
                //throw new RuntimeException("data empty");
            }
            return JSONObject.parseObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
