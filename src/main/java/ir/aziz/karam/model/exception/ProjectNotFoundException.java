/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.exception;

/**
 *
 * @author mahdi
 */
public class ProjectNotFoundException extends Exception {

    public ProjectNotFoundException(String project_not_found) {
        super(project_not_found);
    }
    
}
