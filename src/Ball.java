/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2015_dp_kra0410;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Ball class for creating and painting ball. This class handles objects collision with ball.
 *
 * @author Tomas Kral
 * @version 0.1
 */
public class Ball implements IMovable {
  private int x, y;
  private final int size = 14;
  private final int speed;
  private int dirX, dirY;

  public Ball() {
    this.x = Const.WIDTH / 2 - Const.SCORE_WIDTH / 2;
    this.y = Const.HEIGHT / 2;
    //this.y = 20;
    this.speed = 1;
    this.dirX = 0;
    this.dirY = 1;
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillOval(this.x, this.y, this.size, this.size);
  }

  @Override
  public void move() {
    Game g = Game.getInstance();
    if (!g.isPaused()) {
      return;
    }
    this.x = this.x + this.speed * this.dirX;
    this.y = this.y + this.speed * this.dirY;

    //walls collision
    if (this.x + this.size / 2 >= Const.WIDTH - Const.SCORE_WIDTH - this.size) {
      this.dirX *= -1;
    }
    if (this.x - this.size / 2 <= 0) {
      this.dirX *= -1;
    }
    if (this.y - this.size / 2 <= 0) {
      this.dirY *= -1;
    }
    if (this.y + this.size / 2 >= Const.HEIGHT - 20) {
      g.minusLive();
      //this.dirY *= -1;
    }

    //pad collision first half
    /*if (this.x + this.size >= g.getMyPanel().getPad().getX()
        && this.x + this.size <= g.getMyPanel().getPad().getX()
            + g.getMyPanel().getPad().getWidth() / 2) {
      if (this.y + this.size >= g.getMyPanel().getPad().getY()) {
        this.dirX = -1;
        this.dirY *= -1;
      }
    }
    //pad collision second half
    if (this.x + this.size > g.getMyPanel().getPad().getX()
        + g.getMyPanel().getPad().getWidth() / 2
        && this.x + this.size <= g.getMyPanel().getPad().getX()
            + g.getMyPanel().getPad().getWidth()) {
      if (this.y + this.size >= g.getMyPanel().getPad().getY()) {
        this.dirX = 1;
        this.dirY *= -1;
      }
    }*/

    if (this.x + this.size >= g.getMyPanel().getPad().getX() //micek rovne
        + g.getMyPanel().getPad().getWidth() / 2
        && this.x + this.size <= g.getMyPanel().getPad().getX()
            + g.getMyPanel().getPad().getWidth() / 2 + 6) {
      if (this.y + this.size >= g.getMyPanel().getPad().getY()) {
        this.dirX = 0;
        this.dirY *= -1;
      }
    }

    if (this.x + this.size >= g.getMyPanel().getPad().getX() //prvni polovina doleva
        && this.x + this.size < g.getMyPanel().getPad().getX()
            + g.getMyPanel().getPad().getWidth() / 2) {
      if (this.y + this.size >= g.getMyPanel().getPad().getY()) {
        this.dirX = -1;
        this.dirY *= -1;
      }
    }
    //pad collision second half
    if (this.x + this.size > g.getMyPanel().getPad().getX() //prvni polovina doprava
        + g.getMyPanel().getPad().getWidth() / 2 + 6
        && this.x + this.size <= g.getMyPanel().getPad().getX()
            + g.getMyPanel().getPad().getWidth()) {
      if (this.y + this.size >= g.getMyPanel().getPad().getY()) {
        this.dirX = 1;
        this.dirY *= -1;
      }
    }

    //bricks collision
    for (Brick b : g.getMyPanel().getBricks()) {
      if (!b.isCollision()) {
        continue;
      }

      boolean removeBrick = false;
      //top and bottom
      if (this.x >= b.getX() && this.x <= b.getX() + b.getWidth()) {
        if (this.y + this.size >= b.getY()
            && this.y <= b.getY() + b.getHeight()) {
          //g.getMyPanel().removeBrickLive(b);
          removeBrick = true;
          this.dirY *= -1;
        }
      }
      //left and right
      if (this.y >= b.getY() && this.y <= b.getY() + b.getHeight()) {
        if (this.x + this.size >= b.getX()
            && this.x <= b.getX() + b.getWidth()) {
          //g.getMyPanel().removeBrickLive(b);
          removeBrick = true;
          this.dirX *= -1;
        }
      }
      if (removeBrick) {
        g.getMyPanel().removeBrickLive(b);
      }

    }

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

  /**
   * @return the dirX
   */
  public int getDirX() {
    return this.dirX;
  }

  /**
   * @param dirX
   *          the dirX to set
   */
  public void setDirX(int dirX) {
    this.dirX = dirX;
  }

  /**
   * @return the dirY
   */
  public int getDirY() {
    return this.dirY;
  }

  /**
   * @param dirY
   *          the dirY to set
   */
  public void setDirY(int dirY) {
    this.dirY = dirY;
  }

  @Override
  public int getWidth() {
    return this.size;
  }

  @Override
  public int getHeight() {
    return this.size;
  }
}
