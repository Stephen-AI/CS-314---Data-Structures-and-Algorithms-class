/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 16 Feb 09
 *          01 Feb 12; 08 Feb 12; 22 Sep 13; 26 Dec 13; 23 Sep 16
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
    // instance variables
    private Object car;   // traditional name for first
    private Cons cdr;     // "could-er", traditional name for rest
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }

    // Cons is the data type.
    // cons() is the method that makes a new Cons and puts two pointers in it.
    // cons("a", null) = (a)
    // cons puts a new thing on the front of an existing list.
    // cons("a", list("b","c")) = (a b c)
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }

    // consp is true if x is a Cons, false if null or non-Cons Object
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }

    // first returns the first thing in a list.
    // first(list("a", "b", "c")) = "a"
    // safe, first(null) = null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }

    // rest of a list after the first thing.
    // rest(list("a", "b", "c")) = (b c)
    // safe, rest(null) = null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }

    // second thing in a list
    // second(list("+", "b", "c")) = "b"
    public static Object second (Cons x) { return first(rest(x)); }

    // third thing in a list
    // third(list("+", "b", "c")) = "c"
    public static Object third (Cons x) { return first(rest(rest(x))); }

    // destructively change the first() of a cons to be the specified object
    // setfirst(list("a", "b", "c"), 3) = (3 b c)
    public static void setfirst (Cons x, Object i) { x.car = i; }

    // destructively change the rest() of a cons to be the specified Cons
    // setrest(list("a", "b", "c"), null) = (a)     
    // setrest(list("a", "b", "c"), list("d","e")) = (a d e)
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }

    // make a list of the specified items
    // list("a", "b", "c") = (a b c)
    // list() = null
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }

    // convert a list to a string in parenthesized form for printing
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


    // add up elements of a list of numbers
public static int sum (Cons lst) {
    return (rest(lst) == null)?(Integer)first(lst):(Integer)first(lst) + sum(rest(lst));
}

//recursively add 1 until we get to the end of the list
public static int length(Cons lst)
{
    //basae case for where there's nothing left in the list
    if (rest(lst) == null)
    {
        return 1;
    }
    return 1 + length(rest(lst));
}


    // mean = (sum x[i]) / n  
public static double mean (Cons lst) {
    //return the sum of the list divided by the length of the list
    return (double)sum(lst)/length(lst); 
}


    // square of the mean = mean(lst)^2  

    // mean square = (sum x[i]^2) / n  
public static double meansq (Cons lst) {
    //returns the value returned from the auxillary function and divides it by the value returned from the length function
    return (double)sumsq(lst)/length(lst);
}

//auxilary function for meanssq that finds the sum of the square of each element in the list
 public static int sumsq (Cons lst) {
        if(rest(lst) == null)
        {
            return square((Integer)first(lst));
        }
        else
        return (square((Integer)first(lst)) + sumsq(rest(lst)));
    }

    //returns the difference between the meansq and the square of the mean
public static double variance (Cons lst) {
    return meansq(lst) - (mean(lst) * mean(lst));
}
//returns the square-root of the variance
public static double stddev (Cons lst) {
    return Math.pow(variance(lst),0.5);
}

//passes x to sineb along with 0 to represent the accumulative sum, and 1 to represent the counter and the power
public static double sine (double x) {
    return sineb(0, x , 1, 1);
}


public static double sineb(double sum, double x, int n, int counter)
{
    //first case evaluates sum = x, increases the counter, and the power of operation
    if(n == 1)
    return sineb(x, x, n + 2, ++counter);
    //finds the sum or the difference of the current sum and the value returned from sinec, increases the counter, and the power of operation
    if(n <= 21 && counter % 2 == 0)
      return sineb(sum - sinec(x, x, n, n), x, n + 2, ++counter);
      else if(n <= 21 && counter % 2 != 0)
      return sineb(sum + sinec(x, x, n, n), x, n + 2, ++counter);
      //base case for when the power is above 21
     else
     return sum;
    
     
}
//returns the x raised to the power of the initial count divided by the factorial of the initial count,
public static double sinec(double x, double pow, double fact, int count )
{
     
    return (count == 1? pow/fact: sinec(x, pow * x, fact * --count, count));
}

//returns the list if the length is greater than the given n
public static Cons nthcdr (int n, Cons lst) {
      
    return n == 0 ? lst : nthcdr(--n, rest(lst));    
}

/*
 * The Big O for the array access is O(1), while the Big O for the list access, elt, is O(n), 
 * where n is the position of the desired node relative to the first item in the list  
 */
public static Object elt (Cons lst, int n) {
    //traverse the list until we get to the nth position
    return n == 0 ? first(lst): elt(rest(lst), --n);
}

/* returns lst(i) + delta * (lst(i+1) - lst(i))
 * The shape of the interpolated binomial function is the bell curve like that of a normal distribution
 */
public static double interpolate (Cons lst, double x) {
    return ((Integer)elt(lst, (int)Math.floor(x)) + (x - Math.floor(x))*((Integer)elt(lst, (int)Math.ceil(x)) - (Integer)elt(lst, (int)Math.floor(x))));
}

//send the list and 0 as the accumulative sum, to the auxillary function sumtrb
public static int sumtr (Cons lst) {
    
   return sumtrb(lst, 0);
}

public static int sumtrb(Cons lst, int sum)
{
    //base case for the last item is an integer
    if(rest(lst)==null && consp(first(lst))== false)
    return (Integer)first(lst) + sum;
    //base case for when the last item is a Cons
    else if(rest(lst)==null && consp(first(lst))== true)
    //return the sum and send the cons to the first function
    return sum + sumtr((Cons)first(lst));
    
    //add integers to sum
    if(consp(first(lst))== false)
    sum = sum + (Integer)first(lst);
    //send Cons' back to sumtr and continue with the rest of the list
    else
    return sumtr((Cons)first(lst))+ sumtrb((Cons)rest(lst),sum);
    return sumtrb((Cons)rest(lst), sum);
}

    // use auxiliary functions as you wish
    
public static Cons subseq (Cons lst, int start, int end) {
    //send the list, starting point and ending point to the auxillary function subseqb
   return subseqb(lst, start, end, 0);
}

public static Cons subseqb(Cons lst, int start, int end, int count)
{
    //goes to the starting point, using a counter and returns it and the rest of the list to subseqc
    return subseqc(count == start ? lst:subseqb(rest(lst),start,end, ++count),start, end);
}

public static Cons subseqc (Cons lst, int start, int end)
{
    Integer f = (Integer)first(lst);
    //base case for the last item in the list to be returned
    if(start == end-1)
    return Cons.cons(f,null);
    //goes to the end of the list
    Cons r = subseqc(rest(lst),++start, end);
    //returns all the integers encountered from starting to ending point
    return Cons.cons(f,r);
}



public static Cons posfilter (Cons lst) {
    
    //base case for when the last item in the list is a positive integer
    if(rest(lst)==null && (Integer)first(lst)>=0)
    //
    return Cons.cons((Integer)first(lst),null);
     //base case for when the last item in the list is a negative integer
    else if(rest(lst)==null && (Integer)first(lst)<0)
    return null;
    //Make a subset of lst that contaains just positive elements.
    if((Integer)first(lst)>=0)
    return Cons.cons((Integer)first(lst),posfilter(rest(lst)));
    return posfilter(rest(lst));
    
}

public static Cons subset (Predicate p, Cons lst) {
    Cons sub;
     if(rest(lst)==null && p.pred((Integer)first(lst))==true)
    return Cons.cons((Integer)first(lst),null);
    else if(rest(lst)==null && p.pred((Integer)first(lst))==false)
    return null;
    if(p.pred((Integer)first(lst))==true)
    return Cons.cons((Integer)first(lst),subset(p,rest(lst)));
    return subset(p,rest(lst));
}
//return a new list thst has been augmented by the Functor
public static Cons mapcar (Functor f, Cons lst) {
    Integer map = (Integer)f.fn(first(lst));
    if(rest(lst)==null)
    return Cons.cons(map,null);
    Cons r = mapcar(f, rest(lst));
    return Cons.cons(map,r);
    
}

public static Object some (Predicate p, Cons lst) {
    if(p.pred((Integer)first(lst))==true)
    return first(lst);
    if(rest(lst)==null && p.pred((Integer)first(lst))==false)
    return null;
    return some(p,rest(lst));
}

public static boolean every (Predicate p, Cons lst) {
     if(p.pred((Integer)first(lst))==false)
    return false;
    if(rest(lst)==null && p.pred((Integer)first(lst))==true)
    return true;
    return every(p,rest(lst));
    
}
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
        Cons mylist =
            list(95, 72, 86, 70, 97, 72, 52, 88, 77, 94, 91, 79,
                 61, 77, 99, 70, 91 );
        System.out.println("mylist = " + mylist);
        System.out.println("sum = " + sum(mylist));
        System.out.println("length= " + length(mylist));
        System.out.println("mean = " + mean(mylist));
        System.out.println("meansq = " + meansq(mylist));
        System.out.println("variance = " + variance(mylist));
        System.out.println("stddev = " + stddev(mylist));
        System.out.println("sine(0.5) = " + sine(0.5));  // 0.47942553860420301
        System.out.print("nthcdr 5 = ");
        System.out.println(nthcdr(5, mylist));
        System.out.print("nthcdr 18 = ");
        System.out.println(nthcdr(18, mylist));
        System.out.println("elt 5 = " + elt(mylist,5));

        Cons mylistb = list(0, 30, 56, 78, 96);
        System.out.println("mylistb = " + mylistb);
        System.out.println("interpolate(3.4) = " + interpolate(mylistb, 3.4));
        Cons binom = binomial(12);
        System.out.println("binom = " + binom);
        System.out.println("interpolate(3.4) = " + interpolate(binom, 3.4));

        Cons mylistc = list(1, list(2, 3), list(list(list(list(4)),
                                                     list(5)),
                                                6));
        System.out.println("mylistc = " + mylistc);
        System.out.println("sumtr = " + sumtr(mylistc));
        Cons mylistcc = list(1, list(7, list(list(2), 3)),
                             list(list(list(list(list(list(list(4)))), 9))),
                             list(list(list(list(5), 4), 3)),
                             list(6));
        System.out.println("mylistcc = " + mylistcc);
        System.out.println("sumtr = " + sumtr(mylistcc));

        Cons mylistd = list(0, 1, 2, 3, 4, 5, 6 );
        System.out.println("mylistd = " + mylistd);
        System.out.println("subseq(2 5) = " + subseq(mylistd, 2, 5));

        Cons myliste = list(3, 17, -2, 0, -3, 4, -5, 12 );
        System.out.println("myliste = " + myliste);
        System.out.println("posfilter = " + posfilter(myliste));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        System.out.println("subset = " + subset(myp, myliste));

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return  (Integer) x + 2; }};

        System.out.println("mapcar = " + mapcar(myf, mylistd));

        System.out.println("some = " + some(myp, myliste));

        System.out.println("every = " + every(myp, myliste));

      }

}
