package xml.entity;

/**
 * @author rogvold
 */
public class XMLFeature {

    private String type;
    private String rating;
    private String name;

    public XMLFeature(String type, String rating, String name) {
        this.type = type;
        this.rating = rating;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void trimAllFields() {
        this.name = this.name.trim();
        this.type = this.type.trim();
        this.rating = this.rating.trim();
    }

    @Override
    public String toString() {
        return "name = " + name + "\n"
                + "type = " + type + "\n"
                + "rating = " + rating;
    }
}
