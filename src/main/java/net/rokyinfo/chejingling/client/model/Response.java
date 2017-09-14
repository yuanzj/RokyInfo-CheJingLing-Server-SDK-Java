package net.rokyinfo.chejingling.client.model;

import java.util.List;

/**
 * Created by yuanzhijian on 2017/6/8.
 */
public class Response<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
