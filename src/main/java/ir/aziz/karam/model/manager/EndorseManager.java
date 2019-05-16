/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.manager;

import ir.aziz.karam.model.dataLayer.dataMappers.endorsment.EndorsmentMapper;
import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.types.Endorse;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


public class EndorseManager {

    private static EndorseManager instance;

    public static EndorseManager getInstance() {
        if (instance == null) {
            instance = new EndorseManager();
        }
        return instance;
    }
    
    public List<String> getEndorses(String endorsedUser, String endorser) {
        try {


            List<Endorse> endorses = EndorsmentMapper.getInstance().getSkillsEndorsedByUserOnUser(endorser, endorsedUser);
            List<String> endorseNames = new ArrayList<>();
            for (Endorse endors : endorses) {
                endorseNames.add(endors.getSkill());
            }
            return endorseNames;
        } catch (SQLException ex) {
            Logger.getLogger(EndorseManager.class).error(ex, ex);
            return new ArrayList<>();
        }
    }
}
