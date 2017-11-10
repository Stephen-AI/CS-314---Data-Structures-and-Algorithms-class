// libtest.java      GSN    03 Oct 08; 21 Feb 12; 26 Dec 13
// 

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

@SuppressWarnings("unchecked")
public class libtest {

    // ****** your code starts here ******


public static Integer sumlist(LinkedList<Integer> lst) {
    //use x to traverse the list
    Iterator x = lst.listIterator(0);
    Integer sum = 0;
    //while there is more items in the list add the next integers to the sum accumulator
    while(x.hasNext())
    sum = sum + (Integer)x.next();
    return sum;
}

public static Integer sumarrlist(ArrayList<Integer> lst) {
    Integer sum = 0;
    for(int i = 0; i < lst.size(); i++)
    sum = sum + lst.get(i);
 return sum;   
}

public static LinkedList<Object> subset (Predicate p,
                                          LinkedList<Object> lst) {
 //lstb is the new sublist of lst that satisfies the predicate
 LinkedList<Object> lstb = new LinkedList();
 //listIterator x to traverse the list
 ListIterator<Object> x = lst.listIterator();
 while(x.hasNext())
 {
     //store the items in a variable to prevent unwanted moving forward of pointers in the listIterator 
     Object item = x.next();
  //if the item satisfies the predicare add it to the sublist
  if(p.pred(item))
  lstb.add(item);
  
 }
 return lstb;
}

public static LinkedList<Object> dsubset (Predicate p,
                                           LinkedList<Object> lst) {
 //list iterator to traverse list
 ListIterator<Object> x = lst.listIterator();
  while(x.hasNext())
 {
     Object item =x.next();
     if(!p.pred(item))
     x.remove();
 }
  return lst;
}

public static Object some (Predicate p, LinkedList<Object> lst) {
//list iterator to traverse lst
ListIterator<Object> x = lst.listIterator();
Object item = x.next();
while(x.hasNext())
 {
     //return first object to satisfy the predicate
     if(p.pred(item))
     return item;
     item = (Integer)x.next();
 }
 return item;
}

public static LinkedList<Object> mapcar (Functor f, LinkedList<Object> lst) {
 LinkedList<Object> lstb = new LinkedList();
 ListIterator<Object> x = lst.listIterator();

while(x.hasNext())
 {
    lstb.add(f.fn(x.next()));
 }
 return lstb;
}

public static LinkedList<Object> merge (LinkedList<Object> lsta,
                                        LinkedList<Object> lstb) {
LinkedList<Object> newList = new LinkedList();
//iterators for lsta and lstb respectively 
ListIterator<Object> x = lsta.listIterator();
ListIterator<Object> y = lstb.listIterator();
 
 while(x.hasNext()|| y.hasNext())
 {
     //if x is empty
     if(!x.hasNext())
     newList.add(y.next());
     //if y is empty
     else if(!y.hasNext())
     newList.add(x.next());
     else
     {
         //store items to prevent unwanted traversing
         Object item1 = x.next();
         Object item2 = y.next();
         //if item1 is less than item2
         if(((Comparable)item1).compareTo(item2) < 0)
         {
         newList.add(item1);
         //take pointer back to the previous
         y.previous();
        }
         else
         {
         newList.add(item2);
         x.previous();
        }
     }
 }
 return newList;
}
/*
 * This sort and the destructive merge sort both have a Big O of O(nlogn). But the the garbage collector has to remove unreferenced data from 
 * this sort functions this runs about nlogn - 1 times for a Big O of O(nlogn)whereas the destructive merge has no garbage.
 */
public static LinkedList<Object> sort (LinkedList<Object> lst) {
 //linked lists to hold both halves of lst
 LinkedList<Object> lsta = new LinkedList();
 LinkedList<Object> lstb = new LinkedList();
 //iterator to traverse lst
 ListIterator<Object> x = lst.listIterator();
 //move to the first item in the lst to make make sure when lst hhas only one element it correctly reads hasNext()
 x.next();
 if(x.hasNext())
 {
 //go back to the sentinel header
 x.previous();
 //put the first n/2 items in lsta and the rest in lstb
 for(int i = 0; i < lst.size();i++)
 {
     if(i < lst.size()/2)
     lsta.add(x.next());
     else if(i >= lst.size()/2)
     lstb.add(x.next());
 }
 //recursively call sort function until theres only one item left then merge the little pieces till we get a sorted lst
 return merge(sort(lsta), sort(lstb));
}
//when theres only one item in the list, return the list 
 else 
 return lst;
}


public static LinkedList<Object> intersection (LinkedList<Object> lsta,
                                               LinkedList<Object> lstb) {
 //sort lsta and lstb and create an iterator for the sorted lists
 ListIterator<Object> setA = sort(lsta).listIterator();
 ListIterator<Object> setB = sort(lstb).listIterator();
 LinkedList<Object> newList = new LinkedList();
 //either items have more than one element
 while(setA.hasNext() && setB.hasNext())
 {
     
         Object item1 =setA.next();
         Object item2 =setB.next();
         if(((Comparable)item1).compareTo(item2) < 0)
         {
             //set pointer back to previous element
         setB.previous();
         }
         else if(((Comparable)item1).compareTo(item2) == 0)
         {
         newList.add(item1);
         }
        else if(((Comparable)item1).compareTo(item2) > 0)
        {
        setA.previous();
        }
     
 }
 return newList;
}

public static LinkedList<Object> reverse (LinkedList<Object> lst) {
    LinkedList<Object> lstb = new LinkedList();
    ListIterator<Object> x = lst.listIterator();
    for(int i = 0; i < lst.size();i++)
    lstb.addFirst(x.next());
    return lstb;
}


    // ****** your code ends here ******

    public static void main(String args[]) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        lst.add(new Integer(3));
        lst.add(new Integer(17));
        lst.add(new Integer(2));
        lst.add(new Integer(5));
        System.out.println("lst = " + lst);
        System.out.println("sum = " + sumlist(lst));

        ArrayList<Integer> lstb = new ArrayList<Integer>();
        lstb.add(new Integer(3));
        lstb.add(new Integer(17));
        lstb.add(new Integer(2));
        lstb.add(new Integer(5));
        System.out.println("lstb = " + lstb);
        System.out.println("sum = " + sumarrlist(lstb));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        LinkedList<Object> lstc = new LinkedList<Object>();
        lstc.add(new Integer(3));
        lstc.add(new Integer(17));
        lstc.add(new Integer(2));
        lstc.add(new Integer(5));
        System.out.println("lstc = " + lstc);
        System.out.println("subset = " + subset(myp, lstc));

        System.out.println("lstc     = " + lstc);
        System.out.println("dsubset  = " + dsubset(myp, lstc));
        System.out.println("now lstc = " + lstc);

        LinkedList<Object> lstd = new LinkedList<Object>();
        lstd.add(new Integer(3));
        lstd.add(new Integer(17));
        lstd.add(new Integer(2));
        lstd.add(new Integer(5));
        System.out.println("lstd = " + lstd);
        System.out.println("some = " + some(myp, lstd));

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, lstd));

        LinkedList<Object> lste = new LinkedList<Object>();
        lste.add(new Integer(1));
        lste.add(new Integer(3));
        lste.add(new Integer(5));
        lste.add(new Integer(6));
        lste.add(new Integer(9));
        lste.add(new Integer(11));
        lste.add(new Integer(23));
        System.out.println("lste = " + lste);
        LinkedList<Object> lstf = new LinkedList<Object>();
        lstf.add(new Integer(2));
        lstf.add(new Integer(3));
        lstf.add(new Integer(6));
        lstf.add(new Integer(7));
        System.out.println("lstf = " + lstf);
        System.out.println("merge = " + merge(lste, lstf));

        lste = new LinkedList<Object>();
        lste.add(new Integer(1));
        lste.add(new Integer(3));
        lste.add(new Integer(5));
        lste.add(new Integer(7));
        System.out.println("lste = " + lste);
        lstf = new LinkedList<Object>();
        lstf.add(new Integer(2));
        lstf.add(new Integer(3));
        lstf.add(new Integer(6));
        lstf.add(new Integer(6));
        lstf.add(new Integer(7));
        lstf.add(new Integer(10));
        lstf.add(new Integer(12));
        lstf.add(new Integer(17));
        System.out.println("lstf = " + lstf);
        System.out.println("merge = " + merge(lste, lstf));

        LinkedList<Object> lstg = new LinkedList<Object>();
        lstg.add(new Integer(39));
        lstg.add(new Integer(84));
        lstg.add(new Integer(5));
        lstg.add(new Integer(59));
        lstg.add(new Integer(86));
        lstg.add(new Integer(17));
        System.out.println("lstg = " + lstg);

        System.out.println("intersection(lstd, lstg) = "
                           + intersection(lstd, lstg));
        System.out.println("reverse lste = " + reverse(lste));

        System.out.println("sort(lstg) = " + sort(lstg));

   }
}
