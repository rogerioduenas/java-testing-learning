package article_10_mockito_using_extendWith;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeforeEachParameterTest {

  private DatabaseService databaseService;

  @BeforeEach
  public void setup(@Mock DatabaseService dbService) {
    this.databaseService = dbService;
    when(databaseService.isConnected()).thenReturn(true);
  }

  @Test
  void testBeforeEach() {
    assertTrue(databaseService.isConnected());
  }
}
