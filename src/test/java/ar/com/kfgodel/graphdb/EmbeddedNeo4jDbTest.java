package ar.com.kfgodel.graphdb;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import ar.com.kfgodel.graphdb.api.GraphDbException;
import ar.com.kfgodel.graphdb.api.operations.GraphDbTransaction;
import ar.com.kfgodel.graphdb.api.operations.TransactionOperation;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jConfiguration;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jDb;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of an embedded database
 * Created by kfgodel on 11/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class EmbeddedNeo4jDbTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("an embedded neo4j database", () -> {
      context().graphDb(() -> EmbeddedNeo4jDb.create(context().embeddedConfig()));

      describe("given a configuration", () -> {
        context().embeddedConfig(() -> EmbeddedNeo4jConfiguration.create().locatedIn(new File("testdb")));

        describe("when started", () -> {
          beforeEach(() -> {
            context().graphDb().start();
          });

          it("allows access to a graphdb transaction to access the database", () -> {
            Variable<GraphDbTransaction> variable = Variable.create();
            context().graphDb().ensureTransactionFor((transaction) -> {
              variable.set(transaction);
              return null;
            });
            assertThat(variable.get()).isNotNull();
          });

          afterEach(() -> {
            context().graphDb().stop();
          });
        });

        itThrows(GraphDbException.class, "if the database wasn't started", () -> {
          context().graphDb().ensureTransactionFor(Mockito.mock(TransactionOperation.class));
        }, e -> {
          assertThat(e).hasMessage("The database isn't started");
        });

      });

    });

  }
}