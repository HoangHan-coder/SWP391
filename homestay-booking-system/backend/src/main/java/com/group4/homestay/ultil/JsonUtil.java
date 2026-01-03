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

    /**
     * Converts an object to its JSON string representation.
     *
     * @param obj the object to be converted to JSON
     * @return a JSON string representation of the object
     * @throws RuntimeException if the object cannot be serialized to JSON
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a JSON string to an object of the specified class type.
     *
     * @param <T> the type of the object to be returned
     * @param json the JSON string to be converted
     * @param clazz the class type to convert the JSON string into
     * @return an instance of the specified class type with data populated from the JSON string
     * @throws RuntimeException if the JSON string cannot be processed or mapped to the specified class type
     */
    public static <T> T toModel(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
