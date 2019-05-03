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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
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
