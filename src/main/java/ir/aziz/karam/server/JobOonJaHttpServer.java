package ir.aziz.karam.server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ir.aziz.karam.pages.ProjectListPage;

public class JobOonJaHttpServer {


    public void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/project", new ProjectHandler());
        server.setExecutor(null);
        server.start();
    }

    class ProjectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            String page = tokenizer.nextToken();
//            Class<IPage> pageClass;
            if (page == null || page.equals("")) {
                ProjectListPage pageHandler = new ProjectListPage();
                pageHandler.HandleRequest(httpExchange);
                return;
            }
//            try {
//////                pageClass = (Class<IPage>) Class.forName("ir.ac.ut.ece.modernserver." + page);
//////                IPage newInstance = pageClass.getDeclaredConstructor().newInstance();
//////                newInstance.HandleRequest(httpExchange);
////            } catch (ClassNotFoundException |
////                    InstantiationException |
////                    IllegalAccessException |
////                    IllegalArgumentException |
////                    InvocationTargetException |
////                    NoSuchMethodException |
////                    SecurityException e) {
////                e.printStackTrace();
////                String response =
////                        "<html>"
////                                + "<body>Page \"" + page + "\" not found.</body>"
////                                + "</html>";
////                httpExchange.sendResponseHeaders(404, response.length());
////                OutputStream os = httpExchange.getResponseBody();
////                os.write(response.getBytes());
////                os.close();
////            }
        }
    }
}