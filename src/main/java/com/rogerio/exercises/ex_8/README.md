### 🚀 EXERCISE 08 — Configuration File Processor and I/O Export (`ConfigFileProcessor`)

#### 1. 🎯 Focus & Techniques to Practice

- Reading a project resource file from the classpath using `ClassLoader` / `getResourceAsStream`.
- Writing to and verifying temporary files on disk using the **`@TempDir Path tempDir`** annotation from JUnit 5.
- Testing legacy file manipulation (I/O) without polluting the operating system's real file system.

#### 2. 📄 Description and Business Rules

The `ConfigFileProcessor` reads a template configuration file from the *classpath* (e.g., `resources/template.properties`), replaces internal variables, and writes the final result to an output file on the file system.

- **Business Rules:**
    - The destination path (`Path outputPath`) and replacement key (`appName`) must not be null or blank.
    - The service reads a fictional template file from the classpath called `"template.properties"` containing the text `app.name=${APP_NAME}`.
    - It must replace the `${APP_NAME}` tag with the actual value provided in the `appName` parameter and write the resulting line to the file specified by `outputPath`.
    - If the template does not exist on the classpath or the output path is invalid, it throws `IllegalStateException`.
    - The test must use **`@TempDir`** to provide an isolated temporary directory, perform the file write, and read the generated file using `Files.readAllLines(...)` to verify its contents.

#### 3. 💻 Legacy Code (Copy into the IDE)

Create a resource file for the test at `src/test/resources/template.properties` containing the following line:

Properties

```properties
app.name=${APP_NAME}
```

Add the following production code:

Java

```java
public class ConfigFileProcessor {

  public void generateConfigFile(Path outputPath, String appName) {
    // LEGACY: Does not validate paths or appName, and writes incorrect static content!
    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream("template.properties");
      if (is == null) {
        throw new IllegalStateException("Template resource not found");
      }
      
      String content = new String(is.readAllBytes());
      String result = content.replace("${APP_NAME}", appName);

      Files.writeString(outputPath, result, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to process config file", e);
    }
  }
}
```