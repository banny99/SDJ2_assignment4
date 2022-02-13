import Valuables.Valuable;
import utility.collection.ArrayList;

public class Deposit implements IBlockingQueue
{
  private static final int queueCapacity = 50;
  private ArrayList<Valuable> valuablesQueue;

  public Deposit()
  {
    valuablesQueue = new ArrayList<>();
  }

  @Override public synchronized void enqueue(Valuable v)
  {
    try
    {
      while (valuablesQueue.size() >= queueCapacity)
      {
        ArchiveLog.getInstance().log(Thread.currentThread().getName()+ "-WAITING because of full deposit [" +valuablesQueue.size() +"]");
        wait();
      }
    }catch (InterruptedException e){
      e.printStackTrace();
    }

    valuablesQueue.add(v);
    ArchiveLog.getInstance().log(Thread.currentThread().getName()+ "-ADDED '" +v.getName() + "' valuable into the deposit [" +valuablesQueue.size() +"]");
    notifyAll();
  }

  @Override public synchronized Valuable dequeue()
  {
    try
    {
      while (valuablesQueue.isEmpty())
      {
        ArchiveLog.getInstance().log(Thread.currentThread().getName()+ "-WAITING because of empty deposit [" +valuablesQueue.size()+ "]");
        wait();
      }
    }catch (InterruptedException e){
      e.printStackTrace();
    }

    Valuable v = valuablesQueue.remove(0);
    ArchiveLog.getInstance().log(Thread.currentThread().getName()+ "-WITHDRAWING from deposit[" +valuablesQueue.size()+ "]");
    notifyAll();

    return v;
  }

  @Override public synchronized int size()
  {
    return valuablesQueue.size();
  }

  @Override public synchronized boolean isEmpty()
  {
    return valuablesQueue.isEmpty();
  }

  @Override public synchronized void clear()
  {
    while (!valuablesQueue.isEmpty())
      valuablesQueue.remove(0);
  }

}
