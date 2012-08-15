/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Feature;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rogvold
 */
@Stateless
public class FeatureManager implements FeatureManagerLocal {

    @PersistenceContext(unitName = "OrskRestaurantCorePU")
    EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void createFeature(String name, String description, int type) {
        if (featureExists(name)) {
            System.out.println("feature ( name =  " + name + ") already exists");
            return;
        }
        Feature feature = new Feature(name, description, type);
        feature = em.merge(feature);
        System.out.println("feature (id  = " + feature.getId() + " ) created");
    }

    @Override
    public boolean featureExists(String name) {
        Query q = em.createQuery("select f from Feature f where f.name = '" + name + "'");
        List<Feature> list = q.getResultList();
        if ((list == null) || (list.isEmpty())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Feature> getAllFeatures(int type) {
        System.out.println("getAllFeatures occured");
        try {
            String qlString = "select f from Feature f ";
            if (type != Feature.TYPE_ALL) {
                qlString += "where f.type = " + type;
            }

            Query q = em.createQuery(qlString);
            return q.getResultList();

        } catch (Exception e) {
            System.out.println("getAllFeatures: exception occured exc = " + e.toString());
            return null;
        }
    }

    @Override
    public Feature getFeatureByName(String name) {
//        throw new UnsupportedOperationException("Not supported yet.");
        Query q = em.createQuery("select f from Feature f where f.name = '" + name + "'");
        return (Feature) q.getSingleResult();
    }
}
