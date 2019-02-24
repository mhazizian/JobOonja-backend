package ir.aziz.karam.pages;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import ir.aziz.karam.manager.ProjectManager;
import ir.aziz.karam.manager.UserManager;
import ir.aziz.karam.types.Project;
import ir.aziz.karam.types.User;

import java.util.List;

public class ProjectListPage extends APage implements IPage {

    @Override
    public void HandleRequest(HttpExchange httpExchange) throws IOException {
        User currentUser = UserManager.getInstance().getCurrentUser();
        List<Project> allProject = ProjectManager.getInstance().getAllProjectsFeasibleByUser(currentUser);
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
        for (Project project : allProject) {
            response += "        <tr>\n"
                    + "            <td>" + project.getId() + "</td>\n"
                    + "            <td> " + project.getTitle() + "</td>\n"
                    + "            <td>" + project.getBudget() + "</td>\n"
                    + "        </tr>\n";
        }
        response += "    </table>\n"
                + "</body>\n"
                + "</html>";
        this.sendPage(httpExchange, response, 200);
    }

}
