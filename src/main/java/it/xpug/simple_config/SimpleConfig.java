package it.xpug.simple_config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class SimpleConfig {
    static String WORKING_DIRECTORY = System.getProperty("user.dir");
    private Map<String, String> map = new HashMap<>();

    public String get(String propertyName) {
        return map.get(formatKey(propertyName));
    }

    public SimpleConfig load(String localPath) throws IOException {
        return load(Paths.get(WORKING_DIRECTORY, localPath));
    }

    public SimpleConfig load(Path path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
        Properties properties = new Properties();
        properties.load(reader);
        properties.forEach((key, value) -> map.put(formatKey((String) key), (String) value));
        return this;
    }

    public SimpleConfig load(Map<String, String> map) {
        map.entrySet().stream().forEach(entry -> {
            this.map.put(formatKey(entry.getKey()), entry.getValue());
        });
        return this;
    }

    private String formatKey(String key) {
        return key.toLowerCase(Locale.getDefault()).replaceAll("_", ".");
    }

    public Integer getInteger(String propertyName) {
        return Integer.valueOf(get(propertyName));
    }
}
