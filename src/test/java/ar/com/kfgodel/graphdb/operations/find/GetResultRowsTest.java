package ar.com.kfgodel.graphdb.operations.find;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.find.GetResultRows;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * This tests verifies the behavior of the get all nodes operation
 * Created by kfgodel on 25/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class GetResultRowsTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a get result rows operation", () -> {
      context().getResultRows(() -> GetResultRows.create("a query"));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("gets the result rows from the transaction", () -> {
          context().getResultRows().doWith(context().transaction());

          Mockito.verify(context().transaction()).getResultsFor("a query");
        });

      });
    });

  }
}