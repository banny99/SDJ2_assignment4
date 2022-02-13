
public class ArchiveLog
{
  private static ArchiveLog instance;

  private ArchiveLog(){
  }

  public static ArchiveLog getInstance()
  {
    if (instance == null)
      instance = new ArchiveLog();
    return instance;
  }

  public void log(String note){
    System.out.println(note);
  }
}
