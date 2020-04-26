package Controllers;

import Models.Category;
import Models.Messages;
import Models.Note;
import Models.Views;
import Service.ServeDatabase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddController {
    @FXML
    private TextField titleTF;

    @FXML
    private ComboBox<String> categoryCB;

    @FXML
    private TextArea contentTA;

    @FXML
    void cancelAction(ActionEvent event) {
        boolean val = Messages.ConfirmBox("Anulowanie","Czy na pewno chcesz anulować?");

        if(val) {
            new Views().closeSecondaryStage();
            new Views().refreshPrimaryScene();
        }
    }

    @FXML
    void confirmAction(ActionEvent event) {
        if(titleTF.getText().equals(""))
            Messages.MessageBox("Aby zatwierdzić, należy dodać tytuł notatki");
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String date = formatter.format(new Date());

            new ServeDatabase().addNote(new Note(titleTF.getText(),categoryCB.getValue(),contentTA.getText(),date));

            Note.loadNotesContainer();
            Category.loadCategoriesContainer();
            new Views().closeSecondaryStage();
            new Views().refreshPrimaryScene();
        }
    }

    @FXML
    void lengthControl(KeyEvent event) {

    }

    @FXML
    void initialize() {
        loadComboBox();
        contentTA.setStyle("-fx-font-size:12px;");
        contentTA.setWrapText(true);
        titleTF.setStyle("-fx-font-size:12px;");
        addTextLimiter(titleTF,52);
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    private void loadComboBox() {
        Category.loadCategoriesContainer();
        ObservableList<Category> categories = Category.categoriesContainer;

        for(Category category : categories)
            categoryCB.getItems().add(category.getName());

        categoryCB.setValue(categories.get(0).getName());
        categoryCB.setStyle("-fx-font-size:12px;");
    }
}