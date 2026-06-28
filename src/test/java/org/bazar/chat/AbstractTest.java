package org.bazar.chat;

import static org.bazar.chat.TestDataTransformUtil.readFileWithoutThrow;
import static org.bazar.chat.TestDataTransformUtil.writeValueAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTest {
    protected void assertEqualsToFile(Object value, String fileName) {
        String expected = readFileWithoutThrow(fileName);
        String actual = writeValueAsString(value);
        assertEquals(expected, actual);
    }
}
