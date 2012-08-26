/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.view;

import ejb.FeatureManagerLocal;
import entity.Feature;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rogvold
 */
@ManagedBean
@ViewScoped
public class FeatureViewBean {
    
    private List<Feature> flist;
    
    @EJB
    FeatureManagerLocal featMan;
    
    
    
    @PostConstruct
    private void init(){
        flist = featMan.getAllFeatures(Feature.TYPE_ALL);
    }
    
    public List<Feature> featureByType(int type){
        List<Feature> list = new ArrayList();
        for (Feature f: flist){
            if (f.getType() == type) list.add(f);
        }
        return list;
    }
}
