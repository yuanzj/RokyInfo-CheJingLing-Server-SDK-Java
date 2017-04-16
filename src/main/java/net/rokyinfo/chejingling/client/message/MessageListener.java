package net.rokyinfo.chejingling.client.message;

import net.rokyinfo.chejingling.client.message.exception.RkMessageException;
import net.rokyinfo.chejingling.client.model.Message;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public interface MessageListener {
    void onMessage(Message message) throws Exception;
}
