package ejb;

import javax.ejb.Local;

/**
 *
 * @author rogvold
 */
@Local
public interface XMLManagerLocal {

    public void updateDB(String xml);

    public void updateDB(String xml, String password);
}
