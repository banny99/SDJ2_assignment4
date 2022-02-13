public class Accountant implements Runnable
{
  //will be given the guard(proxy) instead of treasure room itself
  private ITreasureRoomDoor treasureRoom;

  public Accountant(ITreasureRoomDoor treasureRoom)
  {
    this.treasureRoom = treasureRoom;
  }

  @Override public void run()
  {
    while (true)
    {
      countValuablesValue();

      restForAWhile(2000, 10000);
    }
  }

  private void countValuablesValue()
  {
    //acquire read access
    treasureRoom.acquireReadAccess();
    double price = treasureRoom.look();
    restForAWhile(200, 1000);
    ArchiveLog.getInstance().log("price of valuables stored in Treasure room = " + price);
    //release read access
    treasureRoom.releaseReadAccess();
  }

  private void restForAWhile(int min, int max)
  {
    int randomSleepTime = (int)(Math.random() * (max-min) +min);
    try {
      Thread.sleep(randomSleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
