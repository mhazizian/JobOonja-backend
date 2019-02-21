/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.iais.proj_ie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.aziz.karam.manager.DataLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author karam
 */
public class ProjectManager {

    private static ProjectManager instance;
    private static List<Project> projects;

    public static ProjectManager getInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    public List<Project> getAllProject() throws IOException {
        if (projects == null) {
            Gson gson = new Gson();
            String rawData = DataLoader.readFromUrlToString("http://142.93.134.194:8000/joboonja/project");
            projects = gson.fromJson(rawData, new TypeToken<List<Project>>() {
            }.getType());
        }
        return projects;
    }
}
