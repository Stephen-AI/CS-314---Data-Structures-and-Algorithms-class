 /**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 02 Sep 09; 27 Jan 10
 *          05 Feb 10; 16 Jul 10; 02 Sep 10; 13 Jul 11
 */

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }

    // make a new Cons and put the arguments into it
    // add one new thing to the front of an existing list
    // cons("a", list("b", "c"))  =  (a b c)
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }

    // test whether argument is a Cons
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }

    // first thing in a list:    first(list("a", "b", "c")) = "a"
    // safe, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }

    // rest of a list after the first thing:
    //    rest(list("a", "b", "c")) = (b c)
    // safe, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }

    // second thing in a list:    second(list("a", "b", "c")) = "b"
    public static Object second (Cons x) { return first(rest(x)); }

    // third thing in a list:    third(list("a", "b", "c")) = "c"
    public static Object third (Cons x) { return first(rest(rest(x))); }

    // destructively replace the first
    public static void setfirst (Cons x, Object i) { x.car = i; }

    // destructively replace the rest
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }

    // make a list of things:   list("a", "b", "c") = (a b c)
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

    // ****** your code starts here ******

    // Sum of squares of integers from 1..n
    public static int sumsq (int n) {
        if(n == 0)
        {
            return 0;
        }
        else
        return (square(n) + sumsq(--n));
    }

    // Addition using Peano arithmetic
    /**
     * The invariant is that peanoplus runs y times
     * Since the peanoplus function runs until until y gets to 0, 
     * then the Big O is O(y)
     */
    public static int peanoplus(int x, int y) {
        if(y == 0)
        {
        return x;
    }
    
     return peanoplus(++x, --y);
    }

    /*
     * Since peanotimes runs y times and peanoplus runs x times, the Big O is O(xy)
     */
    public static int peanotimes(int x, int y) {
       
       if(y == 1)
       {
           return x;
       }
       
       
        return peanoplus(peanotimes(x,--y), x);
        
       
        
    }

    // n choose k: distinct subsets of k items chosen from n items
    public static int choose(int n, int k) { 
        
        return aux(n,k,n,k);
    }
    
    /**
     * Description: This auxillary function recursively finds the factorial of 2 sets of numbers and returns the quotient
     * @param n The number of things to choose from
     * @param k The number of things allowed to choose
     * @param x The numerator of the modified n choose k formula
     * @param y The denominator of the modified n choose k formula (Which is k!)
     * @return aux(n,k) Recursively multiplies n by x and k * y, k times, until it final returns the quotient of the numerator and the denominator 
     */
    public static int aux(int n, int k, double x, double y )
    {
        //base case
        if(k==0)
        return 1;
        if(k==1)
            return (int)(x/y) ;        
        return aux(--n, --k, n*x, k*y);
         }

    // Add up a list of Integer
    // iterative version, using while
public static int sumlist (Cons lst) {
  int sum = 0;
   while ( lst != null ) {
      sum += (Integer) first(lst);   // cast since first() can be Object
    lst = rest(lst); }
  return sum; }

    // second iterative version, using for
public static int sumlistb (Cons arg) {
  int sum = 0;
  for (Cons lst = arg ; lst != null; lst = rest(lst) )
    sum += (Integer) first(lst);   // cast since first() can be Object
  return sum; }

    // recursive version
    /**
     * Description: This function sums all integers in a list recursively
     * @param lst The list with the numbers to be summed
     * @return first(lst) + rest(lst) Uses recursion to traverse the list and add the next numbers in the list to the first
     */
public static int sumlistr (Cons lst) {
    //base case returns the last item in the list and exits the function
    if(rest(lst) == null)
    return (Integer)first(lst);
    //traversing and totalling the list
    return (Integer)first(lst) + (Integer)sumlistr((rest(lst)));
}

    // tail recursive version
    public static int sumlisttr (Cons lst) {
        //send the first integer an the list itself to the auxillary function
    return sumAux((Integer)first(lst), lst);
}
 /**
  * Description: This functions sums the integers in a list
  * @param sum This is an accumulator that eventually evaluates to the sum of all the integers in the list
  * @param lst This is the list of integers
  */
public static int sumAux(int sum, Cons lst)
{
    //base case returns the sum when there is nothing left in list
    if(rest(lst) == null)
    {
        return sum;
    }
    //add the first and second numbers in the list then return the rest of the list
    return sumAux(sum + (Integer)first(rest(lst)), rest(lst));
}

    // Sum of squared differences of elements of two lists
    // iterative version
 /**
  * Description: This function finds the sum of the square of the difference between 2 sets of numbers in lists
  * @param lst the first list of numbers
  * @param lsttb the second list of numbers
  * @return int sum - The accumulated sum of the square of the difference between a number in a list and it's corresponding pair in another list
  */
public static int sumsqdiff (Cons lst, Cons lstb) {
    int sum = 0;
    Cons i = lst; Cons j = lstb;
    while(i != null)
    {
        sum = sum + square((Integer)first(i) - (Integer)first(j));
        i = rest(i); j = rest(j);
    }
    return sum;
}

    // recursive version
public static int sumsqdiffr (Cons lst, Cons lstb) {
    //base case for when theres nothing left in either list
    if(rest(lst) == null)
    {
        //returns the last square of the difference of the numbers
        return square((Integer)first(lst) - (Integer)first(lstb));
    }
    //find the square difference of the first items in the list then returns the rest and adds recursively until theres nothing left in the list
    return square((Integer)first(lst) - (Integer)first(lstb)) + sumsqdiffr(rest(lst), rest(lstb));
    
}

    // tail recursive version
    //send both lists and the first sumsqdiff as parameters to the auxillary function
public static int sumsqdifftr (Cons lst, Cons lstb) {
    return sqAux(lst, lstb, square((Integer)first(lst)-(Integer)first(lstb)));
}
    
public static int sqAux(Cons lst, Cons lstb, int sum)
{
    //base case for when theres nothing left in either list
    if(rest(lst) == null)
    {
        //returns the accumulated sum
        return sum;
    }
    //returns the rest of the list while performing the sumsqdiff operations on the sum parameter
    return sqAux(rest(lst), rest(lstb), sum + square((Integer)first(rest(lst))-(Integer)first(rest(lstb))));
}

    // Find the maximum value in a list of Integer
    // iterative version
    /**
     * Description: This function returns the highest number in alist of numbers
     * @param lst The list of numbers to evaluate
     * @return highest The highest number in the list
     */
public static int maxlist (Cons lst) {
    //set the first number in the list to be the highest
    int highest = (Integer)first(lst);
    //Traverse the list whilst comparing the highest to the numbers, if a number is greater than the highest, it becomes the highest
    for(Cons i = rest(lst); i != null; i = rest(i))
    {
        if(highest < (Integer)first(i))
        {
            highest = (Integer)first(i);
        }
        
    }
    return highest;
}

    // recursive version
public static int maxlistr (Cons lst) {
    //set the highest to be the first in the list
    int num1 = (Integer)first(lst);
    //base case for when we get to the end of the list
    if(rest(lst) == null)
    {
        return num1;
    }
    //Recursively traverses the list and sets the last number to be equal to rest
    int num2 = maxlistr(rest(lst));
    
    //As the maxlistrs in the call stacks are popped from the Stack Frame, it traverses the list backwards comparing 2 numbers in the list and returning the greater
    return Math.max(num1, num2);
    
         
   
}

    // tail recursive version
    //pass the list and the first number in it as parameters
public static int maxlisttr (Cons lst) {
    return maxAux(lst, (Integer)first(lst));
}

public static int maxAux(Cons lst, int high)
{
    //base case for nothing left in the list
    if(rest(lst) == null)
    {
        return high;
    }
    //compares the items in the list with the high variable and returns the greater number
    if(high < (Integer)second(lst))
    return maxAux(rest(lst),(Integer)second(lst));
    else
    return maxAux(rest(lst), high);
    
}

    // Make a list of Binomial coefficients
    // binomial(2) = (1 2 1)
    /*
     * 4 choose 0 = 1
     * 4 choose 1 = 4
     * 4 choose 2 = 6
     * 4 choose 3 = 4
     * 4 choose 4 = 1
     * 
     * The binomial coefficients are equal to the the n choose k values
     * 
     */
    public static Cons binomial(int n) {
        Cons lst = Cons.cons(Integer.valueOf(1), null);
        return binomialb(n, lst);
    }
    
    public static Cons binomialb(int level, Cons lst)
    {
        Cons lstb = cons(Integer.valueOf(1), null); 
        //base case for when we have gotten to our level in the triangle
        if(level == 0)
         return lst;
         //goes to the next level and appends 1 to whatever binomialc returns
        return binomialb(--level,Cons.cons(Integer.valueOf(1), binomialc(lst, lstb)));
        
    }
    
    public static Cons binomialc(Cons lst, Cons lstb)
    {
        //base case for when theres nothing left in the list
        if(rest(lst) == null)
        {
            return lstb;
        }
        //go through the list and add the first 2 items in the list then recursively does so for the rest of the list until theres nothing left in the list
        return binomialc(rest(lst), Cons.cons((Integer)first(lst) + (Integer)second(lst), lstb));
    }
    

    // ****** your code ends here ******


    public static void main( String[] args )
      { 
        System.out.println("sumsq(5) = " + sumsq(5));

        System.out.println("peanoplus(3, 5) = " + peanoplus(3, 5));
        System.out.println("peanotimes(3, 5) = " + peanotimes(3, 5));
        System.out.println("peanotimes(30, 30) = " + peanotimes(30, 30));

        System.out.println("choose 5 3 = " + choose(5, 3));
        System.out.println("choose 100 3 = " + choose(100, 3));
        System.out.println("choose 20 10 = " + choose(20, 10));
        System.out.println("choose 100 5 = " + choose(100, 5));
        for (int i = 0; i <= 4; i++)
          System.out.println("choose 4 " + i + " = " + choose(4, i));

        Cons mylist = list(Integer.valueOf(3), Integer.valueOf(4),
                           Integer.valueOf(8), Integer.valueOf(2));
        Cons mylistb = list(Integer.valueOf(2), Integer.valueOf(1),
                           Integer.valueOf(6), Integer.valueOf(5));

        System.out.println("mylist = " + mylist);

        System.out.println("sumlist = " + sumlist(mylist));
        System.out.println("sumlistb = " + sumlistb(mylist));
        System.out.println("sumlistr = " + sumlistr(mylist));
        System.out.println("sumlisttr = " + sumlisttr(mylist));

        System.out.println("mylistb = " + mylistb);
        
        System.out.println("sumsqdiff = " + sumsqdiff(mylist, mylistb));
        System.out.println("sumsqdiffr = " + sumsqdiffr(mylist, mylistb));

        System.out.println("sumsqdifftr = " + sumsqdifftr(mylist, mylistb));
         
        System.out.println("maxlist " + mylist + " = " + maxlist(mylist));
        System.out.println("maxlistr = " + maxlistr(mylist));
        System.out.println("maxlisttr = " + maxlisttr(mylist));

        System.out.println("binomial(4) = " + binomial(4));
        System.out.println("binomial(20) = " + binomial(20));
      }

}
