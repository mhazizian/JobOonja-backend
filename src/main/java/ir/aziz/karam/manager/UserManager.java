/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.manager;

import ir.aziz.karam.exception.SkillNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.aziz.karam.exception.UserNotFoundException;
import ir.aziz.karam.types.Skill;
import ir.aziz.karam.types.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    private static List<User> users;
    private static User currentUser;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public List<User> getAllUsers() throws IOException {
        if (users == null) {
            users = new ArrayList<>();
            users.add(getCurrentUser());
        }
        return users;
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            List<Skill> tempSkills = new ArrayList<>();
            tempSkills.add(new Skill("HTML", 5));
            tempSkills.add(new Skill("Javascript", 4));
            tempSkills.add(new Skill("C++", 3));
            tempSkills.add(new Skill("Java", 3));
            currentUser = new User("1", "علی", "شریف زاده", "برنامه نویس وب", null, tempSkills, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه  ولی پول نداشت");
        }
        return currentUser;
    }

    public Skill getSkillOfUser(User user, Skill skill) throws SkillNotFoundException {
        for (int i = 0; i < user.getSkills().size(); i++) {
            if(skill.getName().equals(user.getSkills().get(i).getName())) {
                return user.getSkills().get(i);
            }
        }
        throw new SkillNotFoundException( skill.getName() + " skill not found!");
    }

    public User getUserById(String id) throws IOException, UserNotFoundException {
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("user not found!");
    }
}
