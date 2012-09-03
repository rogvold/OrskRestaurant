/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.view;

import ejb.FacilityManagerLocal;
import ejb.FeatureManagerLocal;
import entity.Facility;
//import entity.FacilityType;
import entity.Feature;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rogvold
 */
@ManagedBean
@ViewScoped
public class FacilityViewBean {

    public static final int MAX_DESCRIPTION_LENGTH = 150;
    @EJB
    FacilityManagerLocal facMan;
    @EJB
    FeatureManagerLocal featMan;
    private List<Long> selectedFeatures;
    private List<Facility> filteredList;

    @PostConstruct
    private void init() {
        try {
            Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            System.out.println("map = " + map);
            Set<String> keys = map.keySet();
            selectedFeatures = new ArrayList();
//            filteredList = facMan.getFilteredFacilities2(selectedFeatures);
            for (String s : keys) {
                try {
                    Long l = Long.parseLong(s);
                    selectedFeatures.add(l);
                } catch (Exception e) {
                }
            }
            System.out.println("selectedList = " + selectedFeatures);
            filteredList = facMan.getFilteredFacilities2(selectedFeatures);
        } catch (Exception e) {
        }

    }

    public List<Facility> getAllFacilities() {
        return facMan.getAllFacilities();
    }

    public List<Facility> allFacilities(int maxAmount) {
        return facMan.getAllFacilitiesExceptForList(maxAmount, null);
    }

    public List<Facility> getFilteredFacilities() {
        System.out.println("getFilteredFacilities occured");
        return facMan.getFilteredFacilities2(selectedFeatures);
    }

    public List<Facility> getFilteredList() {
        return filteredList;
    }
    
    public List<Facility> filteredList(int maxAmount){
        return filteredList.subList(0, maxAmount);
    }

    public void setFilteredList(List<Facility> filteredList) {
        this.filteredList = filteredList;
    }

    public List<Facility> getFilteredFacilities2() {
        return facMan.getFilteredFacilities2(selectedFeatures);
    }

    public String shortDescription(Long facId) {
        String s = facMan.getDescription(facId);
        if (s.length() >= MAX_DESCRIPTION_LENGTH) {
            int t = MAX_DESCRIPTION_LENGTH;
            while ( (t<s.length())&&(s.charAt(t) != ' ')){
                t++;
            }
            s = s.substring(0, t);
        }
        return s;
    }

    public String stringOfFacilityTypes(Long facId) {
        List<Feature> list = facMan.getFeaturesByFacilityIdAndType(facId, Feature.TYPE_FACILITY_TYPE);
        String s = "";
        int last = list.size() - 1;
        if (last < 0) {
            return s;
        }
        if (last == 0) {
            return list.get(0).getName();
        }
        s = list.get(0).getName();
        for (int i = 1; i <= last; i++) {
            s += ", " + list.get(i).getName();
        }
        return s;
    }


    public String avatar(Long facId) {
        return facMan.getAvatarSrc(facId);
    }

    public List<Feature> selectedFeatures(int type) {
        List<Feature> list = new ArrayList();
        for (Long l : selectedFeatures) {
            Feature f = featMan.getFeatureById(l);
            if (f.getType() == type) {
                list.add(f);
            }
        }
        return list;
    }

    public List<Feature> selectedFeatures(String type) {
        return selectedFeatures(Integer.parseInt(type));
    }

    public Feature firstSelectedFeature(String type) {
        return selectedFeatures(Integer.parseInt(type)).get(0);
    }
}
