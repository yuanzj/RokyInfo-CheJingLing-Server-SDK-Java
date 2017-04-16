package net.rokyinfo.chejingling.client.message.invoke;

import net.rokyinfo.chejingling.client.message.config.MessageConfig;
import net.rokyinfo.chejingling.client.model.RemoteResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class HttpInvoker implements Invoker {

    private final static int CONNECT_TIMEOUT = 60;
    private final static int READ_TIMEOUT = 100;
    private final static int WRITE_TIMEOUT = 60;

    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    private HashMap<String, Request> cacheRequests = new HashMap<String, Request>();

    public RemoteResponse perform(MessageConfig request) throws IOException {
        if (!cacheRequests.containsKey(request.getTag())) {
            cacheRequests.put(request.getTag(), new Request.Builder()
                    .url(request.getUrl()).addHeader("Authorization","Basic " + Base64.getEncoder().encodeToString((request.getUserName() + ":" + request.getPassword()).getBytes())).build());
        }

        Response response = mOkHttpClient.newCall(cacheRequests.get(request.getTag())).execute();

        return RemoteResponse.build(response.code() ,response.body().byteStream());
    }

}
