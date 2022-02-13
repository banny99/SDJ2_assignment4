import Valuables.Diamond;
import Valuables.Valuable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.collection.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest
{

  private ArrayList<Valuable> arrayList;
  private Mine mine;

  @BeforeEach
  public void createDeposit(){
    arrayList = new ArrayList<>();
    mine = new Mine();
  }


  @Test
  public void simple_add_Test(){
    arrayList.add(mine.mineValuable());
    assertEquals(1, arrayList.size());
  }

  @Test
  public void multiple_valuables_insertion_Test(){
    int x = 10;
    for (int i=0; i<x; i++)
      arrayList.add(mine.mineValuable());

    assertEquals(x, arrayList.size());
  }

  @Test
  public void simple_add_remove_Test(){
    int x = 10;
    for (int i=0; i<x; i++){
      arrayList.add(mine.mineValuable());
      arrayList.remove(0);
    }

    assertEquals(0, arrayList.size());
  }

  @Test
  public void isEmpty_Test(){
    int x = 10;
    for (int i=0; i<x; i++){
      arrayList.add(mine.mineValuable());
      arrayList.remove(0);
    }

    assertTrue(arrayList.isEmpty());
  }

  @Test
  public void isNOT_Empty_Test(){
    int x = 10;
    for (int i=0; i<x; i++){
      arrayList.add(mine.mineValuable());
    }
    for (int i=0; i<x-1; i++){
      arrayList.add(mine.mineValuable());
      arrayList.remove(0);
    }

    assertFalse(arrayList.isEmpty());
  }

  @Test
  public void remove_from_empty_list_Test(){
    Exception e = assertThrows(IndexOutOfBoundsException.class, ()->{
      arrayList.remove(0);
    }, "IndexOutOfBound exception was expected");

    assertTrue(e.getMessage().contains("index"));
  }

  @Test
  public void remove_with_invalid_index_Test(){
    Exception e = assertThrows(IndexOutOfBoundsException.class, ()->{
      arrayList.remove(-1);
    }, "IndexOutOfBound exception was expected");

    assertTrue(e.getMessage().contains("index"));
  }

  @Test
  public void remove_from_list_Test(){
    arrayList.add(mine.mineValuable());

    Exception e = assertThrows(IndexOutOfBoundsException.class, ()->{
      arrayList.remove(1);
    }, "IndexOutOfBound exception was expected");

    assertTrue(e.getMessage().contains("index"));
  }

  @Test
  public void remove_than_was_added_Test(){
    int x = 10;

    for (int i=0; i<x; i++){
      arrayList.add(mine.mineValuable());
    }

    for (int i=0; i<x; i++){
      arrayList.remove(0);
    }
    Exception e = assertThrows(IndexOutOfBoundsException.class, ()->{
      arrayList.remove(1);
    }, "IndexOutOfBound exception was expected");

    assertTrue(e.getMessage().contains("index"));
  }

  @Test
  public void set_value_at_index_Test(){
    int x = 10;
    for (int i=0; i<x; i++){
      arrayList.add(mine.mineValuable());
    }
    arrayList.set(2, new Diamond());
    assertEquals(arrayList.get(2).getName(), "DIAMOND");
  }

  @Test
  public void contains_Test(){
    Valuable temp = mine.mineValuable();
    arrayList.add(temp);
    assertTrue(arrayList.contains(temp));
  }

  @Test
  public void containsNOT_Test(){
    arrayList.add(mine.mineValuable());
    assertFalse(arrayList.contains(mine.mineValuable()));
  }
}