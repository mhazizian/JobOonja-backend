/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.manager;

import ir.aziz.karam.model.dataLayer.dataMappers.project.ProjectMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.projectSkill.ProjectSkillMapper;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.ProjectSkill;
import ir.aziz.karam.model.types.SkillUser;
import ir.aziz.karam.model.types.User;
import ir.aziz.karam.model.exception.ProjectNotFoundException;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.exception.SkillPointIsNotEnoghException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProjectManager {

    private static ProjectManager instance;
    //    private static List<Project> projects;
    final String ProjectsAPI = "http://142.93.134.194:8000/joboonja/project";

    public static ProjectManager getInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    public void updateProjectListFromServer() throws IOException, SQLException {
        List<Project> projects;

        Gson gson = new Gson();
        String rawData = DataLoader.readFromUrlToString(ProjectsAPI);
        projects = gson.fromJson(rawData, new TypeToken<List<Project>>() {
        }.getType());
        for (Project project : projects) {
            try {
                this.addProject(project);
            } catch (Exception ex) {
                Logger.getLogger(ProjectManager.class).error(ex.getMessage());
                ex.printStackTrace();
            }
        }

    }

    public void addProject(Project project) throws SQLException {
//        Logger.getLogger(ProjectManager.class).info(project.getId() + "ثبت خواهد شد.");
        ProjectMapper.getInstance().insertOrUpdate(project);
        for (ProjectSkill projectSkill : project.getSkillsPermanently()) {
            projectSkill.setProject_id(project.getId());


            ProjectSkillMapper.getInstance().insertOrUpdate(projectSkill);
        }
//        Logger.getLogger(ProjectManager.class).info(project.getId() + "ثبت شد.");

    }

    public List<Project> getAllProjectsFeasibleByUser(User user) throws IOException, SQLException {
        List<Project> projects = ProjectMapper.getInstance().getAll();
        List<Project> result = new ArrayList<>();

        for (Project project : projects) {
            try {
                userCanSolveProject(user, project);
                result.add(project);
            } catch (SkillNotFoundException | SkillPointIsNotEnoghException ignored) {
            }
        }

        return result;
    }

    public Project getProjectById(String id) throws ProjectNotFoundException {
        try {
            return ProjectMapper.getInstance().find(id);
        } catch (SQLException e) {
            throw new ProjectNotFoundException("project not found!");
        }
    }

    public void userCanSolveProject(User user, Project project) throws SkillNotFoundException, SkillPointIsNotEnoghException, SQLException {
        for (int i = 0; i < project.getSkills().size(); i++) {
            SkillUser skillOfUser = user.getUserSkillByName(project.getSkillsPermanently().get(i).getSkill_id());

            if (skillOfUser.getPoints() < project.getSkills().get(i).getReqPoints()) {
                throw new SkillPointIsNotEnoghException(skillOfUser.getName() + " point is not enough for this project!");
            }
        }
    }
}
