/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.xml;

import ejb.XMLManagerLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author rogvold
 */
@ManagedBean(name="xmlBean")
public class XMLBean {
    @EJB
    XMLManagerLocal xmlMan;
    
    private String xmlText;

    public String getXmlText() {
        return xmlText;
    }

    public void setXmlText(String xmlText) {
        this.xmlText = xmlText;
    }
    
    public void updateDBFromXML(){
        xmlMan.updateDB(xmlText);
    }
    
}
