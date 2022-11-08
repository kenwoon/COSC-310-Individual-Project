import java.io.*;
import java.time.*;
import java.util.*;

// https://stackoverflow.com/questions/5632658/how-to-encrypt-or-decrypt-a-file-in-java
public class ProgramState {
    public String path;
    public LocalDate date;
    public Database db;
    public List<Order> orders;
    public double revenue;
    public String password;

    public ProgramState() throws IOException {
        path = "InventorySystem.state";
        /* InventorySystem.state layout:
         * date=yyyy-mm-dd
         * dbpath=c:\path\to\the\csv
         * revenue=1234.56
         * pw=cleartextpassword_doesn't_matter_cause_encryption
         * productId(int),quantity(int),yyyy-mm-dd,isActive(boolean)
         * 1,12,2022-11-06  // ordered 12 products of id=1 on nov 6th 2022
        */
    }
    
    public void save() throws IOException {
        // save current state to the file
        BufferedWriter stream = new BufferedWriter(new FileWriter(path));
        stream.write("date=" + date.toString());    // save date
        stream.write("\ndbpath=" + db.filepath);    // save filepath of the db's csv
        stream.write("\nrevenue=" + revenue);       // save revenue
        stream.write("\npw=" + password);           // save password in plaintext, it will be encrypted later
        for (Order i : orders)                      // save a line for each active order
            if (i.isActive)     // if an order isn't active (ie has been received) we don't need to save it
                stream.write(String.format("\n%d,%d,%s", i.product.getId(), i.quantity, i.date.toString()));
    
        stream.close();     // close the stream

        // ADD CALLS TO ENCRYPT THE FILE HERE
    }

    public void load() throws IOException {
        // ADD CALLS TO DECRYPT THE FILE HERE

        // read in the state to this instance
        BufferedReader stream = new BufferedReader(new FileReader(path));
        String current = stream.readLine();
        date = LocalDate.parse(current.substring(current.indexOf("=")));

        current = stream.readLine();
        db = new Database(current.substring(current.indexOf("=")));
        
        current = stream.readLine();
        revenue = Double.parseDouble(current.substring(current.indexOf("=")));

        current = stream.readLine();
        password = current.substring(current.indexOf("="));

        orders = new ArrayList<Order>();
        while ((current = stream.readLine()) != null) {     // read in a line for each order
            String[] args = current.split(",");     // split the line on commas
            orders.add(new Order(Database.getProductById(Integer.parseInt(args[0]), db.products).get(0), Integer.parseInt(args[1]), LocalDate.parse(args[2])));
        }
        stream.close();
    }
}