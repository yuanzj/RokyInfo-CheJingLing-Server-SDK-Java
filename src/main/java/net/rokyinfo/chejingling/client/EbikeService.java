package net.rokyinfo.chejingling.client;

import net.rokyinfo.chejingling.client.model.SendMsgParameter;
import net.rokyinfo.chejingling.client.model.SendMsgResponse;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by yuanzhijian on 2017/2/22.
 */
public interface EbikeService {

    @POST("v1/send/singleEbike")
    Call<SendMsgResponse> send2SingleEbike(SendMsgParameter msgParameter);

}
