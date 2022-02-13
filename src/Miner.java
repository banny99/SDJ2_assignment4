import Valuables.Valuable;

public class Miner implements Runnable
{
  private Mine mine;
  private IBlockingQueue deposit;

  public Miner(Mine mine, IBlockingQueue deposit)
  {
    this.mine = mine;
    this.deposit = deposit;
  }


  @Override public void run()
  {
    Valuable valuable;

    while (true)
    {
      valuable = mine();
      insertIntoDeposit(valuable);

      restForAWhile(500, 2000);
    }
  }



  private Valuable mine()
  {
    return mine.mineValuable();
  }

  private void insertIntoDeposit(Valuable valuable)
  {
    deposit.enqueue(valuable);
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
