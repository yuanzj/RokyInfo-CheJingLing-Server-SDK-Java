package net.rokyinfo.chejingling.client.model;

public class SendMsg {

    private String command;
    private SendMsgConfig config;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public SendMsgConfig getConfig() {
        return config;
    }

    public void setConfig(SendMsgConfig config) {
        this.config = config;
    }
}