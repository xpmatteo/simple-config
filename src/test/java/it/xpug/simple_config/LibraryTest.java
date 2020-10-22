/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package it.xpug.simple_config;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    @Test
    void testSomeLibraryMethod() throws Exception {
        SimpleConfig simpleConfig = new SimpleConfig().load(Paths.get(System.getProperty("user.dir"), "fixture", "application.properties"));

        assertThat(simpleConfig.get("foo.bar")).isEqualTo("baz");
    }
}
