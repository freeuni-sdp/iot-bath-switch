package ge.edu.freeuni.sdp.iot.switches.bath_vent.data;

import ge.edu.freeuni.sdp.iot.switches.bath_vent.model.Home;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by khrak on 6/25/16.
 */
@XmlRootElement
public class HomeData {

    private static HomeData instance = new HomeData();

    public static HomeData getInstance(){
        return instance;
    }

    @XmlElement
    private ConcurrentHashMap<String, Home> homeData;

    public HomeData() {
        homeData = new ConcurrentHashMap<String, Home>();
        registerHomes();
    }

    public void addHome(Home home) {
        homeData.put(home.getHomeid(), home);
    }

    public Home getHome(String houseid) {
        return homeData.get(houseid);
    }

    private void registerHomes() {
        String respJson = getResponseString("https://iot-house-registry.herokuapp.com/houses/");

        if(respJson == null) return;

        keepHomes(respJson);
    }

    private String getResponseString(String url) {
        Client client = ClientBuilder.newClient();
        Response response =
                client.target(url)
                        .request(MediaType.APPLICATION_JSON)
                        .get();

        int responseStatus = response.getStatus();
        if (responseStatus != Response.Status.OK.getStatusCode())
            return null;

        return response.readEntity(String.class);
    }

    private void keepHomes(String respJson) {

        JSONArray jsonArray = new JSONArray(respJson);
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String homeid = jsonObject.getJSONObject("RowKey").getString("_");
            String ventip = jsonObject.getJSONObject("vent_ip").getString("_");

            Home home = new Home(homeid, ventip);
            addHome(home);
        }
    }
}
