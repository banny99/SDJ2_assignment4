import Valuables.*;

import java.util.ArrayList;

public class Mine
{
  ArrayList<IRandomValuable> randomValuableGetters;

  public Mine()
  {
    randomValuableGetters = new ArrayList<>();

    randomValuableGetters.add(new IRandomValuable()
    {
      @Override public Valuable getRandomValuable()
      {
        return new Gold();
      }
    });
    randomValuableGetters.add(new IRandomValuable()
    {
      @Override public Valuable getRandomValuable()
      {
        return new Diamond();
      }
    });
    randomValuableGetters.add(new IRandomValuable()
    {
      @Override public Valuable getRandomValuable()
      {
        return new Emerald();
      }
    });
  }

  public synchronized Valuable mineValuable()
  {
    int randomIndex = (int)(Math.random() * randomValuableGetters.size());
    return randomValuableGetters.get(randomIndex).getRandomValuable();
  }
}
