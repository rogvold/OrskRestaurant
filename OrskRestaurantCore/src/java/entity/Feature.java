/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rogvold
 */
@Entity
@Table
public class Feature implements Serializable {

    public static final int TYPE_WISH = 1;
    public static final int TYPE_IMPORTANT = 2;
    public static final int TYPE_LOCATION = 3;
    public static final int TYPE_AVERAGE_BILL = 4;
    public static final int TYPE_KITCHEN = 5;
    public static final int TYPE_ALCOHOL = 6;
    
    public static final int TYPE_FACILITY_TYPE = 9;
    
    public static final int TYPE_CREDIT_CARD = 7;
    public static final int TYPE_ALL = -1;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int type;

    public Feature() {
    }

    public Feature(String name, String description, int type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        if (!(object instanceof Feature)) {
            return false;
        }
        Feature other = (Feature) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Feature[ id=" + id + " ]";
    }
}
