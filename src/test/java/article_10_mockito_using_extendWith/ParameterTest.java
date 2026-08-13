package article_10_mockito_using_extendWith;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParameterTest {

  @Test
  void testParameter(@Mock DatabaseService dbService){
    when(dbService.isConnected()).thenReturn(true);
    assertTrue(dbService.isConnected());
  }
}
