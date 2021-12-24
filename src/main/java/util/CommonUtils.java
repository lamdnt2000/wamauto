package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;

public class CommonUtils {
    public static String convertObjectToStringJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        finally {
            return json;
        }
    }

    public static HashMap<String, Object> convertResponseToHashMap(String response) throws JsonProcessingException {
        HashMap<String,Object> map = new ObjectMapper().readValue(response,HashMap.class);
        return map;
    }

    public static List<Object> convertObjectToList(Object object) throws JsonProcessingException {
        List<Object> calenderRewards = new ObjectMapper().readValue(convertObjectToStringJson(object), List.class);
        return calenderRewards;
    }

}
