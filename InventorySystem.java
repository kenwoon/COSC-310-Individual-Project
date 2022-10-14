import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.*;

public class InventorySystem {
    static MainUI ui;
    static JFileChooser fileDialog;
    static LocalDate date;
    public static void main(String[] args) {
        // password check, commented out for now because testing easier
        // PasswordUI passwordDialog = new PasswordUI();   // thread will wait until passwordDialog is disposed before continuing because of modality built into PasswordUI
        // if (!passwordDialog.verified)
        //     return;
        
        date = LocalDate.now();
        ui = new MainUI(new String[][]{{"1", "pencils", "30", "4.99", "0.5", "3 days"}, {"2", "pens", "45", "7.99", "1", "2 days"}}, new String[]{"id", "name", "currentStock", "sellPrice", "buyPrice", "shipTime"}, "C:\\Users\\Paul\\Documents\\School\\etcbruhjustgonnapadthisstringsuperlong.csv");

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
    public static void load() {             // UNFINISHED
        ui.log("load");
        if (fileDialog.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION) {
            File path = fileDialog.getSelectedFile();
            // use Database.load(path.getAbsolutePath()) here
        }
    }
    public static void save() {             // UNFINISHED
        ui.log("save");
        if (fileDialog.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
            File path = fileDialog.getSelectedFile();
            // use Database.save(path.getAbsolutePath()) here
        }
    }
    public static void add() {              // UNFINISHED
        ProductUI dialog = new ProductUI("Add Product");
        ui.log("add");
    }
    public static void edit() {             // UNFINISHED
        ProductUI dialog = new ProductUI("Edit Product");
        ui.log("edit");
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