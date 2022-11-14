import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public class InventorySystemTest {
    String statePath = "InventorySystem.state";

    @Test
    public void DatabaseSaveLoadTest() {    // tests the database object's ability to edit products as well as save/load itself to a new csv file
        String testpath = "db_save_test.csv";
        Database db = new Database("dummy_data.csv");       // start with dummy data
        db.addProduct(new Product(11, "test product", 3, 4.99, 2.79, 7));   // add a product
        Database.saveCSV(testpath, db.products);    // save the new database to a new file

        Database testdb = new Database(testpath);   // init a new database from that file
        assertEquals(db.toString(), testdb.toString()); // check if all products are identical (Database has an overrided toString method)
    }

    @Test
    public void SaveStateTest() throws IOException {   // tests the savestate functionality
        if (new File(statePath).exists())
            Files.delete(Path.of(statePath)); // delete any currently existing state

        ProgramState state = new ProgramState();    // create default programstate
        state.save();       // save and encrypt the current state of the program

        ProgramState test = new ProgramState();     // if state saved properly, the test should now have loaded the state and they should be identical
        assertEquals(state.date, test.date);
        assertEquals(state.db.toString(), test.db.toString());
        assertEquals(state.revenue, test.revenue, 0.0);
        assertEquals(state.password, test.password);
        assertEquals(state.orders, test.orders);
    }

    @Test
    public void EncryptionTest() throws IOException {  // tests that the encryption/decryption works properly
        if (new File(statePath).exists())
            Files.delete(Path.of(statePath)); // delete any currently existing state

        ProgramState state = new ProgramState();
        state.save();   // saves and encrypts the file

        InventorySystemMain.decrypt(statePath);     // decrypt the file
        // manually read in the file and check each value against 'state'
        BufferedReader stream = new BufferedReader(new FileReader(statePath));
        assertEquals(state.date, LocalDate.parse(stream.readLine()));
        assertEquals(state.db.toString(), new Database(stream.readLine()).toString());
        assertEquals(state.revenue, Double.parseDouble(stream.readLine()), 0.0);
        assertEquals(state.password, stream.readLine());
        ArrayList<String> orderTest = new ArrayList<String>();
        for (Order i : state.orders)  // state.save() only saves active orders, so add all active orders to orderTest
            if (i.isActive)
                orderTest.add(i.toString());
        int totalOrders = 0;
        String current;
        while ((current = stream.readLine()) != null) {     // read in a line for each order
            String[] args = current.split(",");     // split the line on commas
            Order order = new Order(Database.getProductById(Integer.parseInt(args[0]), state.db.products).get(0), Integer.parseInt(args[1]), LocalDate.parse(args[2]));
            assertTrue(orderTest.contains(order.toString()));
            totalOrders++;
        }
        assertEquals(orderTest.size(), totalOrders);    // check the number of orders is the same
        stream.close();
    }
}