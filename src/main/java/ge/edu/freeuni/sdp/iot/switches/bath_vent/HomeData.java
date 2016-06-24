package ge.edu.freeuni.sdp.iot.switches.bath_vent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by khrak on 6/25/16.
 */
@XmlRootElement
public class HomeData {

    @XmlElement
    private ConcurrentHashMap<String, VentSwitch> homeData;

    public HomeData() {
        homeData = new ConcurrentHashMap<String, VentSwitch>();
    }

    public void addHome(VentSwitch ventSwitch) {
        homeData.put(ventSwitch.getHouseid(), ventSwitch);
    }

    public VentSwitch getVentSwitch(String houseid) {
        return homeData.get(houseid);
    }
}
