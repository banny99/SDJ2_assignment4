package Valuables;

public abstract class Valuable
{
  private String name;
  private double value;

  public Valuable(String name, double value)
  {
    this.name = name;
    this.value = value;
  }

  public String getName()
  {
    return name;
  }

  public double getValue()
  {
    return value;
  }
}
