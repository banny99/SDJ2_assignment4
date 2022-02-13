import Valuables.Valuable;

public interface ITreasureRoomDoor
{
  void acquireReadAccess();
  void releaseReadAccess();

  void acquireWriteAccess();
  void releaseWriteAccess();


  void add(Valuable valuable);
  Valuable retrieve();
  double look();
}
