/**
 * Created by mridul on 12/08/17.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class TempHttpClient {
    public static void main(String[] args) {

        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10000);
        connectionManager.setDefaultMaxPerRoute(10000);
        connectionManager.setDefaultSocketConfig(socketConfig);

        RequestConfig defaultConfig = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)
                .setSocketTimeout(100000)
                .setRedirectsEnabled(false)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .build();


        HttpClient client = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(defaultConfig)
                .setUserAgent("SOA Client")
                .build();

        long a[]=new long[1000];

        for(int i = 0; i < 100; i++) {
            long time1 = System.currentTimeMillis();
            HttpPost post = new HttpPost("http://localhost:8020/?q=" + i);
            call(client, post, i);
            long time2 = System.currentTimeMillis();
            a[i] = (time2 - time1);
            //System.out.println("time taken for communication is:" + (time2 - time1));
        }
        Arrays.sort(a);
        System.out.println("time taken for communication is:" + a[50] + " " + a[100] + " "  + a[250] + " "  + a[500] + " "  + a[750] + " "  + a[900] + " "  + a[950] + " ");
    }

    private static Thread call(final HttpClient client, final HttpPost post, final int i) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("starting to executed " + i);
                    HttpResponse response = client.execute(post);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                    }
                    System.out.println("it is executed + " + i);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return t;
    }
}