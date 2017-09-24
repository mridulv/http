import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * Created by mridul on 30/08/17.
 */
public class TempUndertowServer {
    public static void helloWorldHandler(HttpServerExchange exchange) {
        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Hello World!");
    }

    public static void main(String[] args) {
        int port = 8010;
    /*
     *  "localhost" will ONLY listen on local host.
     *  If you want the server reachable from the outside you need to set "0.0.0.0"
     */
        String host = "localhost";

    /*
     * This web server has a single handler with no routing.
     * ALL urls are handled by the helloWorldHandler.
     */
        Undertow server = Undertow.builder()
                // Add the helloWorldHandler as a method reference.
                .addHttpListener(port, host, new DefaultHttpHandler())
                .build();
        server.start();
    }
}

class DefaultHttpHandler implements HttpHandler {

    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
