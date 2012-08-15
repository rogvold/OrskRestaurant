package entity;

import entity.FacilityType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-08-14T15:57:03")
@StaticMetamodel(Facility.class)
public class Facility_ { 

    public static volatile SingularAttribute<Facility, Long> id;
    public static volatile SingularAttribute<Facility, String> site;
    public static volatile SingularAttribute<Facility, String> phone;
    public static volatile SingularAttribute<Facility, String> schedule;
    public static volatile SingularAttribute<Facility, String> address;
    public static volatile SingularAttribute<Facility, String> description;
    public static volatile ListAttribute<Facility, FacilityType> facilityTypes;
    public static volatile SingularAttribute<Facility, String> name;

}