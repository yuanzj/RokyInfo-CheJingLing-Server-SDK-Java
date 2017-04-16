package net.rokyinfo.chejingling.client.message;

import net.rokyinfo.chejingling.client.message.config.MessageConfig;
import net.rokyinfo.chejingling.client.message.invoke.HttpInvoker;
import net.rokyinfo.chejingling.client.message.invoke.Invoker;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class ConnectionImpl implements Connection {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ConnectionImpl.class);

    ArrayList<Session> sessions = new ArrayList<Session>();
    HashMap<String, MessageConfig> allRequests = new HashMap<String, MessageConfig>();
    Invoker client = new HttpInvoker();

    MessageConfig request;

    MessageDispatcher messageDispatcher;

    MessageDelivery messageDelivery;

    public ConnectionImpl(MessageConfig request) {
        this.request = request;
    }

    public void start() {
        if (messageDispatcher != null && messageDispatcher.isRunning()) {
            return;
        }
        messageDelivery = new MessageDelivery(sessions);
        messageDispatcher = new MessageDispatcher(allRequests, client, messageDelivery);
        messageDispatcher.start();
        logger.info("Connection start");
    }

    public void stop() {
        if (messageDispatcher != null) {
            messageDispatcher.quit();
        }
        closeAllSessions();
        logger.info("Connection stop");
    }

    public Session createSession() {
        Session session = new SessionImpl(this);
        sessions.add(session);
        return session;
    }

    public void closeSession(Session session) {

        session.clearAllConsumers();
        sessions.remove(session);
        clearInvalidRequests();

    }

    private void clearInvalidRequests() {
        ArrayList<String> unregisterRequests = new ArrayList<String>();
        for (String topicTag : allRequests.keySet()) {
            boolean deleteFlag = true;
            for (Session item : sessions) {
                ArrayList<MessageConsumer> arrayList = item.allConsumers();
                for (MessageConsumer consumer : arrayList) {
                    if (consumer.getTopic().getTag().equals(topicTag)) {
                        deleteFlag = false;
                        break;
                    }
                }
            }
            if (deleteFlag) {
                unregisterRequests.add(topicTag);
            }
        }

        unregisterRequests.forEach(key -> {
            allRequests.remove(key);
        });
    }

    @Override
    public void registerRequestByTopic(Topic topic) {
        if (!allRequests.containsKey(topic.getTag())) {
            allRequests.put(topic.getTag(), request);
        }
    }

    private void closeAllSessions() {

        sessions.forEach(Session::clearAllConsumers);
        sessions.clear();
        allRequests.clear();

    }

}
