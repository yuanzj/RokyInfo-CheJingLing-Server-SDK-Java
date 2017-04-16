package net.rokyinfo.chejingling.client.message;

import net.rokyinfo.chejingling.client.message.config.MessageConfig;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class RkMQConnectionFactory implements ConnectionFactory {

    private static final Object LOCK_OBJ = new Object();

    private static volatile Connection connection;

    MessageConfig request;

    public RkMQConnectionFactory(String userName, String password, String host, int port) {
        this.request = new MessageConfig(userName, password, host, port);
        this.request.addParameter("maxCount", String.valueOf(10000));
    }

    public RkMQConnectionFactory(String userName, String password, String host, int port, String path, int maxCount, int interval) {
        this.request = new MessageConfig(userName, password, host, port);
        this.request.setPath(path);
        this.request.addParameter("maxCount", String.valueOf(maxCount));
        this.request.setInterval(interval);
    }

    public synchronized Connection createConnection() {

        if (connection == null) {
            synchronized (LOCK_OBJ) {
                if (connection == null) {
                    connection = new ConnectionImpl(request);
                }
            }
        }
        return connection;
    }


}
