/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DataLoader {

    public static String readFromUrlToString(String url) throws IOException {
        String result = "";
        URL oracle = new URL(url);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }
        }
        return result;
    }
}
