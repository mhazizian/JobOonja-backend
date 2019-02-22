package ir.aziz.karam.pages;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import ir.aziz.karam.manager.ProjectManager;
import ir.aziz.karam.types.Project;
import java.util.List;

public class ProjectListPage implements IPage {

    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        List<Project> allProject = ProjectManager.getInstance().getAllProject();
        String response = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Projects</title>\n"
                + "    <style>\n"
                + "        table {\n"
                + "            text-align: center;\n"
                + "            margin: 0 auto;\n"
                + "        }\n"
                + "        td {\n"
                + "            min-width: 300px;\n"
                + "            margin: 5px 5px 5px 5px;\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <table>\n"
                + "        <tr>\n"
                + "            <th>id</th>\n"
                + "            <th>title</th>\n"
                + "            <th>budget</th>\n"
                + "        </tr>\n";
        for (int i = 0; i < allProject.size(); i++) {
            response += "        <tr>\n"
                    + "            <td>" + allProject.get(i).getId() + "</td>\n"
                    + "            <td> " + allProject.get(i).getTitle() + "</td>\n"
                    + "            <td>" + allProject.get(i).getBudget() + "</td>\n"
                    + "        </tr>\n";
        }
        response += "    </table>\n"
                + "</body>\n"
                + "</html>";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
