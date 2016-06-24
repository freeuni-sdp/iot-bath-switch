package ge.edu.freeuni.sdp.iot.switches.bath_vent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by khrak on 6/25/16.
 */
@XmlRootElement
public class HomeVentSwitches {

    @XmlElement
    private ConcurrentHashMap<Integer, VentSwitch> homeVentSwitches;
    @XmlElement
    private String homeid;

    public HomeVentSwitches(String homeid) {
        this.homeid = homeid;
        homeVentSwitches = new ConcurrentHashMap<Integer, VentSwitch>();
    }

    public void addVentSwitch(VentSwitch newVentSwitch) {
        homeVentSwitches.put(newVentSwitch.getVentSwitchid(), newVentSwitch);
    }

    public VentSwitch getVentSwitch(Integer ventSwitchid) {
        return homeVentSwitches.get(ventSwitchid);
    }

    public String getHomeid() {
        return homeid;
    }
}
