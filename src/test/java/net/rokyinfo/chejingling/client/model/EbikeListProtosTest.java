package net.rokyinfo.chejingling.client.model;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import java.util.Map;

/**
 * Created by yuanzhijian on 2017/2/19.
 */
public class EbikeListProtosTest {

    @Test
    public void test() throws InvalidProtocolBufferException {
        EbikeListProtos.EbikeList.Builder ebikeList = EbikeListProtos.EbikeList.newBuilder();
        EbikeListProtos.EbikeList mEbikeList = ebikeList.addEbike(
                EbikeListProtos.Ebike.newBuilder().setStatus(1).setFault(ByteString.copyFrom(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0})).setAlarm(1).setODO(100).setSpeed(44).setVoltage(10000).setCurrent(20000).setTurn(50).setSource(EbikeListProtos.DataSource.SMART_PHONE).setReportTime("2016-12-05T14:30Z")
                        .setLbs(EbikeListProtos.LBS.newBuilder().setSource(EbikeListProtos.DataSource.SMART_PHONE).setReportTime("2016-12-05T14:30Z").setLonLatType(EbikeListProtos.LonLatType.WEST_LONGITUDE_NORTH_LATITUDE).setLon(31.987).setLat(102.3332).setBaseStation("888888:999999").setHAccuracy(50))
                        .setBms(EbikeListProtos.BMS.newBuilder().setChargeCount(10).setSOH(106).setT(100).setStatus(10000))
                        .setCcu(EbikeListProtos.CCU.newBuilder().setSN("B000000000").setHardware("W1525.01").setSoftware("W1525.01"))
                        .setPcu(EbikeListProtos.PCU.newBuilder().setUDID("878788888888888888888888").setHardware("W1525.01").setSoftware("W1525.01"))
                        .setDcu(EbikeListProtos.DCU.newBuilder().setIMEI("111111111111111").setIMSI("000000000000000").setHardware("W1525.01").setSoftware("W1525.01").setOnline(EbikeListProtos.OnlineStatus.ONLINE).setGPSRSSI(5).setGSMRSSI(5).setVBAT(9))
                        .setEcu(EbikeListProtos.ECU.newBuilder().setUDID("878788888888888888888888").setHardware("W1525.01").setSoftware("W1525.01"))
        ).build();

        byte[] array = mEbikeList.toByteArray();



        EbikeListProtos.EbikeList newEbikeList = EbikeListProtos.EbikeList.parseFrom(array);
        Descriptors.FieldDescriptor field = newEbikeList.getEbike(0).getBms().getDescriptorForType().findFieldByName("SOH");
        System.out.println("长度："+newEbikeList.getSerializedSize()+newEbikeList.getEbike(0).getBms().hasField(field));
        System.out.print(newEbikeList);

    }

}