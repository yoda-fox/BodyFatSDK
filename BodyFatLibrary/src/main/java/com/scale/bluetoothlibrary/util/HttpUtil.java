package com.scale.bluetoothlibrary.util;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.scale.bluetoothlibrary.bean.BodyFatBean;
import com.scale.bluetoothlibrary.bean.BodyFatConfig;
import com.scale.bluetoothlibrary.listener.OnConfigListener;
import com.scale.bluetoothlibrary.listener.OnStatusListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtil {
    private String accessToken;
    private static HttpUtil httpUtil;
    private String baseUrl = "https://api.yodatech.cn/aip/";
    private OnConfigListener onConfigListener;
    private OnStatusListener onStatusListener;
    private final String headerKey1 = "Content-Type";
    private final String headerKey2 = "Auth-Token";
    private final String headerValue = "application/json;charset=UTF-8";
    private final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    public void setConfigListener(OnConfigListener onConfigListener) {
        this.onConfigListener = onConfigListener;
    }

    public void setStatusListener(OnStatusListener onStatusListener) {
        this.onStatusListener = onStatusListener;
    }

    public void getAccessToken(String appid, String secret) {
        if (StringUtil.getSystemLanguage().equals("zh_CN")) {
            baseUrl = "http://lx.yodatech.cn/app/";
        } else {
            baseUrl = "https://api.yodatech.cn/aip/";
        }
        String url = baseUrl + "jwt/token";
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret", secret);
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(mediaType, gson.toJson(params));
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String json = responseBody.string();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int code = jsonObject.getInt("code");
                        if (code == 1000) {
                            JSONObject dataJson = jsonObject.getJSONObject("data");
                            accessToken = dataJson.getString("accessToken");
                            if (onStatusListener != null) {
                                onStatusListener.onStatus(0, "success");
                            }
                        } else {
                            if (onStatusListener != null) {
                                onStatusListener.onStatus(-1, jsonObject.getString("msg"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (onStatusListener != null) {
                        onStatusListener.onStatus(-1, "onResponse-onFailure");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (onStatusListener != null) {
                    onStatusListener.onStatus(-1, e.getMessage());
                }
            }
        });
    }

    public void getBodyParameter(Map<String, Object> params) {
        if (TextUtils.isEmpty(accessToken)) {
            onConfigListener.onDataFail(-3, "token error");
            return;
        }
        if (StringUtil.getSystemLanguage().equals("zh_CN")) {
            baseUrl = "http://lx.yodatech.cn/app/";
        } else {
            baseUrl = "https://api.yodatech.cn/aip/";
        }
        String url = baseUrl + "user/got/body/parameters";
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(mediaType, gson.toJson(params));
        Request request = new Request.Builder().url(url).post(body).addHeader(headerKey1, headerValue).addHeader(headerKey2, accessToken).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String json = responseBody.string();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int code = jsonObject.getInt("code");
                        if (code == 1000) {
                            JSONObject dataJson = jsonObject.getJSONObject("data");
                            Gson gson1 = new Gson();
                            BodyFatBean bodyFatBean = gson1.fromJson(dataJson.toString(), BodyFatBean.class);
                            if (onConfigListener != null) {
                                onConfigListener.onDataSuccess(new BodyFatConfig(bodyFatBean));
                            }
                        } else {
                            if (onConfigListener != null) {
                                onConfigListener.onDataFail(jsonObject.getInt("code"), jsonObject.getString("msg"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (onConfigListener != null) {
                        onConfigListener.onDataFail(-2, "Unexpected code " + response);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (onConfigListener != null) {
                    onConfigListener.onDataFail(-2, e.getMessage());
                }
            }
        });
    }
}