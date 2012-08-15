package web;

import ejb.FeatureManagerLocal;
import entity.Feature;
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
@FacesConverter("featureConverter")
public class FeatureConverter implements Converter {

    public static volatile FeatureManagerLocal featureMan;

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
        System.out.println("SubjectConverter2.getAsString: value = " + value);
        if (!(value instanceof Feature)) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion failed", "Invalid instance type"));
        }
        Feature f = (Feature) value;
        return f.getName();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Feature f = featureMan.getFeatureByName(value);
        return f;
    }
}