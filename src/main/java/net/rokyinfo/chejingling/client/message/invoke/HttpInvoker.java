package net.rokyinfo.chejingling.client.message.invoke;

import net.rokyinfo.chejingling.client.message.config.MessageConfig;
import net.rokyinfo.chejingling.client.model.RemoteResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class HttpInvoker implements Invoker {

    private static OkHttpClient mOkHttpClient;


    private HashMap<String, Request> cacheRequests = new HashMap<String, Request>();

    public HttpInvoker() {
        try {
            mOkHttpClient = setSSL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置https 访问的时候对所有证书都进行信任
     *
     * @throws Exception
     */
    public static OkHttpClient setSSL() throws Exception {
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();

    }

    public RemoteResponse perform(MessageConfig request) throws IOException {
        if (!cacheRequests.containsKey(request.getTag())) {
            cacheRequests.put(request.getTag(), new Request.Builder()
                    .url(request.getUrl()).addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((request.getUserName() + ":" + request.getPassword()).getBytes())).build());
        }

        Response response = mOkHttpClient.newCall(cacheRequests.get(request.getTag())).execute();

        return RemoteResponse.build(response.code(), response.body().byteStream());
    }

}
