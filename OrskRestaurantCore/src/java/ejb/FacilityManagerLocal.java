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

    public void addFacility(Facility facility, List<Feature> features, List<Image> images);

    public void addFeatures(List<Feature> features, Long facId);

    public void addFeaturesByTheirId(List<Long> featuresId, Long facId);

    public void addImages(List<Image> images, Long facId);

    public List<ExtendedFeature> getExtendedFeaturesByFacilityId(Long facId);

    public String getAvatarSrc(Long facId);

    public List<Facility> getAllFacilities();

    public List<Facility> getAllFacilitiesExceptForList(int maxAmount, List<Long> list);
    
    public List<Facility> getFilteredFacilitiesExceptForList(int maxAmount, List<Long> list, List<Long> featuresId);

    public List<Long> getFilteredFacilitiesId(List<Long> featuresId);

    public List<Facility> getFilteredFacilities(List<Long> featuresId);

    public List<Facility> getCompleteCorrespondentFacilities(List<Long> featuresId);
    
    public List<Facility> getNotCorrespondentFacilities(List<Long> featuresId);

    public List<Facility> getFilteredFacilities2(List<Long> featuresId);
    
    public String getDescription(Long facId);

    public String getAddressById(Long facId);

    public Facility getFacilityById(Long facId);

    public List<Feature> getFeaturesByFacilityIdAndType(Long facId, int type);
}
