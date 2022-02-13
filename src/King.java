import Valuables.Valuable;

import java.util.ArrayList;

public class King implements Runnable
{
  //is given the guard(proxy) instead of treasure room itself
  private final ITreasureRoomDoor treasureRoom;
  private final ArrayList<Valuable> retrievedValuables;
  private ArchiveLog logInstance;

  public King(ITreasureRoomDoor treasureRoom)
  {
    retrievedValuables = new ArrayList<>();
    this.treasureRoom = treasureRoom;
    logInstance = ArchiveLog.getInstance();
  }

  @Override public void run()
  {
    int min = 50;
    int max = 150;

    while (true)
    {
      //generate random number of desired valuables
      int desiredNumOfValuables = (int)(Math.random() * (max-min) +min);

      //acquire write access
      treasureRoom.acquireWriteAccess();

      //retrieve desired valuables
      logInstance.log(Thread.currentThread().getName() + "-Retrieving valuables ...");
      this.retrieveDesiredValuables(desiredNumOfValuables);
      logInstance.log(Thread.currentThread().getName() + "-Retrieved " + retrievedValuables.size() +"/"+ desiredNumOfValuables);

      //if desired valuables retrieved ->make party = spend valuables
      if (retrievedValuables.size() >= desiredNumOfValuables){
        ArchiveLog.getInstance().log("Enough valuables for the party ->Party happening, spending ..." + retrievedValuables.size());
        this.makeParty();
      }
      //else: ->cancel the party = put valuables back and try again later...
      else{
        ArchiveLog.getInstance().log("Not enough valuables for the party ->turning them back ...");
        this.cancelParty();
      }

      treasureRoom.releaseWriteAccess();

      //simulate time
      this.restForAWhile(5000, 15000);
    }
  }

  private void retrieveDesiredValuables(int desiredNumOfValuables)
  {
    //retrieving of valuables one-by-one
    Valuable v;
    while (retrievedValuables.size() < desiredNumOfValuables)
    {
      //try taking valuable from deposit
      v = treasureRoom.retrieve();

      //if there is not enough valuables ->cancel retrieving/break
      if (v == null)
        break;

      //put to retrieved
      retrievedValuables.add(v);
//      restForAWhile(100, 500);
    }
  }

  private void cancelParty()
  {
    //put valuables back
    while (!retrievedValuables.isEmpty())
    {
      treasureRoom.add(retrievedValuables.remove(0));
//      restForAWhile(100, 500);
    }
    ArchiveLog.getInstance().log("Party canceled ->valuables turned back");
  }

  private void makeParty()
  {
    //spend retrieved valuables
    while (!retrievedValuables.isEmpty())
    {
      retrievedValuables.remove(0);
    }
    ArchiveLog.getInstance().log("Party happening ->retrieved valuables spent");
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