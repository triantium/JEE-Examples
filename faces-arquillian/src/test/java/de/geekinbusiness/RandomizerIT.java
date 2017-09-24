package de.geekinbusiness;

import java.io.File;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author manuel müller <manuel.mueller@geekinbusiness.de>
 */
@RunWith(Arquillian.class)
public class RandomizerIT {

    private static final String WEBAPP_SRC = "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "";

    @Inject
    Randomizer rand;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "faces-arquillian.war")
                .addClass(Randomizer.class)
                .addClass(FakeServlet.class);
        archive.addAsWebInfResource(new StringAsset("<faces-config version=\"2.2\"/>"), "faces-config.xml");
//                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");

//        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
//                .importRuntimeDependencies().resolve().withTransitivity().asFile();
//
//        archive.addAsLibraries(files);
        archive.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                .importDirectory(WEBAPP_SRC).as(GenericArchive.class),
                "/", Filters.includeAll());

        System.out.println("WebArchiv erstellt");
        return archive;

    }

    @Before
    public void setUp() {

    }

    /**
     * Test of random method, of class Randomizer.
     */
    @Test
    public void testRandom() {
        String ra = rand.random();
    }

}
