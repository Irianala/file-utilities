package com.irianala.fileutilities;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class FileContentProcessorTest {

    @Test
    void createInstanceTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var constructor = FileContentProcessor.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        var instance = constructor.newInstance();

        assertNotNull(instance);
    }

    @Test
    void processTest() throws IOException {
        var resourcesFolderPath = new File("src/test/resources").getAbsolutePath();

        var inputPath = resourcesFolderPath.concat("/data/input/in");
        var outputPath = resourcesFolderPath.concat("/data/output/out");

        FileContentProcessor.process(inputPath, outputPath, String::toUpperCase);


        var inputBufferedReader = new BufferedReader(new FileReader(inputPath));
        String input;
        List<String> inputContent = new ArrayList<>();
        while (Objects.nonNull(input = inputBufferedReader.readLine())) {
            inputContent.add(input.toUpperCase());
        }

        String output;
        int index = 0;
        var outputBufferedReader = new BufferedReader(new FileReader(outputPath));
        while (Objects.nonNull(output = outputBufferedReader.readLine())) {
            assertEquals(inputContent.get(index), output);
            index++;
        }

        assertEquals(inputContent.size(), index);
    }

    @Test
    void processThrowsExceptionTest() {
        assertThrows(
                IOException.class,
                () -> FileContentProcessor.process("empty", "empty", String::toUpperCase)
        );
    }
}
