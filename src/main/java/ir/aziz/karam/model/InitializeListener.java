package ir.aziz.karam.model;

import ir.aziz.karam.model.dataLayer.dataMappers.bid.BidMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.endorsment.EndorsmentMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.project.ProjectMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.skill.SkillMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.skillUser.SkillUserMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.user.UserMapper;
import ir.aziz.karam.model.manager.ProjectManager;
import ir.aziz.karam.model.manager.SkillManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.SQLException;

@WebListener
public class InitializeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("On start web app");
        try {
            SkillMapper.getInstance();

            ProjectMapper.getInstance();
            UserMapper.getInstance();

            SkillUserMapper.getInstance();
            EndorsmentMapper.getInstance();
            BidMapper.getInstance();

            SkillManager.getInstance().updateSkillListFromServer();
            ProjectManager.getInstance().updateProjectListFromServer();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("On shutdown web app");
    }

}