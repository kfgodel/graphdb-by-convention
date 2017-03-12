package ar.com.kfgodel.graphdb;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jTransaction;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jNode;
import com.google.common.collect.Lists;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.neo4j.graphdb.*;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of an abstracted transaction
 * Created by kfgodel on 11/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class EmbeddedNeo4jTransactionTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("an embedded neo4j graphdb transaction", () -> {
      context().embeddedTransaction(() -> EmbeddedNeo4jTransaction.create(context().neo4jDb(), context().neo4jTransaction()));

      describe("given an internal neo4j transaction and a neo4j database", () -> {
        context().neo4jTransaction(() -> mockear(Transaction.class));
        context().neo4jDb(() -> mockear(GraphDatabaseService.class));

        it("marks the transaction as success when committed", () -> {
          context().embeddedTransaction().commit();
          Mockito.verify(context().neo4jTransaction()).success();
        });

        it("creates a new node when asked", () -> {
          GraphNode node = context().embeddedTransaction().createNode(Lists.newArrayList());
          assertThat(node).isNotNull();
          Mockito.verify(context().neo4jDb()).createNode(new Label[0]);
        });

        describe("when asked to create a relationship", () -> {
          context().origin(() -> EmbeddedNeo4jNode.create(context().neo4jOrigin()));
          context().destination(() -> EmbeddedNeo4jNode.create(context().neo4jDestination()));
          context().relationshipType(() -> "un tipo de relacion");

          context().neo4jOrigin(() -> mockear(Node.class));
          context().neo4jDestination(() -> mockear(Node.class));

          it("creates a new relationship", () -> {
            GraphRelationship relationship = context().embeddedTransaction().createRelationship(context().origin(), context().relationshipType(), context().destination());
            assertThat(relationship).isNotNull();
            Mockito.verify(context().neo4jOrigin()).createRelationshipTo(context().neo4jDestination(), RelationshipType.withName("un tipo de relacion"));
          });
        });

        it("can delete a node", () -> {
          Node neo4jNode = mockear(Node.class);

          context().embeddedTransaction().removeNode(EmbeddedNeo4jNode.create(neo4jNode));

          Mockito.verify(neo4jNode).delete();
        });
      });

    });

  }
}