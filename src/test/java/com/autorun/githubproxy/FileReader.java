package com.autorun.githubproxy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    private static final String FILE_RESOURCE_PATH = "C:/DEV/GithubProxy/src/test/java/com/autorun/githubproxy/json/";

    public static String readResponseFromFile(String fileName) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(FILE_RESOURCE_PATH + fileName));
        return new String(bytes);
    }
}
