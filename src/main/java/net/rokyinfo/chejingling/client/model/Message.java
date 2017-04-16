package net.rokyinfo.chejingling.client.model;

import net.rokyinfo.chejingling.client.message.*;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class Message {

    private EbikeListProtos.EbikeList ebikeList;

    public Message(EbikeListProtos.EbikeList ebikeList) {
        this.ebikeList = ebikeList;
    }

    public EbikeListProtos.EbikeList getEbikeList() {
        return ebikeList;
    }

    public void setEbikeList(EbikeListProtos.EbikeList ebikeList) {
        this.ebikeList = ebikeList;
    }

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new RkMQConnectionFactory("18626313098", "111111", "192.168.16.158", 8080);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession();
        MessageConsumer messageConsumer = session.createConsumer(session.createTopic(Topic.TOPIC_RECEIVED_EBIKE));
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

            }
        });


    }
}
