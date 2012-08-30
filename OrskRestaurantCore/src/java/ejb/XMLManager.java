package ejb;

import com.thoughtworks.xstream.XStream;
import entity.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;
import xml.entity.XMLDatabase;
import xml.entity.XMLFacility;
import xml.entity.XMLFeature;

/**
 * @author rogvold
 */
@Stateless
public class XMLManager implements XMLManagerLocal {

    @PersistenceContext(unitName = "OrskRestaurantCorePU")
    EntityManager em;
    private static final String HARDCODED_PASSWORD = "hardcode";

    private void deleteAllDataFromDatabase() {
        Query q;
        try {
            q = em.createNativeQuery("delete from extendedfeature ");
            q.executeUpdate();

            q = em.createNativeQuery("delete from feature ");
            q.executeUpdate();

            q = em.createNativeQuery("delete from facility ");
            q.executeUpdate();

            q = em.createNativeQuery("delete from image ");
            q.executeUpdate();
        } catch (Exception e) {
            System.out.println("Exception occured while deleting all data from database... exc = " + e.toString());
        }


    }

    private Long getFacilityId(String name, String phone, String site, String description, String schedule, String address, String coordinates) {
        Facility fac = new Facility(name, address, coordinates, description, phone, site, schedule);
        fac = em.merge(fac);
        return fac.getId();
    }

    private Long getFeatureId(String name, String type) {

        Query q = em.createQuery("select f from Feature f where f.name = '" + name + "'");
        try {
            Feature f = (Feature) q.getSingleResult();
            f.setType(Integer.parseInt(type));
            return em.merge(f).getId();
        } catch (NoResultException nex) {
            return em.merge(new Feature(name, null, Integer.parseInt(type))).getId();
        } catch (NonUniqueResultException aex) {
            System.out.println(aex.toString());
        } catch (IllegalStateException ewq) {
            System.out.println(ewq.toString());
        }
        return null;

    }

    private void createExtendedFeature(Long facId, Long feId, String rating) {
        ExtendedFeature ef = new ExtendedFeature(feId, facId, Integer.parseInt(rating));
        em.persist(ef);
    }

    private void addFeatureToFacility(Long facId, String featureName, String featureType, String featureRating) {
        createExtendedFeature(facId, getFeatureId(featureName, featureType), featureRating);
    }

    private void addFeaturesToFacility(Long facId, List<XMLFeature> xmlFeatures) {
        if (xmlFeatures == null) {
            return;
        }
        for (XMLFeature xf : xmlFeatures) {
            addFeatureToFacility(facId, xf.getName(), xf.getType(), xf.getRating());
        }
    }

    private void addOneImageToFacility(Long facId, String imageSrc) {
        Image img = new Image(imageSrc, facId);
        em.persist(img);
    }

    private void addImagesToFacility(Long facId, List<String> images) {
        if (images == null) {
            return;
        }
        for (String im : images) {
            addOneImageToFacility(facId, im);
        }
    }

    private void addOneFacilitytoDB(XMLFacility xmlFacility) {
        Long facId = getFacilityId(xmlFacility.getName(), xmlFacility.getPhone(), xmlFacility.getSite(), xmlFacility.getDescription(), xmlFacility.getSchedule(), xmlFacility.getAddress(), xmlFacility.getCoordinates());
        addFeaturesToFacility(facId, xmlFacility.getFeatures());
        addImagesToFacility(facId, xmlFacility.getImages());
    }

    private void addFacilities(List<XMLFacility> xmlList) {
        for (XMLFacility xf : xmlList) {
            System.out.println("adding facility = " + xf);
            addOneFacilitytoDB(xf);
        }
    }

    @Override
    public void updateDB(String xml) {
        deleteAllDataFromDatabase();
        XStream xs = new XStream();
        xs.alias("facility", XMLFacility.class);
        xs.alias("feature", XMLFeature.class);
        xs.alias("item", String.class);
        xs.alias("root", XMLDatabase.class);
        XMLDatabase xdb = (XMLDatabase) xs.fromXML(xml);
        xdb.trimAllFacilities();
        addFacilities(xdb.getFacilities());
    }

    @Override
    public void updateDB(String xml, String password) {
        if (password.equals(HARDCODED_PASSWORD)) {
            updateDB(xml);
        } else {
            System.out.println("invalid password.. (" + password + ")");
        }
    }
}
