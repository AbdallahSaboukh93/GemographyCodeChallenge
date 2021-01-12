/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Abdallah Saboukh
 */
@Named(value = "trendingLanguagesBB")
@ViewScoped
public class TrendingLanguagesBB implements Serializable {

    @EJB
    private TrendingLanguagesServiceLocal trendingLanguagesService;
    private  List<Languages> trendingLanguagesList =new ArrayList();

    @PostConstruct
    public void init(){
        showtrendingLanguages();
    }
    
    public void showtrendingLanguages() {
        try {
            trendingLanguagesList = trendingLanguagesService.findTrendingLanguges();
        } catch (Exception e) {
            System.out.println("com.TrendingLanguagesBB.showtrendingLanguages()");
        }
    }

    public List<Languages> getTrendingLanguagesList() {
        return trendingLanguagesList;
    }

    public void setTrendingLanguagesList(List<Languages> trendingLanguagesList) {
        this.trendingLanguagesList = trendingLanguagesList;
    }

  




}
