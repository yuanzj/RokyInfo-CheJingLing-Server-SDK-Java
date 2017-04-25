package net.rokyinfo.chejingling.client.message;

import net.rokyinfo.chejingling.client.message.config.MessageConfig;
import net.rokyinfo.chejingling.client.message.exception.RkMessageException;
import net.rokyinfo.chejingling.client.message.invoke.Invoker;
import net.rokyinfo.chejingling.client.message.monitor.MessageMonitor;
import net.rokyinfo.chejingling.client.message.serialize.Parser;
import net.rokyinfo.chejingling.client.message.serialize.ProtobufParser;
import net.rokyinfo.chejingling.client.message.util.InputStreamUtil;
import net.rokyinfo.chejingling.client.model.Message;
import net.rokyinfo.chejingling.client.model.RemoteResponse;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class MessageDispatcher extends Thread {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private volatile boolean mQuit = false;

    HashMap<String, MessageConfig> allRequests;

    Invoker client;

    MessageDelivery messageDelivery;

    Parser mParser;

    public MessageDispatcher(HashMap<String, MessageConfig> allRequests, Invoker client, MessageDelivery messageDelivery) {
        this.allRequests = allRequests;
        this.client = client;
        this.messageDelivery = messageDelivery;
        this.mParser = new ProtobufParser();
    }

    public void quit() {
        mQuit = true;
        interrupt();
    }

    public boolean isRunning() {
        if ((!mQuit && isAlive())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        while (true) {

            if (mQuit) {
                return;
            }

            allRequests.forEach((topicTag, request) -> {

                RemoteResponse remoteResponse = null;
                try {

                    long interval =  System.currentTimeMillis() - MessageMonitor.getInstance().getRequestStatistics().getStartTimeMs();
                    if (interval > 0 && interval < request.getInterval()){
                        Thread.sleep((request.getInterval() - interval));
                        logger.info("interval:"+(request.getInterval() - interval));
                    }

                    MessageMonitor.getInstance().startRequest();
                    remoteResponse = client.perform(request);
                    MessageMonitor.getInstance().requestSuccess();

                } catch (Exception e) {

                    logger.error("request-error", e);
                    MessageMonitor.getInstance().requestError(new RkMessageException(e));

                }

                if (remoteResponse != null && remoteResponse.getCode() == RemoteResponse.CODE_SUCCESS && remoteResponse.getContent() != null) {
                    MessageMonitor.getInstance().startPostMessage();

                    long msgSize = 0;
                    try {
                        Message message = new Message(this.mParser.rsbParse(remoteResponse.getContent()));

                        if (message.getEbikeList() != null) {
                            msgSize = message.getEbikeList().getSerializedSize();
                            logger.info(message.getEbikeList().toString());
                            messageDelivery.postMessage(topicTag, message);
                        }
                        MessageMonitor.getInstance().postMessageComplete();

                    } catch (Exception e) {

                        logger.error("post-error", e);
                        MessageMonitor.getInstance().postMessageError(new RkMessageException(e));

                    }

                    MessageMonitor.getInstance().bodySize(msgSize);

                } else if (remoteResponse != null) {
                    logger.error("post-error code:[" +remoteResponse.getCode() +"] content:"+InputStreamUtil.convertStreamToString(remoteResponse.getContent()));
                }

            });


        }
    }


}
