package net.rokyinfo.chejingling.client.message;

import okhttp3.Request;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public interface Session {

    Topic createTopic(String topic);

    MessageConsumer createConsumer(Topic topic);

    void close();

    void clearAllConsumers();

    ArrayList<MessageConsumer> allConsumers();


}
