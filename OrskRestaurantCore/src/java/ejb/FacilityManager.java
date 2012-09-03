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

    public static final int INFINITE_RATING = 1000000;
    @PersistenceContext(unitName = "OrskRestaurantCorePU")
    EntityManager em;
    @EJB
    FeatureManagerLocal featMan;

    @Override
    public List<Image> getImages(Long facId) {
        if (facId == null) {
            return null;
        }
        Query q = em.createQuery("select i from Image i where i.ownerFacilityId = " + facId + " order by i.id");
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
    public void addFacility(Facility facility, List<Feature> features, List<Image> images) {
        System.out.println("try to create facility ");
        System.out.println("features = " + features);
        Facility nf = new Facility(facility.getName(), facility.getAddress(), facility.getCoordinates(), facility.getDescription(), facility.getPhone(), facility.getSite(), facility.getSchedule());
        nf = em.merge(nf);
        addFeatures(features, nf.getId());
        addImages(images, nf.getId());
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
        }
    }



    @Override
    public List<Facility> getAllFacilities() {
        Query q = em.createQuery("select f from Facility f");
        return q.getResultList();
    }

    private int calculateСompatibility(List<Long> featuresId, Long facId) {
        List<ExtendedFeature> list = getExtendedFeaturesByFacilityId(facId);
        int result = 0;
        int k = 0;
        for (ExtendedFeature ef : list) {
            if (featuresId.contains(ef.getFeatureId())) {
                k++;
                result += ef.getRating();
            }
        }
        if (k == featuresId.size()) {
            result = result + FacilityManager.INFINITE_RATING;
        }
//        System.out.println("id ; k/list.size / rating : " + facId + " ; " + k + " / " + featuresId.size() + " / " + result);

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
        try {
            List<String> imgs = em.createQuery("select i.src from Image i where i.ownerFacilityId = " + facId).getResultList();
            return imgs.get(0);
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
        List<Facility> list = new ArrayList();
        list.addAll(getCompleteCorrespondentFacilities(featuresId));
        list.addAll(getNotCorrespondentFacilities(featuresId));
        System.out.println("getFilteredFacilities(): list = " + list);
        return list;
    }

    @Override
    public Facility getFacilityById(Long facId) {
        Facility f = em.find(Facility.class, facId);
        return f;
    }

    @Override
    public List<Feature> getFeaturesByFacilityIdAndType(Long facId, int type) {
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

    @Override
    public List<Facility> getAllFacilitiesExceptForList(int maxAmount, List<Long> list) {
        System.out.println("getAllFacilitiesExceptForList excList = " + list);
        String sq = "select f from Facility f ";
        if (list != null && !list.isEmpty()) {
            sq += " where 1 = 1 ";
            for (Long l : list) {
                sq += " and f.id != " + l;
            }
        }
        Query q = em.createQuery(sq);
        if (maxAmount > 0) {
            q.setMaxResults(maxAmount);
        }
        return q.getResultList();
    }

    @Override
    public List<Facility> getFilteredFacilitiesExceptForList(int maxAmount, List<Long> list, List<Long> featuresId) {
        List<Facility> rlist = new ArrayList();
        List<Facility> alist = getFilteredFacilities2(featuresId);
        int k = 0;
        for (Facility f : alist) {
            if (k >= maxAmount) {
                break;
            }
            if (list.contains(f.getId())) {
                continue;
            }
            k++;
            rlist.add(f);
        }
        return rlist;
    }

    private boolean isCorrespondent(Long facId, List<Long> featuresList) {
        List<ExtendedFeature> elist = getExtendedFeaturesByFacilityId(facId);
        boolean f = false;
        for (Long l : featuresList) {
            f = false;
            for (ExtendedFeature ef : elist) {
                if (ef.getFeatureId().equals(l)) {
                    f = true;
                    break;
                }
            }
            if (f == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Facility> getFilteredFacilities2(List<Long> featuresId) {
        System.out.println("EJB: getFilteredFacilities2 occured");
        List<Facility> list = new ArrayList();
        List<Facility> allFac = getAllFacilities();
        for (Facility fac : allFac) {
            fac.setStatus(calculateСompatibility(featuresId, fac.getId()));
            list.add(fac);
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public List<Facility> getCompleteCorrespondentFacilities(List<Long> featuresId) {
        List<Facility> allFac = getAllFacilities();
        List<Facility> flist = new ArrayList();
        for (Facility fac : allFac) {
            if (isCorrespondent(fac.getId(), featuresId) == false) {
                continue;
            }
            fac.setStatus(calculateСompatibility(featuresId, fac.getId()));
            flist.add(fac);
        }
        Collections.sort(flist);
        return flist;
    }

    @Override
    public List<Facility> getNotCorrespondentFacilities(List<Long> featuresId) {
        List<Facility> clist = getCompleteCorrespondentFacilities(featuresId);
        List<Facility> alist = getAllFacilities();
        List<Facility> list = new ArrayList();
        for (Facility fac : alist) {
            if (clist.contains(fac)) {
                continue;
            }
            fac.setStatus(calculateСompatibility(featuresId, fac.getId()));
            list.add(fac);
        }
        Collections.sort(list);
        return list;
    }
}
