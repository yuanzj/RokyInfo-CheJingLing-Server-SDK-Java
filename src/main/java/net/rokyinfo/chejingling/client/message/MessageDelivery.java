package net.rokyinfo.chejingling.client.message;

import net.rokyinfo.chejingling.client.message.exception.RkMessageException;
import net.rokyinfo.chejingling.client.model.Message;

import java.util.ArrayList;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class MessageDelivery {

    ArrayList<Session> sessions;

    public MessageDelivery(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public void postMessage(String topicTag, Message message) throws Exception {

        if (sessions != null && sessions.size() > 0) {
            for (Session session : sessions) {
                for (MessageConsumer messageConsumer : session.allConsumers()) {
                    if (messageConsumer.getTopic().getTag().equals(topicTag)) {
                        messageConsumer.postMessage(message);
                    }
                }
            }
        }

    }

    public void postError(RkMessageException e) {

    }


}
