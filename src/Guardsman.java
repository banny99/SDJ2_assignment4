import Valuables.Valuable;

public class Guardsman implements ITreasureRoomDoor
{

  // #FAVORING-WRITERS method

  private TreasureRoom treasureRoom;
  private int readers, writers, waitingWriters;

  public Guardsman(TreasureRoom treasureRoom)
  {
    this.treasureRoom = treasureRoom;
    readers = 0;
    writers = 0;
    waitingWriters = 0;
  }


  @Override public synchronized void add(Valuable valuable)
  {
    treasureRoom.add(valuable);
  }

  @Override public synchronized Valuable retrieve()
  {
    return treasureRoom.retrieve();
  }

  @Override public synchronized double look()
  {
    return treasureRoom.look();
  }


  @Override public synchronized void acquireWriteAccess()
  {
    waitingWriters++;

    try{
      while (readers>0 || writers>0)
      {
        ArchiveLog.getInstance().log(Thread.currentThread().getName() + "-WAITING; [r="+readers+", w="+writers+", ww="+waitingWriters+"]");
        wait();
      }
    }catch (InterruptedException e){
      e.printStackTrace();
    }

    waitingWriters--;
    writers++;
    ArchiveLog.getInstance().log(Thread.currentThread().getName() + "-WRITING; [r="+readers+", w="+writers+", ww="+waitingWriters+"]");
  }

  @Override public synchronized void releaseWriteAccess()
  {
    writers--;
    ArchiveLog.getInstance().log(Thread.currentThread().getName() + "-WRITING-released; [r="+readers+", w="+writers+", ww=" +waitingWriters+ "]");
    notifyAll();
  }


  @Override public synchronized void acquireReadAccess()
  {
    try
    {
      while (writers>0 || waitingWriters>0)
      {
        ArchiveLog.getInstance().log(Thread.currentThread().getName() + "-WAITING; [r="+readers+", w="+writers+". ww="+waitingWriters+"]");
        wait();
      }
    }catch (InterruptedException e){
      e.printStackTrace();
    }

    readers++;
    ArchiveLog.getInstance().log(Thread.currentThread().getName() + "-READING; [r="+readers+", w="+writers+", ww="+waitingWriters+"]");
  }

  @Override public synchronized void releaseReadAccess()
  {
    readers--;
    ArchiveLog.getInstance().log(Thread.currentThread().getName() + "-READING-released; [r="+readers+", w="+writers+", ww="+waitingWriters+"]");
    if (readers == 0)
      notify();
  }
}
