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
public class FacilityType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Facility_FacilityType", joinColumns = {
        @JoinColumn(name = "facilityType_id")
    },
    inverseJoinColumns = {
        @JoinColumn(name = "facility_id")
    })
    private List<Facility> facilities;

    public FacilityType(String name) {
        this.name = name;
    }

    public FacilityType() {
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

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
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
        if (!(object instanceof FacilityType)) {
            return false;
        }
        FacilityType other = (FacilityType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FacilityType[ id=" + id + " ]";
    }
}
