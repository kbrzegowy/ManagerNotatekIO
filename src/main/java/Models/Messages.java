package Models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Messages {
    public static void MessageBox(String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga!");
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait().ifPresent(e->{if(e == ButtonType.OK) alert.close();});
    }
    public static boolean ConfirmBox(String header, String info){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uwaga!");
        alert.setHeaderText(header);
        alert.setContentText(info);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            return true;
        } else if (option.get() == ButtonType.CANCEL) {
            return  false;
        }else{
            return false;
        }
    }
}
