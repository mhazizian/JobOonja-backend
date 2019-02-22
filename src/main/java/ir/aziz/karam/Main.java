/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam;


import ir.aziz.karam.server.JobOonJaHttpServer;
import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        JobOonJaHttpServer jobOonJaHttpServer = new JobOonJaHttpServer();
        jobOonJaHttpServer.startServer();
    }

}
