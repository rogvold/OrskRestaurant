package web;

import ejb.FacilityManagerLocal;
import ejb.FeatureManagerLocal;
import entity.FacilityType;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rogvold
 */
@FacesConverter("facilityTypeConverter2")
public class FacilityTypeConverter2 implements Converter {

    public static volatile FeatureManagerLocal featureMan;
    public static volatile FacilityManagerLocal facMan;

//    @Override
//    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        System.out.println("SubjectConverter2.getAsObject: value = "+value);
//        try {
//            Subject s = new entity.Subject();
//            s = sm.getSubjectById(Long.parseLong(value));
//            return s;
//            //return sm.getSubjectById(Long.parseLong(value));
//            }
//        } catch (Exception ex) {
//            throw new ConverterException(ex);
//        }
//        return null;
//    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        System.out.println("SubjectConverter2.getAsString: value = " + value);
//        if (!(value instanceof FacilityType)) {
//            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion failed", "Invalid instance type"));
//        }
//        FacilityType f = (FacilityType) value;
//        return f.getName();
        return value.toString();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("value = " + value);
//        FacilityType f = facMan.getFacilityTypeByName(value);
//        return f;
        return Long.parseLong(value);
    }
}