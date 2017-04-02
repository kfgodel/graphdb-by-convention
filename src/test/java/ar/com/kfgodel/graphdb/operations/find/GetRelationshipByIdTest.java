package ar.com.kfgodel.graphdb.operations.find;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.find.GetRelationshipById;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;

/**
 * Esta clase verifica que la busqueda de nodo por id funcione como se espera
 * Created by kfgodel on 01/04/17.
 */
@RunWith(JavaSpecRunner.class)
public class GetRelationshipByIdTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a get relationship by id operation", () -> {
      context().getRelationshipById(() -> GetRelationshipById.create(30));

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        it("gets the relationship using the id from the transaction", () -> {
          context().getRelationshipById().doWith(context().transaction());

          Mockito.verify(context().transaction()).getRelationshipById(30);
        });

      });
    });
  }
}
