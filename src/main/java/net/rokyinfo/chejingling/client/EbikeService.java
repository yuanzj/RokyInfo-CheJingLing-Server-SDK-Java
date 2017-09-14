package net.rokyinfo.chejingling.client;

import net.rokyinfo.chejingling.client.model.Firm;
import net.rokyinfo.chejingling.client.model.Response;
import net.rokyinfo.chejingling.client.model.SendMsgResponse;
import net.rokyinfo.chejingling.client.model.UE;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by yuanzhijian on 2017/2/22.
 */
public interface EbikeService {

    @FormUrlEncoded
    @POST("v1/send/singleEbike")
    Call<SendMsgResponse> send2SingleEbike(@Field("ueSn") String ueSn, @Field("data") String data);

    @GET("stock/firms")
    Call<Response<Firm>> getFirmInfo();

    @GET("stock/ccus")
    Call<Response<UE>> getUEStockInfo(@Query("maxId") String maxId);

}
