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
        context().neo4jTransaction(() -> Mockito.mock(Transaction.class, Mockito.RETURNS_SMART_NULLS));
        context().neo4jDb(() -> Mockito.mock(GraphDatabaseService.class, Mockito.RETURNS_SMART_NULLS));

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

          context().neo4jOrigin(() -> Mockito.mock(Node.class, Mockito.RETURNS_SMART_NULLS));
          context().neo4jDestination(() -> Mockito.mock(Node.class, Mockito.RETURNS_SMART_NULLS));

          it("creates a new relationship", () -> {
            GraphRelationship relationship = context().embeddedTransaction().createRelationship(context().origin(), context().relationshipType(), context().destination());
            assertThat(relationship).isNotNull();
            Mockito.verify(context().neo4jOrigin()).createRelationshipTo(context().neo4jDestination(), RelationshipType.withName("un tipo de relacion"));
          });
        });

      });

    });

  }
}