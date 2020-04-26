package Service;

import Models.Category;
import Models.Database;
import Models.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServeDatabase {
    private Database database = null;

    public ObservableList<Note> getNotes(){
        database = new Database();

        Statement notesStatement = null;
        ResultSet resultSetNotes = null;

        ObservableList<Note> collection = FXCollections.observableArrayList();

        String queryNotes = "select * from notes order by date desc";

        try {
            if(database.getConnection() != null) {
                notesStatement = database.getConnection().createStatement();
            }

            if(notesStatement != null){
                resultSetNotes = notesStatement.executeQuery(queryNotes);
            }

            while (resultSetNotes.next()) {
                Integer id = resultSetNotes.getInt("noteId");
                String title = resultSetNotes.getString("title");
                String category = resultSetNotes.getString("categoryName");
                String content = resultSetNotes.getString("content");
                String date = resultSetNotes.getString("date");

                collection.add(new Note(id, title, category,content,date));
            }
        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.closeConnection();
            return collection;
        }
    }

    public ObservableList<Category> getCategories(){
        database = new Database();

        Statement categoriesStatement = null;
        ResultSet resultSetCategories = null;
        ObservableList<Category> collection = FXCollections.observableArrayList();

        String query = "select * from categories order by name desc";

        try {
            if(database.getConnection() != null)
                categoriesStatement = database.getConnection().createStatement();

            if(categoriesStatement != null)
                resultSetCategories = categoriesStatement.executeQuery(query);

            while (resultSetCategories.next())
                collection.add(new Category(resultSetCategories.getString("name")));

        }catch (SQLException e){
            e.getMessage();
        }finally {
            database.closeConnection();
            return collection;
        }
    }

    public void addNote(Note note){
        database = new Database();

        try {
            if(database.getConnection() != null){
                String query = "insert into notes (title,content,date,categoryName)" + " values (?,?,?,?)";

                PreparedStatement preparedStmt = database.getConnection().prepareStatement(query);

                preparedStmt.setString(1,note.getTitle());
                preparedStmt.setString(2,note.getContent());
                preparedStmt.setString(3,note.getDate());
                preparedStmt.setString(4,note.getCategory());

                preparedStmt.execute();
                preparedStmt.close();
            }
        }catch (Exception e){
            e.getMessage();
        }finally {
            database.closeConnection();
        }
    }

    public void deleteNote(Note note){
        database = new Database();

        try {
            if(database.getConnection() != null){
                String query = "delete from notes where noteId=?";

                PreparedStatement preparedStmt = database.getConnection().prepareStatement(query);

                preparedStmt.setInt(1,note.getId());

                preparedStmt.execute();
                preparedStmt.close();
            }
        }catch (Exception e){
            e.getMessage();
        }
        finally {
            database.closeConnection();
        }
    }

    public void overwriteNote(Note note){
        database = new Database();

        try {
            if(database.getConnection() != null){
                String query = "update notes set title=?, categoryName=?, content=?, date=? where noteId=?";

                PreparedStatement preparedStmt = database.getConnection().prepareStatement(query);

                preparedStmt.setString(1,note.getTitle());
                preparedStmt.setString(2,note.getCategory());
                preparedStmt.setString(3,note.getContent());
                preparedStmt.setString(4,note.getDate());
                preparedStmt.setInt(5, note.getId());

                preparedStmt.executeUpdate();
                preparedStmt.close();
            }
        }catch (Exception e){
            e.getMessage();
        }finally {
            database.closeConnection();
        }
    }

    public void addCategory(String name){
        database = new Database();

        try {
            if(database.getConnection() != null){
                String query = "insert into categories (name)" + " values (?)";

                PreparedStatement preparedStmt = database.getConnection().prepareStatement(query);

                preparedStmt.setString(1,name);

                preparedStmt.execute();
                preparedStmt.close();
            }
        }catch (Exception e){
            e.getMessage();
        }finally {
            database.closeConnection();
        }
    }

    public void deleteCategory(Category category){
        database = new Database();

        try {
            if(database.getConnection() != null){
                String query = "delete from categories where name=?";

                PreparedStatement preparedStmt = database.getConnection().prepareStatement(query);

                preparedStmt.setString(1,category.getName());

                preparedStmt.execute();
                preparedStmt.close();
            }
        }catch (Exception e){
            e.getMessage();
        }
        finally {
            database.closeConnection();
        }
    }
}
