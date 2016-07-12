package ge.edu.freeuni.sdp.iot.switches.bath_vent.service;

import ge.edu.freeuni.sdp.iot.switches.bath_vent.data.HomeData;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.Home;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.SwitchResponse;
import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.VentSwitch;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
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
public class BathVentSwitchService {

    @POST
    public Response post(@PathParam("house_id") String houseid,
                                String body) {

        HomeData homeData = HomeData.getInstance();

        Home home = homeData.getHome(houseid);

        System.out.println(body + " body ");

        if(home == null) {
            SwitchResponse response = new SwitchResponse(houseid, null, false);
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }


        JSONObject jsonBody = new JSONObject(body);

        String state = jsonBody.getString("set_status");
        int timeout = jsonBody.getInt("timeout");

        Client client = ClientBuilder.newClient();

        String url = home.getVentUrl() + "webapi/bath/vent-switch/";

        Response simulatorResponse =
                client.target(url)
                        .request(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(body, MediaType.APPLICATION_JSON));

        if(simulatorResponse.getStatus() == Response.Status.SERVICE_UNAVAILABLE.getStatusCode()) {
            /* if connection was failed, return result, according to last update. */
            VentSwitch ventSwitch = home.getVentSwitch();
            String status =  ventSwitch.getStatus();

            SwitchResponse response = new SwitchResponse(houseid, status, false);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(response).build();
        }

        /* check if state is on. If its on, then wait for timeout number of
        * seconds and then off switch. */

        home.getVentSwitch().setStatus(state);

        if(state.equals("on")) {
            Timeout threadToOff = new Timeout(timeout, home);
            threadToOff.start();
        }

        SwitchResponse response = new SwitchResponse(houseid, state, true);

        return Response.status(Response.Status.OK).entity(response).build();
    }

    private class Timeout extends Thread{
        private int timeout;
        private Home home;

        public Timeout(int timeout, Home home) {
            this.timeout = timeout;
            this.home = home;
        }

        public void run() {
            try {
                Thread.sleep(timeout * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turnSwitchOff();
        }

        private void turnSwitchOff() {

            Client client = ClientBuilder.newClient();

            String url = home.getVentUrl() + "webapi/bath/vent-switch/off";

            Response simulatorResponse =
                    client.target(url)
                            .request(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .get();

            /* set status */
            home.getVentSwitch().setStatus("off");
            System.out.println("switch off");
        }
    }
}
