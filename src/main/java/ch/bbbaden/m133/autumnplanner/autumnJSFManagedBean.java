/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.autumnplanner;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.jdom2.JDOMException;

/**
 *
 * @author Alexander
 */
@Named(value = "autumn")
@SessionScoped
public class autumnJSFManagedBean implements Serializable {

    private String userLogin;
    
    private final String path1 = "/faces/index.xhtml";
    private final String path2 = "/secured/indexDecision.xhtml";
    private final String path3 = "/secured/indexStayAtHome.xhtml";
    private final String path4 = "/secured/indexGoingAbroad.xhtml";
    private final String path5 = "/secured/indexTable.xhtml";
    
    private String passWord;
    private final LoginDAO ldao = new LoginDAO();
    private User user;
    private String setting;
    private String activity;
    private final String[] activities = {"Read books", "Play videogames", "Watch Movies"};
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Please enter your text of the message correctly")
    @NotNull(message = "Please enter your text of the message")
    private String holidayDestination;   
    private boolean loggedIn;
    private List<User> data;

    /**
     * Creates a new instance of autumnJSFManagedBean
     */
    public autumnJSFManagedBean() {
    }

    public String[] getActivities() {
        return activities;
    }

    public String getUserlogin() {
        return userLogin;
    }

    public void setUserlogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getPassword() {
        return passWord;
    }

    public void setPassword(String password) {
        this.passWord = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getSetting() {
        return setting;
    }

    public String getHolidayDestination() {
        return holidayDestination;
    }

    public void setHolidayDestination(String holidayDestination) {
        this.holidayDestination = holidayDestination;
    }
    
    public void setSetting(String setting) {
        this.setting = setting;
    }
    
    public String doLogout() {
        this.loggedIn = false;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        autumnJSFManagedBean login = new autumnJSFManagedBean();
        externalContext.getSessionMap().put("log", login);
        return path1;
    }
    
    public String loadnextpage(int buttonNumber) throws JDOMException, IOException {
        this.user = ldao.check(userLogin, passWord);

        switch (buttonNumber) {
            case 1:
                if (this.user != null) {
                    this.loggedIn = true;
                    return path2;
                }
                break;
            case 2:
                if (setting.equals("staytAtHome")) {
                    return path3;
                }
                return path4;
            case 3:
                return path5;
            case 4:
                return path5;
            default:
                return path1;
        }
        return path1;
    }

    public List<User> getData() throws JDOMException, IOException {
        data = this.ldao.getData();
        return data;
    }

    public void textinput() throws IOException {
        ldao.setInput(this.userLogin, this.holidayDestination, this.activity);
    }
}
