/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;

/**
 *
 * @author Abdallah Saboukh
 */
@Stateless
public class TrendingLanguagesService implements TrendingLanguagesServiceLocal {

    @Override
    public List<Languages> findTrendingLanguges() throws Exception {

        URL url = new URL("https://api.github.com/search/repositories?q=created:%3E2020-12-10&sort=stars&order=desc&page=1&per_page=100");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);

        //Reading the Response
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        List<Languages> languageList = new ArrayList<>();
        StringBuffer content = new StringBuffer();
        for (String inputLine; (inputLine = in.readLine()) != null;) {
            content.append(inputLine);
            //  convertJsonToArray(inputLine);
            languageList = prepareLanguagesList(inputLine);
        }
        in.close();
        con.disconnect();
        languageList.sort((Languages s1, Languages s2) -> s2.getRepoCount() - s1.getRepoCount());
        return languageList;
    }

    private List<Languages> prepareLanguagesList(String inputLine) {
        List<Languages> languagesList = new ArrayList<>();
        List<String> langList = new ArrayList<>();
        HashMap<String, String> repsUrl = new HashMap<String, String>();
        String[] words = inputLine.split(",+");
        String[] wordsUrl = inputLine.split(",+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].concat(",");
        }
        for (int i = 0; i < words.length; i++) {
            if (!words[i].contains("\"language\"")) {
                     words[i] = null;
            } else {
                langList.add(findBetween(words[i], "\"language\": ", ","));
            }
        }

        for (int i = 0; i < wordsUrl.length; i++) {
            wordsUrl[i] = wordsUrl[i].concat(",");
        }
        int count = 0;
        for (int i = 0; i < wordsUrl.length; i++) {
            if (!wordsUrl[i].contains("\"svn_url\"")) {
                wordsUrl[i] = null;
            } else {
                repsUrl.put(findBetween(wordsUrl[i], "\"svn_url\"", ","), String.valueOf(count++));
            }
        }
        Set<String> unrepatedLang = new HashSet<>(langList);
        for (String lang : unrepatedLang) {
            Languages langObj = new Languages();
            langObj.setLangName(lang);
            langObj.setRepoCount(Collections.frequency(langList, lang));
            for (Map.Entry me : repsUrl.entrySet()) {
                if (langList.get( Integer.parseInt((String)me.getValue())).equals(lang)) {
                    langObj.getReposList().add(me.getKey());
                }
            }
            languagesList.add(langObj);
        }

        return languagesList;
    }

    private String findBetween(String inputText, String from, String to) {
        String result = null;
        result = inputText.substring(inputText.indexOf(from) + from.length(), inputText.indexOf(to));
        return result;
    }

    private void convertJsonToArray(String data) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
//read JSON like DOM Parser

            byte[] jsonData = data.getBytes();
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode idNode = rootNode.path("language");
            System.out.println("id = " + idNode.asInt());

            JsonNode phoneNosNode = rootNode.path("language");
            Iterator<JsonNode> elements = phoneNosNode.elements();
            while (elements.hasNext()) {
                JsonNode phone = elements.next();
                System.out.println("language = " + phone.asLong());
            }

        } catch (Exception e) {
        }
    }

}
