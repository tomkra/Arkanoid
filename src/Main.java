/*******************************************************************************
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/
package pj1_2015_dp_kra0410;

/**
 * Run the game in infinity loop.
 *
 * @author Tomas Kral
 * @version 0.1
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    Game game = Game.getInstance();

    while (true) {
      game.getMyPanel().run();
    }
  }

}
