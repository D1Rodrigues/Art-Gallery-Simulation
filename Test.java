import java.sql.*;
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
*/

import java.util.*;
public class Test {
    public static void createManual(Statement stmt, ArrayList<List<String>> tables) throws SQLException{
        for(int i = 0; i < tables.size(); i++){
            String q = "CREATE Table ";
            stmt.executeUpdate(q + tables.get(i).get(0) + tables.get(i).get(1));
        }
    }    
    
    public static void manualDrop(Statement stmt, ArrayList<List<String>> tables) throws SQLException{
        String q = "DROP TABLE "; 
        
        for( int i = tables.size()-1; i >= 0; i--){
            String name = tables.get(i).get(0);
            stmt.executeUpdate(q + name);
        }
    }
    
    // this function will be called only if user decides to manually inserts values
    // Insert values from a function or from user input
    public static void manualInsert(Statement stmt, List<String> s) throws SQLException{
        String q = "INSERT INTO ";
        int loc = 0;
        q += s.get(loc) + " VALUES (";
        loc++;
        while(loc < s.size()){
            try{
                int t = Integer.parseInt(s.get(loc));
                if(t == 0){
                    t = 1;
                }
                q += s.get(loc);
            }catch(Exception e){
                q += "\'" + s.get(loc) +"\'";
            }
            loc++;
            if(loc < s.size()){
                q += ", ";
            }
        }
        q += ") ";
        //System.out.println(q);
        stmt.executeUpdate(q);
    }

    public static void query(Statement stmt, String q)throws SQLException{
        ResultSet rs = stmt.executeQuery(q);
        printQ(rs);
    }

public static void printQ(ResultSet rs) throws SQLException{
        
        ResultSetMetaData rsmd = rs.getMetaData();
        String result = "";
        int j = 1;
        try{
            while(true){
                result += rsmd.getColumnName(j) + " ";
                j++;
            }
        }catch(Exception e){
        
        }
        result+= "\n-----------------------------------\n";
        while (rs.next()) {
            
            int i = 1;
            try{
                while(true){
                    result += rs.getString(i) + " ";
                    i++;
                }
                
            }catch(Exception e){
                result+= "\n";
            }
            
        } 
        result += "\n";        
        System.out.println(result);
    }

/*
    public static void printQ(ResultSet rs) throws SQLException{
        ResultSetMetaData rsmd = rs.getMetaData();
        String name = "";
        int j = 1;
        try{
            while(true){
                name += rsmd.getColumnName(j) + " ";
                j++;
            }
        }catch(Exception e){
            System.out.println(name);
        }
        System.out.println("-----------------------------------");
        while (rs.next()) {
            String out = "";
            int i = 1;
            try{
                while(rs.getString(i) != null){
                    out += rs.getString(i) + " ";
                    i++;
                }
            }catch(Exception e){
                System.out.println(out);
            }
            //System.out.println();
        } 
        System.out.println();         
    }
*/
    public static String pMenu(Scanner Scan){
        System.out.println("~~~~~~~~~~~~~~Neat Menu~~~~~~~~~~~~~~");
        System.out.println("-------------------------------------");
        System.out.println("1) Create Tables");
        System.out.println("2) Drop Tables");
        System.out.println("3) Insert Values");
        System.out.println("4) Simple Queries");
        System.out.println("5) Join Queries");
        System.out.println("6) Interesting Queries");
        System.out.println("E) Exit");
        System.out.println("-------------------------------------");
        System.out.print("INPUT: ");
        return Scan.next();
    }
    
    public static void main(String[] args) {
        Connection conn1 = null;
        String user = "SYSTEM";
        String pass = "password";
        String dbURL1 = "jdbc:oracle:thin:@localhost:1521:xe";
        File_test Ft = new File_test();
        Scanner scan = new Scanner(System.in);
        ArrayList<List<String>> inserts = Ft.getInsert();
        ArrayList<List<String>> tables = Ft.getTables("tables.txt");
        ArrayList<String> BasicQ = Ft.getThingy("basic_Queries.txt");
        ArrayList<String> JoinQ = Ft.getThingy("join_Queries.txt");
        ArrayList<String> IntQ = Ft.getThingy("Interesting_Queries.txt");
        String input = "";

        try {
            // registers Oracle JDBC driver - though this is no longer required
            // since JDBC 4.0, but added here for backward compatibility
            Class.forName("oracle.jdbc.OracleDriver");
            																						
			/* This XE or local database that you installed on your laptop. 1521 is the default port for database, change according to what you used during installation. 
			xe is the sid, change according to what you setup oduring installation. */
			
            conn1 = DriverManager.getConnection(dbURL1,user,pass);
            
            if (conn1 != null) {
                System.out.println("Connected with connection #1");
            }
            

            //In your database, you should have a table created already with at least 1 row of data. In this select query example, table testjdbc was already created with at least 2 rows of data with columns NAME and NUM.
			//When you enter your data into the table, please make sure to commit your insertions to ensure your table has the correct data. So the commands that you need to type in Sqldeveloper are
			// CREATE TABLE TESTJDBC (NAME varchar(8), NUM NUMBER);
            // INSERT INTO TESTJDBC VALUES ('ALIS', 67);
            // INSERT INTO TESTJDBC VALUES ('BOB', 345);
            // COMMIT;

			// here we will use our query
			//String q = "select NAME, NUM from TESTJDBC";
            //Statement s1 = conn1.createStatement();
            
            //int four = s1.executeUpdate("DROP TABLE TESTJDBC");
            

			try (Statement stmt = conn1.createStatement()) {
                while (input.compareTo("E") != 0){
                input = pMenu(scan);
                

                //manualDrop(stmt, tables, 1);
                if( input.compareTo("1") == 0)
                    try{
                        createManual(stmt, tables);
                        System.out.println("Tables Created");
                    }catch (Exception e) {
                        System.out.println("Error: Tables already Created");
                    }
                else if(input.compareTo("2") == 0)
                    try{
                        manualDrop(stmt, tables);
                        System.out.println("Tables Dropped");
                    }catch(Exception e){
                        System.out.println("Error: No tables to drop");
                    }
                else if(input.compareTo("3") == 0)
                    try{
                        for(int i = 0; i < inserts.size(); i++){
                            manualInsert(stmt, inserts.get(i));
                        }
                        System.out.println("Values Inserted");
                    }catch(Exception e){
                        System.out.println("Error: Values already exist");
                    }
                else if(input.compareTo("4") == 0)
                    try{
                        for(int i = 0; i < BasicQ.size(); i++){
                            query(stmt,BasicQ.get(i));
                        }
                    }catch(Exception e){
                        System.out.println("Error: Query(ies) could not evaluated");
                    }
                else if(input.compareTo("5") == 0)
                    try{
                        for(int i = 0; i < JoinQ.size(); i++){
                            query(stmt,JoinQ.get(i));
                        }
                    }catch(Exception e){
                        System.out.println("Error: Query(ies) could not evaluated");
                    }
                else if(input.compareTo("6") == 0)
                    try{
                        for(int i = 0; i < IntQ.size(); i++){
                            query(stmt,IntQ.get(i));
                        }
                    }catch(Exception e){
                        System.out.println("Error: Query(ies) could not evaluated");
                    }
                else if(input.compareTo("E") == 0)
                    System.out.println();
                else 
                    System.out.println("Invalid Input");
                System.out.println();
                // creates all the tables
               // createManual(stmt, tables, 1);
                //createManual(stmt, view, 0);
                //System.out.println("Im free");
                /*
                insert(stmt);
                for(int i = 0; i < inserts.size(); i++){
                    manualInsert(stmt, inserts.get(i));
                }
                manualInsert(stmt, s);

                //query thingy, replace array list for different file
                for(int i = 0; i < IntQ.size(); i++){
                    query(stmt,IntQ.get(i));
                }
                query(stmt,q);

                */
               
            }

			} catch (SQLException e) {
                System.out.println("here1");
                System.out.println(e.getErrorCode());
                
			}

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn1 != null && !conn1.isClosed()) {
                    conn1.close();
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }  
    }
}
