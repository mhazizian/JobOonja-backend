/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.manager;

import ir.aziz.karam.model.types.Skill;
import ir.aziz.karam.model.types.User;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.exception.ReapeatSkillAddedToUserException;
import ir.aziz.karam.model.types.Endorse;

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
            List<Skill> tempSkills = new ArrayList<>();
            tempSkills.add(new Skill("html", 5));
            tempSkills.add(new Skill("Javascrpipt", 4));
            tempSkills.add(new Skill("C", 1));
            tempSkills.add(new Skill("Java", 20));
            User user = new User("2", "مهدی", "کرمی", "برنامه نویس وب", null, tempSkills, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه  ولی پول نداشت");
            users.add(user);
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
            if (skill.getName().equals(user.getSkills().get(i).getName())) {
                return user.getSkills().get(i);
            }
        }
        throw new SkillNotFoundException(skill.getName() + " skill not found!");
    }
    
    public Skill getSkillOfUserBySkillName(User user, String skill) throws SkillNotFoundException {
        for (int i = 0; i < user.getSkills().size(); i++) {
            if (skill.equals(user.getSkills().get(i).getName())) {
                return user.getSkills().get(i);
            }
        }
        throw new SkillNotFoundException(skill + " skill not found!");
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

    public void deleteASkillFromAUser(User user, String skillName) {
        for (int i = 0; i < user.getSkills().size(); i++) {
            if (skillName.equals(user.getSkills().get(i).getName())) {
                user.getSkills().remove(i);
            }
        }
    }

    public void addASkillFromAUser(User user, String skillName) throws ReapeatSkillAddedToUserException {
        for (int i = 0; i < user.getSkills().size(); i++) {
            if (skillName.equals(user.getSkills().get(i).getName())) {
                throw new ReapeatSkillAddedToUserException(skillName + " is now assigned to this user");
            }
        }
        List<Skill> skills = user.getSkills();
        skills.add(new Skill(skillName, 0));
        user.setSkills(skills);
    }
    
    public boolean userEndorseThisEndorse(User user, String userName, String skillName) {
        Endorse endorse = new Endorse(userName, skillName);
        for (int i = 0; i < user.getEndorses().size(); i++) {
            if(endorse.getSkill().equals(user.getEndorses().get(i).getSkill()) && endorse.getUserIsEndorsed().equals(user.getEndorses().get(i).getUserIsEndorsed())) {
                return true;
            }
        }
        return false;
    }
}
