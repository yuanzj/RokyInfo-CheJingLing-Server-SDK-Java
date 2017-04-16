package net.rokyinfo.chejingling.client.model;

import java.io.InputStream;

/**
 * Created by yuanzhijian on 2017/3/24.
 */
public class RemoteResponse {

    public static final int CODE_SUCCESS = 200;

    private int code;

    private InputStream content;

    public static RemoteResponse build(int code,InputStream content){
        RemoteResponse remoteResponse = new RemoteResponse();
        remoteResponse.setCode(code);
        remoteResponse.setContent(content);
        return remoteResponse;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }
}
