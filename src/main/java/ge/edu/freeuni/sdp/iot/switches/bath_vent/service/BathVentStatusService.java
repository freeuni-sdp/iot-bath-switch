package ge.edu.freeuni.sdp.iot.switches.bath_vent.service;

import ge.edu.freeuni.sdp.iot.switches.bath_vent.data.HomeData;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.Home;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.SwitchResponse;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by khrak on 6/25/16.
 */

@Path("/houses/{house_id}")
@Consumes( { MediaType.APPLICATION_JSON})
@Produces( { MediaType.APPLICATION_JSON})
public class BathVentStatusService {
    @GET
    public SwitchResponse get(@PathParam("house_id") String houseid) {

        Client client = ClientBuilder.newClient();
        String houseID = "c637a8eb-5c1e-46fc-9de8-607df60df27a";
        String url = "https://2.2.2.4" +
                "/webapi/houses/" + houseID;

        Response response =
                client.target("")
                        .request(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .get();

        SwitchResponse switchResponse = response.readEntity(SwitchResponse.class);

        Home home = HomeData.getInstance().getHome(houseid);

        home.getVentSwitch().setStatus(switchResponse.getStatus());

        return switchResponse;
    }


}
