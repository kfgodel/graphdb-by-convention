package ar.com.kfgodel.graphdb.api.concepts;

import ar.com.kfgodel.nary.api.Nary;

/**
 * This type represents a node of the graph
 * Created by kfgodel on 12/03/17.
 */
public interface GraphNode extends PropertyContainer {
  /**
   * @return El conjunto de labels que posee este nodo
   */
  Nary<String> getLabels();

  /**
   * @return Devuelve un id que permite identificar a este nodo (no se puede asegurar que sea constante a traves del tiempo)
   */
  long getId();
}
