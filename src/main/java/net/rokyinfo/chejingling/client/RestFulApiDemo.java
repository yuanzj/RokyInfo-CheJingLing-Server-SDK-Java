package net.rokyinfo.chejingling.client;

import net.rokyinfo.chejingling.client.model.Firm;
import net.rokyinfo.chejingling.client.model.Response;
import net.rokyinfo.chejingling.client.model.UE;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * RESTFul API 测试
 * Created by yuanzhijian on 2017/6/8.
 */
public class RestFulApiDemo {

    public static void main(String... orgs){
        EbikeService service = ServiceGenerator.createService("http://192.168.102.110:8909/SpiritServiceApp/",EbikeService.class,"Basic c3VucmE6RTEwQURDMzk0OUJBNTlBQkJFNTZFMDU3RjIwRjg4M0U=");
        Call<Response<Firm>> repos = service.getFirmInfo();
        repos.enqueue(new Callback<Response<Firm>>() {

            @Override
            public void onResponse(Call<Response<Firm>> call, retrofit2.Response<Response<Firm>> response) {
                System.out.println(response.body().getData().size() + "");
            }

            @Override
            public void onFailure(Call<Response<Firm>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<Response<UE>> repos1 = service.getUEStockInfo("0");
        repos1.enqueue(new Callback<Response<UE>>() {

            @Override
            public void onResponse(Call<Response<UE>> call, retrofit2.Response<Response<UE>> response) {
                System.out.println(response.body().getData().size() + "");
            }

            @Override
            public void onFailure(Call<Response<UE>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
