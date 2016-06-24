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
    private ConcurrentHashMap<String, HomeVentSwitches> homeData;

    public HomeData() {
        homeData = new ConcurrentHashMap<String, HomeVentSwitches>();
    }

    public void addHome(HomeVentSwitches home) {
        homeData.put(home.getHomeid(), home);
    }

    public HomeVentSwitches getHome(String homeid) {
        return homeData.get(homeid);
    }
}
