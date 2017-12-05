import java.util.*;
import java.io.*;
/**
 * Write a description of class StreamDemo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StreamDemo
{
   public static ArrayList<String> stream(String file) throws FileNotFoundException
   {
       ArrayList<String> test = new ArrayList();
       Scanner input = new Scanner(new FileInputStream(file));
       for(int i = 0; i < 10 && input.hasNextLine(); i++)
       {
           test.add(input.nextLine() + "#");
       }
       return test;
   }
   
   public static int compareFile(String file1, String file2) throws FileNotFoundException
   {
       boolean equal = true;
       int lineno = 0;
       String line1,line2;
       Scanner input1 = new Scanner(new FileInputStream(file1));
       Scanner input2 = new Scanner(new FileInputStream(file2));
       while(input1.hasNextLine() || input2.hasNextLine())
       {
           
           line1 = input1.nextLine();
           line2 = input2.nextLine();
           if(line1.equals(line2))
           lineno++;
           else
           {
               equal = false;
               System.out.println("not equal at line " + lineno + "\n " + file1 + "@" + lineno + ": " + line1 + "\n"+
               file2 + "@" + lineno + ": " + line2);
           }
       }
       
       if(equal)
       return 0;
       else
       return -1;
   }
   public static void main(String[] args) throws FileNotFoundException
   {
       /*
       System.out.println(stream("decind.txt"));
       System.out.println(stream("file1.txt"));
       System.out.println(stream("movies.txt"));
       */
      System.out.println(compareFile("gucci.txt","gucci2.txt"));
       
   }
   }
