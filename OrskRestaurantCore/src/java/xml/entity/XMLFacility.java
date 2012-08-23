/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rogvold
 */
public class XMLFacility {

    private String name;
    private String phone;
    private String site;
    private String description;
    private String schedule;
    private String address;
    private String coordinates;
    private List<XMLFeature> features;
    private List<String> types;
    private List<String> images;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<XMLFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<XMLFeature> features) {
        this.features = features;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScedule() {
        return schedule;
    }

    public void setScedule(String scedule) {
        this.schedule = scedule;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void trimAllFields() {
        this.address = this.address.trim();
        this.name = this.name.trim();
        this.phone = this.phone.trim();
        this.site = this.site.trim();
        this.schedule = this.schedule.trim();
        this.description = this.description.trim();
        this.coordinates = this.coordinates.trim();
        List<String> imlist = new ArrayList();
        for (String s : images) {
            imlist.add(s.trim());
        }
        images = imlist;

        if (types != null) {
            List<String> tlist = new ArrayList();

            for (String s : types) {
                tlist.add(s.trim());
            }
            types = tlist;
        }
        if (features != null) {
            List<XMLFeature> list = new ArrayList();
            for (XMLFeature f : features) {
                f.trimAllFields();
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        s += "name = " + name + "\n"
                + "address = " + address + "\n"
                + "coordinates = " + coordinates + "\n"
                + "site = " + site + "\n"
                + "phone = " + phone + "\n"
                + "description = " + description + "\n"
                + "schedule = " + schedule + "\n"
                + "images = " + images + "\n"
                + "types = " + types + "\n"
                + "features = " + features + "\n";

        return s;
    }
}
