package ir.aziz.karam.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ir.aziz.karam.pages.NotFound404Page;
import ir.aziz.karam.pages.ProjectDetailPage;
import ir.aziz.karam.pages.ProjectListPage;
import ir.aziz.karam.pages.UserDetailPage;
import org.apache.log4j.Logger;

public class JobOonJaHttpServer {

    public void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/project", new ProjectHandler());
        server.createContext("/user", new UserHandler());
        server.setExecutor(null);
        server.start();
    }

    class ProjectHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            try {

                String context = tokenizer.nextToken();
                if (!tokenizer.hasMoreTokens()) {
                    ProjectListPage pageHandler = new ProjectListPage();
                    pageHandler.HandleRequest(httpExchange);
                } else {
                    String page = tokenizer.nextToken();
                    ProjectDetailPage projectDetailPage = new ProjectDetailPage(page);
                    projectDetailPage.HandleRequest(httpExchange);
                }
            } catch (IllegalArgumentException | SecurityException e) {
                Logger.getLogger("JobOonJaHttpServer").error(e, e);
                NotFound404Page notFound404Page = new NotFound404Page();
                notFound404Page.HandleRequest(httpExchange);
            }
        }
    }

    class UserHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens()) {
                NotFound404Page notFound404Page = new NotFound404Page();
                notFound404Page.HandleRequest(httpExchange);
            } else {
                String page = tokenizer.nextToken();
                UserDetailPage userDetailPage = new UserDetailPage(page);
                userDetailPage.HandleRequest(httpExchange);
            }
        }
    }
}
