package ar.com.kfgodel.graphdb.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.GraphDbTestContext;
import ar.com.kfgodel.graphdb.api.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.CreateNode;
import ar.com.kfgodel.graphdb.impl.concepts.EmbeddedNeo4jNode;
import com.google.common.collect.Lists;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;

import java.util.List;
import java.util.stream.Collectors;

import static ar.com.kfgodel.graphdb.MockitoHelper.mockear;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior for the creation action
 * Created by kfgodel on 12/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class CreateNodeTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("a node creation operation", () -> {
      context().createNode(CreateNode::create);

      describe("given a transaction", () -> {
        context().transaction(() -> mockear(GraphDbTransaction.class));

        describe("when executed", () -> {
          context().createdNode(() -> context().createNode().doWith(context().transaction()));
          context().nodeLabels(Lists::newArrayList);

          beforeEach(() -> {
            Node neo4jNode = mockearNodoNeo4j();
            Mockito
              .when(context().transaction().createNode(context().nodeLabels()))
              .thenReturn(EmbeddedNeo4jNode.create(neo4jNode));
          });

          it("creates a new node", () -> {
            assertThat(context().createdNode()).isNotNull();
          });

          describe("when labels are used", () -> {
            context().nodeLabels(() -> Lists.newArrayList("label1", "label2"));
            beforeEach(() -> {
              context().createNode().labeledWith(context().nodeLabels());
            });

            it("creates a node with the given labels", () -> {
              assertThat(context().createdNode().getLabels().collect(Collectors.toList())).isEqualTo(context().nodeLabels());
            });
          });
        });
      });
    });

  }

  private Node mockearNodoNeo4j() {
    Node neo4jNode = mockear(Node.class);

    Mockito.when(neo4jNode.getLabels())
      .then(invocation -> {
        List<Label> expectedLabels = context().nodeLabels().stream()
          .map(Label::label)
          .collect(Collectors.toList());
        return expectedLabels;
      });

    return neo4jNode;
  }
}