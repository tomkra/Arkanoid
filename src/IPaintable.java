/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2015_dp_kra0410;

import java.awt.Graphics;

/**
 * Interface for paintable objects.
 *
 * @author Tomas Kral
 * @version 0.1
 */
public interface IPaintable {

  /**
   * Method for painting
   *
   * @param g
   *          graphics to paint
   */
  public void paint(Graphics g);

  /**
   * @return x position of object
   */
  public int getX();

  /**
   * @return y position of object
   */
  public int getY();

  /**
   * @param new
   *          x position
   */
  public void setX(int x);

  /**
   * @param new
   *          y position
   */
  public void setY(int y);

  /**
   * @return width of object
   */
  public int getWidth();

  /**
   * @return height of object
   */
  public int getHeight();
}
