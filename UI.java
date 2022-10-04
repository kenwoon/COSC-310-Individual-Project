import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class UI implements ActionListener {
    int count = 0;
    JFrame frame;
    JPanel root;
    JTextArea consoleOutput;

    public UI() {
        frame = new JFrame();
        root = new JPanel();

        // set up our components
        JButton mainButton = new JButton("click me");
        mainButton.setActionCommand("bruh");
        mainButton.addActionListener(this);
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setAutoscrolls(true);
        JScrollPane scrollPane = new JScrollPane(consoleOutput);

        // set up our main layout
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.setLayout(new GridLayout(2, 1, 5, 5));     // create a gridlayout with 2 rows and 1 column
        root.add(mainButton);
        root.add(scrollPane);       // add the scrollPane that the consoleOutput is inside

        // frame parameter boilerplate code
        frame.add(root);    // add the main root panel to the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Assignment 2");
        frame.pack();
        frame.setSize(500, 300);
        frame.setLocation(500, 300);
        frame.setVisible(true);     // actually show the window
    }

    @Override
    public void actionPerformed(ActionEvent e) {    // this is a listener method that waits for any event to occur and runs the corresponding code for each event
        String command = e.getActionCommand();
        if (command.equals(("bruh"))) {
            count++;
            log("Button has now been clicked " + count + " times.");
        }
        else {
            log("unknown command");
        }
    }

    public void log(String msg) {   // method to print any error messages/user messages with a timestamp to the consoleOutput
        consoleOutput.setText(consoleOutput.getText() + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " : " + msg + "\n");
    }
}