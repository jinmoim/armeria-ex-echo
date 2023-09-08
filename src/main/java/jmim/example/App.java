package jmim.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;


public class App {
    private static final Logger logger = LogManager.getLogger(App.class);


    static Server newServer(int port) {
        ServerBuilder sb = Server.builder();
        return sb.http(port)
                 .service("/", (ctx, req) -> {
                    String headers = req.headers().toString();
                    logger.info("{}", headers);
                    return HttpResponse.of(headers);
                 })
                 .build();
      }

    public static void main(String[] args) {
        Server server = newServer(8080);

        server.closeOnJvmShutdown();

        server.start().join();
        
        logger.info("Server has been started. Serving dummy service at http://127.0.0.1:{}",
              server.activeLocalPort());
    }
}
