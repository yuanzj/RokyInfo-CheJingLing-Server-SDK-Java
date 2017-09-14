package net.rokyinfo.chejingling.client.model;

/**
 * Created by yuanzhijian on 2017/6/8.
 */
public class UE {
//    ueSn 中控设备序列号
//    firmFlag 厂商flag
//    mac1 蓝牙mac1地址
//    mac2 蓝牙mac2地址
//    authenticationCode 蓝牙授权码

    private String id;

    private String ueSn;
    private String firmFlag;
    private String mac1;
    private String mac2;
    private String authenticationCode;
    private String lifeStatus;

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUeSn() {
        return ueSn;
    }

    public void setUeSn(String ueSn) {
        this.ueSn = ueSn;
    }

    public String getFirmFlag() {
        return firmFlag;
    }

    public void setFirmFlag(String firmFlag) {
        this.firmFlag = firmFlag;
    }

    public String getMac1() {
        return mac1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public String getMac2() {
        return mac2;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public String getAuthenticationCode() {
        return authenticationCode;
    }

    public void setAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }
}
