package ar.com.kfgodel.graphdb;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.api.GraphDb;
import ar.com.kfgodel.graphdb.impl.CleanUpThread;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;

/**
 * This type verifies that the cleanup thread behaves as expected when run
 * Created by kfgodel on 11/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class CleanUpThreadTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a cleanup thread", () -> {
      context().cleanupThread(() -> CleanUpThread.create(context().graphDb()));

      describe("given a graph database", () -> {
        context().graphDb(() -> mock(GraphDb.class, RETURNS_SMART_NULLS));

        it("stops the database when ran", () -> {
          context().cleanupThread().run();
          Mockito.verify(context().graphDb()).stop();
        });
      });
    });

  }
}