import Valuables.Valuable;

import java.util.ArrayList;

public class TreasureRoom implements ITreasureRoomDoor
{
  private ArrayList<Valuable> storedValuables;
  public TreasureRoom()
  {
    storedValuables = new ArrayList<>();
  }


  @Override public void add(Valuable valuable)
  {
    storedValuables.add(valuable);
  }

  @Override public Valuable retrieve()
  {
    if (storedValuables.isEmpty())
      return null;
    else
      return storedValuables.remove(0);
  }


  @Override public double look()
  {
    //simulate counting time
    simulateActionTime();

    double value = 0;
    for (Valuable v : storedValuables)
      value += v.getValue();

    return value;
  }


  private void simulateActionTime()
  {
    int min = 1000;
    int max = 9000;

    int randomSleepTime = (int)(Math.random() * (max-min) +min);
    try {
      Thread.sleep(randomSleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override public void acquireReadAccess()
  {
    //do nothing ?
  }
  @Override public void releaseReadAccess()
  {
    //do nothing ?
  }

  @Override public void acquireWriteAccess()
  {
    //do nothing ?
  }
  @Override public void releaseWriteAccess()
  {
    // do nothing ?
  }
}
