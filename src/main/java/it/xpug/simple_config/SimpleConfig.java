package it.xpug.simple_config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class SimpleConfig {
    private Properties properties = new Properties();

    public SimpleConfig() {
    }

    public String get(String propertyName) {
        return (String) properties.get(propertyName);
    }

    public SimpleConfig load(Path path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
        properties.load(reader);
        return this;
    }

    public SimpleConfig load(Map<String, String> map) {
        return this;
    }
}
