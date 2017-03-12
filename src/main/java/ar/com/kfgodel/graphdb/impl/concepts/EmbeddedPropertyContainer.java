package ar.com.kfgodel.graphdb.impl.concepts;

import ar.com.kfgodel.graphdb.api.concepts.PropertyContainer;

/**
 * This type represents the common interface as a property container for embedded concepts
 * Created by kfgodel on 12/03/17.
 */
public interface EmbeddedPropertyContainer extends PropertyContainer {

  /**
   * @return The embdedded neo4j property container
   */
  org.neo4j.graphdb.PropertyContainer getNeo4jContainer();
}
