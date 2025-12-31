package com.group4.homestay.ultil;

import java.io.BufferedReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    /**
     * Reads the request body and returns it as a JSON string.
     *
     * @param request the {@link HttpServletRequest} object containing the HTTP request data
     * @return a {@link String} representing the request body in JSON format
     * @throws RuntimeException if an I/O error occurs while reading from the request
     */
    public static String getParamJson(HttpServletRequest request) {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonBuilder.toString();
    }

    /**
     * Sends a JSON response to the client through the HttpServletResponse.
     * 
     * This method converts the provided object to a JSON string and writes it
     * to the response output stream with appropriate content type and character encoding.
     * 
     * @param response the HttpServletResponse object used to send the response to the client
     * @param obj the object to be converted to JSON and sent as response
     * 
     * @throws RuntimeException if an exception occurs during JSON conversion or writing to response
     */
    public static void responseJson(HttpServletResponse response, Object obj) {
        try {
            String json = toJson(obj);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
