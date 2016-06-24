package ge.edu.freeuni.sdp.iot.switches.bath_vent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by khrak on 6/25/16.
 */

@Path("/houses/{house_id}/action/{action_type}")
@Consumes( { MediaType.APPLICATION_JSON})
@Produces( { MediaType.APPLICATION_JSON})
public class BathVentSwitchService {

    @GET
    public SwitchResponse get(@PathParam("house_id") String houseid,
                              @PathParam("action_type") String action) {

        SwitchResponse switchResponse = new SwitchResponse("1234", "on", true);
        return switchResponse;
    }
}
