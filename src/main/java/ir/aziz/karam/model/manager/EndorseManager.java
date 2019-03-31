/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.manager;

import ir.aziz.karam.model.exception.UserNotFoundException;
import ir.aziz.karam.model.types.Endorse;
import ir.aziz.karam.model.types.User;
import java.io.IOException;
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
    
    public List<String> getEndorses(String isEndorsed, String endorser) {
        try {
            User userById = UserManager.getInstance().getUserById(endorser);
            List<Endorse> endorses = userById.getEndorses();
            List<String> endorseNames = new ArrayList<>();
            for (int i = 0; i < endorses.size(); i++) {
                if(endorses.get(i).getUserIsEndorsed().equals(isEndorsed)) {
                    endorseNames.add(endorses.get(i).getSkill());
                }
            }
            return endorseNames;
        } catch (IOException | UserNotFoundException ex) {
            Logger.getLogger(EndorseManager.class).error(ex, ex);
            return new ArrayList<>();
        }
    }
}
