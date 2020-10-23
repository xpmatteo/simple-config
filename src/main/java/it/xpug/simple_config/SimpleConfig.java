package it.xpug.simple_config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

public class SimpleConfig implements ConfigSource {
    static String WORKING_DIRECTORY = System.getProperty("user.dir");
    private Map<String, String> map = new HashMap<>();

    @Override
    public String get(String propertyName) {
        String formattedKey = formatKey(propertyName);
        if (!map.containsKey(formattedKey)) {
            throw new SimpleConfig.PropertyNotFound(formattedKey);
        }
        return map.get(formattedKey);
    }

    @Override
    public String get(String propertyName, String defaultValue) {
        String formattedKey = formatKey(propertyName);
        if (!map.containsKey(formattedKey)) {
            return defaultValue;
        }
        return map.get(formattedKey);
    }

    @Override
    public Integer getInteger(String propertyName) {
        return Integer.valueOf(get(propertyName));
    }

    @Override
    public Integer getInteger(String propertyName, int defaultValue) {
        String formattedKey = formatKey(propertyName);
        if (!map.containsKey(formattedKey)) {
            return defaultValue;
        }
        return getInteger(formattedKey);
    }

    public SimpleConfig load(String localPath) throws IOException {
        return load(Paths.get(WORKING_DIRECTORY, localPath));
    }

    public SimpleConfig load(Path path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
        loadProperties(reader);
        return this;
    }

    public SimpleConfig load(Map<String, String> map) {
        map.entrySet().stream().forEach(entry -> {
            this.map.put(formatKey(entry.getKey()), entry.getValue());
        });
        return this;
    }

    public SimpleConfig load(Function<ConfigSource, Map<String, String>> refinement) {
        load(refinement.apply(this));
        return this;
    }

    public SimpleConfig loadFromClasspath(String path) throws IOException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(path));
        loadProperties(reader);
        return this;
    }

    private String formatKey(String key) {
        return key.toLowerCase(Locale.getDefault()).replaceAll("_", ".");
    }

    private void loadProperties(Reader reader) throws IOException {
        Properties properties = new Properties();
        properties.load(reader);
        properties.forEach((key, value) -> map.put(formatKey((String) key), (String) value));
    }

    public static class PropertyNotFound extends RuntimeException {
        public PropertyNotFound(String formattedKey) {
            super(String.format("No property \"%s\" found", formattedKey));
        }
    }
}
