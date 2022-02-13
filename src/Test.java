public class Test
{
  public static void main(String[] args)
  {
    Mine mine = new Mine();
    Deposit deposit = new Deposit();
    Guardsman guardsman = new Guardsman(new TreasureRoom());


    Thread[] miners = new Thread[15];
    for (int i=0; i< miners.length; i++)
    {
      Runnable miner = new Miner(mine, deposit);
      miners[i] = new Thread(miner, "M"+i);
      miners[i].start();
    }

    Thread[] transporters = new Thread[3];
    for (int i=0; i< transporters.length; i++)
    {
      Runnable transporter = new Transporter(deposit, guardsman);
      transporters[i] = new Thread(transporter, "T"+i);
      transporters[i].start();
    }

    Thread[] accountants = new Thread[2];
    for (int i=0; i<accountants.length; i++)
    {
      Runnable accountant = new Accountant(guardsman);
      accountants[i] = new Thread(accountant, "A"+i);
      accountants[i].start();
    }

    Thread king = new Thread(new King(guardsman), "K");
    king.start();


//    ArchiveLog.getInstance().closeStreams();
  }
}
