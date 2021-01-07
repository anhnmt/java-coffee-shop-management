package coffeeshop.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TUANANH-PC
 */
public class Common {

    // Lấy thời gian của hệ thống
    public static Long getTimeStamp() {
        return System.currentTimeMillis();
    }

    // Kiếm tra null hoặc rỗng
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return obj.toString().isEmpty();
        }

        if (obj instanceof List) {
            return ((List) obj).isEmpty();
        }

        if (obj instanceof Set) {
            return ((Set) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        return false;
    }

    // Kiểm tra là số nguyên
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    // Tạo message log
    public static String createMessageLog(Object input, Object response, String methodName) {
        StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        sb.append(getTimeStamp());

        try {
            if (!isNullOrEmpty(input)) {
                sb.append("_").append(objectMapper.writeValueAsString(input));
            }

            if (!isNullOrEmpty(methodName)) {
                sb.append("_").append(objectMapper.writeValueAsString(methodName));
            }

            if (!isNullOrEmpty(response)) {
                sb.append("_").append(objectMapper.writeValueAsString(response));
            }
        } catch (JsonProcessingException e) {
            e.getMessage();
        }

        return sb.toString();
    }
}
