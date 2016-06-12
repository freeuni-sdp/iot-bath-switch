package ge.edu.freeuni.sdp.switches.bath_vent;

import ge.edu.freeuni.sdp.iot.switches.bath_vent.PingService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import static org.junit.Assert.*;

/**
 * Created by mi.maghriani on 6/11/2016.
 */
public class PingServiceTest extends JerseyTest{
    @Override
    protected Application configure() {
        return new ResourceConfig(PingService.class);
    }

    @Test
    public void get() throws Exception {
        Response response = target("ping").request().get();
        assertEquals(200, response.getStatus());
    }


}
