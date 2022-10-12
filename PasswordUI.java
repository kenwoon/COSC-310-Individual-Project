import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PasswordUI extends JDialog implements ActionListener {
    boolean verified;
    JTextField input;
    public PasswordUI() {
        super(null, "Credential Check", ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed

        verified = false;
        JPanel root = new JPanel();
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.setLayout(new GridBagLayout());
        input = new JPasswordField();
        input.setPreferredSize(new Dimension(270, 50));
        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);

        MainUI.addComponent(root, input, 0, 0, 1, 1, 0.9, 1, -1, 0, 0);
        MainUI.addComponent(root, submitButton, 1, 0, 1, 1, 0.1, 1, -1, 0, 0);

        // frame parameter boilerplate code
        this.add(root);    // add the main root panel to the frame
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //this.setTitle("Credential Check");    // this line commented out as the code for modality already sets the title
        this.pack();
        this.setSize(300, 90);
        this.setLocation(500, 300);
        this.setVisible(true);     // actually show the window
    }

    @Override
    public void actionPerformed(ActionEvent e) {    // this is a listener method that waits for any GUI event to occur and runs the corresponding method for each event
        if (e.getActionCommand().equals("submit")) {
            if (input.getText().equals("password")) {
                verified = true;
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Incorrect password.", "Error!", 0);
            }
        }
    }
}