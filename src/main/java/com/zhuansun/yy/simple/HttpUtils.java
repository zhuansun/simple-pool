package com.zhuansun.yy.simple;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * @author zhuansunpengcheng
 * @create 2019-05-05 9:28 PM
 **/
public class HttpUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String getPage(String url) {
        return getPage(url, null, null, DEFAULT_CHARSET);

    }

    public static String getPage(String pageUrl, Map<String, String> parameters, Map<String, String> headers, String charset) {
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(pageUrl);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), charset);
        } catch (Exception e) {
            System.out.println("get request error,url:" + pageUrl);
            e.printStackTrace();
        } finally {
            close(response);
            close(client);
        }
        return result;
    }

    public static void close(CloseableHttpResponse response) {
        if (response != null) {
            try {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    InputStream in = httpEntity.getContent();
                    if (in != null) {
                        in.close();
                    }
                }
            } catch (Exception ex) {
                // ignore
            }

            try {
                response.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }


    public static void close(CloseableHttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception ex) {
                // ignored
            }
        }
    }
}
