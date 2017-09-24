package de.geekinbusiness;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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

    @Inject
    Randomizer rand;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "faces-arquillian.war")
                .addClass(Randomizer.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
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
