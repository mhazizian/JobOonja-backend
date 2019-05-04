/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

public class ProjectSkill {


    private String name; // skill name
    private String project_id;
    private int point; // reqPoint

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getSkill_id() {
        return name;
    }

    public void setSkill_id(String skill_id) {
        this.name = skill_id;
    }

    public int getReqPoints() {
        return point;
    }

    public void setReqPoints(int reqPoints) {
        this.point = reqPoints;
    }

    public ProjectSkill(String project_id, String skill_id, int reqPoints) {
        this.project_id = project_id;
        this.name = skill_id;
        this.point = reqPoints;
    }

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
    public void setPoint(int point) {this.point= point;}

}
