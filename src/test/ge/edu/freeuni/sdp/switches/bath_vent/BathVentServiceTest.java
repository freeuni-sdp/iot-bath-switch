package ge.edu.freeuni.sdp.switches.bath_vent;


import ge.edu.freeuni.sdp.iot.switches.bath_vent.data.HomeData;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.Home;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.SwitchResponse;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.VentSwitch;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.service.BathVentStatusService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import javax.ws.rs.core.Application;
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
        return new ResourceConfig(BathVentStatusService.class);
    }

    @Before
    public void setUpChild() throws Exception {
        homeid = "3c5afb74-2e82-4f10-9931-89187fe47adf";

        MockitoAnnotations.initMocks(this);
        HomeData.setTestInstance(data);
        HomeData.setTestMode(true);
    }

    @After
    public void tearDownChild() throws Exception {

    }

    @Test
    public void getOkStatusCode200() throws Exception{

        String status = "on";

        Home home = new Home(homeid, homeUrl);

        when(data.getHome(homeid)).thenReturn(home);

        Response response = target("/houses/" + homeid).request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getNotFoundStatusCode404() throws Exception{

        String status = "on";

        String nonExistingid = "not_existing_id";
        Home home = new Home(nonExistingid, homeUrl);

        when(data.getHome(homeid)).thenReturn(null);

        Response response = target("/houses/" + homeid).request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void getCheckHomeDataForgetHomeCalling() throws Exception{

        String status = "on";

        Home home = new Home(homeid, homeUrl);

        when(data.getHome(homeid)).thenReturn(home);

        Response response = target("/houses/" + homeid).request().get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

        verify(data).getHome(homeid);
    }

}