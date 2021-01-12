package com;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdallah Saboukh
 */
public class Languages {
    private String langName ;
    private int   repoCount ;
    private List<String> reposList =new ArrayList(); 

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public int getRepoCount() {
        return repoCount;
    }

    public void setRepoCount(int repoCount) {
        this.repoCount = repoCount;
    }

    public List getReposList() {
        return reposList;
    }

    public void setReposList(List reposList) {
        this.reposList = reposList;
    }
    
    
    
}
