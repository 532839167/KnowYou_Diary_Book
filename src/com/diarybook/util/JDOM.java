package com.diarybook.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 * read and write xml files
 */
public class JDOM {

    // register user info
    public static String write(String n, String p, String id) {

        String path = "C:\\Users\\vasil\\Desktop\\project\\diary\\UserInfo.xml";

        File file = new File(path);
        // sax parser: parse xml to document
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc;
        try {
            doc = saxBuilder.build(file);

            // create elements (tags) in file
            Element root = doc.getRootElement(); // get root element node
            Element user = new Element("User");
            Element name = new Element("name");
            Element passwd = new Element("passwd");

            /* check if the id already exist */
            if (checkID(id, root)) {
                // set ID as an attribute of user
                user.setAttribute(new Attribute("id", id));
                // set name and password
                name.setText(n);
                passwd.setText(p);

                // add name, password to user
                user.addContent(name);
                user.addContent(passwd);

                root.addContent(user);

                //output xml
                XMLOutputter out = new XMLOutputter();
                out.output(doc, new FileOutputStream(file));
                return "Successfully registered";
            } else
                // fail - already exist
                return "ID already exists, please input again";

        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "ERROR";

    }

    // check if id exist
    public static boolean checkID(String id, Element root) {
        // check if ID exist
        boolean flag = true;

        // get all sub elements for User tag in a map
                List<Element> list = root.getChildren("User");
        // check if ID exist
        Iterator<Element> it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            if (e.getAttributeValue("id").equals(id)) {
                flag = false;
            }
        }
        return flag;

    }

    //read xml
    public static String read(String id, String passwd) {

        String path = "C:\\Users\\vasil\\Desktop\\project\\diary\\UserInfo.xml";
        File file = new File(path);
        SAXBuilder saxBuilder = new SAXBuilder();

        try {
            Document doc = saxBuilder.build(file);
            Element root = doc.getRootElement();

            // get username and pass
            String info = getPasswd(root).get(id);
            if (info == null) {
                return "User does not exist!!";
            }
            String[] buf = info.split("/");

            if (buf[0].equals(passwd)) {
                return "Successful landing/" + buf[1];
            }

        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Wrong password!!";
    }

    /* add user and password to the map */
    private static Map<String, String> getPasswd(Element root) {
        // map for user info
        Map<String, String> map = new TreeMap<String, String>();
        List<Element> list = new ArrayList<Element>();

        list = root.getChildren("User");
        Iterator<Element> it = list.iterator();
        while (it.hasNext()) {
            Element e = it.next();
            String id = e.getAttributeValue("id");
            String passwd = e.getChildText("passwd");
            String name = e.getChildText("name");
            map.put(id, getInfo(passwd, id));
        }

        return map;

    }


    private static String getInfo(String passwd, String name) {

        return passwd + "/" + name;

    }
}
