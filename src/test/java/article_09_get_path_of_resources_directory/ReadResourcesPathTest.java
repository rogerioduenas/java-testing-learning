package article_09_get_path_of_resources_directory;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadResourcesPathTest {

  @Test
  void testReadResourcesPathUsingFileIO() {
    String path = "src/test/resources";

    File file = new File(path);
    String absolutePath = file.getAbsolutePath();

    System.out.println(absolutePath);

    assertTrue(absolutePath.endsWith("src/test/resources"));
  }

  @Test
  void testReadResourcesPathUsingPath() {
    Path resourceDirectory = Paths.get("src","test","resources");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();

    System.out.println(absolutePath);

    assertTrue(absolutePath.endsWith("src/test/resources"));
  }

  @Test
  void testReadResourcesPathUsingClassLoader() {
    String resourceName = "example_resource.txt";

    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(resourceName).getFile());
    String absolutePath = file.getAbsolutePath();

    System.out.println(absolutePath);

    assertTrue(absolutePath.endsWith("/example_resource.txt"));
  }
}
