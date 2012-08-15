/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.FacilityManagerLocal;
import ejb.FeatureManagerLocal;
import entity.Facility;
import entity.FacilityType;
import entity.Feature;
import entity.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
//import javax.faces.bean.ViewScoped;

/**
 *
 * @author rogvold
 */
@ManagedBean
@RequestScoped
public class FacilityBean implements Serializable {

    @EJB
    FacilityManagerLocal facMan;
    @EJB
    FeatureManagerLocal feaMan;
    private List<Feature> selectedFeatures;
    private List<FacilityType> selectedTypes;
    private List<Long> selectedFeaturesId;
    private List<Long> selectedTypesId;
    private Facility newFacility;
    private String imageStr;

    public FacilityBean() {
        newFacility = new Facility();
    }

    @PostConstruct
    private void init() {
        FeatureConverter.featureMan = feaMan;
        FacilityTypeConverter.facMan = facMan;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public Facility getNewFacility() {
        return newFacility;
    }

    public void setNewFacility(Facility newFacility) {
        this.newFacility = newFacility;
    }

    public List<Feature> getSelectedFeatures() {
        return selectedFeatures;
    }

    public void setSelectedFeatures(List<Feature> selectedFeatures) {
        this.selectedFeatures = selectedFeatures;
    }

    public List<Long> getSelectedFeaturesId() {
        return selectedFeaturesId;
    }

    public void setSelectedFeaturesId(List<Long> selectedFeaturesId) {
        this.selectedFeaturesId = selectedFeaturesId;
    }

    public List<Long> getSelectedTypesId() {
        return selectedTypesId;
    }

    public void setSelectedTypesId(List<Long> selectedTypesId) {
        this.selectedTypesId = selectedTypesId;
    }

    
    public List<FacilityType> getSelectedTypes() {
        return selectedTypes;
    }

    public void setSelectedTypes(List<FacilityType> selectedTypes) {
        this.selectedTypes = selectedTypes;
    }

    public void createNewFacility() {
        System.out.println("createNewFacility() occured");
        facMan.addFacility(newFacility, selectedFeatures, extractImages(imageStr), selectedTypes);
    }

    public void createNewFuckingFacility() {
        System.out.println("createNewFacility() occured");
        facMan.addFacilityByFuckingFeaturesAndFacilityTypeId(newFacility, selectedFeaturesId, extractImages(imageStr), selectedTypesId);
    }

    public void test() {
        System.out.println("list = " + selectedFeatures);
    }

    public void makeCheat() {
        System.out.println("cheating....");
        facMan.cheat();
    }

    private List<Image> extractImages(String text) {
        System.out.println("extracting images...");
        System.out.println("text = " + text);

        List<Image> list = new ArrayList();
        StringTokenizer st = new StringTokenizer(text);

        while (st.hasMoreTokens()) {
            Image img = new Image();
            img.setName("default name");
            img.setSrc(st.nextToken());
            list.add(img);
        }
        return list;
    }
}
