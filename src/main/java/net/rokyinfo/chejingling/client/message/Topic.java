package net.rokyinfo.chejingling.client.message;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class Topic {

    public static String TOPIC_RECEIVED_EBIKE = "Received Ebike";

    private String tag;

    public Topic(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

}
