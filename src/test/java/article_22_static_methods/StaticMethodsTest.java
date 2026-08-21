package article_22_static_methods;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StaticMethodsTest {

  //without arguments
  @Test
  void givenStaticMethodWithNoArgs_whenMocked_thenReturnsMockSuccessfully() {
    assertEquals("Company Name", JobService.getCompanyName());

    try (MockedStatic<JobService> utilities = Mockito.mockStatic(JobService.class)) {
      utilities.when(JobService::getCompanyName).thenReturn("New Company");

      assertEquals("New Company", JobService.getCompanyName());
    }

    assertEquals("Company Name", JobService.getCompanyName());
  }

  //with arguments
  @Test
  void givenStaticMethodWithArgs_whenMocked_thenReturnsMockSuccessfully() {
    assertEquals("Java", JobService.findJobByTitle("Java"));

    try (MockedStatic<JobService> utilities = Mockito.mockStatic(JobService.class)) {
      utilities.when(() -> JobService.findJobByTitle("Java"))
          .thenReturn("Java Developer");

      assertEquals("Java Developer", JobService.findJobByTitle("Java"));
    }

    assertEquals("Java", JobService.findJobByTitle("Java"));
  }
}
