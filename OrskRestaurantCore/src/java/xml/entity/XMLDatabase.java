package xml.entity;

import java.util.List;

/**
 *
 * @author rogvold
 */
public class XMLDatabase {

    private List<XMLFacility> facilities;

    public List<XMLFacility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<XMLFacility> facilities) {
        this.facilities = facilities;
    }

    public void trimAllFacilities() {
        for (XMLFacility f : facilities) {
            f.trimAllFields();
        }
    }

    @Override
    public String toString() {
        return "facilities = " + facilities;
    }
}
