package ar.com.kfgodel.graphdb.operations.find;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.find.GetNodeById;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * Esta clase verifica que la busqueda de nodo por id funcione como se espera
 * Created by kfgodel on 01/04/17.
 */
@RunWith(JavaSpecRunner.class)
public class GetNodeByIdTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a get node by id operation", () -> {
      context().getNodeById(() -> GetNodeById.create(20));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("gets the node using the id from the transaction", () -> {
          context().getNodeById().doWith(context().transaction());

          Mockito.verify(context().transaction()).getNodeById(20);
        });

      });
    });
  }
}
