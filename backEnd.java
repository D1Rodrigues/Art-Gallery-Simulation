import java.sql.*;
import java.util.*;
public class backEnd {

        private File_test Ft;
        private ArrayList<List<String>> inserts;
        private ArrayList<List<String>> tables;
        private ArrayList<String> BasicQ;
        private ArrayList<String> JoinQ;
        private ArrayList<String> IntQ;
        
    public backEnd(){
        this.Ft = new File_test();
        this.inserts = Ft.getInsert();
        this.tables = Ft.getTables("tables.txt");
        this.BasicQ = Ft.getThingy("basic_Queries.txt");
        this.JoinQ = Ft.getThingy("join_Queries.txt");
        this.IntQ = Ft.getThingy("Interesting_Queries.txt");
    }
    
    public Connection conn(){
        String user = "SYSTEM";
        String pass = "password";
        String dbURL1 = "jdbc:oracle:thin:@localhost:1521:xe";
        Connection c1;
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            c1 = DriverManager.getConnection(dbURL1,user,pass);
            return c1;
        }catch (Exception e){
            return null;
        }
    }

    public void disc(Connection c1){
        try {
            if (c1 != null && !c1.isClosed()) {
                c1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createManual(Statement stmt) throws SQLException{
        for(int i = 0; i < tables.size(); i++){
            String q = "CREATE Table ";
            stmt.executeUpdate(q + tables.get(i).get(0) + tables.get(i).get(1));
        }
    }    
    
    public void manualDrop(Statement stmt) throws SQLException{
        String q = "DROP TABLE "; 
        
        for(int i = tables.size()-1; i >= 0; i--){
            String name = tables.get(i).get(0);
            stmt.executeUpdate(q + name);
        }
    }

    public void autoInsert(Statement stmt) throws SQLException{
        for(int i = 0; i < inserts.size(); i++){
            manualInsert(stmt, inserts.get(i));
        }
    }

    public void manualInsert(Statement stmt, List<String> s) throws SQLException{
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

    public String query(Statement stmt, int type, int pos)throws SQLException{
        ArrayList<String> a = new ArrayList<>();
        switch(type){
            case 1:
                a = BasicQ;
                break;
            case 2:
                a = JoinQ;
                break;
            case 3:
                a = IntQ;    
                break;
        }
        ResultSet rs = stmt.executeQuery(a.get(pos));
        return printQ(rs);
    }

    public String printQ(ResultSet rs) throws SQLException{
        
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
        return result;
    }

}
