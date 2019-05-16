
import ir.aziz.karam.model.dataLayer.dataMappers.bid.BidMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.endorsment.EndorsmentMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.project.ProjectMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.skill.SkillMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.skillUser.SkillUserMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.user.UserMapper;
import ir.aziz.karam.model.manager.ProjectManager;
import ir.aziz.karam.model.manager.SkillManager;

import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class TEST {
    public static void main(String[] args) throws SQLException {
        SkillMapper.getInstance();

        ProjectMapper.getInstance();
        UserMapper.getInstance();

        SkillUserMapper.getInstance();
        EndorsmentMapper.getInstance();
        BidMapper.getInstance();

        try {
            ProjectManager.getInstance().updateProjectListFromServer();
            SkillManager.getInstance().updateSkillListFromServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
