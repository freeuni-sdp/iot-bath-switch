package ge.edu.freeuni.sdp.iot.switches.bath_vent.service;

import ge.edu.freeuni.sdp.iot.switches.bath_vent.data.HomeData;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.Home;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.SwitchResponse;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.VentSwitch;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by khrak on 6/25/16.
 */

@Path("/houses/{house_id}")
@Produces( { MediaType.APPLICATION_JSON})
public class BathVentStatusService {
    @GET
    public Response get(@PathParam("house_id") String houseid) {

        HomeData homeData = HomeData.getInstance();

        Home home = homeData.getHome(houseid);

        if(home == null) {
            SwitchResponse response = new SwitchResponse(houseid, null, false);
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }


        Client client = ClientBuilder.newClient();

        String url = home.getVentUrl() + "webapi/bath/vent-switch";


        Response simulatorResponse =
                client.target(url)
                        .request(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .get();

        if(simulatorResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            SwitchResponse response = new SwitchResponse(houseid, null, false);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(response).build();
        }

        return simulatorResponse;
    }

}
