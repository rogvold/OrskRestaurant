/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.view;

import ejb.FacilityManagerLocal;
import entity.Facility;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rogvold
 */
@ManagedBean
@ViewScoped
public class FacilityViewBean {

    @EJB
    FacilityManagerLocal facMan;

    public List<Facility> getAllFacilities() {
        return facMan.getAllFacilities();
    }
}
