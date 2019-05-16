/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.manager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
        try {
            return UserMapper.getInstance().find("1");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addUser(User user) throws SQLException {
        UserMapper.getInstance().insertOrUpdate(user);

        for (SkillUser skillUser : user.getSkillsPermanently()) {
            SkillUserMapper.getInstance().insertOrUpdate(skillUser);
        }
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

    public String convertPasswordToHash(String passwordToHash) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String createJWTToken(String userId) throws IllegalArgumentException, JWTCreationException {
        Date expiresAt = new Date(System.currentTimeMillis() + 1000 * 3600 * 24);
        Date currentDate = new Date();
        Algorithm algorithm = Algorithm.HMAC256("joboonja");
        String token = JWT.create()
                .withIssuer("joboonja.com")
                .withExpiresAt(expiresAt)
                .withIssuedAt(currentDate)
                .withClaim("userId", userId)
                .sign(algorithm);
        return token;
    }

}
