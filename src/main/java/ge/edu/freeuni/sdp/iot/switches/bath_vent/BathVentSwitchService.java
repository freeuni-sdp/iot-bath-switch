package ge.edu.freeuni.sdp.iot.switches.bath_vent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by khrak on 6/25/16.
 */

@Path("/houses/{house_id}/bath_vent/{bath_vent_id}/action/{action_type}")
@Consumes( { MediaType.APPLICATION_JSON})
@Produces( { MediaType.APPLICATION_JSON})
public class BathVentSwitchService {

    @PUT
    public SwitchResponse put(@PathParam("house_id") String houseid,
                              @PathParam("bath_vent_id") Integer switchid,
                              @PathParam("action_type") String action) {

        SwitchResponse switchResponse = new SwitchResponse("1234",4321, "on", true);
        return switchResponse;
    }
}
