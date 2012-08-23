/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;

/**
 *
 * @author rogvold
 */
@Local
public interface XMLManagerLocal {
    public void updateDB(String xml);
}
