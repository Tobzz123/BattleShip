
import battleship.BattleShip;

/**
 * Starting code for COMP10205 - Assignment#6
 *
 * STATEMENT OF AUTHORSHIP
 * We, the group, have not copied our work from anyone, and we have not allowed our code to be copied by anyone too.
 * for better understanding of what we were doing, we had to switch up some things the original code
 * happy marking :)
 *
 * @author Mitchell Aninyang - 000796709
 * @author Olaoluwa Anthony-Egorp - 000776467
 */

public class A6
{
   static final int NUMBEROFGAMES = 1000;
   public static void startingSolution()
  {
    int totalShots = 0;
    System.out.println(BattleShip.version());
    for (int game = 0; game < NUMBEROFGAMES; game++) {

      BattleShip battleShip = new BattleShip();
      SampleBot sampleBot = new SampleBot(battleShip);
      
      // Call SampleBot Fire randomly - You need to make this better!
      while (!battleShip.allSunk()) {
        sampleBot.hunt();
        
      }
      int gameShots = battleShip.totalShotsTaken();
      totalShots += gameShots;
    }
    System.out.printf("SampleBot - The Average # of Shots required in %d games to sink all Ships = %.2f\n", NUMBEROFGAMES, (double)totalShots / NUMBEROFGAMES);
    
  }
  public static void main(String[] args)
  {
    startingSolution();
  }
}
