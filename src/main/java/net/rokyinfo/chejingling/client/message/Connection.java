package net.rokyinfo.chejingling.client.message;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public interface Connection {

    void start();

    void stop();

    Session createSession();

    void closeSession(Session session);

    void registerRequestByTopic(Topic topic);

}
