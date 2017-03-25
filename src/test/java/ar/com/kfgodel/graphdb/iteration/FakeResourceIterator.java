package ar.com.kfgodel.graphdb.iteration;

import org.neo4j.graphdb.ResourceIterator;

import java.util.Iterator;

/**
 * This type fakes a resource iterator to mock results
 * Created by kfgodel on 25/03/17.
 */
public class FakeResourceIterator<T> implements ResourceIterator<T> {

  private Iterator<T> iterador;

  @Override
  public void close() {
    // Nothing to close
  }

  @Override
  public boolean hasNext() {
    return iterador.hasNext();
  }

  @Override
  public T next() {
    return iterador.next();
  }

  public static<T> FakeResourceIterator<T> create(Iterator<T> iterador) {
    FakeResourceIterator<T> fakeResourceIterator = new FakeResourceIterator<>();
    fakeResourceIterator.iterador = iterador;
    return fakeResourceIterator;
  }

}
