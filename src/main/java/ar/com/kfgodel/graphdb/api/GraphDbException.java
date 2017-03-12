package ar.com.kfgodel.graphdb.api;

/**
 * This type represents an error using the database
 * Created by kfgodel on 11/03/17.
 */
public class GraphDbException extends RuntimeException {
  public GraphDbException(String message) {
    super(message);
  }

  public GraphDbException(String message, Throwable cause) {
    super(message, cause);
  }

  public GraphDbException(Throwable cause) {
    super(cause);
  }
}
