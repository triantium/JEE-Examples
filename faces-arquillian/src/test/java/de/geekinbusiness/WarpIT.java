package de.geekinbusiness;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;


@RunWith(Arquillian.class)
@RunAsClient
public class WarpIT {

    @Drone
    WebDriver driver;

    @Deployment
    public static WebArchive createDeployment() {

        return RandomizerIT.createDeployment();

    }

    @Test
    public void testSimple() throws Exception {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.

        // And now use this to visit NetBeans
        driver.get("http://localhost:8088/faces-arquillian/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.netbeans.org");

        // Check the title of the page
        // Wait for the page to load, timeout after 10 seconds
        //Close the browser
        Thread.sleep(5000);
        driver.quit();
    }

}
