package Models;

import Service.ServeDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Note {
    private Integer id;
    private String title;
    private String category;
    private String content;
    private String date;

    public static ObservableList<Note> notesContainer = FXCollections.observableArrayList();

    public static Note note = null;

    public Note(Integer id, String title, String category, String content, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.date = date;
    }

    public Note(String title, String category, String content, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.date = date;
    }

    public static void loadNotesContainer(){
        ObservableList<Note> observableList = new ServeDatabase().getNotes();
        if(!notesContainer.equals(observableList)){
            notesContainer.clear();
            notesContainer.addAll(observableList);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}