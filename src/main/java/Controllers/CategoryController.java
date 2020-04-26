package Controllers;

import Models.Category;
import Models.Messages;
import Models.Note;
import Service.ServeDatabase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryController {
    @FXML
    private TextField nameTF;

    @FXML
    private TableView<Category> categoriesTable;

    @FXML
    private TableColumn<Category, String> categoryNameColumn;

    @FXML
    void addCategoryAction(ActionEvent event) {
        if(nameTF.getText().equals(""))
            Messages.MessageBox("Pole z nazwą kategorii nie może być puste!");
        else {
            new ServeDatabase().addCategory(nameTF.getText());

            nameTF.clear();

            Category.loadCategoriesContainer();
            loadCategoriesTable(Category.categoriesContainer);
        }
    }

    @FXML
    void deleteCategoryAction(ActionEvent event) {
        boolean val = Messages.ConfirmBox("Usunięcie kategorii","Czy na pewno usunąć kategorię?");
        boolean categoryAssign = false;

        if (val){
            Category category = categoriesTable.getSelectionModel().getSelectedItem();

            for(Note note : Note.notesContainer){
                if(note.getCategory().equals(category.getName())){
                   categoryAssign = true;
                   break;
                }
            }

            if(!categoryAssign)
            {
                new ServeDatabase().deleteCategory(category);
                Category.loadCategoriesContainer();
                loadCategoriesTable(Category.categoriesContainer);
            }else {
                Messages.MessageBox("Istnieją notatki zawierające daną kategorię!");
            }
        }
    }

    @FXML
    void initialize() {
        loadCategoriesTable(Category.categoriesContainer);
        addTextLimiter(nameTF,35);
    }

    private void loadCategoriesTable(ObservableList categories) {
        categoryNameColumn = new TableColumn<>("Nazwa");
        categoryNameColumn.setMinWidth(440);

        categoriesTable.getColumns().setAll(categoryNameColumn);
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        categoriesTable.setItems(categories);
        categoriesTable.setStyle("-fx-font-size:12px;");
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }
}