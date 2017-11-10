 /**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          02 Oct 09; 12 Feb 10; 04 Oct 12; 03 Oct 14; 25 Feb 15; 23 Sep 16
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

@SuppressWarnings("unchecked")
public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }
// safe car, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }
// safe cdr, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    public static Object second (Cons x) { return first(rest(x)); }
    public static Object third (Cons x) { return first(rest(rest(x))); }
    public static void setfirst (Cons x, Object i) { x.car = i; }
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }

    // convert a list to a string for printing
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public static int square(int x) { return x*x; }

    // iterative destructive merge using compareTo
public static Cons dmerj (Cons x, Cons y) {
  if ( x == null ) return y;
   else if ( y == null ) return x;
   else { Cons front = x;
          if ( ((Comparable) first(x)).compareTo(first(y)) < 0)
             x = rest(x);
            else { front = y;
                   y = rest(y); };
          Cons end = front;
          while ( x != null )
            { if ( y == null ||
                   ((Comparable) first(x)).compareTo(first(y)) < 0)
                 { setrest(end, x);
                   x = rest(x); }
               else { setrest(end, y);
                      y = rest(y); };
              end = rest(end); }
          setrest(end, y);
          return front; } }

public static Cons midpoint (Cons lst) {
  Cons current = lst;
  Cons prev = current;
  while ( lst != null && rest(lst) != null) {
    lst = rest(rest(lst));
    prev = current;
    current = rest(current); };
  return prev; }

    // Destructive merge sort of a linked list, Ascending order.
    // Assumes that each list element implements the Comparable interface.
    // This function will rearrange the order (but not location)
    // of list elements.  Therefore, you must save the result of
    // this function as the pointer to the new head of the list, e.g.
    //    mylist = llmergesort(mylist);
public static Cons llmergesort (Cons lst) {
  if ( lst == null || rest(lst) == null)
     return lst;
   else { Cons mid = midpoint(lst);
          Cons half = rest(mid);
          setrest(mid, null);
          return dmerj( llmergesort(lst),
                        llmergesort(half)); } }


    // ****** your code starts here ******
    // add other functions as you wish.
    public static int length(Cons lst)
{
    //basae case for where there's nothing left in the list
    if (rest(lst) == null)
    {
        return 1;
    }
    return 1 + length(rest(lst));
}

public static Cons union (Cons x, Cons y) {
    return mergeunion(llmergesort(x), llmergesort(y));
}

    // following is a helper function for union
public static Cons mergeunion (Cons x, Cons y) {
    if(rest(y)==null)
    return x;
    if(rest(x)==null)
    return y;
    if( ((Comparable) first(x)).compareTo(first(y)) < 0)
    return Cons.cons(first(x), mergeunion(rest(x),y));
    else if( ((Comparable) first(x)).compareTo(first(y)) == 0)
    return Cons.cons((String)first(x), mergeunion(rest(x), rest(y)));
    else
    return Cons.cons((String)first(y), mergeunion(rest(y),x));
}

public static Cons setDifference (Cons x, Cons y) {
    return mergediff(llmergesort(x), llmergesort(y));
}

    // following is a helper function for setDifference
public static Cons mergediff (Cons x, Cons y) {
       if(rest(x)==null)
    return null;
    if( ((Comparable) first(x)).compareTo(first(y)) != 0)
    return Cons.cons((String)first(x), mergediff(rest(x),y));
    else
    return mergediff(rest(x),rest(y));
}


public static Cons bank(Cons accounts, Cons updates) {
       return bankb(llmergesort(accounts),llmergesort(updates));    
}

public static Cons bankb(Cons x , Cons y)
{
    
    if(rest(x) == null)
    {        
       return Cons.cons(new Account(((Account)first(x)).name(), ((Account)first(x)).amount()),null);
    }
    
    else if(y == null)
    {
        return Cons.cons(new Account(((Account)first(x)).name(), ((Account)first(x)).amount()), bankb(rest(x), y));
    }
    else if(((Account)first(x)).name().compareTo(((Account)first(y)).name()) < 0)
    {
       return Cons.cons(new Account(((Account)first(x)).name(), ((Account)first(x)).amount()), bankb(rest(x), y));
    }
    else if(((Account)first(x)).name().compareTo(((Account)first(y)).name())== 0)
    {
        int balance = ((Account)first(x)).amount() + ((Account)first(y)).amount();
        if(balance < 0)
        {
        x = Cons.cons(new Account(((Account)first(x)).name(), balance - 30), rest(x));
        System.out.println("Due to negative balance, you have been charged an overdraft of $30" + "\n" + first(x));
      }
      else
      x = Cons.cons(new Account(((Account)first(x)).name(), balance), rest(x));
      return bankb(x, rest(y));
    }      
    else if(((Account)first(x)).name().compareTo(((Account)first(y)).name()) > 0)
    {
       if(((Account)first(y)).amount() > 0)
       {
       System.out.println("There is a new account for: \n" + ((Account)first(y)).toString());
       return Cons.cons(new Account(((Account)first(y)).name(), ((Account)first(y)).amount()), bankb(x, rest(y)));
       }
       else 
       {
       System.out.println("There is no account under: \n" + ((Account)first(y)).toString());
       return bankb(x, rest(y));
       }
    }
    else
    return null;
    
}

public static String [] mergearr(String [] x, String [] y) {
    
    String[] result = new String[x.length + y.length];
    int topA = 0; int topB = 0;
    for(int i = 0; i < result.length; i++)
    {
        if(topA < x.length && topB < y.length)
        {
        if(x[topA].compareTo(y[topB]) < 0)
          result[i] = x[topA++];
        else
        result[i] = y[topB++];
       }
       else if(topA == x.length)
       result[i] = y[topB++];
       else
       result[i] = x[topA++];
        
    }
    return result;
    
}

public static boolean markup(Cons text) {
    boolean okay = false;
    Cons xml = text;
    Cons stack = null;
    int i = 0;
    while(xml != null)
    {
        if(((String)first(xml)).length() <= 0)
        break;
        else if((((String)first(xml)).charAt(0) == '<' && ((String)first(xml)).charAt(1) != '/' ))
        stack = Cons.cons(first(xml), stack);
        if(((String)first(xml)).charAt(1) == '/')
        {
            if(((String)first(xml)).regionMatches(2,(String)first(stack), 1, ((String)first(xml)).length()- 3))
            {
            okay = true;
            stack = rest(stack);

           }
            else
            {
            okay = false;
            System.out.println("Error! Invalid closing tag, " + (String)first(xml)+ " at: " + i + " expected closing tag for: " + (String)first(stack));
            return okay;  
            }
        }
        xml = rest(xml);
        i++;
    } 
    if(stack != null)
    {
     if(xml != null)
     System.out.println("Error! Tag at end of the list is invalid, expected closing tag for: " + (String)first(stack));
     else
     System.out.println("Error! No closing tag");
    return false;
    }    
    return okay;    
}

    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        Cons set1 = list("d", "b", "c", "a");
        Cons set2 = list("f", "d", "b", "g", "h");
        System.out.println("set1 = " + set1);
        System.out.println("set2 = " + set2);
        System.out.println("union = " + union(set1, set2));

        Cons set3 = list("d", "b", "c", "a");
        Cons set4 = list("f", "d", "b", "g", "h");
        System.out.println("set3 = " + set3);
        System.out.println("set4 = " + set4);
        System.out.println("difference = " +
                           setDifference(set3, set4));

        Cons accounts = list(
               new Account("Arbiter", new Integer(498)),
               new Account("Flintstone", new Integer(102)),
               new Account("Foonly", new Integer(123)),
               new Account("Kenobi", new Integer(373)),
               new Account("Rubble", new Integer(514)),
               new Account("Tirebiter", new Integer(752)),
               new Account("Vader", new Integer(1024)) );

        Cons updates = list(
               new Account("Foonly", new Integer(100)),
               new Account("Flintstone", new Integer(-10)),
               new Account("Arbiter", new Integer(-600)),
               new Account("Garble", new Integer(-100)),
               new Account("Rabble", new Integer(100)),
               new Account("Flintstone", new Integer(-20)),
               new Account("Foonly", new Integer(10)),
               new Account("Tirebiter", new Integer(-200)),
               new Account("Flintstone", new Integer(10)),
               new Account("Flintstone", new Integer(-120))  );
        System.out.println("accounts = " + accounts);
        System.out.println("updates = " + updates);
        Cons newaccounts = bank(accounts, updates);
        System.out.println("result = " + newaccounts);

        String[] arra = {"a", "big", "dog", "hippo"};
        String[] arrb = {"canary", "cat", "fox", "turtle"};
        String[] resarr = mergearr(arra, arrb);
        
        for ( int i = 0; i < resarr.length; i++ )
            System.out.println(resarr[i]);
            
        
        Cons xmla = list( "<TT>", "foo", "</TT>");
        Cons xmlb = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "</TR>",
                          "<TR>", "<TD>", "fum", "</TD>", "<TD>",
                          "baz", "</TD>", "</TR>", "</TABLE>" );
        Cons xmlc = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "</TR>",
                          "<TR>", "<TD>", "fum", "</TD>", "<TD>",
                          "baz", "</TD>", "</WHAT>", "</TABLE>" );
        Cons xmld = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "", "</TR>",
                          "</TABLE>", "</NOW>" );
        Cons xmle = list( "<THIS>", "<CANT>", "<BE>", "foo", "<RIGHT>" );
        Cons xmlf = list( "<CATALOG>",
                          "<CD>",
                          "<TITLE>", "Empire", "Burlesque", "</TITLE>",
                          "<ARTIST>", "Bob", "Dylan", "</ARTIST>",
                          "<COUNTRY>", "USA", "</COUNTRY>",
                          "<COMPANY>", "Columbia", "</COMPANY>",
                          "<PRICE>", "10.90", "</PRICE>",
                          "<YEAR>", "1985", "</YEAR>",
                          "</CD>",
                          "<CD>",
                          "<TITLE>", "Hide", "your", "heart", "</TITLE>",
                          "<ARTIST>", "Bonnie", "Tyler", "</ARTIST>",
                          "<COUNTRY>", "UK", "</COUNTRY>",
                          "<COMPANY>", "CBS", "Records", "</COMPANY>",
                          "<PRICE>", "9.90", "</PRICE>",
                          "<YEAR>", "1988", "</YEAR>",
                          "</CD>", "</CATALOG>");
        System.out.println("xmla = " + xmla);
        System.out.println("result = " + markup(xmla));
        System.out.println("xmlb = " + xmlb);
        System.out.println("result = " + markup(xmlb));
        System.out.println("xmlc = " + xmlc);
        System.out.println("result = " + markup(xmlc));
        System.out.println("xmld = " + xmld);
        System.out.println("result = " + markup(xmld));
        System.out.println("xmle = " + xmle);
        System.out.println("result = " + markup(xmle));
        System.out.println("xmlf = " + xmlf);
        System.out.println("result = " + markup(xmlf));
        

      }

}
