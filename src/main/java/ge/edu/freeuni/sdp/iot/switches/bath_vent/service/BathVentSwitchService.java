package ge.edu.freeuni.sdp.iot.switches.bath_vent.service;

import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.SwitchResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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

        SwitchResponse switchResponse = new SwitchResponse("1234", "on", true);
        return switchResponse;
    }
}
