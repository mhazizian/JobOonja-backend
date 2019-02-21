/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.iais.proj_ie;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ir.aziz.karam.server.JobOonJaHttpServer;
import javafx.util.Pair;
import org.apache.log4j.Logger;

/**
 *
 * @author karam
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
//        List<User> users = new ArrayList<>();
        List<Project> projects = ProjectManager.getInstance().getAllProject();

        JobOonJaHttpServer jobOonJaHttpServer = new JobOonJaHttpServer();
        jobOonJaHttpServer.startServer();

//        List<Bid> bids = new ArrayList<>();
//        Gson gson = new Gson();
//        whileLoop:
//        while (true) {
////            Pair<String, String> commandParts = getCommandParts();
////            String commandType = commandParts.getKey();
////            String inputWithoutCommand = commandParts.getValue();
//            switch (commandType) {
//                case "register":
//                    User userInfo = gson.fromJson(inputWithoutCommand, User.class);
//                    users.add(userInfo);
//                    break;
//                case "addProject":
//                    Project project = gson.fromJson(inputWithoutCommand, Project.class);
//                    projects.add(project);
//                    break;
//                case "bid":
//                    Bid bid = gson.fromJson(inputWithoutCommand, Bid.class);
//                    bids.add(bid);
//                    break;
//                case "auction":
//                    auctionWork(gson, inputWithoutCommand, bids, projects, users);
//                    break whileLoop;
//                default:
////                    Logger.getLogger("Main").info("invalid command!");
//                    break;
//            }
//        }
    }

    private static void auctionWork(Gson gson, String inputWithoutCommand, List<Bid> bids, List<Project> projects, List<User> users) throws Exception, JsonSyntaxException {
        Auction auction = gson.fromJson(inputWithoutCommand, Auction.class);
        List<Bid> specBids = findSpecBidsWithProjectName(bids, auction);
        Project auctionedProject = findProjectWithName(auction, projects);
        int maxValue = 0;
        String winner = "";
        outFor:
        for (int i = 0; i < specBids.size(); i++) {
            Bid bid1 = specBids.get(i);
            User findUserWithName = findUserWithName(users, bid1);
            int val = 0;
            for (int j = 0; j < auctionedProject.getSkills().size(); j++) {
                try {
                    Skill skillPeople = findSkill(findUserWithName, auctionedProject.getSkills().get(j).getName());
                    int num =  skillPeople.getPoints() - auctionedProject.getSkills().get(j).getPoints();
                    if(num < 0) {
//                        Logger.getLogger("Main").info(findUserWithName.getUsername() + " not have " + skillPeople.getName());
                        continue outFor;
                    }
                    val += 10000 * num * num;
                } catch (Exception e) {
//                    Logger.getLogger("Main").info(e.getMessage());
                    continue outFor;
                }
            }
            val += auctionedProject.getBudget() - bid1.getBidAmount();
            if (maxValue < val || winner.equals("")) {
                maxValue = val;
                winner = findUserWithName.getUsername();
            }
        }
        System.out.println(winner);
    }

    private static List<Bid> findSpecBidsWithProjectName(List<Bid> bids, Auction auction) {
        List<Bid> specBids = new ArrayList<>();
        for (int i = 0; i < bids.size(); i++) {
            Bid specBid = bids.get(i);
            String projectTitle = specBid.getProjectTitle();
            if (projectTitle.equals(auction.getProjectTitle())) {
                specBids.add(specBid);
            }
        }
        if (specBids.isEmpty()) {
//            Logger.getLogger("Main").info("not found any bid for this project!");
        }
        return specBids;
    }

    private static User findUserWithName(List<User> users, Bid bid1) throws Exception {
        for (int j = 0; j < users.size(); j++) {
            if (users.get(j).getUsername().equals(bid1.getBiddingUser())) {
                return users.get(j);
            }
        }
        throw new Exception("user not found!");
    }

    private static Project findProjectWithName(Auction auction, List<Project> projects) throws Exception {
        for (int i = 0; i < projects.size(); i++) {
            Project specProj = projects.get(i);
            if (specProj.getTitle().equals(auction.getProjectTitle())) {
                return specProj;
            }
        }
        throw new Exception("project not found!");
    }

    private static Skill findSkill(User findUserWithName, String name) throws Exception {
        for (int i = 0; i < findUserWithName.getSkills().size(); i++) {
            if (findUserWithName.getSkills().get(i).getName().equals(name)) {
                return findUserWithName.getSkills().get(i);
            }
        }
        throw new Exception(findUserWithName.getUsername() + " not have " + name + " skill!");
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }
}
