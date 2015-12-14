/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellouser.controller;

import hellouser.model.XmlData;
import hellouser.view.MainFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.ParseException;

/**
 *
 * @author syahril
 */
public class MainController {
    
    MainFrame frame ;
    int frameWidth, frameHeight ;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    XmlData data = new XmlData();
    
    public MainController(MainFrame frame) throws ParseException{
        this.frame = frame;
        this.frameWidth = frame.getWidth();
        this.frameHeight = frame.getHeight();
        //System.out.println(data.getCity()+","+data.getCountry());
        //System.out.println(data.getSunSet());
        MainFrame.setCityLabel(data.getCity());
        MainFrame.setIconLabel(data.getIcon());
        MainFrame.setWeatherNameLabel(data.getWheather());
        MainFrame.setSetLabel(data.getSunSet());
        MainFrame.setRiseLabel(data.getSunRise());
        MainFrame.setTemperatureLabel(data.getTemperatur());
//        System.out.print(data.getIcon().toString());
    }
    
    public void setToRightBottom(){
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        int y = (int) (height - frameHeight);
        int x = (int)  (width - frameWidth);
        frame.setLocation(x, y);
    }
}
