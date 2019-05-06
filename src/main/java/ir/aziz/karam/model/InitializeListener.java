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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import ir.aziz.karam.model.manager.UserManager;
import ir.aziz.karam.model.types.SkillUser;
import ir.aziz.karam.model.types.User;
import org.apache.log4j.Logger;

@WebListener
public class InitializeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger.getLogger(InitializeListener.class).info("On startUp web app");
        try {
            SkillMapper.getInstance();

            ProjectMapper.getInstance();
            UserMapper.getInstance();

            SkillUserMapper.getInstance();
            EndorsmentMapper.getInstance();
            BidMapper.getInstance();


            List<SkillUser> tempSkills = new ArrayList<>();
            tempSkills.add(new SkillUser("HTML", "1", 5));
            tempSkills.add(new SkillUser("Javascript","1", 4));
            tempSkills.add(new SkillUser("MySQL","1", 4));
            tempSkills.add(new SkillUser("SQL","1", 4));
            tempSkills.add(new SkillUser("C", "1",3));
            tempSkills.add(new SkillUser("Java", "1",3));
            User user = new User("1", "علی", "شریف زاده", "برنامه نویس وب", null, tempSkills, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه  ولی پول نداشت");


            UserManager.getInstance().addUser(user);

            List<SkillUser> tempSkills2 = new ArrayList<>();
            tempSkills2.add(new SkillUser("HTML", "1", 5));
            tempSkills2.add(new SkillUser("Javascript","1", 4));
//            tempSkills2.add(new SkillUser("MySQL","1", 4));
//            tempSkills2.add(new SkillUser("SQL","1", 4));
            tempSkills2.add(new SkillUser("C", "1",3));
            tempSkills2.add(new SkillUser("Java", "1",3));
            user = new User("2", "مهدی", "کرمی", "برنامه نویس فول استک!!", null, tempSkills, "روی سنگ قبرم ننویسید: خدا بیامرز میخواست خیلی کارا بکنه  ولی پول نداشت");

            UserManager.getInstance().addUser(user);


            Timer timer = new Timer();
            timer.schedule(new UpdateDataCenterScheduler(), 0, 30000);

        } catch (SQLException e) {
            Logger.getLogger(InitializeListener.class).error(e, e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger.getLogger(InitializeListener.class).info("On sutdown web app");
    }

    class UpdateDataCenterScheduler extends TimerTask {

        @Override
        public void run() {
            try {
                SkillManager.getInstance().updateSkillListFromServer();
                ProjectManager.getInstance().updateProjectListFromServer();
            } catch (IOException | SQLException e) {
                Logger.getLogger(InitializeListener.class).error(e, e);
            }
        }
    }

}
