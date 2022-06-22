package de.geekinbusiness;

import java.io.File;
import java.time.Duration;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author manuel müller <manuel.mueller@geekinbusiness.de>
 */
@RunWith(Arquillian.class)
public class RandomizerIT {

    private static final String WEBAPP_SRC = "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "";

    @Drone
    WebDriver driver;

    @Inject
    Randomizer rand;

    @BeforeClass
    public static void beforeClass() {
    }

    @Deployment
    //    @OverProtocol("Servlet 3.0")
    public static WebArchive createDeployment() {

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "facesarquillian.war")
                .addClass(Randomizer.class);

        archive.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(WEBAPP_SRC).as(GenericArchive.class),
                "/", Filters.exclude(".*\\.xml"));
        //archive.addClass(FakeServlet.class)
        System.out.println(archive.toString(true));
        archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");//To activate CDI
        archive.addAsWebInfResource("WEB-INF/web.xml", "web.xml");
        archive.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
//doesn't Have Runtime dependencies
//        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
//                .importRuntimeDependencies().resolve().withTransitivity().asFile();
//        archive.addAsLibraries(files);
        archive.addAsWebInfResource("WEB-INF/faces-config.xml", "faces-config.xml");
        System.out.println("WebArchiv erstellt");
        System.out.println(archive.toString(true));
        return archive;

    }

    /**
     * Test of random method, of class Randomizer.
     */
    @Test
    public void testRandom() {
        String ra = rand.random();
    }

    @Test
    @RunAsClient
    public void testSimple() throws Exception {

        try {
            // And now use this to visit NetBeans
            driver.get("http://localhost:8088/facesarquillian/");

            // Check the title of the page
            // Wait for the page to load, timeout after 10 seconds
            Duration  timeout = Duration.ofSeconds(10);
            (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver d) {
                    return d.findElement(By.id("test")).getText().equals("I work");
                }
            });
        } catch (Exception ex) {
            throw ex;
        } finally {

            //Close the browser
            driver.quit();
        }
    }
}
