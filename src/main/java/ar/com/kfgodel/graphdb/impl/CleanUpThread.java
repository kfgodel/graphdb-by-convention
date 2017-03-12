package ar.com.kfgodel.graphdb.impl;

import ar.com.kfgodel.graphdb.api.GraphDb;

/**
 * This type is used to ensure the database is correctly cleaned up on VM termination
 * Created by kfgodel on 11/03/17.
 */
public class CleanUpThread extends Thread {

  private GraphDb graphDb;

  @Override
  public void run() {
    graphDb.stop();
  }

  public static CleanUpThread create(GraphDb graphDb) {
    CleanUpThread thread = new CleanUpThread();
    thread.graphDb = graphDb;
    return thread;
  }

}
