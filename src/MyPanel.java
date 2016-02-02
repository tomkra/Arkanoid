/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2015_dp_kra0410;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This class is special class for painting on screen. It extends functionality of Game class.
 *
 * @author Tomas Kral
 * @version 0.1
 */
public class MyPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private IMovable ball;
  private IListener pad;
  private BufferedImage image;
  private ArrayList<Brick> bricks = new ArrayList<Brick>();
  private int numOfBricks = 0;
  private int difficulty;

  /**
   * @param difficulty
   *          the difficulty to set
   */
  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public MyPanel() {
    //this.init(1, false);
  }

  @Override
  public void paint(Graphics g) {
    this.image = new BufferedImage(Const.WIDTH - Const.SCORE_WIDTH,
        Const.HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics b = this.image.getGraphics();
    this.ball.paint(b);
    this.pad.paint(b);
    for (Brick brick : this.bricks) {
      if (brick.isCollision()) {
        brick.paint(b);
      }
    }
    g.drawImage(this.image, 0, 0, this);
  }

  public void run() throws InterruptedException {
    this.ball.move();
    repaint();
    Thread.sleep(this.difficulty);
  }

  /**
   * @return the bricks
   */
  public ArrayList<Brick> getBricks() {
    return this.bricks;
  }

  public void removeBrickLive(Brick b) {
    b.setStrenght(b.getStrenght() - 1);
    Game g = Game.getInstance();
    if (b.getStrenght() <= 0) {
      b.setCollision(false);
      this.numOfBricks--;
      g.setS(g.getS() + 1);
      g.getScore().setText("Score: " + g.getS());
    }
    if (this.numOfBricks == 0) {
      g.nextLevel();
    }
  }

  public void init(int level, boolean respawn) {
    this.pad = new Pad();
    this.ball = new Ball();
    Brick brick;
    int x = 100;
    int y = 50;
    if (respawn) {
      this.difficulty -= level;
      if (this.difficulty < 2) {
        this.difficulty = 2;
      }
      this.bricks = new ArrayList<Brick>();
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 8; j++) {
          brick = new Brick(x, y, level);
          this.bricks.add(brick);
          this.numOfBricks++;
          x += 60;
        }
        x = 100;
        y += 40;
      }
    }
  }

  /**
   * @return the pad
   */
  public IListener getPad() {
    return this.pad;
  }
}
