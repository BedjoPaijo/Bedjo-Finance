/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellouser.model;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author syahril
 */
public class XmlData {

    Document doc;
    ImageIcon icon;

    public XmlData() {
        setXmlDoc();
        setImageIcon(getIconName());
    }

    private void setXmlDoc() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new URL("http://api.openweathermap.org/data/2.5/weather?q=Malang,id&mode=xml&appid=2de143494c0b295cca9337e1e96b00e0").openStream());

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void setImageIcon(String name) {
        try {
            Image img = ImageIO.read(getClass().getResource("/hellouser/image/"+name+".png"));
            Image newImg = img.getScaledInstance(80 , 80, 0);
            this.icon = new ImageIcon(newImg);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(XmlData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getIconName(){
        NodeList nList = doc.getElementsByTagName("weather");
        Node nNode = nList.item(0);
        Element element = (Element) nNode;

        return "" + element.getAttribute("icon");
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getCity() {
        NodeList nList = doc.getElementsByTagName("city");
        Node nNode = nList.item(0);
        Element element = (Element) nNode;

        return "" + element.getAttribute("name");
    }

    public String getCountry() {
        NodeList nList = doc.getElementsByTagName("city");
        Node nNode = nList.item(0);
        Element element = (Element) nNode;

        return "" + element.getElementsByTagName("country").item(0).getTextContent();
    }

    public String getWheather() {
        NodeList nList = doc.getElementsByTagName("weather");
        Node nNode = nList.item(0);
        Element element = (Element) nNode;

        return "" + element.getAttribute("value");
    }

    public String getSunSet() throws ParseException {
        NodeList nList = doc.getElementsByTagName("city");
        Node nNode = nList.item(0);
        Element element = (Element) nNode;

        String string = element.getElementsByTagName("sun").item(0).getAttributes().item(1).getNodeValue();
        String[] data = string.split("T");
        String[] hour = data[1].split(":");
        int jam = Integer.parseInt(hour[0]);
        int menit = Integer.parseInt(hour[1]);
        for (int i = 0; i < 7; i++) {
            jam++;
            if(jam>24){
                jam = 0;
            }
        }
        
        return ""+jam+":"+menit;
    }
    
    public String getSunRise() throws ParseException {
        NodeList nList = doc.getElementsByTagName("city");
        Node nNode = nList.item(0);
        Element element = (Element) nNode;

        String string = element.getElementsByTagName("sun").item(0).getAttributes().item(0).getNodeValue();
        String[] data = string.split("T");
        String[] hour = data[1].split(":");
        int jam = Integer.parseInt(hour[0]);
        int menit = Integer.parseInt(hour[1]);
        for (int i = 0; i < 7; i++) {
            jam++;
            if(jam==24){
                jam = 0;
            }
        }
        
        return ""+jam+":"+menit;
    }
    
    public String getTemperatur(){
        NodeList nList = doc.getElementsByTagName("temperature");
        Node nNode = nList.item(0);
        Element element = (Element) nNode;

        double temperatur = (Double.parseDouble( element.getAttribute("value"))) - 273.15;
        
        return ""+temperatur+"°C";
    }
    
}
