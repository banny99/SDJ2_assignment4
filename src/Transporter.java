import Valuables.Valuable;
import java.util.ArrayList;

public class Transporter implements Runnable
{
  //is given the guard(proxy) instead of treasure room itself
  private final ITreasureRoomDoor treasureRoom;
  private final IBlockingQueue deposit;
  private final ArrayList<Valuable> valuableTransportBag;

  public Transporter(IBlockingQueue deposit, ITreasureRoomDoor treasureRoom)
  {
    this.treasureRoom = treasureRoom;
    this.deposit = deposit;
    valuableTransportBag = new ArrayList<>();
  }

  @Override public void run()
  {
    int min = 20;
    int max = 50;

    while (true)
    {
      this.withdrawValuables((int)(Math.random() * (max-min) +min));

      this.transportValuables();

      restForAWhile(3000, 9000);
    }
  }

  private void withdrawValuables(int desiredNumOfValuables)
  {
    Valuable v;
    while (valuableTransportBag.size() < desiredNumOfValuables)
    {
      //take from deposit
      v = deposit.dequeue();
      //put to transport bag
      valuableTransportBag.add(v);

      restForAWhile(50, 200);
    }
    ArchiveLog.getInstance().log(Thread.currentThread().getName() +"-Withdrew " + valuableTransportBag.size() +"/"+ desiredNumOfValuables + " valuables");
  }

  private void transportValuables()
  {
    treasureRoom.acquireWriteAccess();

    int tempSize = valuableTransportBag.size();
    while (!valuableTransportBag.isEmpty())
    {
      treasureRoom.add(valuableTransportBag.remove(0));
      restForAWhile(10, 100);
    }

    ArchiveLog.getInstance().log(Thread.currentThread().getName()+"-Transported " +tempSize+ " valuables to the treasure room");
    treasureRoom.releaseWriteAccess();
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
