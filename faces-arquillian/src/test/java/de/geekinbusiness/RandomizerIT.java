package de.geekinbusiness;

import java.io.File;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

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

//                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
//        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
//                .importRuntimeDependencies().resolve().withTransitivity().asFile();
//        archive.addAsLibraries(files);
        archive.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                .importDirectory(WEBAPP_SRC).as(GenericArchive.class),
                "/", Filters.includeAll());

        archive.addAsWebInfResource("WEB-INF/faces-config.xml", "faces-config.xml");
        System.out.println("WebArchiv erstellt");
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
    public void testSimple() throws Exception {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit NetBeans
        driver.get("http://localhost:8088/faces-arquillian/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.netbeans.org");

        // Check the title of the page
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("Kanban Board");
            }
        });

        //Close the browser
        driver.quit();
    }
}
