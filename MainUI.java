import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

// https://www.educba.com/java-swing-layout/
// https://docs.oracle.com/javase/7/docs/api/java/awt/GridBagConstraints.html
// https://docs.oracle.com/javase/7/docs/api/javax/swing/JScrollPane.html
public class MainUI implements ActionListener {
    JFrame frame;
    JPanel root;
    JLabel dataPath;
    JTable dataTable;
    JTextArea consoleOutput;

    public MainUI(String[][] rows, String[] columns, String dbPath) {   // constructor
        // set up our main components
        frame = new JFrame();
        root = new JPanel();
        dataPath = new JLabel(dbPath);
        dataTable = new JTable(rows, columns);
        dataTable.getTableHeader().setReorderingAllowed(false);
        dataTable.setDefaultEditor(Object.class, null);     // makes the cells non-editable as the JTable edits are all in string format but we want to be more strict and have number checking
        dataTable.setPreferredScrollableViewportSize(new Dimension(300, 100));
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
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.setLayout(new GridBagLayout());     // create a gridbaglayout and set it as the root panel's layout manager
        addComponent(root, dataPath, 0, 0, 1, 1, 0.7, 0.1, -1, 0, 0);                           // add dataPath label
        addComponent(root, new JScrollPane(dataTable), 0, 1, 1, 1, 0.7, 0.5, -1, 0, 0);         // add dataTable table
        addComponent(root, new JScrollPane(consoleOutput), 0, 2, 1, 1, 0.7, 0.4, -1, 300, 100); // add our console log textbox
        addComponent(root, mainButtonPanel, 1, 0, 1, 2, 0.3, .75, -1, 0, 0);                    // add our sub-layout for main buttons
        addComponent(root, devButtonPanel, 1, 2, 1, 1, 0.3, 0.25, GridBagConstraints.HORIZONTAL, 0, 0);                    // add our sub-layout for dev buttons

        // frame parameter boilerplate code
        frame.add(root);    // add the main root panel to the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Team 5: Inventory System");
        frame.pack();
        //frame.setSize(500, 300);
        frame.setLocation(500, 300);
        frame.setVisible(true);     // actually show the window
    }

    @Override
    public void actionPerformed(ActionEvent e) {    // this is a listener method that waits for any GUI event to occur and runs the corresponding method for each event
        String command = e.getActionCommand();
        switch (command) {
            case "load":
                InventorySystem.load(); break;
            case "save":
                InventorySystem.save(); break;
            case "add":
                InventorySystem.add(); break;
            case "edit":
                InventorySystem.edit(); break;
            case "order":
                InventorySystem.order(); break;
            case "transaction":
                InventorySystem.transaction(); break;
            case "set time":
                InventorySystem.setTime(); break;
            default:
                log("Unknown command: " + command); break;
        }
    }

    public void log(String msg) {   // method to print any error messages/user messages with a timestamp to the consoleOutput
        consoleOutput.setText(consoleOutput.getText() + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " : " + msg + "\n");
    }

    public static void addComponent(JPanel panel, JComponent component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int fill, int ipadx, int ipady) {  // wrapper to add components to panels, pass -1 for any argument for them to default
        GridBagConstraints constraints = new GridBagConstraints(
            gridx < 0 ? GridBagConstraints.RELATIVE : gridx,
            gridy < 0 ? GridBagConstraints.RELATIVE : gridy,
            gridwidth < 0 ? 1 : gridwidth,
            gridheight < 0 ? 1 : gridheight,
            weightx < 0 ? 0 : weightx,
            weighty < 0 ? 0 : weighty,
            GridBagConstraints.CENTER,
            fill < 0 ? GridBagConstraints.BOTH : fill,
            new Insets(0, 0, 0, 0),
            ipadx < 0 ? 0 : ipadx,
            ipady < 0 ? 0 : ipady
        );
        panel.add(component, constraints);
    }
}