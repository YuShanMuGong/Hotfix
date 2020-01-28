package com.mu.hotfix.client.util;

import okhttp3.*;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class OkHttpUtils {

    private static Charset charset = Charset.forName("utf8");

    private static class OkHttpClientHolder {
        static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3,TimeUnit.SECONDS)
                .writeTimeout(3,TimeUnit.SECONDS)
                .build();
    }

    private static OkHttpClient getOkHttpClient(){
        return OkHttpClientHolder.okHttpClient;
    }

    public static String getJson(String url , Map<String,String> params) throws Exception{
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String,String> item : params.entrySet()){
            formBodyBuilder.add(item.getKey(),item.getValue());
        }
        FormBody postBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .post(postBody)
                .url(url)
                .build();
        Call call = getOkHttpClient().newCall(request);
        Response response = call.execute();
        if(response == null || !response.isSuccessful() || response.body() == null){
            throw new IllegalStateException("http request fail");
        }
        return response.body().string();
    }

}
