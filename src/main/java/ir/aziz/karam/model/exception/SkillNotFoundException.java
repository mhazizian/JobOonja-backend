/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.exception;

public class SkillNotFoundException extends Exception {

    public SkillNotFoundException(String skill_not_found) {
        super(skill_not_found);
    }
    
}
