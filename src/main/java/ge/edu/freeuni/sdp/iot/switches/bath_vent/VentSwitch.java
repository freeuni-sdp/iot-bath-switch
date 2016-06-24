package ge.edu.freeuni.sdp.iot.switches.bath_vent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by khrak on 6/25/16.
 */
@XmlRootElement
public class VentSwitch {

    @XmlElement
    private String houseid;
    @XmlElement
    private Integer ventSwitchid;
    @XmlElement
    private String status;

    public VentSwitch(String houseid, Integer ventSwitchid, String status) {
        this.houseid = houseid;
        this.ventSwitchid = ventSwitchid;
        this.status = status;
    }

    public String getHouseid() {
        return houseid;
    }

    public Integer getVentSwitchid() {
        return ventSwitchid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }
}
