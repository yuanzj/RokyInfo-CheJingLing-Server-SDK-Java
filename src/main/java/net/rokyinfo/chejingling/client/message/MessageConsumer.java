package net.rokyinfo.chejingling.client.message;

import net.rokyinfo.chejingling.client.model.Message;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class MessageConsumer {

    private Topic topic;
    private MessageListener messageListener;

    public MessageConsumer(Topic topic){
        this.topic = topic;
    }

    void postMessage(Message message) throws Exception {
        if (messageListener != null){
            messageListener.onMessage(message);
        }
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
