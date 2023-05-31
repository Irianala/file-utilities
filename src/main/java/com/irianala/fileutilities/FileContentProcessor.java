package com.irianala.fileutilities;

import java.io.*;
import java.util.function.Function;


public class FileContentProcessor {

    private FileContentProcessor() { /* hide public constructor */}

    /**
     * Take one file and process its content to produce another file
     * @param fileInputPath path of the input file
     * @param fileOutputPath path where the output will be created
     * @param processor a function that will be applied to the content of the input file to produce a new file
     * @throws IOException when the input file is not present
     */
    public static void process(String fileInputPath, String fileOutputPath, Function<String, String> processor) throws IOException {
        var file = new File(fileInputPath);
        try(
                var inputStreamReader = new InputStreamReader(new FileInputStream(file));
                var fileWriter = new FileWriter(fileOutputPath)
        ) {
            var bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                fileWriter.write(processor.apply(line));
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
