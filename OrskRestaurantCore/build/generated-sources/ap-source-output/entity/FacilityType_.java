package entity;

import entity.Facility;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-08-14T15:57:03")
@StaticMetamodel(FacilityType.class)
public class FacilityType_ { 

    public static volatile SingularAttribute<FacilityType, Long> id;
    public static volatile SingularAttribute<FacilityType, String> description;
    public static volatile SingularAttribute<FacilityType, String> name;
    public static volatile ListAttribute<FacilityType, Facility> facilities;

}