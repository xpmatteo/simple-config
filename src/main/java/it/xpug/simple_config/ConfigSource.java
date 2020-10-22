package it.xpug.simple_config;

public interface ConfigSource {
    String get(String propertyName);

    String get(String propertyName, String defaultValue);

    Integer getInteger(String propertyName);

    Integer getInteger(String propertyName, int defaultValue);
}
