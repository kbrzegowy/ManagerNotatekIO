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
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteController {
    @FXML
    private TextField noteTitleTF;

    @FXML
    private ComboBox<String> categoriesCB;

    @FXML
    private TextArea contentTA;

    private boolean flag = false;

    @FXML
    void confirmAction(ActionEvent event) {
        boolean flag = false;

        if(noteTitleTF.getText().equals(Note.note.getTitle()) && contentTA.getText().equals(Note.note.getContent()) && categoriesCB.getValue().equals(Note.note.getCategory())){
            new Views().closeSecondaryStage();
            new Views().refreshPrimaryScene();
        }else{
            if(noteTitleTF.getText().equals(""))
                Messages.MessageBox("Aby zatwierdzić, należy dodać tytuł notatki");
            else
                flag = Messages.ConfirmBox("Zmodyfikowano notetkę","Czy zapisać zmiany?");
        }

        if(flag){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String date = formatter.format(new Date());

            Note addNote = new Note(Note.note.getId(),noteTitleTF.getText(),categoriesCB.getValue(),contentTA.getText(),date);

            new ServeDatabase().overwriteNote(addNote);

            Note.loadNotesContainer();
            new Views().closeSecondaryStage();
            new Views().refreshPrimaryScene();
        }
    }

    @FXML
    void editNoteAction(ActionEvent event) {
        if(flag){
            noteTitleTF.setEditable(false);
            contentTA.setEditable(false);
            flag = false;
        }else{
            noteTitleTF.setEditable(true);
            contentTA.setEditable(true);
            flag = true;
        }
    }

    @FXML
    void initialize() {
        loadComboBox();
        contentTA.setText(Note.note.getContent());
        noteTitleTF.setText(Note.note.getTitle());
        noteTitleTF.setEditable(false);
        noteTitleTF.setStyle("-fx-font-size:12px;");
        contentTA.setEditable(false);
        contentTA.setStyle("-fx-font-size:12px;");
        contentTA.setWrapText(true);
        addTextLimiter(noteTitleTF,52);
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
            if(!category.equals("Wszystkie"))
                categoriesCB.getItems().add(category.getName());

        categoriesCB.setValue(Note.note.getCategory());
        categoriesCB.setStyle("-fx-font-size:12px;");
    }
}