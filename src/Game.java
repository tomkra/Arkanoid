/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2015_dp_kra0410;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The Game class is used like singleton. This class is main class of game.
 * It is used for creating UI and implementing game methods.
 *
 * @author Tomas Kral
 * @version 0.1
 */
public class Game {
  private static Game instance = null;
  private final JFrame myFrame;
  private final MyPanel myPanel;
  private final JPanel scorePanel;
  private final JLabel score;
  private final JLabel lives;
  private int s = 0;
  private int l;
  private int level = 1;
  private boolean paused = true;

  private Game() {
    this.myFrame = new JFrame("Arkanoid 2015 v0.1");
    this.myFrame.setSize(Const.WIDTH, Const.HEIGHT);
    this.myFrame.setLocationRelativeTo(null);
    this.myFrame.setLayout(new BorderLayout());
    this.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.myFrame.setResizable(false);

    this.myPanel = new MyPanel();
    this.scorePanel = new JPanel();

    this.scorePanel.setBackground(Color.DARK_GRAY);
    this.scorePanel
        .setPreferredSize(new Dimension(Const.SCORE_WIDTH, Const.HEIGHT));
    this.scorePanel.setLayout(new BoxLayout(this.scorePanel, BoxLayout.Y_AXIS));

    this.score = new JLabel("Score: " + this.s);
    this.score.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.score.setForeground(Color.PINK);
    this.score.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); //pro odsazeni
    this.scorePanel.add(this.score);

    this.lives = new JLabel("Lives: " + this.l);
    this.lives.setForeground(Color.CYAN);
    this.lives.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.lives.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
    this.scorePanel.add(this.lives);

    JButton btn = new JButton("Play/Pause");
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    btn.setMaximumSize(new Dimension(100, 40));
    btn.setFocusable(false);
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        gamePause();
      }
    });
    this.scorePanel.add(btn);

    btn = new JButton("New Game");
    btn.setMaximumSize(new Dimension(100, 40));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    btn.setFocusable(false);
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (isPaused()) {
          gamePause();
        }
        String ObjButtons[] = {"Yes", "No"};
        int PromptResult = JOptionPane.showOptionDialog(null,
            "Start new game? Progress will not be saved.", "New game?",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
            ObjButtons, ObjButtons[1]);
        if (PromptResult == JOptionPane.YES_OPTION) {
          newGame();
        }
      }
    });
    this.scorePanel.add(btn);

    btn = new JButton("Last Scores");
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    btn.setMaximumSize(new Dimension(100, 40));
    btn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (isPaused()) {
          gamePause();
        }
        showBestScore();
      }
    });
    btn.setFocusable(false);
    this.scorePanel.add(btn);

    btn = new JButton("Exit");
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    btn.setMaximumSize(new Dimension(100, 40));
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent a) {
        if (isPaused()) {
          gamePause();
        }
        String ObjButtons[] = {"Yes", "No"};
        int PromptResult =
            JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
                "Exit?", JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
        if (PromptResult == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    });
    btn.setPreferredSize(new Dimension(100, 30));
    btn.setFocusable(false);
    this.scorePanel.add(btn);

    this.myFrame.add(this.myPanel);
    this.myFrame.add(this.scorePanel, BorderLayout.EAST);

    this.myFrame.setVisible(true);
    newGame();
  }

  public void newGame() {
    this.s = 0;
    this.l = 3;
    this.level = 1;
    this.score.setText("Score: " + this.s);
    this.lives.setText("Lives: " + this.l);
    this.myPanel.setDifficulty(6);
    this.myPanel.init(this.level, true);
    if (isPaused()) {
      gamePause();
    }
  }

  public void nextLevel() {
    this.l++;
    this.lives.setText("Lives: " + this.l);
    this.level++;
    this.myPanel.init(this.level, true);
    this.myFrame.addKeyListener(this.myPanel.getPad());
  }

  public void gamePause() {
    if (this.paused) {
      this.myFrame.removeKeyListener(this.myPanel.getPad());
      this.paused = false;
    } else {
      this.myFrame.addKeyListener(this.myPanel.getPad());
      this.paused = true;
    }
  }

  public void gameOver() {
    String str =
        "GAME OVER!!!\nYour Score: " + this.s + "\n Insert your name: ";
    String name = JOptionPane.showInputDialog(str);

    if (name != null) {
      String write = name + " " + this.s + "\n";

      try {
        Files.write(Paths.get("lastScores.txt"), write.getBytes(),
            StandardOpenOption.APPEND);
      } catch (IOException e) {
        try {
          FileOutputStream f = new FileOutputStream(new File("lastScores.txt"));
          f.write(write.getBytes());
          f.close();
        } catch (IOException e1) {
        }

      }
    }

    String ObjButtons[] = {"Continue", "Exit"};
    int PromptResult = JOptionPane.showOptionDialog(null,
        "Do you want to play again?", "Play again?", JOptionPane.DEFAULT_OPTION,
        JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
    if (PromptResult == JOptionPane.YES_OPTION)

    {
      newGame();
    } else

    {
      System.exit(0);
    }

  }

  public void showBestScore() {
    BufferedReader inputStream = null;
    String score = "Last scores:\n";
    try {
      inputStream = new BufferedReader(new FileReader("lastScores.txt"));
      String l;
      while ((l = inputStream.readLine()) != null) {
        score = score + "\n" + l;
      }
    } catch (FileNotFoundException e) {
      score = score + "No one is here.";
    } catch (IOException e) {
      score = score + "No one is here.";
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
        }
      }
    }
    JOptionPane.showMessageDialog(this.myFrame, score, "Last scores",
        JOptionPane.INFORMATION_MESSAGE);
    return;
  }

  public void minusLive() {
    this.l -= 1;
    if (!(this.l < 0)) {
      this.lives.setText("Lives: " + this.l);
      this.myPanel.init(this.level, false);
      this.myFrame.addKeyListener(this.myPanel.getPad());
      gamePause();
    } else {
      gameOver();
    }
  }

  /**
   * @return the paused
   */
  public boolean isPaused() {
    return this.paused;
  }

  /**
   * @param paused
   *          the paused to set
   */
  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  /**
   * @return the s
   */
  public int getS() {
    return this.s;
  }

  /**
   * @param s
   *          the s to set
   */
  public void setS(int s) {
    this.s = s;
  }

  /**
   * @return the l
   */
  public int getL() {
    return this.l;
  }

  /**
   * @param l
   *          the l to set
   */
  public void setL(int l) {
    this.l = l;
  }

  /**
   * @return the score
   */
  public JLabel getScore() {
    return this.score;
  }

  /**
   * @return the lives
   */
  public JLabel getLives() {
    return this.lives;
  }

  /**
   * @return the myPanel
   */
  public MyPanel getMyPanel() {
    return this.myPanel;
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }
}
