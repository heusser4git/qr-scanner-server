package ch.ibw.appl.restserver.item.shared.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonSerializer {
    public String serialize(Object object) {
        if(object == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserialize(String content, TypeReference<T> ref) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(content, ref);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
