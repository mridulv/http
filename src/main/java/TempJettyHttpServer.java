import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.thread.QueuedThreadPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TempJettyHttpServer extends AbstractHandler {

    static QueuedThreadPool q = new QueuedThreadPool();

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8020);

        server.setHandler(new TempJettyHttpServer());
        q.setMinThreads(10);
        q.setMaxThreads(10);
        //server.setThreadPool(q);

        // Start things up! By using the server.join() the server thread will join with the current thread.
        // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.
        server.start();
        server.join();

    }

    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {
        System.out.println("request no. picked is: " + httpServletRequest.getParameter("q"));
        System.out.println(q.getQueueSize() + " " + q.getThreads());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
