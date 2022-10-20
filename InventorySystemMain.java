import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.*;

public class InventorySystemMain {
    static MainUI ui;
    static JFileChooser fileDialog;
    static LocalDate date;
    static Database db;
    public static void main(String[] args) {
        // password check, commented out for now because testing easier
        // PasswordUI passwordDialog = new PasswordUI();   // thread will wait until passwordDialog is disposed before continuing because of modality built into PasswordUI
        // if (!passwordDialog.verified)
        //     return;
        
        date = LocalDate.now();
        db = new Database("dummy_data.csv");
        ui = new MainUI(db);

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
            Database.saveCSV(path + ".csv", db.products);
            ui.log("Saved current table contents to: " + path + ".csv");
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
    public static void order() {            // UNFINISHED
        TransactionUI dialog = new TransactionUI(ui.dataTable, "Order more stock");
        if (dialog.products != null) {
            ui.log("received order: " + dialog.products.toString());
        }
    }
    public static void transaction() {      // UNFINISHED
        TransactionUI dialog = new TransactionUI(ui.dataTable, "Fake a Transaction");
        if (dialog.products != null) {
            ui.log("received transaction: " + dialog.products.toString());
        }
    }
    public static void setTime() {          // UNFINISHED
        SetTimeUI dialog = new SetTimeUI();
        if (dialog.timeSpan != null) {
            date = date.plus(dialog.timeSpan);
            ui.log("Increased date to (Y/M/D): " + date.toString());
        }
    }
}