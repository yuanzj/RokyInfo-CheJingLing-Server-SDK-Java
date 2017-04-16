package net.rokyinfo.chejingling.client.model;

/**
 * Created by yuanzhijian on 2017/2/22.
 */


public class SendMsgParameter {



    private String ueSn;
    private SendMsg data;
    private String dataExpires;

    public String getUeSn() {
        return ueSn;
    }

    public void setUeSn(String ueSn) {
        this.ueSn = ueSn;
    }

    public SendMsg getData() {
        return data;
    }

    public void setData(SendMsg data) {
        this.data = data;
    }

    public String getDataExpires() {
        return dataExpires;
    }

    public void setDataExpires(String dataExpires) {
        this.dataExpires = dataExpires;
    }
}




