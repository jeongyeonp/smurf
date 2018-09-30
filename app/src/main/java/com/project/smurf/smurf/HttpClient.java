package com.project.smurf.smurf;

import com.loopj.android.http.*;

public class HttpClient {
    private static final String login_url = "http://210.102.181.158:62003/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static AsyncHttpClient getInstance(){
        return HttpClient.client;
    }
    public static void get (String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url),params,responseHandler);
    }

    public static void post (String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url),params,responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return login_url+relativeUrl;
    }
}

