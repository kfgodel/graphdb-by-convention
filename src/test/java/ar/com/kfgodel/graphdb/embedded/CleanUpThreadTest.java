package ar.com.kfgodel.graphdb.embedded;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.GraphDb;
import ar.com.kfgodel.graphdb.impl.CleanUpThread;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

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
        context().graphDb(() -> mockear(GraphDb.class));

        it("stops the database when ran", () -> {
          context().cleanupThread().run();
          Mockito.verify(context().graphDb()).stop();
        });
      });
    });

  }
}