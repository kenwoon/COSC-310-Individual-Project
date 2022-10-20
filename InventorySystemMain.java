import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.*;
import java.util.*;

public class InventorySystemMain {
    static MainUI ui;
    static JFileChooser fileDialog;
    static LocalDate date;
    static Database db;
    static List<Order> orders;
    static double revenue;
    public static void main(String[] args) {
        // password check, commented out for now because testing easier
        // PasswordUI passwordDialog = new PasswordUI();   // thread will wait until passwordDialog is disposed before continuing because of modality built into PasswordUI
        // if (!passwordDialog.verified)
        //     return;
        
        // initialize class level variables
        date = LocalDate.now();
        db = new Database("dummy_data.csv");
        orders = new ArrayList<Order>();
        revenue = 0;
        ui = new MainUI(db);
        ui.log("Loaded: " + "dummy_data.csv");
        
        // set up our JFileChooser for loading and saving CSVs
        fileDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileDialog.setFileFilter(new FileFilter() {
            public boolean accept(File file) {      // show all directories and *.csv files in the file picker
                if (file.getName().endsWith(".csv") || file.isDirectory())
                    return true;
                return false;
            }
            public String getDescription() {        // show the description for csv files
                return "Comma separated value file (*.csv)";
            }
        });
    }

    // methods called by the listener in MainUI
    public static void load() {
        if (fileDialog.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION) {
            String path = fileDialog.getSelectedFile().getAbsolutePath();
            db.filepath = path;
            db.products = Database.loadCSV(path);
            ui.updateRows(db);
            ui.log("Loaded: " + path);
        }
    }
    public static void save() {
        if (fileDialog.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
            String path = fileDialog.getSelectedFile().getAbsolutePath();
            path = path.endsWith(".csv") ? path : path + ".csv";
            Database.saveCSV(path, db.products);
            ui.log("Saved current table contents to: " + path);
        }
    }
    public static void add() {
        ProductUI dialog = new ProductUI("Add Product", null);
        Product product = dialog.product;
        if (product != null) {
            if (Database.getProductById(product.getId(), db.products).size() > 0 || product.getId() < 1)
                ui.log("Id already in use or Id negative, product add failed.");
            else {
                db.addProduct(product);
                ui.updateRows(db);
                ui.log("Added product '" + product.getName() + "' successfully.");
            }
        }
        else
            ui.log("Add canceled by user.");
    }
    public static void edit() {
        int id = ui.getSelectedRow();   // will return -1 if no row selected
        if (id > 0) {       // check if a row is selected
            Product original = Database.getProductById(id, db.products).get(0);
            ProductUI dialog = new ProductUI("Edit Product", original);
            if (dialog.product != null) {   // check if input was validated and a valid product was returned
                db.removeProduct(original);
                db.addProduct(dialog.product);
                ui.updateRows(db);
                ui.log("Edit succeeded.");
            }
            else
                ui.log("Edit canceled by user.");
        }
        else
            ui.log("No row selected, so product editing failed.");
    }
    public static void order() {
        TransactionUI dialog = new TransactionUI(ui.dataTable, "Order more stock");
        if (dialog.products != null) {
            for (int id : dialog.products.keySet()) {     // add an order for each item in dialog.products
                int quantity = dialog.products.get(id);
                if (quantity > 0) {     // decrease revenue and place the order
                    revenue -= Database.getProductById(id, db.products).get(0).getBuyPrice() * quantity;
                    orders.add(new Order(Database.getProductById(id, db.products).get(0), quantity, date));
                }
            }
            ui.updateRevenue(revenue);
            ui.log("Placed order(s).");
        }
        else
            ui.log("Order canceled by user.");
    }
    public static void transaction() {
        TransactionUI dialog = new TransactionUI(ui.dataTable, "Fake a Transaction");
        if (dialog.products != null) {
            for (int id : dialog.products.keySet()) {
                int quantity = dialog.products.get(id);
                if (quantity > 0) {     // increase revenue and decrease the inventory
                    Product p = Database.getProductById(id, db.products).get(0);
                    revenue += p.getSellPrice() * quantity;
                    p.setCurrentStock(p.getCurrentStock() - quantity);
                }
            }
            ui.updateRows(db);
            ui.updateRevenue(revenue);
            ui.log("Customer transaction occurred.");
        }
        else
            ui.log("Transaction canceled by user.");
    }
    public static void setTime() {
        SetTimeUI dialog = new SetTimeUI();
        if (dialog.timeSpan != null) {
            date = date.plus(dialog.timeSpan);
            ui.log("Increased date to (Y/M/D): " + date.toString());

            // check for if any orders have arrived
            for (Order order : orders) {
                if (order.hasArrived(date)) {
                    order.receive(db, ui);
                }
            }
        }
        else
            ui.log("Set time operation canceled by user.");
    }
}