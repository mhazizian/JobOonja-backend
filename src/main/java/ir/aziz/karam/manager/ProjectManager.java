/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.manager;

import ir.aziz.karam.exception.UserNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.aziz.karam.exception.SkillNotFoundException;
import ir.aziz.karam.types.Project;
import ir.aziz.karam.types.User;
import java.io.IOException;
import java.util.List;

public class ProjectManager {

    private static ProjectManager instance;
    private static List<Project> projects;

    public static ProjectManager getInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    public List<Project> getAllProject() throws IOException {
        if (projects == null) {
            Gson gson = new Gson();
            String rawData = DataLoader.readFromUrlToString("http://142.93.134.194:8000/joboonja/project");
            projects = gson.fromJson(rawData, new TypeToken<List<Project>>() {
            }.getType());
        }
        return projects;
    }
    
    public Project getProjectById(String id) throws IOException, UserNotFoundException {
        List<Project> allProjects = getAllProject();
        for (int i = 0; i < allProjects.size(); i++) {
            if(allProjects.get(i).getId().equals(id)) {
                return allProjects.get(i);
            }
        }
        throw new UserNotFoundException("user not found!");
    }
    
    public void userCanSolveProject(User user, Project project) throws SkillNotFoundException{
        for (int i = 0; i < project.getSkills().size(); i++) {
            UserManager.getInstance().getSkillOfUser(user, project.getSkills().get(i));
        }
    }
}
