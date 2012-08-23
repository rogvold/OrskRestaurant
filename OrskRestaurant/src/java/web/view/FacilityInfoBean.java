/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.view;

import ejb.FacilityManagerLocal;
import entity.Facility;
import entity.FacilityType;
import entity.Feature;
import entity.Image;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import utils.MySchedule;

/**
 *
 * @author rogvold
 */
@ManagedBean
@RequestScoped
public class FacilityInfoBean {

    @EJB
    FacilityManagerLocal facMan;
    private Long currentId;
    private List<String> prettySchedule;
    private MySchedule ms;

    @PostConstruct
    private void init() {
        try {
            currentId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            System.out.println("id = " + currentId);
            ms = new MySchedule(facMan.getFacilityById(currentId).getSchedule());
            prettySchedule = ms.getPrettySchedule();
        } catch (Exception e) {
        }

    }

    public Facility getFacility() {
        return facMan.getFacilityById(currentId);
    }

    public String stringOfFacilityTypes(Long facId) {
        List<FacilityType> list = facMan.getFaсilityTypes(facId);
        if (list == null) {
            return null;
        }
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

    public List<Image> imagesByFacilityId(Long facId) {
        return facMan.getImages(facId);
    }

    public List<Feature> featuresByIdAndType(Long facId, int type) {
        return facMan.getFeaturesByFacilityIdAndType(facId, type);
    }

    public List<String> getPrettySchedule() {
        return prettySchedule;
    }

    public void setPrettySchedule(List<String> prettySchedule) {
        this.prettySchedule = prettySchedule;
    }
}
