/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.aziz.karam.types.Skill;

import java.io.IOException;
import java.util.List;

public class SkillManager {

    private final String skillsAPI = "http://142.93.134.194:8000/joboonja/skill";
    private static SkillManager instance;
    private static List<Skill> skills;

    public static SkillManager getInstance() {
        if (instance == null) {
            instance = new SkillManager();
        }
        return instance;
    }

    public List<Skill> getAllSkills() throws IOException {
        if (skills == null) {
            Gson gson = new Gson();
            String rawData = DataLoader.readFromUrlToString(skillsAPI);
            skills = gson.fromJson(rawData, new TypeToken<List<Skill>>() {
            }.getType());
        }
        return skills;
    }
}
