package ar.com.kfgodel.graphdb;

import org.mockito.Mockito;

/**
 * This type helps on reducing duplicated code
 * Created by kfgodel on 12/03/17.
 */
public class MockitoHelper {

  public static <T> T mockear(Class<T> clase) {
    return Mockito.mock(clase, Mockito.RETURNS_SMART_NULLS);
  }
}
