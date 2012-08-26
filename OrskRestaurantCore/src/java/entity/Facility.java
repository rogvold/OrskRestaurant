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
public class Facility implements Serializable, Comparable<Facility> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String address;
    @Column(length = 3000)
    private String description;
    @Column
    private String phone;
    @Column
    private String site;
    @Column
    private String schedule;
    @Column
    private int status;
    @Column
    private String coordinates;

    public Facility() {
    }

    public Facility(String name, String address, String coordinates, String description, String phone, String site, String schedule) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.phone = phone;
        this.site = site;
        this.schedule = schedule;
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return "Facility[ id=" + id + " ; status = " + status + " ]";
    }

    @Override
    public int compareTo(Facility n) {
//        throw new UnsupportedOperationException("Not supported yet.");
//        int lastCmp = status.compareTo(n.status);
//        return (lastCmp != 0 ? lastCmp : firstName.compareTo(n.firstName));
        if (n.status < status) {
            return -1;
        }
        if (n.status > status) {
            return 1;
        }
        return 0;
    }
}
