package ge.edu.freeuni.sdp.iot.switches.bath_vent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by khrak on 6/25/16.
 */
@XmlRootElement
public class SwitchResponse {

    @XmlElement
    private String houseid;
    @XmlElement
    private Integer switchid;
    @XmlElement
    private String status;
    @XmlElement
    private boolean succeed;

    public SwitchResponse(String houseid, Integer switchid, String status, boolean succeed) {
        this.houseid = houseid;
        this.switchid = switchid;
        this.status = status;
        this.succeed = succeed;
    }

    public String getHouseid() {
        return houseid;
    }

    public Integer getSwitchid() {
        return switchid;
    }

    public String getStatus() {
        return status;
    }

    public boolean getSucceed() {
        return succeed;
    }
}
