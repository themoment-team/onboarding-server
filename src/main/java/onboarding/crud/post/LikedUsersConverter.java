package onboarding.crud.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class LikedUsersConverter implements AttributeConverter<List<Long>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> longList) {
        if (longList == null || longList.isEmpty()) {
            return "";
        }
        return longList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {
        if (string == null || string.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(string.split(SPLIT_CHAR))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}
