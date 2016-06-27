package ge.edu.freeuni.sdp.iot.switches.bath_vent.model;

/**
 * Created by khrak on 6/25/16.
 */
public class Home {

    private String homeid;
    private String venturl;
    private VentSwitch ventSwitch;

    public Home(String homeid, String venturl) {
        this.homeid = homeid;
        this.venturl = venturl;
        ventSwitch = new VentSwitch(homeid, "off");
    }

    public String getHomeid() {
        return homeid;
    }

    public String getVentUrl() {
        return venturl;
    }

    public VentSwitch getVentSwitch() {
        return ventSwitch;
    }

}
