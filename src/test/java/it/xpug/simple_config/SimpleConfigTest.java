/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package it.xpug.simple_config;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleConfigTest {

    private static final Path APP_PROPERTIES = Paths.get(System.getProperty("user.dir"), "fixture", "application.properties");
    private SimpleConfig simpleConfig = new SimpleConfig();

    @Test
    void getAProperty() throws Exception {
        simpleConfig.load(APP_PROPERTIES);

        assertThat(simpleConfig.get("foo.bar")).isEqualTo("baz");
    }

    @Test
    void getWillNeverReturnNull() throws Exception {
        assertThatThrownBy(() -> simpleConfig.get("nonexistent")).hasMessage("No property \"nonexistent\" found");
    }

    @Test
    void defaultValues() throws Exception {
        simpleConfig.load(APP_PROPERTIES);

        assertThat(simpleConfig.get("foo.bar", "defaultValue")).isEqualTo("baz");
        assertThat(simpleConfig.get("A", "defaultValue")).isEqualTo("defaultValue");
        assertThat(simpleConfig.getInteger("foobar", 456)).isEqualTo(123);
        assertThat(simpleConfig.getInteger("A", 456)).isEqualTo(456);
    }

    @Test
    void getIntegerProperty() throws Exception {
        simpleConfig.load(APP_PROPERTIES);

        assertThat(simpleConfig.getInteger("fooBar")).isEqualTo(123);
    }

    @Test
    void loadFileRelativeToLocalDirectory() throws Exception {
        SimpleConfig.WORKING_DIRECTORY = System.getProperty("user.dir") + "/fixture";
        simpleConfig.load("application.properties");

        assertThat(simpleConfig.get("foo.bar")).isEqualTo("baz");
    }

    @Test
    void overridePropertiesWithEnvVariables() throws Exception {
        simpleConfig
                .load(APP_PROPERTIES)
                .load(Map.of("foo.bar", "aha"))
                ;

        assertThat(simpleConfig.get("foo.bar")).isEqualTo("aha");
    }

    @Test
    void convertEnvStyleToPropertiesStyle() throws Exception {
        simpleConfig.load(Map.of("FOO_BAR", "aha"));

        assertThat(simpleConfig.get("foo.bar")).isEqualTo("aha");
    }

    @Test
    void normalizeGetPropertyToo() throws Exception {
        simpleConfig.load(Map.of("foo_bar", "aha"));

        assertThat(simpleConfig.get("Foo.Bar")).isEqualTo("aha");
    }

    @Test
    void progressivelyRefinePropertySet() throws Exception {
        simpleConfig.load(Map.of("a", "b"));
        Function<ConfigSource, Map<String, String>> refinement = configSource -> Map.of("foobar", configSource.get("a"));
        simpleConfig.load(refinement);
    }
}