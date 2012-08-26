/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

//import ejb.UserManagerLocal;
import ejb.FacilityManagerLocal;
import ejb.FeatureManagerLocal;
import entity.Feature;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rogvold
 */
@ManagedBean
@RequestScoped
public class FeatureBean implements Serializable {

    private List<Feature> flist;
    @EJB
    FeatureManagerLocal fMan;
    @EJB
    FacilityManagerLocal facMan;
    private List<Feature> allFeatures;
//    private List<FacilityType> allFacilityTypes;
    private Feature newFeature;
//    private FacilityType newFacilityType;

//    public List<FacilityType> getAllFacilityTypes() {
//        return facMan.getAllExistingFacilityTypes();
//    }
//
//    public FacilityType getNewFacilityType() {
//        return this.newFacilityType;
//    }
//    public void setNewFacilityType(FacilityType newFacilityType) {
//        this.newFacilityType = newFacilityType;
//    }
    public FeatureBean() {
        newFeature = new Feature();
//        newFacilityType = new FacilityType();
    }

    @PostConstruct
    private void init() {
        flist = fMan.getAllFeatures(Feature.TYPE_ALL);
    }

    public List<Feature> getAllFeatures() {
        return fMan.getAllFeatures(Feature.TYPE_ALL);
    }

    public List<Feature> customFeatures(int type) {
        List<Feature> list = new ArrayList();
        for (Feature f : flist) {
            if (f.getType() == type) {
                list.add(f);
            }
        }
        return list;
    }

    public Feature getNewFeature() {
        return newFeature;
    }

    public void setNewFeature(Feature newFeature) {
        this.newFeature = newFeature;
    }

    public void addNewFeature() {
        System.out.println("addNewFeature occured!");
        fMan.createFeature(newFeature.getName(), newFeature.getDescription(), newFeature.getType());
    }
//    public void addNewFacilityType() {
//        System.out.println("addNewFacilityType occured!");
//        facMan.addFacilityType(newFacilityType.getName(), newFacilityType.getDescription());
//    }
}
