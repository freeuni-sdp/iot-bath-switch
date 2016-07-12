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

        /* Take home data, where we'll find home identified by houseid */
        HomeData homeData = HomeData.getInstance();

        Home home = homeData.getHome(houseid);

        /* if nothing was found, that return 404 error code */
        if(home == null) {
            SwitchResponse response = new SwitchResponse(houseid, null, false);
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }


        Client client = ClientBuilder.newClient();

        String url = home.getVentUrl() + "webapi/bath/vent-switch";


        /* try to read current switch state. */
        Response simulatorResponse =
                client.target(url)
                        .request(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .get();

        if(simulatorResponse.getStatus() == Response.Status.SERVICE_UNAVAILABLE.getStatusCode()) {
            /* if connection was failed, return result, according to last update. */
            VentSwitch ventSwitch = home.getVentSwitch();
            String status =  ventSwitch.getStatus();

            SwitchResponse response = new SwitchResponse(houseid, status, false);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(response).build();
        }

        String st = simulatorResponse.readEntity(String.class);

        System.out.println(st + " response");
        JSONObject jsonResponse = new JSONObject(st);

        SwitchResponse switchResponse = new SwitchResponse(jsonResponse.getString("houseid"),
                                                           jsonResponse.getString("status"),
                                                           true);

        /* keep current current response, got from simulator, in homeData. */
        home.setVentSwitch(new VentSwitch(switchResponse.getHouseid(), switchResponse.getStatus()));

        return Response.status(Response.Status.OK).entity(switchResponse).build();
    }

}
