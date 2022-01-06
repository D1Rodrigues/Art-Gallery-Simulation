import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

import java.util.*;
public class tester extends Application{

    private Stage window;
    private Scene start, instantiate, query;
    private TextField txt1,txt2,txt3,txt4,txt5,txt6,txt7;
    private ComboBox<String> ComboBreaker;
    private ArrayList<TextField> txts;
    private ArrayList<List<String>> Atrib;
    private backEnd bE;
    //private File_test ft;
    //private Test testerester;
    @Override 
    public void start(Stage primaryStage){
        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14;    
        VBox vBox1, vBox2, vBox3, vBox4, vBox5, vBox6, vBox7;
        HBox hBox1, hBox2, hBox3, hBox4;
        Label lbl1, lbl2, lbl3;
        Label ES1,ES2,ES3,ES4,ES5;
        Label disp;
        HashMap<String,Integer> h = new HashMap<>();
        bE = new backEnd();
        
        // label for first scene
        lbl1 = new Label();
        lbl1.setText("Aarth");
        
        // ReadMe button
        btn1 = new Button();
        btn1.setText("ReadMe");
        btn1.setOnAction(z -> {
            PopoutInterface.display("READ ME","When starting application for the first time,\nuse Create tables first then click instantiate to\nload information");
            
        });

        // Create button
        btn2 = new Button();
        btn2.setText("Create tables");
        btn2.setOnAction(e ->{
            Connection a = bE.conn();
            try(Statement stmt = a.createStatement()){
                bE.createManual(stmt);
                PopoutInterface.display("CREATE TABLE","Tables Created!");
            }catch(Exception ex){
                PopoutInterface.display("CREATE TABLE","Already Created!"); 
            }
            bE.disc(a);
        });

        //Instantiate
        btn3 = new Button();
        btn3.setText("Instantiate");
        btn3.setOnAction(e -> {
            window.setScene(instantiate);
        });

        hBox1 = new HBox(10);
        hBox1.getChildren().addAll(btn2,btn3);

        // drop button
        btn4 = new Button();
        btn4.setText("Drop tables");
        btn4.setOnAction(e -> {
            Connection a = bE.conn();
            try(Statement stmt = a.createStatement()){
                bE.manualDrop(stmt);
                PopoutInterface.display("DROP TABLE","Tables Dropped!");
            }catch(Exception ex){
                PopoutInterface.display("DROP TABLE","Don't Exist!");
            }
            bE.disc(a);
        });
        
        //Query
        btn5 = new Button();
        btn5.setText("Query");
        btn5.setOnAction(e -> {
            window.setScene(query);
        });

        hBox2 = new HBox(18);
        hBox2.getChildren().addAll(btn4,btn5);

        // Terminate
        btn6 = new Button();
        btn6.setText("Terminate");
        btn6.setOnAction(e -> {
           window.close();
        });

        // vbox for first scene
        
        vBox1 = new VBox();
        vBox1.setPadding(new Insets(10,10,10,10));
        vBox1.getChildren().addAll(lbl1,btn1,hBox1,hBox2,btn6);

        // setting the scene
        start = new Scene(vBox1, 330, 350);


        //Label for second scene
        lbl2 = new Label();
        lbl2.setText("Insert");
        
        //setting the middle vbox 
        vBox3 = new VBox();
        vBox3.getChildren().addAll(lbl2);

        // First empty space label
        ES1 = new Label();
        ES1.setVisible(false);

        //Default Instert
        btn7 = new Button();
        btn7.setText("Default Insert");
        btn7.setOnAction(e->{
            Connection a = bE.conn();
            try(Statement stmt = a.createStatement()){
                bE.autoInsert(stmt);
                PopoutInterface.display("DEFAULT INSTERT","Values Inserted!");
            }catch(Exception ex){
                PopoutInterface.display("DEFAULT INSTERT","Already Exist!");
            }
            bE.disc(a);
        });

        //Custom Insert
        btn8 = new Button();
        btn8.setText("Custom Insert");
        btn8.setOnAction(e->{
            Connection a = bE.conn();
            try(Statement stmt = a.createStatement()){
                ArrayList<String> s = new ArrayList<>();
                s.add(ComboBreaker.getValue());
                for(int i = 0; i < txts.size(); i++){
                    if(!txts.get(i).isVisible()){
                        break;
                    }
                    s.add(txts.get(i).getText());
                    txts.get(i).setText("");
                }
                bE.manualInsert(stmt,s);
                PopoutInterface.display("CUSTOM INSERT","Insterted!");
            }catch(Exception ex){
                PopoutInterface.display("CUSTOM INSTERT","Error During Submision!");
            }
            bE.disc(a);
        });

        //Back 1
        btn9 = new Button();
        btn9.setText("Back");
        btn9.setOnAction(e -> {
            window.setScene(start);
        });
        
        // setting the left most vBox
        vBox2 = new VBox();
        vBox2.getChildren().addAll(ES1,btn7,btn8,btn9);

        // Second empty space label
        ES2 = new Label();
        ES2.setVisible(false);

        // combo box
        ComboBreaker = new ComboBox<>();
        ComboBreaker.getItems().addAll("Storage","Period","Users","Admin","Admin Role","Curator","Artist","Gallery Floor", "Display","Art Piece","Piece Added","Art Piece Year","Contains","Manages");
        // ComboBreaker.setPromptText("Choose");

        // selector button
        for(int i = 0; i < ComboBreaker.getItems().size(); i++){
            h.put(ComboBreaker.getItems().get(i),i);
        }

        Atrib = new ArrayList<>();
        // Storage
        ArrayList<String> AtribS = new ArrayList<>();
        AtribS.add("name");
        AtribS.add("unitId");
        Atrib.add(AtribS);
        
        // Period
        ArrayList<String> AtribP = new ArrayList<>();
        AtribP.add("name");
        AtribP.add("periodId");
        Atrib.add(AtribP);
        
        // Users
        ArrayList<String> AtribU = new ArrayList<>();
        AtribU.add("userId");
        AtribU.add("password");
        Atrib.add(AtribU);
        
        // Admin
        ArrayList<String> AtribA = new ArrayList<>();
        AtribA.add("userId");
        AtribA.add("adminId");
        Atrib.add(AtribA);

        // Admin role
        ArrayList<String> AtribAr = new ArrayList<>();
        AtribAr.add("adminId");
        AtribAr.add("role");
        Atrib.add(AtribAr);

        // Curator
        ArrayList<String> AtribC = new ArrayList<>();
        AtribC.add("userId");
        AtribC.add("name");
        Atrib.add(AtribC);

        // Artist
        ArrayList<String> AtribArt = new ArrayList<>();
        AtribArt.add("name");
        AtribArt.add("periodId");
        AtribArt.add("artistId");
        Atrib.add(AtribArt);

        //  Gallery Floor
        ArrayList<String> AtribG = new ArrayList<>();
        AtribG.add("location");
        AtribG.add("adminId");
        AtribG.add("galleryId");
        Atrib.add(AtribG);

        // Display 
        ArrayList<String> AtribD = new ArrayList<>();
        AtribD.add("theme");
        AtribD.add("unitid");
        AtribD.add("userid");
        AtribD.add("galleryid");
        AtribD.add("displayid");
        Atrib.add(AtribD);
        
        // Art Piece 
        ArrayList<String> AtribAp = new ArrayList<>();
        AtribAp.add("price");
        AtribAp.add("yearCreated");
        AtribAp.add("unitid");
        AtribAp.add("pieceid");
        Atrib.add(AtribAp);
        
        // Piece Added
        ArrayList<String> AtribPA = new ArrayList<>();
        AtribPA.add("dateAdded");
        AtribPA.add("pieceId");
        Atrib.add(AtribPA);
        
        // Art Piece Year 
        ArrayList<String> AtribAy = new ArrayList<>();
        AtribAy.add("century");
        AtribAy.add("yearCreated");
        Atrib.add(AtribAy);
        
        // Contains 
        ArrayList<String> AtribCo = new ArrayList<>();
        AtribCo.add("pieceId");
        AtribCo.add("displayId");
        Atrib.add(AtribCo);
        
        // Manages
        ArrayList<String> AtribM = new ArrayList<>();
        AtribM.add("userId");
        AtribM.add("adminId");
        Atrib.add(AtribM);
        
        // array list of text boxes
        txts = new ArrayList<>();

        btn14 = new Button();
        btn14.setText("Choice selector");
        btn14.setOnAction(e -> {
            lbl2.setText("Insert");
            int loc = 0;
            int loc1 = 0;
            //System.out.println(ComboBreaker.getValue());
            if(h.containsKey(ComboBreaker.getValue())){
                loc1 = h.get(ComboBreaker.getValue());
            }
            switch (ComboBreaker.getValue()){
                case "Storage":
                    loc = 0;
                    break;
                case "Period":
                    loc = 1;
                    break;
                case "Users": 
                    loc = 2;
                    break;
                case "Admin": 
                    loc = 3;
                    break;
                case "Admin Role": 
                    loc = 4;
                    break;
                case "Curator":
                    loc = 5;
                    break;                
                case "Artist": 
                    loc = 6;
                    break;
                case "Gallery Floor":
                    loc = 7;
                    break;
                case"Display":
                    loc = 8;
                    break;
                case "Art Piece":
                    loc = 9;
                    break;
                case"Piece Added": 
                    loc = 10;
                    break;
                case "Art Piece Year":
                    loc = 11;
                    break;
                case "Contains":
                    loc = 12;
                    break;
                case "Manages":
                    loc = 13;
                    break;
                default:
                    lbl2.setText("You fool, You clown \n -Sun Tzu The art of war");
                    //System.out.println("You fool, \nYou clown \n -Sun Tzu The art of war");
                    return;
            }
            
            
            int size = Atrib.get(loc1).size();
            //System.out.println(size + " " + loc);
            for(int i = 0; i < size; i++){
                txts.get(i).setVisible(true);
                txts.get(i).setPromptText(Atrib.get(loc1).get(i));
            }
            for(int i = size; i < 6; i++){
                txts.get(i).setVisible(false);
            }

            ComboBreaker.setPromptText("Choose");

        });

        // txt fields
        txt1 = new TextField();
        txt1.setVisible(false);
        txt2 = new TextField();
        txt2.setVisible(false);
        txt3 = new TextField();
        txt3.setVisible(false);
        txt4 = new TextField();
        txt4.setVisible(false);
        txt5 = new TextField();
        txt5.setVisible(false);
        txt6 = new TextField();
        txt6.setVisible(false);
        
        txts.add(txt1);
        txts.add(txt2);
        txts.add(txt3);
        txts.add(txt4);
        txts.add(txt5);
        txts.add(txt6);

        // vBox thingy
        vBox4 = new VBox();
        vBox4.getChildren().addAll(ES2,btn14,ComboBreaker,txt1,txt2,txt3,txt4,txt5,txt6);

        // Hbox Vbox for Instert scene
        hBox3 = new HBox();
        hBox3.setPadding(new Insets(10,10,10,10));
        hBox3.getChildren().addAll(vBox2,vBox3,vBox4);         

        // setting the scene 2: electric boogaloo
        instantiate = new Scene(hBox3);
        
        
        //Third Empty Space
        ES3 = new Label();
        ES3.setVisible(false);

        // Display label
        disp = new Label();
        disp.setMinSize(150, 75);
        disp.setText("You fool,\n You clown \n -Sun Tzu The art of war");

        // vbox5
        vBox5 = new VBox();
        vBox5.getChildren().addAll(ES3,disp);

        //label for top
        lbl3 = new Label();
        lbl3.setText("Queries");

        // Fourth empty space
        ES4 = new Label();
        ES4.setVisible(false);

        // textbox7 selct number of query
        txt7 = new TextField();
        txt7.setPromptText("Enter QueryId");
        
        // vbox 6
        vBox6 = new VBox();
        vBox6.getChildren().addAll(lbl3,ES4,txt7);
        
        //Fifth Empty Space
        ES5 = new Label();
        ES5.setVisible(false);
        
        // Simple queries
        btn10 = new Button();
        btn10.setText("Simple");
        btn10.setOnAction(e -> {
            Connection a = bE.conn();
            try(Statement stmt = a.createStatement()){
                String temp = bE.query(stmt,1,Integer.parseInt(txt7.getText())-1);
                disp.setText(temp);
            }catch(Exception ex){
                
            }
            bE.disc(a);
        });

        //Join
        btn11 = new Button();
        btn11.setText("Join");
        btn11.setOnAction(e -> {
            Connection a = bE.conn();
            try(Statement stmt = a.createStatement()){
                String temp = bE.query(stmt,2,Integer.parseInt(txt7.getText())-1);
                //System.out.println(temp);
                disp.setText(temp);
            }catch(Exception ex){
                
            }
            bE.disc(a);
        });

        //Interesting
        btn12 = new Button();
        btn12.setText("Interesting");
        btn12.setOnAction(e -> {
            Connection a = bE.conn();
            try(Statement stmt = a.createStatement()){
                String temp = bE.query(stmt,3,Integer.parseInt(txt7.getText())-1);
                //System.out.println(temp);
                disp.setText(temp);
            }catch(Exception ex){
                
            }
            bE.disc(a);
        });

        //Back 2
        btn13 = new Button();
        btn13.setText("Back");
        btn13.setOnAction(e -> {
            window.setScene(start);
        });

        // vBox7
        vBox7 = new VBox();
        vBox7.getChildren().addAll(ES5,btn10,btn11,btn12,btn13);


        //Hbox Vbox for Query scene
        hBox4 = new HBox();
        hBox4.setPadding(new Insets(10,10,10,10));
        hBox4.getChildren().addAll(vBox5,vBox6,vBox7);
        // hbox for whole scene, comprised of 3 vboxes 

        query = new Scene(hBox4);

        // setting the stage
        window = primaryStage;
        window.setTitle("Gallery");
        window.setScene(start);
        window.show();

    }
    public static void main(String[] args){
        
        //connection
        launch(args);
        //disconnect
        
    }

    
}
        



