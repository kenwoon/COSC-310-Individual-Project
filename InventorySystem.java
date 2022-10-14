import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import java.io.*;

public class InventorySystem {
    static MainUI ui;
    static JFileChooser dialog;
    public static void main(String[] args) {
        // password check, commented out for now because testing easier
        // PasswordUI passwordDialog = new PasswordUI();   // thread will wait until passwordDialog is disposed before continuing because of modality built into PasswordUI
        // if (!passwordDialog.verified)
        //     return;
        
        ui = new MainUI(new String[][]{{"1", "pencils", "30"}}, new String[]{"id", "name", "currentStock"}, "C:\\Users\\Paul\\Documents\\School\\etcbruhjustgonnapadthisstringsuperlong.csv");
        dialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        dialog.setFileFilter(new FileFilter() {
            public boolean accept(File file) {
                if (file.getName().endsWith(".csv") || file.isDirectory())
                    return true;
                return false;
            }
            public String getDescription() {
                return "Comma separated files (*.csv)";
            }
        });
    }

    // methods called by the listener in MainUI
    public static void load() {
        ui.log("load");
        if (dialog.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION) {
            File path = dialog.getSelectedFile();
            // use Database.load(path.getAbsolutePath()) here
        }
    }
    public static void save() {
        ui.log("save");
        if (dialog.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
            File path = dialog.getSelectedFile();
            // use Database.save(path.getAbsolutePath()) here
        }
    }
    public static void add() { 
        ui.log("add");
    }
    public static void edit() {
        ui.log("edit");
    }
    public static void order() {
        ui.log("order");
    }
    public static void transaction() {
        ui.log("transaction");
    }
    public static void setTime() {
        ui.log("set time");
    }
}