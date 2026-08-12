package article_08_dynamic_tests;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicTestSuiteComposerTest {

  @TestFactory
  Stream<DynamicTest> dynamicTestsForEmployeeWorkflows() {
    List<Employee> employees = List.of(
        new Employee(1, "Fred"),
        new Employee(2, null),
        new Employee(3, "John")
    );
    EmployeeDao dao = new EmployeeDao();

    Stream<DynamicTest> saveTests = employees.stream()
        .map(emp -> DynamicTest.dynamicTest(
            "saveEmployee: " + emp.id(),
            () -> assertEquals(emp.id(), dao.save(emp.id()).id())
        ));

    Stream<DynamicTest> saveWithNameTests = employees.stream()
        .filter(emp -> emp.firstName() != null)
        .map(emp -> DynamicTest.dynamicTest(
            "saveEmployeeWithName: " + emp.firstName(),
            () -> assertEquals(emp.firstName(), dao.save(emp.id(), emp.firstName()).firstName())
        ));

    return Stream.concat(saveTests, saveWithNameTests);
  }

  private record Employee(long id, String firstName) {}

  private static class EmployeeDao {
    public Employee save(long id) { return new Employee(id, null); }
    public Employee save(long id, String firstName) { return new Employee(id, firstName); }
  }
}
