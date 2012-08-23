/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmltest;

import com.thoughtworks.xstream.XStream;
import entity.Feature;
import entity.Person;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import utils.MySchedule;
import xml.entity.XMLDatabase;
import xml.entity.XMLFacility;
import xml.entity.XMLFeature;

/**
 *
 * @author rogvold
 */
public class XMLTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

// 
        MySchedule ms = new MySchedule(MySchedule.EXAMPLE);
        System.out.println(ms.getPrettySchedule());
    }
}
