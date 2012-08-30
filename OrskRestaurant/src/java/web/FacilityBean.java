package web;

import ejb.FacilityManagerLocal;
import ejb.FeatureManagerLocal;
import entity.Facility;
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

/**
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


    public void createNewFacility() {
        System.out.println("createNewFacility() occured");
        facMan.addFacility(newFacility, selectedFeatures, extractImages(imageStr));
    }


    public void test() {
        System.out.println("list = " + selectedFeatures);
    }


    private List<Image> extractImages(String text) {
        List<Image> list = new ArrayList();
        StringTokenizer st = new StringTokenizer(text);

        while (st.hasMoreTokens()) {
            Image img = new Image();
            img.setSrc(st.nextToken());
            list.add(img);
        }
        return list;
    }
    
    public List<Facility> getAllFacilities() {
        return facMan.getAllFacilities();
    }
}
