/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengYi
 * @date 17-7-25
 * @since : Java 8
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonString(Object object) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
        }
        return jsonString;
    }

    public static Map<String, Object> getMapFromJson(String jsonString) {

        Map<String, Object> rtv = getObjFromJson(jsonString, Map.class);

        if (null == rtv) {
            rtv = new HashMap<>(0);
        }

        return rtv;
    }

    public static List getListFromJson(String jsonString) {
        List<String> rtv = getObjFromJson(jsonString, List.class);
        if (null == rtv) {
            rtv = new ArrayList();
        }
        return rtv;
    }

    private static <T> T getObjFromJson(String jsonString, Class<T> valueType) {
        T rtv = null;
        try {
            rtv = objectMapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return rtv;
    }
}
