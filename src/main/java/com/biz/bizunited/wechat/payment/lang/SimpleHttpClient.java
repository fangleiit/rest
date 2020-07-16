package com.biz.bizunited.wechat.payment.lang;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SimpleHttpClient {

    public static final String UTF_8 = "utf-8";

    private SSLContext ctx;

    public SimpleHttpClient() {

    }

    public SimpleHttpClient(SSLContext ctx) {
        this.ctx = ctx;
    }

    public String doPost(final String url, final ContentType contentType, final String body)
        throws IOException {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if (ctx != null) {
            httpClientBuilder.disableAuthCaching().disableCookieManagement().setSslcontext(ctx);
        }
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new StringEntity(body, contentType));
            httpResponse = httpClient.execute(httpPost);
            return getResponseBody(httpResponse);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException ignored) {
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException ignored) {
            }
        }

    }

    private String getResponseBody(HttpResponse resp) throws IOException {
        HttpEntity httpEntity = resp.getEntity();
        Long length = httpEntity.getContentLength();
        ByteArrayOutputStream buffer = (length > 0) ?
            new ByteArrayOutputStream(length.intValue()) :
            new ByteArrayOutputStream();
        resp.getEntity().writeTo(buffer);
        return buffer.toString(UTF_8);
    }
}
