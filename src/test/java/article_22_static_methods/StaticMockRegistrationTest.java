package article_22_static_methods;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class StaticMockRegistrationTest {

  private MockedStatic<JobService> mockStatic;

  @BeforeEach
  void setUp() {
    mockStatic = mockStatic(JobService.class);
  }

  @AfterEach
  void tearDown() {
    mockStatic.close();
  }

  @Test
  void givenStaticMockRegistration_whenMocked_thenReturnsMockSuccessfully() {
    mockStatic.when(() -> JobService.findJobByTitle("Java")).thenReturn("Beautiful");

    assertEquals("Beautiful", JobService.findJobByTitle("Java"));
  }

  @Test
  void givenAnotherStaticMockRegistration_whenMocked_thenReturnsMockSuccessfully() {
    mockStatic.when(() -> JobService.findJobByTitle("Node")).thenReturn("Ugly");

    assertEquals("Ugly", JobService.findJobByTitle("Node"));
  }
}
