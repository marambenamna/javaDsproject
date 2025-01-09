import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class window4 {
    public static void openWindow4() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the main frame
                JFrame frame = new JFrame("F4");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
                frame.setSize(400, 300); // Set the size of the window
                frame.setLocationRelativeTo(null); // Center the window
                frame.setLayout(new FlowLayout(FlowLayout.CENTER,80,20));
                //JPanel panel3 = new JPanel();
                JButton button = new JButton("Ajouter une nouvelle voiture ");
                //JPanel panel3 = new JPanel();
                JButton button2 = new JButton("Consulter la liste des voitures");
                //JPanel panel3 = new JPanel();
                JButton button3 = new JButton("Consulter la liste des utilisateurs");
                //panel3.add(button3);
                JPanel panel6 = new JPanel();
                JButton ExitButton = new JButton("Exit");
                panel6.add(ExitButton);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        window5.fifthWindow();

                    }
                });

                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        window7.seventhWindow();

                    }});
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        window6.sixthWindow();


                    }});
                ExitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();


                    }});



                // Add the button to the frame

                frame.getContentPane().add(button);
                frame.getContentPane().add(button2);
                frame.getContentPane().add(button3);
                frame.getContentPane().add(panel6);

                // Make the frame visible
                frame.setVisible(true);
            }
        });
    }


}
