
/**
 * Write a description of class Merge here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Merge
{
    // instance variables - replace the example below with your own
    public static Cons dmerjr(Cons x, Cons y)
    {
        if(x == null)
        return y;
        else if(y==null)
        return x;
        else if(((Comparable)Cons.first(x)).compareTo(Cons.first(y)) < 0)
        {
            Cons.setrest(x, dmerjr(Cons.rest(x),y));
            return x;
        }
        else
        {
            Cons.setrest(y, dmerjr(x, Cons.rest(y)));
            return y;
        }
    }
    
    public static void main(String[] args)
    {
        String str1 = "<TT>";
        String str2 = "</TT>";
        System.out.println(str1.regionMatches(1,str2,2,str1.length()-2));
        
    }

}
