/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.aziz.karam.model.dataLayer.dataMappers.skill.SkillMapper;
import ir.aziz.karam.model.types.Skill;
import ir.aziz.karam.model.types.SkillUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SkillManager {

    private final String skillsAPI = "http://142.93.134.194:8000/joboonja/skill";
    private static SkillManager instance;
    private static List<SkillUser> skills;

    public static SkillManager getInstance() {
        if (instance == null) {
            instance = new SkillManager();
        }
        return instance;
    }

    public List<Skill> getAllSkills() throws IOException, SQLException {
        return SkillMapper.getInstance().getAll();
    }

    public void updateSkillListFromServer() throws IOException, SQLException {
        List<Skill> skills;
        Gson gson = new Gson();
        String rawData = DataLoader.readFromUrlToString(skillsAPI);
        skills = gson.fromJson(rawData, new TypeToken<List<Skill>>() {
        }.getType());

        for (Skill skill: skills) {
            SkillMapper.getInstance().insertOrUpdate(skill);
        }

    }
}
