/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.autumnplanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.jdom2.Document;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Alexander
 */
public class LoginDAO {

    private SAXBuilder builder;
    private File xmlFile;
    private List<User> data;

    public LoginDAO() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String path01 = path + "WEB-INF\\DataLogin.xml";
        xmlFile = new File(path01);
        builder = new SAXBuilder();
    }

    public User check(String user, String password) throws JDOMException, IOException {

        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        List list = rootNode.getChildren("User");
        for (int i = 0; i < list.size(); i++) {
            Element node = (Element) list.get(i);
            if (node.getChildText("Name").equals(user) && node.getChildText("Password").equals(password)) {
                return new User(user,password, Integer.parseInt(node.getChildText("ID")), node.getChildText("HolidayDestination"), node.getChildText("Activity"));
            }
        }
        System.out.println("Check");
        return null;
    }
    
    public List getData() throws JDOMException, IOException {
        data = new ArrayList<User>();
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        List list = rootNode.getChildren("User");
        for (int i = 0; i < list.size(); i++) {
            Element node = (Element) list.get(i);
            data.add(new User(node.getChildText("Name"),node.getChildText("Password"), Integer.parseInt(node.getChildText("ID")), node.getChildText("HolidayDestination"), node.getChildText("Activity")));
        }
        System.out.println("flop");
        return data;
    }
    
    public boolean setInput(String name, String holidayDestination, String activity) throws IOException {
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element eintrag = new Element("User");
            List list = rootNode.getChildren("User");
            eintrag.addContent(new Element("ID").setText("" + getAufzaehlungid(list, document, rootNode)));
            eintrag.addContent(new Element("Name").setText(name));
            eintrag.addContent(new Element("Activity").setText(""+activity));
            eintrag.addContent(new Element("HolidayDestination").setText(""+holidayDestination));
            rootNode.addContent(eintrag);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(xmlFile));

            return true;
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    
    }
    public int getAufzaehlungid(List list, Document documen, Element rootNode) {
        //Vergleichswert setzen
        int aufzaehlungid = Integer.MAX_VALUE;
        Element node = (Element) list.get(0);
        aufzaehlungid = Integer.parseInt(node.getChildText("ID"));
        for (int i = 0; i < list.size(); i++) {
            node = (Element) list.get(i);
            if (aufzaehlungid < Integer.parseInt(node.getChildText("ID"))) {
                aufzaehlungid = Integer.parseInt(node.getChildText("ID"));
            }
        }
        return aufzaehlungid + 1;
    }
}
