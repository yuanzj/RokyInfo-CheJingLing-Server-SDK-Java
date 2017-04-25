package net.rokyinfo.chejingling.client;

import net.rokyinfo.chejingling.client.message.*;
import net.rokyinfo.chejingling.client.model.EbikeListProtos;
import net.rokyinfo.chejingling.client.model.Message;

/**
 * Created by yuanzhijian on 2017/4/25.
 */
public class Application {

    public static void main(String... org){

        ConnectionFactory connectionFactory = new RkMQConnectionFactory("test","95C142F453429FD931A4EBA5B692DE80","https://exchange.rokyinfo.net",80,"SpiritServiceApp/v1/receivedCache/ebikes",1000,1000);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session =  connection.createSession();
        MessageConsumer messageConsumer = session.createConsumer(session.createTopic(Topic.TOPIC_RECEIVED_EBIKE));
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //如果有车辆数据上报这个方法会被自动回调
                EbikeListProtos.EbikeList ebikeList = message.getEbikeList();
                System.out.print("EbikeList:"+ebikeList);
            }
        });

    }
}
