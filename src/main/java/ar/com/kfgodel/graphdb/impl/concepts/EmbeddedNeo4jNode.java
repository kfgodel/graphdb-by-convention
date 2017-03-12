package ar.com.kfgodel.graphdb.impl.concepts;

import ar.com.kfgodel.graphdb.api.concepts.GraphNode;
import ar.com.kfgodel.nary.api.Nary;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;

/**
 * This type represents a neo4j database node
 * Created by kfgodel on 12/03/17.
 */
public class EmbeddedNeo4jNode implements GraphNode, EmbeddedPropertyContainer {

  private Node neo4jNode;

  public Node getNeo4jNode() {
    return neo4jNode;
  }

  public static EmbeddedNeo4jNode create(Node neo4jNode) {
    EmbeddedNeo4jNode node = new EmbeddedNeo4jNode();
    node.neo4jNode = neo4jNode;
    return node;
  }

  @Override
  public Nary<String> getLabels() {
    return Nary.create(neo4jNode.getLabels())
      .mapNary(Label::name);
  }

  @Override
  public PropertyContainer getNeo4jContainer() {
    return getNeo4jNode();
  }
}
