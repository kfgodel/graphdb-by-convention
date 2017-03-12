package ar.com.kfgodel.graphdb.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.CreateRelationship;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the relationship creation behavior
 * Created by kfgodel on 12/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class CreateRelationshipTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a relationship creation operation", () -> {
      context().createRelationship(() -> CreateRelationship.create(context().origin(), context().relationshipType(), context().destination()));

      describe("given the nodes and the relationship type", () -> {
        context().origin(() -> Mockito.mock(GraphNode.class, Mockito.RETURNS_SMART_NULLS));
        context().destination(() -> Mockito.mock(GraphNode.class, Mockito.RETURNS_SMART_NULLS));
        context().relationshipType(() -> "tipo de relacion");

        describe("when a transaction is provided", () -> {
          context().transaction(() -> Mockito.mock(GraphDbTransaction.class, Mockito.RETURNS_SMART_NULLS));

          it("creates a new relationship", () -> {
            Mockito.when(context().transaction().createRelationship(context().origin(), context().relationshipType(), context().destination()))
              .thenReturn(Mockito.mock(GraphRelationship.class, Mockito.RETURNS_SMART_NULLS));

            GraphRelationship createdRelationship = context().createRelationship().doWith(context().transaction());
            assertThat(createdRelationship).isNotNull();
          });
        });
      });
    });

  }
}