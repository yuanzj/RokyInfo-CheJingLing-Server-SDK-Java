package net.rokyinfo.chejingling.client.message.serialize;

import net.rokyinfo.chejingling.client.model.EbikeListProtos;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuanzhijian on 2017/2/26.
 */
public class ProtobufParser implements Parser {
    @Override
    public <T> T rsbParse(InputStream result) throws IOException {
        return (T) EbikeListProtos.EbikeList.parseFrom(result);
    }
}
