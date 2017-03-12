package ar.com.kfgodel.graphdb;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.graphdb.impl.EmbeddedNeo4jConfiguration;
import org.junit.runner.RunWith;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies the behavoir for an embedded config
 * Created by kfgodel on 11/03/17.
 */
@RunWith(JavaSpecRunner.class)
public class EmbeddedNeo4jConfigurationTest extends JavaSpec<GraphDbTestContext> {
  @Override
  public void define() {
    describe("an embedded db configuration", () -> {
      context().embeddedConfig(EmbeddedNeo4jConfiguration::create);

      it("has a default storage dir for the database files", () -> {
        assertThat(context().embeddedConfig().getStorageDir()).isEqualTo(new File("neodb"));
      });

      it("has a default page cache size", () -> {
        assertThat(context().embeddedConfig().getPageCacheMemory()).isEqualTo("512M");
      });

      describe("with the default location overriden", () -> {
        beforeEach(() -> {
          context().embeddedConfig().locatedIn(new File("other"));
        });

        it("changes the storage dir", () -> {
          assertThat(context().embeddedConfig().getStorageDir()).isEqualTo(new File("other"));
        });
      });

      describe("with the default page cache size overriden", () -> {
        beforeEach(() -> {
          context().embeddedConfig().withPageCacheSize("1024M");
        });

        it("changes the page cache size", () -> {
          assertThat(context().embeddedConfig().getPageCacheMemory()).isEqualTo("1024M");
        });

      });


    });

  }
}