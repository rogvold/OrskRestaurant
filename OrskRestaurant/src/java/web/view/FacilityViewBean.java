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
        try {
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
        List<FacilityType> list = facMan.getFaсilityTypes(facId);
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
    
 public String generateHtmlForOneFacility(Facility facility) {
        String html = "<div class='tab-wrapper' id='" + facility.getId() + "'>" + "\n"
                + "<div class='tab-content' style='border-radius: 10px; background-color: #DEC79D;' >" + "\n"
                + " <div class='tab' style='display: block; '>" + "\n"
                + "  <h3>" + "\n"
                + "    <a style='cursor: pointer; color: #A12124;' href='info.xhtml?id=" + facility.getId() + "' >" + "\n"
                + facility.getName() + "" + "\n"
                + "    </a>" + "\n"
                + "    <span style='font-style: italic; font-family: Georgia, Times New Roman, Times, serif; font-size: 12px; color: #565656; padding-left: 5px;'>" + "\n"
                + stringOfFacilityTypes(facility.getId()) + "" + "\n"
                + "    </span>" + "\n"
                + "  </h3>" + "\n"
                + "   <p>" + "\n"
                + "     <span style='float:right; width: 200px;  display: block; margin-left:20px;'>" + "\n"
                + "       <span style='display: inline-block; height: 150px; margin: 0 auto; float: right; background-color: yellow;'>" + "\n"
                + "           <a style='cursor: pointer;' href='info.xhtml?id=" + facility.getId() + "'>" + "\n"
                + "               <img src='" + avatar(facility.getId()) + "'  style='height: 150px; max-width: 230px; '  alt=''  />" + "\n"
                + "           </a>" + "\n"
                + "       </span>" + "\n"
                + "     </span>" + "\n"
                + ""
                + "     <span style='width:400px; display: block; color: black; font-size: 14px; font-weight: bold;'>" + "\n"
                + "           <img src='http://www.restosapiens.ru/img/icons/home.gif' style='width:16px; height:16px; ' />" + "\n"
                + facility.getAddress() + "\n"
                + "     </span>" + "\n"
                + "" + "\n"
                + "     <span style='width:400px; display: block; color: black; font-size: 14px; font-weight: bold;'>" + "\n"
                + "           <img src='http://www.restosapiens.ru/img/icons/phone.gif' style='width:16px; height:16px; ' />" + "\n"
                + facility.getPhone() + "\n"
                + "     </span>" + "\n"
                + "" + "\n"
                + "     <span style='width:400px; display: block; color: black; font-size: 14px; font-style: italic;'>" + "\n"
                + "       <img src='http://www.restosapiens.ru/img/openbull.gif' style='width:10px; height:10px;' />" + "\n"
                + "           Работает ещё mm минуты, до HH:MM" + "\n"
                + "     </span>" + "\n"
                + ""
                + "     <span style='width:400px; display: block; color: black; font-size: 14px; '>" + "\n"
                + shortDescription(facility.getId()) + "\n"
                + "     </span>" + "\n"
                + "     <span style='width:400px; display: block; color: black; font-size: 14px; text-align:  right;'>" + "\n"
                + "           <a style='cursor: pointer;' href='info.xhtml?id=" + facility.getId() + "'>Подробнее</a>" + "\n"
                + "     </span>" + "\n"
                + "   </p>" + "\n"
                + "  </div>" + "\n"
                + " </div>" + "\n"
                + "</div>";

        return html;
    }

    public String avatar(Long facId) {
        return facMan.getAvatarSrc(facId);
    }
}
