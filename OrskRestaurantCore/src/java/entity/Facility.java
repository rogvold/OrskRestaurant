/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author rogvold
 */
@Entity
@Table
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String description;
    @Column
    private String phone;
    @Column
    private String site;
    @Column
    private String schedule;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Facility_FacilityType", joinColumns = {
        @JoinColumn(name = "facility_id")
    },
    inverseJoinColumns = {
        @JoinColumn(name = "facilityType_id")
    })
    private List<FacilityType> facilityTypes;

    public Facility() {
    }

    public Facility(String name, String address, String description, String phone, String site, String schedule) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.phone = phone;
        this.site = site;
        this.schedule = schedule;
    }

    public List<FacilityType> getFacilityTypes() {
        return facilityTypes;
    }

    public void setFacilityTypes(List<FacilityType> facilityTypes) {
        this.facilityTypes = facilityTypes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facility)) {
            return false;
        }
        Facility other = (Facility) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Facility[ id=" + id + " ]";
    }
}