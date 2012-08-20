/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rogvold
 */
@Stateless
public class FacilityManager implements FacilityManagerLocal {

    @PersistenceContext(unitName = "OrskRestaurantCorePU")
    EntityManager em;
    @EJB
    FeatureManagerLocal featMan;

    @Override
    public List<Image> getImages(Long facId) {
        if (facId == null) {
            return null;
        }
        Query q = em.createQuery("select i from Image i where i.ownerFacilityId = " + facId);
        return q.getResultList();
    }

    @Override
    public void addPicture(Long facId, Image img) {
        Image image = new Image();
        image.setSrc(img.getSrc());
        image.setDescription(img.getDescription());
        image.setMimeType(img.getMimeType());
        image.setName(img.getName());
        image.setOwnerFacilityId(facId);
        em.persist(image);
        System.out.println("picture (src = " + img.getSrc() + " ) is added to database");
    }

    @Override
    public void addFacilityType(String name, String description) {
        FacilityType ft = new FacilityType();
        ft.setDescription(description);
        ft.setName(name);
        em.persist(ft);
    }

    @Override
    public List<FacilityType> getAllExistingFacilityTypes() {
        Query q = em.createQuery("select ft from FacilityType ft");
        return q.getResultList();
    }

    private boolean extendedFeatureExists(Long facilityId, Long featureId) {
        Query q = em.createQuery("select ef from ExtendedFeature ef where ef.featureId = " + featureId + " and ef.facilityId = " + facilityId);
        List list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addFeatures(List<Feature> features, Long facId) {
        if (features == null) {
            return;
        }
        System.out.println("try to add features features = " + features);
        for (Feature f : features) {
            if (extendedFeatureExists(f.getId(), facId)) {
                System.out.println("extended feature (facId-feaId = " + facId + " - " + f.getId() + ") already exists");
                continue;
            }
            ExtendedFeature nef = new ExtendedFeature(f.getId(), facId, ExtendedFeature.RATING_DEFAULT);
            em.persist(nef);
        }
    }

    @Override
    public void addFeaturesByTheirId(List<Long> featuresId, Long facId) {
        if (featuresId == null) {
            return;
        }
        System.out.println("try to add features be their id; features = " + featuresId);
        List<Feature> list = new ArrayList();
        for (Long l : featuresId) {
            Feature f = em.find(Feature.class, l);
            list.add(f);
        }
        addFeatures(list, facId);
    }

    @Override
    public void addFacility(Facility facility, List<Feature> features, List<Image> images, List<FacilityType> types) {
        System.out.println("try to create facility ");
        System.out.println("features = " + features);
        Facility nf = new Facility(facility.getName(), facility.getAddress(), facility.getDescription(), facility.getPhone(), facility.getSite(), facility.getSchedule());
        nf = em.merge(nf);
        addFeatures(features, nf.getId());
        addImages(images, nf.getId());
        addFacilityTypes(types, nf.getId());
    }

    @Override
    public void addFacilityByFuckingFeaturesAndFacilityTypeId(Facility facility, List<Long> featuresId, List<Image> images, List<Long> typesId) {
        Facility nf = new Facility(facility.getName(), facility.getAddress(), facility.getDescription(), facility.getPhone(), facility.getSite(), facility.getSchedule());
        nf = em.merge(nf);
        addFeaturesByTheirId(featuresId, nf.getId());
        addImages(images, nf.getId());
        addFacilityTypesByTheirId(typesId, nf.getId());
    }

    @Override
    public List<ExtendedFeature> getExtendedFeaturesByFacilityId(Long facId) {
        Query q = em.createQuery("select ef from ExtendedFeature ef where ef.facilityId = " + facId);
        return q.getResultList();
    }

    @Override
    public void addImages(List<Image> images, Long facId) {

        System.out.println("try to add images images = " + images);
        if (images == null) {
            return;
        }
        for (Image im : images) {
            im.setOwnerFacilityId(facId);
            em.persist(im);
//            ExtendedFeature nef = new ExtendedFeature(f.getId(), facId, ExtendedFeature.RATING_DEFAULT);
//            em.persist(nef);
        }
    }

    @Override
    public void cheat() {
        ExtendedFeature ef = new ExtendedFeature();
        em.persist(ef);
    }

    @Override
    public FacilityType getFacilityTypeByName(String name) {
        System.out.println("getFacilityTypeByName occured ; name = " + name);
        Query q = em.createQuery("select ft from FacilityType ft where ft.name = '" + name + "'");
        return (FacilityType) q.getSingleResult();
    }

    @Override
    public void addFacilityTypes(List<FacilityType> types, Long facId) {
        System.out.println("try to add facility types ( types = " + types + " ) ; facId = " + facId);
        Facility fac = em.find(Facility.class, facId);
        fac.setFacilityTypes(types);
        Facility merge = em.merge(fac);
    }

    @Override
    public void addFacilityTypesByTheirId(List<Long> typesId, Long facId) {
        Facility fac = em.find(Facility.class, facId);
        List<FacilityType> list = new ArrayList();
        for (Long l : typesId) {
            FacilityType t = em.find(FacilityType.class, l);
            list.add(t);
        }
        fac.setFacilityTypes(list);
        Facility merge = em.merge(fac);
    }

    @Override
    public List<Facility> getAllFacilities() {
        Query q = em.createQuery("select f from Facility f");
        return q.getResultList();
    }

    // if there is no compliance in location then fuck this facility (it means that result is -1)
    private int calculateСompatibility(List<Long> featuresId, Long facId) {
        List<ExtendedFeature> list = getExtendedFeaturesByFacilityId(facId);
        int result = 0;
        // check location
        boolean flag = false;
        for (ExtendedFeature ef : list) {
            if (featMan.getTypeByFeatureId(ef.getFeatureId()) != Feature.TYPE_LOCATION) {
                result += ef.getRating(); //sum not location features
                continue;
            }
            for (Long l : featuresId) {
                if (l.equals(ef.getFeatureId())) {
                    flag = true;
                }
            }
        }
        if (flag == false) {
            return -1; // location does not matches
        }
        return result;
    }

    @Override
    public List<Long> getFilteredFacilitiesId(List<Long> featuresId) {
        List<Long> list = new ArrayList();
        List<Facility> flist = new ArrayList();

        List<Facility> allFacilities = getAllFacilities();

        for (Facility f : allFacilities) {
            int t = calculateСompatibility(featuresId, f.getId());
            if (t < 0) {
                continue;
            }
            f.setStatus(-t);
            flist.add(f);
        }
        Collections.sort(flist);
        for (Facility f : flist) {
            list.add(f.getId());
        }
        return list;
    }

    @Override
    public String getAvatarSrc(Long facId) {
//        Facility fac = em.find(Facility.class, facId);
        try {
            List<String> imgs = em.createQuery("select i.src from Image i where i.ownerFacilityId = " + facId).getResultList();
            return imgs.get(0);
        } catch (Exception e) {
            return null;
        }



    }

    @Override
    public List<FacilityType> getFaсilityTypes(Long facId) {
        try {
            Facility fac = em.find(Facility.class, facId);
            return fac.getFacilityTypes();

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String getDescription(Long facId) {
        try {
            Facility fac = em.find(Facility.class, facId);
            return fac.getDescription();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAddressById(Long facId) {
        try {
            Facility fac = em.find(Facility.class, facId);
            return fac.getAddress();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Facility> getFilteredFacilities(List<Long> featuresId) {
        try {
            List<Long> lolist = getFilteredFacilitiesId(featuresId);
            List<Facility> list = new ArrayList();
            for (Long l : lolist) {
                list.add(getFacilityById(l));
            }
            return list;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Facility getFacilityById(Long facId) {
        Facility f = em.find(Facility.class, facId);
        return f;
    }

    @Override
    public List<Feature> getFeaturesByFacilityIdAndType(Long facId, int type) {
//        throw new UnsupportedOperationException("Not supported yet.");
        List<ExtendedFeature> elist = getExtendedFeaturesByFacilityId(facId);
        List<Feature> list = new ArrayList();
        for (ExtendedFeature ef : elist) {
            Feature f = em.find(Feature.class, ef.getFeatureId());
            if (f.getType() == type) {
                list.add(f);
            }
        }
        return list;
    }
}
