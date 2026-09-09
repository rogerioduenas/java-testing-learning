package article_24_template_directory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TempDirTest {

  @Test
  @DisplayName("Demonstrates creating and reading a file in a temporary JUnit directory")
  void shouldWriteAndReadFromFileInTempDirectory(@TempDir Path tempDir) throws IOException {
    //create a temporary directory
    Path filePath = tempDir.resolve("temporary-file.txt");

    Files.writeString(filePath, "Text");

    assertTrue(Files.exists(filePath));
    assertEquals("Text", Files.readString(filePath));
  }
}
