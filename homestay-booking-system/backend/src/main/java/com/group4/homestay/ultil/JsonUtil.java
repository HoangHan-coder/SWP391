package com.group4.homestay.ultil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {
    private static final  ObjectMapper mapper = new ObjectMapper();

    static {
        // QUAN TRỌNG: Đăng ký module để xử lý LocalDateTime
        mapper.registerModule(new JavaTimeModule());

        // Cấu hình để ngày tháng xuất ra dạng String ISO-8601 (VD: "2023-12-05T15:30:00")
        // Nếu không có dòng này, nó sẽ ra dạng mảng số [2023, 12, 5, 15, 30]
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toModel(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
