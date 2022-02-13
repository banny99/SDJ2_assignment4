import Valuables.Valuable;

public interface IBlockingQueue
{
  void enqueue(Valuable v);
  Valuable dequeue();
  int size();
  boolean isEmpty();
  void clear();
}
