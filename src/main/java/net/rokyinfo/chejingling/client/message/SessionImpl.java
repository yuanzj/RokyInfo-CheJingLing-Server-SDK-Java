package net.rokyinfo.chejingling.client.message;

import java.util.ArrayList;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class SessionImpl implements Session {

    private ArrayList<MessageConsumer> messageConsumers = new ArrayList<MessageConsumer>();

    private Connection connection;


    public SessionImpl(Connection connection) {
        this.connection = connection;
    }

    public Topic createTopic(String topic) {
        return new Topic(topic);
    }

    public MessageConsumer createConsumer(Topic topic) {
        connection.registerRequestByTopic(topic);

        MessageConsumer messageConsumer = new MessageConsumer(topic);
        messageConsumers.add(messageConsumer);
        return messageConsumer;
    }

    public void close() {
        this.messageConsumers.clear();
        this.connection.closeSession(this);
    }

    @Override
    public void clearAllConsumers() {
        this.messageConsumers.clear();
    }

    @Override
    public ArrayList<MessageConsumer> allConsumers() {
        return messageConsumers;
    }


}
