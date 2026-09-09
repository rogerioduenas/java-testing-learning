package com.rogerio.exercises.ex_8;

import utils.Validate;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ConfigFileProcessor {

  public void generateConfigFile(Path outputPath, String appName) {
    generateConfigFile(outputPath, appName, "template.properties");
  }

  public void generateConfigFile(Path outputPath, String appName, String templateResourceName) {
    Validate.notNull(outputPath, "Output path cannot be null");
    Validate.notBlank(appName, "App name cannot be blank");

    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream(templateResourceName);
      if (is == null) {
        throw new IllegalStateException("Template resource not found: " + templateResourceName);
      }

      String content = new String(is.readAllBytes());
      String result = content.replace("${APP_NAME}", appName);

      Files.writeString(outputPath, result, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to process config file", e);
    }
  }
}
