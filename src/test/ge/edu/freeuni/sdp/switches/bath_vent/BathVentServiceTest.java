package ge.edu.freeuni.sdp.switches.bath_vent;


import ge.edu.freeuni.sdp.iot.switches.bath_vent.data.HomeData;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.Home;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.SwitchResponse;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.VentSwitch;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.service.BathVentSwitchService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nika on 7/11/16.
 */
public class BathVentServiceTest extends JerseyTest {

    @Mock private HomeData data;

    private String homeid;
    private String homeUrl = "https://iot-sim-bath.herokuapp.com/";

    @Override
    protected Application configure() {
        return new ResourceConfig(BathVentSwitchService.class);
    }

    @Before
    public void setUpChild() throws Exception {
        homeid = "3db0d8b8-b664-4152-892d-2434e7216643";

        MockitoAnnotations.initMocks(this);
        HomeData.setTestInstance(data);
    }

    @After
    public void tearDownChild() throws Exception {
        HomeData.setTestMode(false);
    }

    @Test
    public void getOkStatusCode200() throws Exception{

        Response response = target("/houses/" + homeid).request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getNotFoundStatusCode404() throws Exception{

        String nonExistingid = "not_existing_id";

        Response response = target("/houses/" + nonExistingid).request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getCheckHomeDataForgetHomeCalling() throws Exception{

        HomeData.setTestMode(true);
        String status = "on";

        Home home = new Home(homeid, homeUrl);

        when(data.getHome(homeid)).thenReturn(home);

        Response response = target("/houses/" + homeid).request().get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

        verify(data).getHome(homeid);
    }

    @Test
    public void postOkStatusCode200() throws Exception{

        int timeout = 10;

        String body = "{\n" +
                "    \"set_status\": \"off\",\n" +
                "    \"timeout\":" + timeout + "\n" +
                "}";

        Response response = target("/houses/" + homeid).request()
                .post(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void postNotFoundStatusCode404() throws Exception{

        int timeout = 10;


        String nonExistingid = "not_existing_id";

        String body = "{\n" +
                "    \"set_status\": \"off\",\n" +
                "    \"timeout\":" + timeout + "\n" +
                "}";

        Response response = target("/houses/" + nonExistingid).request()
                .post(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }

    @Test
    public void postCheckCallingGetHome() throws Exception{

        HomeData.setTestMode(true);
        int timeout = 10;

        String body = "{\n" +
                "    \"set_status\": \"off\",\n" +
                "    \"timeout\":" + timeout + "\n" +
                "}";

        Home home = new Home(homeid, homeUrl);

        when(data.getHome(homeid)).thenReturn(home);

        Response response = target("/houses/" + homeid).request()
                .post(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(data).getHome(homeid);
    }



    @Test
    public void postOnAndCheckOffAfterTimeout() throws Exception{

        int timeout = 10;

        String body = "{\n" +
                "    \"set_status\": \"on\",\n" +
                "    \"timeout\":" + timeout + "\n" +
                "}";

        Response response = target("/houses/" + homeid).request()
                .post(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        System.out.println("start sleeping ");
        Thread.sleep(2 * timeout * 1000);

        Response request = target("/houses/" + homeid).request().get();

        String st = request.readEntity(String.class);


        JSONObject json = new JSONObject(st);

        String status = json.getString("status");

        assert(status.equals("off"));

    }

}