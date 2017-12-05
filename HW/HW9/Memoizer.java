import java.util.HashMap;
/**
 * Write a description of class Memoizer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Memoizer
{
    private Functor fx;
    private HashMap map;
   public Memoizer(Functor f)
   {
       fx = f;
       map = new HashMap();
    }
    
   public Object call(Object x)
   {
      if(map.containsKey(x))
      return map.get(x);
      else
      {
       map.put(x, fx.fn(x));
       return map.get(x);
       }
   }
}
