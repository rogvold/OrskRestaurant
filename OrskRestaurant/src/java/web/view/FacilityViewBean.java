/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.view;

import ejb.FacilityManagerLocal;
import entity.Facility;
import entity.FacilityType;
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
    private List<Long> selectedFeatures;

    @PostConstruct
    private void init() {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        System.out.println("map = " + map);
        Set<String> keys = map.keySet();
        selectedFeatures = new ArrayList();
        for (String s : keys) {
            try {
                Long l = Long.parseLong(s);
                selectedFeatures.add(l);
            } catch (Exception e) {
            }
        }
        System.out.println("selectedList = " + selectedFeatures);
    }

    public List<Facility> getAllFacilities() {
        return facMan.getAllFacilities();
    }

    public List<Facility> getFilteredFacilities() {
        return facMan.getFilteredFacilities(selectedFeatures);
    }

    public String shortDescription(Long facId) {
        String s = facMan.getDescription(facId);
        if (s.length() >= MAX_DESCRIPTION_LENGTH) {
            s = s.substring(0, MAX_DESCRIPTION_LENGTH) + " ...";
        }
        return s;
    }

    public String stringOfFacilityTypes(Long facId) {
        List<FacilityType> list = facMan.getFailityTypes(facId);
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
}
