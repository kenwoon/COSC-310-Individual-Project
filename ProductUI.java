import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ProductUI extends JDialog implements ActionListener {
    JSpinner idInput, currentStockInput, sellPriceInput, buyPriceInput, shipTimeMonthInput, shipTimeDayInput;
    JTextField nameInput;

    public ProductUI() {   // change to allow a Product argument to pre-fill the boxes etc
        super(null, "Edit Product", ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed
        
        JPanel root = new JPanel();
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.setLayout(new GridBagLayout());
        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        idInput = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
        nameInput = new JTextField();
        currentStockInput = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
        sellPriceInput = new JSpinner(new SpinnerNumberModel((double)0, (double)0, null, (double)0.01));
        buyPriceInput = new JSpinner(new SpinnerNumberModel((double)0, (double)0, null, (double)0.01));
        shipTimeMonthInput = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
        shipTimeDayInput = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(2, 7, 5, 5));
        Insets dummy = new Insets(0, 0, 0, 0);
        for (String i : new String[] {"id", "name", "currentStock", "sellPrice", "buyPrice", "shipTimeMonths", "shipTimeDays"})
            subPanel.add(new JLabel(i));
        for (Component i : new Component[] {idInput, nameInput, currentStockInput, sellPriceInput, buyPriceInput, shipTimeMonthInput, shipTimeDayInput})
            subPanel.add(i);

        root.add(subPanel, new GridBagConstraints(0, 0, 1, 1, 1, .5, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, dummy, 0, 0));
        root.add(submitButton, new GridBagConstraints(0, 1, 1, 1, 1, .5, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, dummy, 0, 0));

        this.add(root);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(750, 200);
        this.setLocation(500, 300);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submit")) {
            int id = (int)idInput.getValue();
            String name = nameInput.getText();
            int currentStock = (int)currentStockInput.getValue();
            double sellPrice = (double)sellPriceInput.getValue();
            double buyPrice = (double)buyPriceInput.getValue();
            int shipTimeMonths = (int)shipTimeMonthInput.getValue();
            int shipTimeDays = (int)shipTimeDayInput.getValue();

            JOptionPane.showMessageDialog(this, String.format("%d, %s, %d, %f, %f, %d, %d", id, name, currentStock, sellPrice, buyPrice, shipTimeMonths, shipTimeDays));
            // set a class level variable of type Product before disposing
            this.dispose();
        }
    }
}