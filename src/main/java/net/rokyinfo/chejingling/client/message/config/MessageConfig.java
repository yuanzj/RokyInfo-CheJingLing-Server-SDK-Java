package net.rokyinfo.chejingling.client.message.config;

import java.util.HashMap;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class MessageConfig {

    private String userName;
    private String password;
    private String host;
    private int port;
    private String path;
    private HashMap<String, Object> parameter;
    private String tag;
    private int interval;

    public MessageConfig(String userName, String password, String host, int port) {
        this.userName = userName;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public void addParameter(String name, Object value) {
        if (parameter == null) {
            parameter = new HashMap<String, Object>();
        }
        parameter.put(name, value.toString());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTag() {
        if (tag == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.userName);
            stringBuilder.append(this.password);
            stringBuilder.append(this.host);
            stringBuilder.append(this.path);
            tag = stringBuilder.toString();
        }

        return tag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getUrl() {
        if (parameter != null) {
            StringBuilder stringBuilder = new StringBuilder("?");
            parameter.forEach((k, v) -> {
                stringBuilder.append(k);
                stringBuilder.append("=");
                stringBuilder.append(v);
                stringBuilder.append("&");
            });
            String parameterURl = stringBuilder.substring(0, stringBuilder.length() - 1);
            return "http://" + host + ":" + port + "/" + path + parameterURl;
        }
        return "http://" + host + ":" + port + "/" + path;
    }
}
