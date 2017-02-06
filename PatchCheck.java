/*
 *  Program Name: Router Patch Check
 *  Author: Gabriel Uliano 
 *  Date: Sunday 5th February 2017
 */

import java.io.*;
import java.util.*;

class PatchCheck{

    public static void main (String args[]) throws IOException {

        ArrayList<String[]> routerData = readData(args); //Load data into the ArrayList
        checkPatch(routerData); //Checking if all conditions are met
        
    }
    
    public static void checkPatch(ArrayList<String[]> routerData){

        for(int i=0; i<routerData.size(); i++){
            String[] data = routerData.get(i);

            String host = data[0];
            String address = data[1];
            String patched = data[2];
            double version = Double.parseDouble(data[3]);

            if( patched.equalsIgnoreCase("no") && version>11){//Initial checks; has it been patched and is the version above 11?
                int counter1 = 0;
                int counter2 = 0;
                for(int j=0;j<routerData.size();j++){
                    if(address.equalsIgnoreCase(routerData.get(j)[1]))
                        counter1++;
                    if(host.equalsIgnoreCase(routerData.get(j)[0]))
                        counter2++;
                }
                if(counter1==1 && counter2==1){ //Only print if only one occurance of IP address and host
                    System.out.println( routerData.get(i)[0] +" ("+ routerData.get(i)[1] +") "+ "OS version " + routerData.get(i)[3] + " "+ routerData.get(i)[4]);
                }
            }

        }
        
    }
    
    public static ArrayList<String[]> readData(String[] args) throws IOException { //This method will read the CSV file and organise it into an ArrayList
        ArrayList<String[]> routerData = new ArrayList<String[]>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(args[0])));
        String line = "";

        while((line = bufferedReader.readLine()) != null){
            String[] data = line.split(",");

            if (data.length == 4) { //Handle event where note is empty
                data = fixFormat(data);
                data[4] = "";
            }
            routerData.add(data);
        }
        routerData.remove(0); //Remove header

        return routerData;
    }

    public static String[] fixFormat(String[] arr) { //This method will fix the format if the note section is empty
        String[] newArray = new String[5];

        for (int i=0; i<arr.length; i++){
            newArray[i] = arr[i];
        }
        
            newArray[4]="["+newArray[4]+"]";
        

        return newArray;
    }
}
