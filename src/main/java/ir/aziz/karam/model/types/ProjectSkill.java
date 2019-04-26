/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

public class ProjectSkill {

    private String project_id;
    private String skill_id;
    private int reqPoints;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(String skill_id) {
        this.skill_id = skill_id;
    }

    public int getReqPoints() {
        return reqPoints;
    }

    public void setReqPoints(int reqPoints) {
        this.reqPoints = reqPoints;
    }

    public ProjectSkill(String project_id, String skill_id, int reqPoints) {
        this.project_id = project_id;
        this.skill_id = skill_id;
        this.reqPoints = reqPoints;
    }

}
