/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.manager;

import ir.aziz.karam.exception.ProjectNotFoundException;
import ir.aziz.karam.exception.SkillPointIsNotEnoghException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.aziz.karam.exception.SkillNotFoundException;
import ir.aziz.karam.types.Project;
import ir.aziz.karam.types.Skill;
import ir.aziz.karam.types.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProjectManager {

    private static ProjectManager instance;
    private static List<Project> projects;
    final String ProjectsAPI = "http://142.93.134.194:8000/joboonja/project";

    public static ProjectManager getInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    public List<Project> getAllProject() throws IOException {
        if (projects == null) {
            Gson gson = new Gson();
            String rawData = DataLoader.readFromUrlToString(ProjectsAPI);
            projects = gson.fromJson(rawData, new TypeToken<List<Project>>() {
            }.getType());
        }
        return projects;
    }


    public List<Project> getAllProjectsFeasibleByUser(User user) throws IOException {
        List<Project> projects = this.getAllProject();
        List<Project> result = new ArrayList<>();
        for (Project project : projects) {
            try{
                userCanSolveProject(user, project);
                result.add(project);
            } catch (SkillNotFoundException | SkillPointIsNotEnoghException ignored) {}
        }
        return result;
    }

    public Project getProjectById(String id) throws IOException, ProjectNotFoundException {
        List<Project> allProjects = getAllProject();
        for (Project project : allProjects) {
            if (project.getId().equals(id)) {
                return project;
            }
        }
        throw new ProjectNotFoundException("project not found!");
    }

//    private boolean userCanSolveProjectWithoutExp(User user, Project project) throws SkillNotFoundException {
//
//        for (int i = 0; i < project.getSkills().size(); i++) {
//            Skill skillOfUser = UserManager.getInstance().getSkillOfUser(user, project.getSkills().get(i));
//            if (skillOfUser.getPoints() < project.getSkills().get(i).getPoints()) {
//                return false;
//            }
//        }
//        return true;
//
//    }

    public void userCanSolveProject(User user, Project project) throws SkillNotFoundException, SkillPointIsNotEnoghException {
        for (int i = 0; i < project.getSkills().size(); i++) {
            Skill skillOfUser = UserManager.getInstance().getSkillOfUser(user, project.getSkills().get(i));
            if (skillOfUser.getPoints() < project.getSkills().get(i).getPoints()) {
                throw new SkillPointIsNotEnoghException(skillOfUser.getName() + " point is not enough for this project!");
            }
        }
    }
}
