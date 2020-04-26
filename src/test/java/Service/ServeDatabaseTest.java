package Service;

import Models.Category;
import Models.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.assertj.core.condition.Not;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServeDatabaseTest {
    @Test
    public void categoriesContainerExist(){
        ObservableList<Category> categories;
        Category.loadCategoriesContainer();
        categories = new ServeDatabase().getCategories();

        int tmp = 0;

        for(Category category : categories)
        {
            tmp++;
            if(tmp>0) break;
        }

        if(tmp>0)
            assertNotEquals(categories,Category.categoriesContainer);
        else
            assertEquals(categories,Category.categoriesContainer);
    }
    @Test
    public void notesContainerExist(){
        ObservableList<Note> notes;
        Note.loadNotesContainer();
        notes = new ServeDatabase().getNotes();

        int tmp = 0;

        for(Note note : notes)
        {
            tmp++;
            if(tmp>0) break;
        }

        if(tmp>0)
            assertNotEquals(notes,Note.notesContainer);
        else
            assertEquals(notes,Note.notesContainer);
    }
    @Test
    public void addAndDeleteCategoryWork(){
        Category.loadCategoriesContainer();
        ObservableList<Category> categories = Category.categoriesContainer;

        ServeDatabase serveDatabase = new ServeDatabase();

        Category testCategory = new Category("Test");

        serveDatabase.addCategory(testCategory.getName());
        serveDatabase.deleteCategory(testCategory);

        int tmp = 0;

        for(Category category : categories)
        {
            tmp++;
            if(tmp>0) break;
        }

        Category.loadCategoriesContainer();

        if(tmp>0)
            assertNotEquals(categories,Category.categoriesContainer);
    }
    @Test
    public void addAndDeleteNoteWork(){
        Note.loadNotesContainer();
        ObservableList<Note> notes = Note.notesContainer;

        ServeDatabase serveDatabase = new ServeDatabase();

        Note testNote = new Note(1,"","","","");

        serveDatabase.addNote(testNote);
        serveDatabase.deleteNote(testNote);

        int tmp = 0;

        for(Note note : notes)
        {
            tmp++;
            if(tmp>0) break;
        }

        Note.loadNotesContainer();

        if(tmp>0)
            assertEquals(notes,Note.notesContainer);
    }
}