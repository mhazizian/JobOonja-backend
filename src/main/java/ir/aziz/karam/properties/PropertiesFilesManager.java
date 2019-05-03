/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.properties;

import java.io.IOException;
import java.util.Properties;

public class PropertiesFilesManager {

    private static PropertiesFilesManager instance;
    private Properties projectProperties;
    private String dbAddress;

    private Properties getProjectProperties() {
        if (projectProperties == null) {
            try {
                projectProperties = new Properties();
                projectProperties.load(this.getClass().getResourceAsStream("/project.properties"));
            } catch (IOException ex) {
                org.apache.log4j.Logger.getLogger(this.getClass()).fatal(ex, ex);
            }
        }
        return projectProperties;
    }

    private PropertiesFilesManager() {

    }

    public static PropertiesFilesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesFilesManager();
        }
        return instance;
    }

    public String getDbAddress() {
        if (dbAddress == null) {
            dbAddress = this.getProjectProperties().getProperty("dbAddress", "taca7.db");
        }
        return dbAddress;
    }
}
