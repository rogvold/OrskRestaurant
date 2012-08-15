/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.*;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rogvold
 */
@Local
public interface FacilityManagerLocal {

    public List<Image> getImages(Long facId);

    public void addPicture(Long facId, Image img);

    public void addFacility(Facility facility, List<Feature> features, List<Image> images, List<FacilityType> types);

    public void addFacilityByFuckingFeaturesAndFacilityTypeId(Facility facility, List<Long> featuresId, List<Image> images, List<Long> typesId);

    public void addFacilityType(String name, String description);

    public void addFeatures(List<Feature> features, Long facId);

    public void addFeaturesByTheirId(List<Long> featuresId, Long facId);

    public void addFacilityTypes(List<FacilityType> types, Long facId);

    public void addFacilityTypesByTheirId(List<Long> typesId, Long facId);

    public void addImages(List<Image> images, Long facId);

    public List<FacilityType> getAllExistingFacilityTypes();

    public List<ExtendedFeature> getExtendedFeaturesByFacilityId(Long facId);

    public FacilityType getFacilityTypeByName(String name);

    public void cheat();

    public List<Facility> getAllFacilities();
}
