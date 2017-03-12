package ar.com.kfgodel.graphdb.impl;

import ar.com.kfgodel.graphdb.api.GraphDbConfiguration;

import java.io.File;

/**
 * This type represents a configuration for an embedded neo4j database instance
 * <p>
 * Created by kfgodel on 11/03/17.
 */
public class EmbeddedNeo4jConfiguration implements GraphDbConfiguration {

  private File storageDir;
  private String pageCacheSize;

  public static EmbeddedNeo4jConfiguration create() {
    EmbeddedNeo4jConfiguration configuration = new EmbeddedNeo4jConfiguration();
    configuration.storageDir = new File("neodb");
    configuration.pageCacheSize = "512M"; // Taken from https://neo4j.com/docs/java-reference/current/#tutorials-java-embedded-setup-config
    return configuration;
  }

  public EmbeddedNeo4jConfiguration locatedIn(File storageDirectory) {
    this.storageDir = storageDirectory;
    return this;
  }

  public File getStorageDir() {
    return storageDir;
  }

  public String getPageCacheMemory() {
    return pageCacheSize;
  }

  public EmbeddedNeo4jConfiguration withPageCacheSize(String newSize) {
    this.pageCacheSize = newSize;
    return this;
  }
}
