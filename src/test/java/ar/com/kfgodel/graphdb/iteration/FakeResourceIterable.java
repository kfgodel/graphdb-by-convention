package ar.com.kfgodel.graphdb.iteration;

import org.assertj.core.util.Lists;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.ResourceIterator;

import java.util.List;

/**
 * This type allows testing with mocked results
 * Created by kfgodel on 25/03/17.
 */
public class FakeResourceIterable<T> implements ResourceIterable<T> {

  private List<T> resultados;

  @Override
  public ResourceIterator<T> iterator() {
    return FakeResourceIterator.create(resultados.iterator());
  }

  public static<T> FakeResourceIterable<T> create(T... results) {
    FakeResourceIterable<T> fakeResourceIterable = new FakeResourceIterable<>();
    fakeResourceIterable.resultados = Lists.newArrayList(results);
    return fakeResourceIterable;
  }

}
