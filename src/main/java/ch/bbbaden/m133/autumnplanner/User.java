/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.autumnplanner;

/**
 *
 * @author Alexander
 */
public class User {
    private String userName;
    private String passWord;
    private String holidayDestination;
    private String activity;
    private int id;
    

    public User(String userName, String passWord, int id, String holidayDestination, String activity) {
        this.userName = userName;
        this.passWord = passWord;
        this.holidayDestination = holidayDestination;
        this.activity = activity;
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public String getHolidayDestination() {
        return holidayDestination;
    }
    
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

}
