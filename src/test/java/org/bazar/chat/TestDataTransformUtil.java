package org.bazar.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.core.util.Separators.Spacing.AFTER;
import static com.fasterxml.jackson.core.util.Separators.Spacing.NONE;

@UtilityClass
public class TestDataTransformUtil {
    public static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("MM-dd-yyyy"));
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter()
                .withSeparators(new Separators()
                        .withArrayValueSpacing(NONE)
                        .withObjectEntrySpacing(NONE)
                        .withObjectFieldValueSpacing(AFTER))
                .withArrayIndenter(new DefaultIndenter("  ", "\n"));
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static ObjectMapper getTestObjectMapper() {
        return objectMapper;
    }

    public static String readFileWithoutThrow(String fileName) {
        try {
            URL resource = TestDataTransformUtil.class.getResource(fileName);
            return Files.readString(Paths.get(resource.toURI()));
        } catch (Exception e) {
            return "";
        }
    }

    @SneakyThrows
    public static <T> T readObjectFromFile(String fileName, Class<T> clazz) {
        String valueAsString = readFileWithoutThrow(fileName);
        return objectMapper.readValue(valueAsString, clazz);
    }

    @SneakyThrows
    public static <T> T readObjectFromFile(String fileName, TypeReference<T> typeReference) {
        String valueAsString = readFileWithoutThrow(fileName);
        return objectMapper.readValue(valueAsString, typeReference);
    }

    @SneakyThrows
    public static String writeValueAsString(Object value) {
        return objectMapper.writeValueAsString(value);
    }
}
