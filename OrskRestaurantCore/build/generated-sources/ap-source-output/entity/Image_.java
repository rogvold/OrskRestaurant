package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-08-30T15:42:40")
@StaticMetamodel(Image.class)
public class Image_ { 

    public static volatile SingularAttribute<Image, Long> id;
    public static volatile SingularAttribute<Image, String> description;
    public static volatile SingularAttribute<Image, String> name;
    public static volatile SingularAttribute<Image, Long> ownerFacilityId;
    public static volatile SingularAttribute<Image, String> src;
    public static volatile SingularAttribute<Image, String> mimeType;

}