package ar.com.kfgodel.graphdb.embedded;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.graphdb.api.concepts.GraphRelationship;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jTransaction;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jNode;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jRelationship;
import ar.com.kfgodel.graphdb.iteration.FakeResourceIterable;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;
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

        it("can delete a relationship", () -> {
          Relationship neo4jRelationship = mockear(Relationship.class);

          context().embeddedTransaction().removeRelationship(EmbeddedNeo4jRelationship.create(neo4jRelationship));

          Mockito.verify(neo4jRelationship).delete();
        });

        describe("properties", () -> {

          it("can set a property value on a node", () -> {
            Node neo4jNode = mockear(Node.class);

            context().embeddedTransaction().setPropertyOn(EmbeddedNeo4jNode.create(neo4jNode), "una property", "un valor");

            Mockito.verify(neo4jNode).setProperty("una property", "un valor");
          });
          it("can set a property value on a relationship", () -> {
            Relationship neo4jRelationship = mockear(Relationship.class);

            context().embeddedTransaction().setPropertyOn(EmbeddedNeo4jRelationship.create(neo4jRelationship), "una property", "un valor");

            Mockito.verify(neo4jRelationship).setProperty("una property", "un valor");
          });

          it("can remove a property from a node",()->{
            Node neo4jNode = mockear(Node.class);

            context().embeddedTransaction().removePropertyFrom(EmbeddedNeo4jNode.create(neo4jNode), "una property");

            Mockito.verify(neo4jNode).removeProperty("una property");
          });
          it("can remove a property from a relationship",()->{
            Relationship neo4jRelationship = mockear(Relationship.class);

            context().embeddedTransaction().removePropertyFrom(EmbeddedNeo4jRelationship.create(neo4jRelationship), "una property");

            Mockito.verify(neo4jRelationship).removeProperty("una property");
          });

          it("can get a property value from a node",()->{
            Node neo4jNode = mockear(Node.class);
            Mockito.when(neo4jNode.hasProperty("una property")).thenReturn(true);

            context().embeddedTransaction().getPropertyFrom(EmbeddedNeo4jNode.create(neo4jNode), "una property");

            Mockito.verify(neo4jNode).getProperty("una property");
          });
          it("gets an empty optional if the node doesn't have a property",()->{
            Node neo4jNode = mockear(Node.class);
            Mockito.when(neo4jNode.hasProperty("una property")).thenReturn(false);

            Optional<Object> result = context().embeddedTransaction().getPropertyFrom(EmbeddedNeo4jNode.create(neo4jNode), "una property");

            assertThat(result.isAbsent()).isTrue();
          });
          it("can get a property value from a relationship",()->{
            Relationship neo4jRelationship = mockear(Relationship.class);
            Mockito.when(neo4jRelationship.hasProperty("una property")).thenReturn(true);

            context().embeddedTransaction().getPropertyFrom(EmbeddedNeo4jRelationship.create(neo4jRelationship), "una property");

            Mockito.verify(neo4jRelationship).getProperty("una property");
          });
          it("gets an empty optional if the relationship doesn't have the property",()->{
            Relationship neo4jRelationship = mockear(Relationship.class);
            Mockito.when(neo4jRelationship.hasProperty("una property")).thenReturn(false);

            Optional<Object> result = context().embeddedTransaction().getPropertyFrom(EmbeddedNeo4jRelationship.create(neo4jRelationship), "una property");

            assertThat(result.isAbsent()).isTrue();
          });   
        });

        it("can retrieve all the nodes in the database",()->{
          Mockito.when(context().neo4jDb().getAllNodes())
            .thenReturn(FakeResourceIterable.create(mockear(Node.class)));

          Nary<GraphNode> allNodes = context().embeddedTransaction().getAllNodes();
          assertThat(allNodes.count()).isEqualTo(1);

          Mockito.verify(context().neo4jDb()).getAllNodes();
        });

        it("can retrieve all the nodes in the database",()->{
          Mockito.when(context().neo4jDb().getAllRelationships())
            .thenReturn(FakeResourceIterable.create(mockear(Relationship.class)));

          Nary<GraphRelationship> allRelationships = context().embeddedTransaction().getAllRelationships();
          assertThat(allRelationships.count()).isEqualTo(1);

          Mockito.verify(context().neo4jDb()).getAllRelationships();
        });
      });
    });
  }
}