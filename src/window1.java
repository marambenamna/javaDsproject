import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class window1 {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Create the main frame
                    JFrame frame = new JFrame("F1");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
                    frame.setSize(400, 300); // Set the size of the window
                    frame.setLocationRelativeTo(null); // Center the window


                    // Create a button with an action listener
                    JButton button1 = new JButton("Admin");
                    JButton button2 = new JButton("User");
                    frame.setLayout(null);
                    button1.setBounds(50, 100, 100, 30); // Position (x, y) et taille (largeur, hauteur)
                    button2.setBounds(200, 100, 100, 30);
                    frame.getContentPane().add(button1);
                    frame.getContentPane().add(button2);
                    button1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            window2.secondWindow();
                            // Optionally hide the current window
                            frame.setVisible(false);
                        }

                    });
                    button2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            window2.secondWindow();

                            // Optionally hide the current window
                            frame.setVisible(false);
                        }

                    });

                    // Add the button to the frame

                    frame.getContentPane().add(button1);
                    frame.getContentPane().add(button2);
                    

                    // Make the frame visible
                    frame.setVisible(true);
                }
            });
        }
    }


