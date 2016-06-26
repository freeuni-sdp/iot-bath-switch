package ge.edu.freeuni.sdp.iot.switches.bath_vent.service;

import ge.edu.freeuni.sdp.iot.switches.bath_vent.data.HomeData;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.Home;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.SwitchResponse;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by khrak on 6/25/16.
 */

@Path("/houses/{house_id}/action/{action_type}")
@Consumes( { MediaType.APPLICATION_JSON})
@Produces( { MediaType.APPLICATION_JSON})
public class BathVentSwitchService {

    @PUT
    public SwitchResponse put(@PathParam("house_id") String houseid,
                              @PathParam("action_type") String action) {

        Client client = ClientBuilder.newClient();
        String homeID = "c637a8eb-5c1e-46fc-9de8-607df60df27a";
        String url = "https://2.2.2.4" +
                "/webapi/houses/" + homeID;


        SwitchResponse switchResponse = new SwitchResponse(homeID,action, true);


        Response response =
                client.target("")
                        .request(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .put(Entity.json(switchResponse.getStatus()));


        SwitchResponse switchResponse1 = response.readEntity(SwitchResponse.class);

        Home home = HomeData.getInstance().getHome(houseid);

        if (switchResponse1.getSucceed()) {
            home.getVentSwitch().setStatus(switchResponse.getStatus());
        }
        
        return switchResponse;
    }
}
