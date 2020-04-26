package Models;

import Service.ServeDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Category {
    private String name;

    public static ObservableList<Category> categoriesContainer = FXCollections.observableArrayList();

    public Category(String name){
        this.name = name;
    }

    public static void loadCategoriesContainer() {
        ObservableList<Category> categories = new ServeDatabase().getCategories();

        if (!categoriesContainer.equals(categories)) {
            categoriesContainer.clear();
            categoriesContainer = categories;
        }
    }

    public String getName() {
        return name;
    }
}
