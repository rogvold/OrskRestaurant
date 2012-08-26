/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Feature;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rogvold
 */
@Local
public interface FeatureManagerLocal {

    public void createFeature(String name, String description, int type);

    public boolean featureExists(String name);

    public List<Feature> getAllFeatures(int type);
    
    public Feature getFeatureByName(String name);
    
    public int getTypeByFeatureId(Long featureId);
    
    public Feature getFeatureById(Long featId);
    
}
