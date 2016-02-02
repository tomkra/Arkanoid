/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2015_dp_kra0410;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * Moving pad.
 * 
 * @author Tomas Kral
 * @version 0.1
 */
public class Pad implements IListener {
  private final int width;
  private final int height;
  private int x;
  private int y;

  public Pad() {
    this.width = 120;
    this.height = 10;
    this.x = Const.WIDTH / 2 - this.width / 2 - Const.SCORE_WIDTH / 2;
    this.y = Const.HEIGHT - this.height * 2 - 20;
    //this.y = Const.HEIGHT - 200;
  }

  /**
   * @return the x
   */
  @Override
  public int getX() {
    return this.x;
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
   * @return the y
   */
  @Override
  public int getY() {
    return this.y;
  }

  /**
   * @param y
   *          the y to set
   */
  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_RIGHT:
        if (!(this.getX() + Const.PAD_STEP > Const.WIDTH - Const.SCORE_WIDTH
            - this.getWidth())) {
          this.setX(this.getX() + Const.PAD_STEP);
        } else {
          this.setX(Const.WIDTH - Const.SCORE_WIDTH - this.getWidth());
        }
        break;
      case KeyEvent.VK_LEFT:
        if (!(this.getX() - Const.PAD_STEP < 0)) {
          this.setX(this.getX() - Const.PAD_STEP);
        } else {
          this.setX(0);
        }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub

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
}
