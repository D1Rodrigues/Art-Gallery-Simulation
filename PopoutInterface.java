import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class PopoutInterface{
    
  public static void display(String title, String message){
      Stage window = new Stage();
      Label lbl = new Label(message);
      Button closeButton = new Button("OK");
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle(title);
      window.setWidth(450);
      
      VBox layout = new VBox(50);
      layout.getChildren().addAll(lbl,closeButton);
      layout.setAlignment(Pos.CENTER);
      
      Scene scene = new Scene(layout);
      
      closeButton.setOnAction(e->  window.close());
      
      window.setScene(scene);
      window.showAndWait();
      
  }  
}