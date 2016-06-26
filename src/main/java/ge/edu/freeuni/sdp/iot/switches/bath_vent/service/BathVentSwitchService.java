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
@Produces( { MediaType.APPLICATION_JSON})
public class BathVentSwitchService {

    @POST
    public SwitchResponse post(@PathParam("house_id") String houseid) {

        SwitchResponse response = new SwitchResponse(houseid, "on", true);
        
        return response;
    }
}
