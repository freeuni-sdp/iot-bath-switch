package ge.edu.freeuni.sdp.iot.swich.bath_vent;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by mi.maghriani on 6/11/2016.
 */


@Path("/ping")
public class PingService {
    @GET
    public Response get() {
        return Response.ok().build();
    }
}

