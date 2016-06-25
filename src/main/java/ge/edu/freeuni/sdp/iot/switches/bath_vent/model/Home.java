package ge.edu.freeuni.sdp.iot.switches.bath_vent.model;

/**
 * Created by khrak on 6/25/16.
 */
public class Home {

    private String homeid;
    private String ventip;
    private VentSwitch ventSwitch;

    public Home(String homeid, String ventip) {
        this.homeid = homeid;
        this.ventip = ventip;
        ventSwitch = new VentSwitch(homeid, "off");
    }

    public String getHomeid() {
        return homeid;
    }

    public String getVentip() {
        return ventip;
    }

    public VentSwitch getVentSwitch() {
        return ventSwitch;
    }

}
