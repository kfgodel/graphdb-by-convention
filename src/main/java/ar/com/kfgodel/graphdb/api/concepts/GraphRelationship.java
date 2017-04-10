package ar.com.kfgodel.graphdb.api.concepts;

/**
 * This type represents a graph relationshipt between two nodes
 * Created by kfgodel on 12/03/17.
 */
public interface GraphRelationship extends PropertyContainer {

  /**
   * @return Devuelve el identificador temporal de esta relacion (no se asegura que sea constante en el tiempo)
   */
  long getId();

  /**
   * @return Devuelve el nodo origen de esta relacion
   */
  GraphNode getOrigin();

  /**
   * @return Devuelve el nombre de este tipo de relacion
   */
  String getRelationshipType();

  /**
   * @return Devuelve el nodo destino de esta relacion
   */
  GraphNode getDestination();
}
