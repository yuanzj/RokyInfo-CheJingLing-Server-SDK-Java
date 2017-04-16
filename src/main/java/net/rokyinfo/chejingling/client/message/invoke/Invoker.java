package net.rokyinfo.chejingling.client.message.invoke;

import net.rokyinfo.chejingling.client.message.config.MessageConfig;
import net.rokyinfo.chejingling.client.model.RemoteResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public interface Invoker {

    public RemoteResponse perform(MessageConfig request) throws IOException;

}
