import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class MainUI extends JFrame implements ActionListener {
    JPanel root;
    JLabel dataPath;
    JTable dataTable;
    JTextArea consoleOutput;

    public MainUI(Database db) {   // constructor
        // get all products in String[] objects
        String[][] rows = new String[db.products.size()][6];
        for (int i = 0; i < db.products.size(); i++)
            rows[i] = db.products.get(i).toStringArray();

        // set up our main components, mainly dataTable
        root = new JPanel();
        dataPath = new JLabel(db.filepath);
        dataTable = new JTable(rows, new String[] {"id", "name", "stock", "sellPrice", "buyPrice", "shipTimeDays"});
        resizeColumns();
        dataTable.getTableHeader().setReorderingAllowed(false); // disallow re-ordering of the columns in the table
        dataTable.setDefaultEditor(Object.class, null);     // makes the cells non-editable as the JTable edits are all in string format but we want to be more strict and have number checking
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        // only allow the user to select one row at a time
        dataTable.setPreferredScrollableViewportSize(new Dimension(500, 150));
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setAutoscrolls(true);

        // set up our main admin buttons and the sub layout for them
        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new GridLayout(6, 1, 0, 5));
        mainButtonPanel.add(new JLabel("Tools", JLabel.CENTER));
        for (JButton button : new JButton[] {
                new JButton("Load"),
                new JButton("Save"),
                new JButton("Add"),
                new JButton("Edit"),
                new JButton("Order") }) {
            button.setActionCommand(button.getText().toLowerCase());
            button.addActionListener(this);
            mainButtonPanel.add(button);
        }

        // set up our dev tool buttons and the sublayout for them
        JPanel devButtonPanel = new JPanel();
        devButtonPanel.setLayout(new GridLayout(3, 1, 0, 5));
        devButtonPanel.add(new JLabel("Dev Tools", JLabel.CENTER));
        for (JButton button : new JButton[] {
                new JButton("Transaction"),
                new JButton("Set Time") }) {
            button.setActionCommand(button.getText().toLowerCase());
            button.addActionListener(this);
            devButtonPanel.add(button);
        }

        // set up our main layout
        Insets dummy = new Insets(0, 0, 0, 0);
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.setLayout(new GridBagLayout());     // create a gridbaglayout and set it as the root panel's layout manager
        root.add(dataPath, new GridBagConstraints(0, 0, 1, 1, 0.85, 0.1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, dummy, 0, 0));// add dataPath label
        root.add(new JScrollPane(dataTable), new GridBagConstraints(0, 1, 1, 1, 0.85, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, dummy, 0, 0));// add dataTable table
        root.add(new JScrollPane(consoleOutput), new GridBagConstraints(0, 2, 1, 1, 0.85, 0.4, GridBagConstraints.CENTER, GridBagConstraints.BOTH, dummy, 300, 100));// add our console log textbox
        root.add(mainButtonPanel, new GridBagConstraints(1, 0, 1, 2, 0.15, .75, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, dummy, 0, 0));// add our sub-layout for main buttons
        root.add(devButtonPanel, new GridBagConstraints(1, 2, 1, 1, 0.15, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, dummy, 0, 0));// add our sub-layout for dev buttons

        // frame parameter boilerplate code
        this.add(root);    // add the main root panel to the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Team 5: Inventory System");
        this.pack();
        //this.setSize(500, 300);
        this.setLocation(750, 300);
        this.setVisible(true);     // actually show the window
    }

    @Override
    public void actionPerformed(ActionEvent e) {    // this is a listener method that waits for any GUI event to occur and runs the corresponding method for each event
        String command = e.getActionCommand();
        switch (command) {
            case "load":
                InventorySystemMain.load(); break;
            case "save":
                InventorySystemMain.save(); break;
            case "add":
                InventorySystemMain.add(); break;
            case "edit":
                InventorySystemMain.edit(); break;
            case "order":
                InventorySystemMain.order(); break;
            case "transaction":
                InventorySystemMain.transaction(); break;
            case "set time":
                InventorySystemMain.setTime(); break;
            default:
                log("Unknown command: " + command); break;
        }
    }

    public void log(String msg) {   // method to print any error messages/user messages with a timestamp to the consoleOutput
        consoleOutput.setText(consoleOutput.getText() + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " : " + msg + "\n");
    }

    public void resizeColumns() {
        dataTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        dataTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        dataTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        dataTable.getColumnModel().getColumn(5).setPreferredWidth(200);
    }

    public void updateRows(Database db) {
        dataPath.setText(db.filepath);  // set the filepath label

        // set up our new data model with the new db
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {"id", "name", "stock", "sellPrice", "buyPrice", "shipTimeDays"});
        model.setRowCount(0);
        for (int i = 0; i < db.products.size(); i++)
            model.addRow(db.products.get(i).toStringArray());

        dataTable.setModel(model);  // update the JTable's data model
        resizeColumns();        // resize the data columns to be more aesthetic
    }

    public int getSelectedRow() {    // returns the 'id' value of the selected row's product or null if no row is selected
        int index = dataTable.getSelectedRow(); // returns 0-based index of currently selected row, will return -1 if no row is selected
        if (index < 0)
            return -1;
        else
            return Integer.parseInt(dataTable.getModel().getValueAt(index, 0).toString());
    }
}