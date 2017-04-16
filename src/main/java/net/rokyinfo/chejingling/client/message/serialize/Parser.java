package net.rokyinfo.chejingling.client.message.serialize;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuanzhijian on 2017/2/26.
 */
public interface Parser {


    public <T> T rsbParse(InputStream result) throws IOException;

}
