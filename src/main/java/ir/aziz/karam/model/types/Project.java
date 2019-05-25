/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

import ir.aziz.karam.model.dataLayer.dataMappers.bid.BidMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.project.ProjectMapper;
import ir.aziz.karam.model.dataLayer.dataMappers.projectSkill.ProjectSkillMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author karam
 */
public class Project {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private int winnerId;
    private int budget;
    private long deadline;
    private long creationDate;

    private List<ProjectSkill> skills;
    private List<Bid> bids;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProjectSkill> getSkills() throws SQLException {
        this.skills = ProjectSkillMapper.getInstance().getSkillsOfProject(this);
        return skills;
    }

    public List<ProjectSkill> getSkillsPermanently() {
        return this.skills;
    }

    public void setSkills(List<ProjectSkill> skills) {
        this.skills = skills;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrption() {
        return description;
    }

    public void setDescrption(String descrption) {
        this.description = descrption;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String imageURL) {
        this.imageUrl = imageURL;
    }

    public List<Bid> getBids() throws SQLException {
        this.bids = BidMapper.getInstance().getProjectBids(this.id);
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) throws SQLException {
        BidMapper.getInstance().insert(bid);
//        if (this.bids == null)
//            this.bids = new ArrayList<>();
//        this.bids.add(bid);
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public Project(String title, List<ProjectSkill> skills, int budget) {
        this.title = title;
        this.skills = skills;
        this.budget = budget;
        this.bids = new ArrayList<>();
    }


    public Project(String id, String title, String description, String imageUrl, int budget, long deadline, long creationDate, int winnerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.budget = budget;
        this.deadline = deadline;
        this.creationDate = creationDate;
        this.winnerId = winnerId;
    }

    public boolean hasBided(User user) throws SQLException {
        this.bids = this.getBids();
        if (this.bids == null) {
            return false;
        }

        System.out.println(this.bids.size());
        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getBiddingUser() == user.getId()) {
                return true;
            }
        }
        return false;
    }

    public void runAuction() throws SQLException {
        this.getBids();
        int maxAmount = -1, userId = 0;
        for (Bid bid : this.bids) {
            if (bid.getBidAmount() > maxAmount) {
                maxAmount = bid.getBidAmount();
                userId = bid.getBiddingUser();
            }
        }
        if (maxAmount >= 0) {
            this.winnerId = userId;
            ProjectMapper.getInstance().update(this);

            System.out.println("Aucted on " + this.id);
        }
    }
}
