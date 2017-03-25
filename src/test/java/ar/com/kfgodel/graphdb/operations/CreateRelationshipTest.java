package ar.com.kfgodel.graphdb.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.create.CreateRelationship;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;
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
        context().origin(() -> mockear(GraphNode.class));
        context().destination(() -> mockear(GraphNode.class));
        context().relationshipType(() -> "tipo de relacion");

        describe("when a transaction is provided", () -> {
          context().transaction(() -> mockear(GraphDbTransaction.class));

          it("creates a new relationship", () -> {
            Mockito.when(context().transaction().createRelationship(context().origin(), context().relationshipType(), context().destination()))
              .thenReturn(mockear(GraphRelationship.class));

            GraphRelationship createdRelationship = context().createRelationship().doWith(context().transaction());
            assertThat(createdRelationship).isNotNull();
          });
        });
      });
    });

  }
}