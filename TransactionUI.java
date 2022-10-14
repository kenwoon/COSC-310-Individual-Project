import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class TransactionUI extends JDialog implements ActionListener {
    JTable dataTable;
    ArrayList<JSpinner> spinners;
    ArrayList<String> names;
    Dictionary<String, Integer> transaction;
    public TransactionUI(JTable _dataTable, String title) {
        super(null, title, ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed
        
        spinners = new ArrayList<JSpinner>();
        names = new ArrayList<String>();
        JPanel root = new JPanel();
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.setLayout(new GridBagLayout());
        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        dataTable = _dataTable;

        Insets dummy = new Insets(0, 0, 0, 0);
        for (int i = 0; i < dataTable.getRowCount(); i++) {
            String name = dataTable.getModel().getValueAt(i, 1).toString(); // might have to change column index when the actual database gets created
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            root.add(spinner, new GridBagConstraints(0, i, 1, 1, .5, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, dummy, 0, 0));
            root.add(new JLabel(name), new GridBagConstraints(1, i, 1, 1, .5, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, dummy, 0, 0));
            spinners.add(spinner);
            names.add(name);
        }
        root.add(submitButton, new GridBagConstraints(0, dataTable.getRowCount(), 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, dummy, 0, 0));

        this.add(root);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setMinimumSize(new Dimension(200, 0));
        this.setLocation(500, 300);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submit")) {
            transaction = new Hashtable<String, Integer>();
            for (int i = 0; i < dataTable.getRowCount(); i++)
                transaction.put(names.get(i), (int)spinners.get(i).getValue());
            this.dispose();
        }
    }
}