/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.manager;

import ir.aziz.karam.model.dataLayer.dataMappers.endorsment.EndorsmentMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.skillUser.SkillUserMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.user.UserMapper;
import ir.aziz.karam.model.types.SkillUser;
import ir.aziz.karam.model.types.User;
import ir.aziz.karam.model.exception.SkillNotFoundException;
import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.exception.ReapeatSkillAddedToUserException;
import ir.aziz.karam.model.types.Endorse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    private static User currentUser;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public List<User> getAllUsers() throws IOException, SQLException {
        return UserMapper.getInstance().getAll();
    }

    public List<User> getAllUsersWithoutCurrentUser() throws IOException, SQLException {
        return UserMapper.getInstance().getAllUserWithoutCurrent(getCurrentUser().getId());
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            List<SkillUser> tempSkills = new ArrayList<>();
            tempSkills.add(new SkillUser("HTML", 5));
            tempSkills.add(new SkillUser("Javascript", 4));
            tempSkills.add(new SkillUser("C++", 3));
            tempSkills.add(new SkillUser("Java", 3));
            currentUser = new User("1", "علی", "شریف زاده", "برنامه نویس وب", null, tempSkills, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه  ولی پول نداشت");
        }
        return currentUser;
    }

    public SkillUser getSkillOfUserBySkillName(User user, String skillName) throws SkillNotFoundException {
        return user.getUserSkillByName(skillName);
    }

    public User getUserById(String id) throws UserNotFoundException {
        try {
            return UserMapper.getInstance().find(id);
        } catch (SQLException e) {
            throw new UserNotFoundException("user not found!");
        }

    }

    public void deleteASkillFromAUser(User user, String skillName) throws SQLException {
        SkillUserMapper.getInstance().deleteSkillUser(skillName, user.getId());
    }

    public void addASkillFromAUser(User user, String skillName) throws ReapeatSkillAddedToUserException, SQLException {
        List<SkillUser> skills = user.getSkills();
        for (SkillUser skill : skills) {
            if (skillName.equals(skill.getName())) {
                throw new ReapeatSkillAddedToUserException(skillName + " is now assigned to this user");
            }
        }

        try {
            SkillUserMapper.getInstance().insert(new SkillUser(skillName, user.getId(), 0));
        } catch (SQLException e) {
            throw new ReapeatSkillAddedToUserException(skillName + " is now assigned to this user");
        }
    }

    public boolean hasUserEndorsedThisUser(User endorser, String userName, String skillName) {
        try {

            EndorsmentMapper.getInstance().find(endorser.getId(), userName, skillName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
