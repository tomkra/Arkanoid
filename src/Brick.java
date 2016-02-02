/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2015_dp_kra0410;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Bricks class.
 *
 * @author Tomas Kral
 * @version 0.1
 */
public class Brick implements IPaintable {
  private int strenght;
  private int x;
  private int y;
  private final int width;
  private final int height;
  private boolean collision = true;

  public Brick(int x, int y, int strenght) {
    this.x = x;
    this.y = y;
    if (strenght > 0 && strenght <= 6) {
      this.strenght = strenght;
    } else {
      this.strenght = 6;
    }
    this.width = 50;
    this.height = 30;
  }

  @Override
  public void paint(Graphics g) {
    switch (this.strenght) {
      case 1:
        g.setColor(Color.GREEN);
        break;
      case 2:
        g.setColor(Color.YELLOW);
        break;
      case 3:
        g.setColor(Color.ORANGE);
        break;
      case 4:
        g.setColor(Color.PINK);
        break;
      case 5:
        g.setColor(Color.MAGENTA);
        break;
      case 6:
        g.setColor(Color.RED);
        break;
      default:
        g.setColor(Color.GREEN);
        break;
    }
    g.fillRect(this.x, this.y, this.width, this.height);
    g.setColor(Color.WHITE);
    g.drawRect(this.x, this.y, this.width, this.height);
  }

  /**
   * @param strenght
   *          the strenght to set
   */
  public void setStrenght(int strenght) {
    this.strenght = strenght;
  }

  /**
   * @return the strenght
   */
  public int getStrenght() {
    return this.strenght;
  }

  /**
   * @return the x
   */
  @Override
  public int getX() {
    return this.x;
  }

  /**
   * @return the y
   */
  @Override
  public int getY() {
    return this.y;
  }

  /**
   * @param x
   *          the x to set
   */
  @Override
  public void setX(int x) {
    this.x = x;
  }

  /**
   * @param y
   *          the y to set
   */
  @Override
  public void setY(int y) {
    this.y = y;
  }

  /**
   * @return the width
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * @return the height
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * @return the colision
   */
  public boolean isCollision() {
    return this.collision;
  }

  /**
   * @param colision
   *          the colision to set
   */
  public void setCollision(boolean collision) {
    this.collision = collision;
  }

}
