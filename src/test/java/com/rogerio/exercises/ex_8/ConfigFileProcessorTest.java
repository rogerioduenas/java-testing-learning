package com.rogerio.exercises.ex_8;

import com.rogerio.exercises.ex_8.ConfigFileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigFileProcessorTest {

  private ConfigFileProcessor configFileProcessor;

  @BeforeEach
  void setUp() {
    configFileProcessor = new ConfigFileProcessor();
  }

  @Test
  @DisplayName("Should write processed file to temp directory when valid template and path provided")
  void given_validTemplateAndTempPath_when_generateConfigFile_then_writesProcessedFileToTempDirectory(@TempDir Path tempDir) throws IOException {

    Path path = tempDir.resolve("template.properties");

    configFileProcessor.generateConfigFile(path, "New app name");

    assertTrue(Files.exists(path));

    Properties prop = new Properties();
    try (InputStream input = Files.newInputStream(path)) {
      prop.load(input);
    }

    assertEquals("New app name", prop.getProperty("app.name"));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when output path is null")
  void given_nullPath_when_generateConfigFile_then_throwsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> configFileProcessor.generateConfigFile(null, "New app name")
    );

    assertEquals("Output path cannot be null", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"  ", "\t", "\n"})
  @NullAndEmptySource
  @DisplayName("Should throw IllegalArgumentException when app name is null or blank")
  void given_nullOrBlankAppName_when_generateConfigFile_then_throwsIllegalArgumentException(String invalidAppName, @TempDir Path tempDir) {
    Path path = tempDir.resolve("template.properties");
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> configFileProcessor.generateConfigFile(path, invalidAppName)
    );

    assertEquals("App name cannot be blank", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw IllegalStateException with cause when template resource is missing")
  void given_missingResourceTemplate_when_generateConfigFile_then_throwsIllegalStateException(@TempDir Path tempDir) {
    Path outputPath = tempDir.resolve("output.properties");
    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> configFileProcessor.generateConfigFile(outputPath, "MyApp", "non-existent-template.properties")
    );

    assertEquals("Template resource not found: non-existent-template.properties",
        exception.getCause().getMessage());
    assertEquals("Failed to process config file", exception.getMessage());
  }
}
