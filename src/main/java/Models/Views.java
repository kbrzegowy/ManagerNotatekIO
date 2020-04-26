package Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Views {
    private static Stage primaryStage, secondaryStage;
    private static Scene primaryScene, secondaryScene;

    public void changeSecondaryScene(String fxmlFile){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/" + fxmlFile + ".fxml"));
            if(secondaryStage!=null)
                secondaryStage.close();

            secondaryStage = new Stage();

            secondaryStage.setTitle("Manager Notatek");
            secondaryStage.setResizable(false);
            secondaryStage.setOnCloseRequest(event -> refreshPrimaryScene());

            secondaryScene = new Scene(root);
            secondaryStage.setScene(secondaryScene);
            secondaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void refreshPrimaryScene(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
            if(primaryStage!=null)
                primaryStage.close();

            primaryStage = new Stage();

            primaryStage.setTitle("Manager Notatek");
            primaryStage.setResizable(false);

            primaryScene = new Scene(root,640,400);
            primaryStage.setScene(primaryScene);
            primaryStage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeSecondaryStage(){
        secondaryStage.close();
    }
    public void closePrimaryStage(){
        primaryStage.close();
    }
}