/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package it.xpug.simple_config;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private static final Path APP_PROPERTIES = Paths.get(System.getProperty("user.dir"), "fixture", "application.properties");

    @Test
    void getAProperty() throws Exception {
        SimpleConfig simpleConfig = new SimpleConfig().load(APP_PROPERTIES);

        assertThat(simpleConfig.get("foo.bar")).isEqualTo("baz");
    }

    @Test
    void overridePropertiesWithEnvVariables() throws Exception {
        SimpleConfig simpleConfig = new SimpleConfig()
                .load(APP_PROPERTIES)
                .load(Map.of("FOO_BAR", "aha"))
                ;

        assertThat(simpleConfig.get("foo.bar")).isEqualTo("aha");
    }

}
