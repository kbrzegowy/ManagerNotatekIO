package Controllers;

import Models.Category;
import Models.Messages;
import Models.Note;
import Models.Views;
import Service.ServeDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
    @FXML
    private ComboBox<String> categoryCB;

    @FXML
    private TableColumn<Note,String> noteDataColumn;

    @FXML
    private TableColumn<Note,String> noteTitleColumn;

    @FXML
    private TableView<Note> notesTable;

    @FXML
    void addCategoryAction(ActionEvent event) {
        new Views().closePrimaryStage();
        new Views().changeSecondaryScene("categoryView");
    }

    @FXML
    void addNoteAction(ActionEvent event) {
        int tmp = 0;

        for(Category category : Category.categoriesContainer)
            tmp++;

        if(tmp > 0) {
            new Views().closePrimaryStage();
            new Views().changeSecondaryScene("addView");
        }else{
            Messages.MessageBox("Brak kategorii, dodaj kategorię!");
        }
    }

    @FXML
    void editNoteAction(ActionEvent event) {
        Note.note = notesTable.getSelectionModel().getSelectedItem();

        if(Note.note != null){
            new Views().closePrimaryStage();
            new Views().changeSecondaryScene("noteView");
        }
    }

    @FXML
    void deleteNoteAction(ActionEvent event) {
        Note note = notesTable.getSelectionModel().getSelectedItem();
        boolean val = Messages.ConfirmBox("Usunięcie notatki","Czy usunąć notatkę?");

        if(val){
            new ServeDatabase().deleteNote(note);
            loadNotesTable();
        }
    }

    @FXML
    void comboBoxAction(ActionEvent event) {
        ObservableList<Note> notesWithCategory = FXCollections.observableArrayList();
        if(categoryCB.getValue().equals("Wszystkie"))
            loadNotesTable();
        else {
            for (Note note : Note.notesContainer) {
                if (note.getCategory().equals(categoryCB.getValue()))
                    notesWithCategory.add(note);

                loadNotesTable(notesWithCategory);
            }
        }
    }

    @FXML
    void initialize() {
        initNotesTable();
        loadNotesTable();
        loadComboBox();
    }

    private void initNotesTable(){
        noteDataColumn = new TableColumn<>("Data");
        noteTitleColumn = new TableColumn<>("Tytul");

        noteDataColumn.setMinWidth(165);
        noteTitleColumn.setMinWidth(475);

        noteTitleColumn.setStyle("-fx-font-size : 12px");
        noteDataColumn.setStyle("-fx-font-size : 12px");

        noteDataColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        noteTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        notesTable.getColumns().setAll(noteDataColumn,noteTitleColumn);
    }

    private void loadNotesTable(){
        Note.loadNotesContainer();
        notesTable.setItems(Note.notesContainer);
    }

    public void loadNotesTable(ObservableList<Note> notes ){
        notes.sorted();
        notesTable.setItems(notes);
    }

    private void loadComboBox() {
        Category.loadCategoriesContainer();
        ObservableList<Category> categories = Category.categoriesContainer;

        Category all = new Category("Wszystkie");

        categoryCB.getItems().add(all.getName());
        categoryCB.setValue(all.getName());
        categoryCB.setStyle("-fx-font: 12px\"System\";");

        for(Category category : categories)
            categoryCB.getItems().add(category.getName());
    }
}