import java.io.*;
import java.util.*;
public class File_test{
    public ArrayList<List<String>> getTables(String spot){
        File f = new File(spot);
        ArrayList<List<String>> out = new ArrayList<>();
        try{
            Scanner scan  = new Scanner(f); 
            while(scan.hasNextLine()){
                String temp = scan.nextLine();
                int loc = 0;
                ArrayList<String> s = new ArrayList<>();
                while(temp.charAt(loc) != ' '){
                    loc++;
                }
                String s1 = temp.substring(0,loc);
                String s2 = temp.substring(loc+1);
                s.add(s1);
                s.add(s2);
                out.add(s);
            }

            scan.close();
        }catch(Exception e){
            
        }
        
        return out;
    }
    public List<String> getDrop(){
        ArrayList<String> out = new ArrayList<>();
        File f = new File("tables.txt");
        try{
            Scanner scan  = new Scanner(f);
            while(scan.hasNextLine()){
                out.add(scan.nextLine());
            }
            scan.close();
        }catch(Exception e){

        }

        return out;
    }

    public ArrayList<List<String>> getInsert(){
        File f = new File("InsertValues.txt");
        ArrayList<List<String>> out = new ArrayList<>();
        try{
            Scanner scan = new Scanner(f);
            while(scan.hasNextLine()){
                ArrayList<String> temporary = new ArrayList<>();
                String temp = scan.nextLine();
                String[] t = temp.split(" ");
                for(int i = 0; i < t.length; i++){
                    temporary.add(t[i]);
                }
                out.add(temporary);
            }
            scan.close();
        }catch(Exception e){
            
        }
        return out;
    }

    public ArrayList<String> getThingy(String loc){
        File f = new File(loc);
        ArrayList<String> out = new ArrayList<>();
        try{
            Scanner scan = new Scanner(f);
            while(scan.hasNextLine()){
                out.add(scan.nextLine());
            }
            scan.close();
        }catch(Exception e){

        }
        return out;
    }
}