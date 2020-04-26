package Models;

import Service.ServeDatabase;
import javafx.collections.ObservableList;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseTest {
    @Test
    public void connectionExist(){
        Database database = new Database();
        assertNotEquals(database.getConnection(),null);
    }
}