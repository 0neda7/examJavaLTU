/**
* This program manages a rental database.
*
*  Pseudocode:
*  - Initilazise needed arrays
*  - Do methods according to instructions
*  - Solve needed problems
* 
* @author Hans Malm, hanmal-7
*/

import java.util.Scanner;
 
public class Main 
{
  static final int COST_PER_HOUR = 120;
  private static Scanner kb = new Scanner(System.in);
  
  // Initializes databases, calls methods according to choise from menu
  public static void main(String[] args) throws Exception
  {
    System.out.print("\033[H\033[2J");
    String[][] cars = new String[10][3];
    cars[0][0] = "CYW426";
    cars[0][1] = "BMW 330i xDrive";
    cars[0][2] = "Rented";
    cars[1][0] = "DWW341";
    cars[1][1] = "Skoda Enyaq iv 80x";
    cars[1][2] = "Available";
    cars[2][0] = "RUC654";
    cars[2][1] = "Tesla Model Y LR";
    cars[2][2] = "Available";
    cars[3][0] = "TRW499";
    cars[3][1] = "Audi A3 Sportback";
    cars[3][2] = "Rented";
    
    String[][] rentals = new String[10][4];
    rentals[0][0] = "RUC654";
    rentals[0][1] = "Sandeep Patil";
    rentals[0][2] = "16:00";
    rentals[0][3] = "18:20";
    rentals[1][0] = "CYW426";
    rentals[1][1] = "Maxim Khamrakulov";
    rentals[1][2] = "12:05";
    rentals[1][3] = "18:35";
    rentals[2][0] = "TRW499";
    rentals[2][1] = "Erik Karlsson";
    rentals[2][2] = "07:00";
    rentals[2][3] = null;

    
    boolean exit = false;
    System.out.println("\nWelcome to the program");
    while(!exit)
    {
      switch(menu())
        {
          case 1:
          {
            addCar(cars);
            break;
          }
          case 2:
          {
            rentCar(cars, rentals);
            break;
          }
          case 3:
          {
            returnCar(cars, rentals);
            break;
          }
          case 4:
          {
            printCars(cars);
            break;
          }
          case 5:
          {
            printRentals(rentals);
            break;
          }

          case 6:
          {
            System.out.println("\nProgram is terminating..");
            exit = true;
            break;
          }

        }
      }
  }
  static int menu()
  {
    System.out.print("\nPress enter to continue...");
    kb.nextLine();
    System.out.print("\033[H\033[2J");
    System.out.println("\n----------------------------");
    System.out.println("# LTU Rent-a-car");
    System.out.println("----------------------------");
    System.out.println("\n1. Add a car to fleet\n2. Rent a car\n3. Return a car");
    System.out.println("4. Print car fleet");
    System.out.print("5. Print rental summary\n");
    System.out.print("q. End program\n\n> Enter your option: ");    

    int choise = inputNum();
    while (choise<1||choise>6)
      {
        System.out.print("\nNot valid, enter number between 1 and 5 (q to quit): ");
        choise = inputNum();
      }
    return choise;
  }
  // Renting a car from database cars
  static void rentCar(String[][] cars, String[][] rentals)
  {
    String rentedCar ="";
    int indexNoRentals = firstZeroPos(rentals);
    //int indexNoCars = firstZeroPos(cars);
    boolean gateClosed = true;
    do
    {
      
      System.out.printf("\nWhat car registration number to rent? (format:AAA111): ");
      String rentRegNo = kb.nextLine().toUpperCase();
      if(!searchRegNo(cars, rentRegNo))
      {
        System.out.println("\nRegistration number is not in register. ");
        gateClosed = true;
      }
      else if(cars[indexOfRegNo(cars, rentRegNo)][2].compareTo("Rented")==0)
      {
        System.out.println("\nCar is already rented. Choose new: ");
        gateClosed = true;
      }
      else if(checkRegNo(rentRegNo))
      {
        rentals[indexNoRentals][0] = rentRegNo;
        rentedCar = rentRegNo;
        gateClosed = false;
      }

    }
    while(gateClosed);
    
    System.out.printf("\nEnter time of pickup: ");
    rentals[indexNoRentals][2] = kb.nextLine();
    //while(checkTimeFormat(rentals[indexNoRentals][2]))
    //  {
    //    rentals[indexNoRentals][2] = kb.nextLine();
    //  }

    System.out.printf("\nEnter renter's name: ");
    rentals[indexNoRentals][1] = kb.nextLine();

    cars[indexOfRegNo(cars, rentedCar)][2] = "Rented";
    
    System.out.printf("\nThe car with registration number "+rentals[indexNoRentals][0]+" was rented by "+rentals[indexNoRentals][1]+" at "+rentals[indexNoRentals][2]+".\n");
    
  }
  // Returning a car from database rental
  static void returnCar(String[][] cars, String[][] rentals)
  {
    String returnedCar ="";
    boolean gateClosed = true;
    do
    {
      System.out.printf("\nWhat car registration number to return? (format:AAA111): ");
      String returnRegNo = kb.nextLine().toUpperCase();
      if(!searchRegNo(cars, returnRegNo))
      {
        System.out.println("\nRegistration number is not in register. ");
        gateClosed = true;
      }
      else if(cars[indexOfRegNo(cars, returnRegNo)][2].compareTo("Available")==0)
      {
        System.out.println("\nCar is not rented. Choose new: ");
        gateClosed = true;
      }
      else if(checkRegNo(returnRegNo))
      {
        returnedCar = returnRegNo;
        gateClosed = false;
      }

    }
    while(gateClosed);
    int carIndex = indexOfRegNo(rentals,returnedCar);
    System.out.printf("\nEnter time of return: ");
    rentals[carIndex][3] = kb.nextLine();
    while(rentals[carIndex][3].compareTo(rentals[carIndex][2])<=0)
      {
        System.out.printf("\nTime must be after: "+rentals[carIndex][2]);
        rentals[carIndex][3] = kb.nextLine();
      }

    System.out.println("\n===================================");
    System.out.println("LTU Rent-a-car");
    System.out.println("===================================");
    System.out.println("Name: "+rentals[carIndex][1]);
    System.out.println("Car: "+cars[indexOfRegNo(cars,returnedCar)][1]+"("+returnedCar+")");
    
          int outHours = Integer.parseInt(rentals[carIndex][2].substring(0,2));
          int outMinutes = Integer.parseInt(rentals[carIndex][2].substring(3,5));
          int backHours = Integer.parseInt(rentals[carIndex][3].substring(0,2));
          int backMinutes = Integer.parseInt(rentals[carIndex][3].substring(3,5));
          int rentedHours = (backHours*60+backMinutes-outHours*60+outMinutes)/60;
          int cost = (rentedHours+1)*COST_PER_HOUR;
    
    System.out.println("Time: "+rentals[carIndex][2]+"-"+rentals[carIndex][3]+" (" +(rentedHours+1)+")");
    System.out.println("Total cost: "+cost+" SEK");
    System.out.println("===================================");

    cars[indexOfRegNo(cars, returnedCar)][2] = "Available";
  }
  
  
  
  
  // returns positive integer from keyboard
  static int inputNum()
  {
    int number = 0;
    while(true)
      {
        if(kb.hasNextInt())
          {
            number = Math.abs(kb.nextInt());
            kb.nextLine();
            break;
          }
        else if(kb.nextLine().equals("q"))
          {
            number = 6;
            break;
          }
        else
          {
            System.out.print("\nNot a number, try again: ");
            kb.nextLine();
          }
      }
    
    return number;
  }
  // Printing database cars
  static void printCars (String[][] cars)
  {
    int availableCars=0;
    System.out.println("\n\nFleet:\n");
    String[] s = {"Model","Numberplate","Status"};
    System.out.printf("%-22s%-15s%-15s\n",s[0],s[1],s[2]);
    System.out.println("---------------------------------------------");
    for(int i=0; i<firstZeroPos(cars); i++)
      {
        System.out.printf("%-22s",cars[i][1]);
        System.out.printf("%-15s",cars[i][0]);
        System.out.printf("%-15s",cars[i][2]);
        if(cars[i][2].equals("Available"))
          availableCars++;
        System.out.print("\n");         
      }
    System.out.println("\nTotal number of cars: "+firstZeroPos(cars));
    System.out.println("Total number of available cars: "+availableCars);
    
  }
  // Printing database rentals
  static void printRentals (String[][] rentals)
  {
    sortRentals(rentals);
    int totalRevenue = 0;
    System.out.println("\n\nRentals:\n");
    String[] s = {"Name","Numberplate","Pickup","Return","Cost"};
    String substitute = "";
    System.out.printf("%-22s%-15s%-10s%-10s%-10s\n",s[0],s[1],s[2],s[3],s[4]);
    System.out.println("-------------------------------------------------------------");
    for(int i=0; i<firstZeroPos(rentals); i++)
      {
        System.out.printf("%-22s",rentals[i][1]);
        System.out.printf("%-15s",rentals[i][0]);
        System.out.printf("%-10s",rentals[i][2]);
        if(rentals[i][3]==null)
        {
          System.out.printf("%-10s",substitute);
          System.out.printf("%-10s",substitute);
        } 
        else
        {
          System.out.printf("%-10s",rentals[i][3]);
          
          int outHours = Integer.parseInt(rentals[i][2].substring(0,2));
          int outMinutes = Integer.parseInt(rentals[i][2].substring(3,5));
          int backHours = Integer.parseInt(rentals[i][3].substring(0,2));
          int backMinutes = Integer.parseInt(rentals[i][3].substring(3,5));
          int rentedHours = (backHours*60+backMinutes-outHours*60+outMinutes)/60;
          int cost = (rentedHours+1)*COST_PER_HOUR;
          totalRevenue += cost;
          System.out.printf("%-10d",cost);
          
        }
        System.out.print("\n");         
      }
    System.out.println("\nTotal number of rentals: "+firstZeroPos(rentals));
    System.out.println("Total revenue: "+totalRevenue+" SEK");

    
  }
  // Searches for first empty row in database
  static int firstZeroPos(String array[][])
  {
    int zeroPos = 0;
    for (int i=0; i<array.length; i++)
    {
      if (array[i][0]==null)
      {
        zeroPos = i;
        break;
      }
    }
    return zeroPos;
  }
  // Adds a new car to batabase cars
  static void addCar(String[][] cars)
  {
    int indexNo = firstZeroPos(cars);
    boolean gateClosed = true;
    do
    {
      
      System.out.printf("\nEnter registation number (format:AAA111): ");
      String regNo = kb.nextLine().toUpperCase();
      if(searchRegNo(cars, regNo))
      {
        System.out.println("\nRegistration number already in register. ");
        gateClosed = true;
      }
      else if(checkRegNo(regNo))
      {
        cars[indexNo][0]= regNo;
        gateClosed = false;
      }
   /*   else
      {
        System.out.printf("\nWrong format, try again: ");
        gateClosed = true;
      } */
    }
    while(gateClosed);
    
    System.out.printf("\nEnter car make and model: ");
    cars[indexNo][1] = kb.nextLine();
    cars[indexNo][2] = "Available";
    

    System.out.printf("\n"+cars[indexNo][1]+" with registration number "+cars[indexNo][0]+" was added to the car fleet.\n");
    
  }
  // Checks valid registration number
  static boolean checkRegNo(String regNo)
  {
    if(regNo.length()==6)
    {
      for(int i=0; i<6; i++)
      {
        if (i==0||i==1||i==2)
        {
          if(!Character.isLetter(regNo.charAt(i)))
          {
            System.out.print("Not characters, try again: ");
            return false;
          }
        }
        else
          {
            if(!Character.isDigit(regNo.charAt(i)))
            {
              System.out.print("Not numbers, try again: ");
              return false;
            }
          }
        }
      return true;
      }

    else
      System.out.print("Wrong length, try again: ");

    return false;
  }
  // Checks if registration number is present in database
  static boolean searchRegNo(String[][] array, String regNo)
  {
    boolean found = false;
    for(int i=0; i<firstZeroPos(array); i++)
      {
        if(array[i][0].equals(regNo))
          found = true;
      }
    return found;
  }
  // Sorts database retals alphabetically after name of customer
  static void sortRentals(String[][] rentals)
  {
    int j = firstZeroPos(rentals);
    boolean sorted = true;
    do
    {
      sorted = true;
      for(int i=0; i<j-1; i++)
        {
          if(rentals[i][1].compareTo(rentals[i+1][1])>0)
          {
            for(int k=0; k<4; k++)
            {
              String temp = rentals[i][k];
              rentals[i][k] = rentals[i+1][k];
              rentals[i+1][k] = temp;
            }
            sorted = false;
          }
        }
      j--;
    }
    while(!sorted);
  }
  // Search for index of register number
  static int indexOfRegNo(String[][] array, String rentRegNo)
  {
    int index = 0;
    for(int i=0; i<firstZeroPos(array); i++)
      {
        if(array[i][0].equals(rentRegNo))
          index = i;
      }
    return index;
  }
    // Checks valid time entry
  static boolean checkTimeFormat(String time)
  {
    if(time.length()==5)
    {
      if(time.charAt(2)!=':')
      {
        System.out.print("Wrong format, try again: ");
        return false;
      }
      int hours = Integer.parseInt(time.substring(0,2));
      int minutes = Integer.parseInt(time.substring(2,5));
                               
      if(hours<1||hours>12||minutes<0||minutes>59)
        {
          System.out.print("Wrong format, try again: ");
          return false;
        }
      return true;
      }

    else
      System.out.print("Wrong length, try again: ");

    return false;
  }
  
}