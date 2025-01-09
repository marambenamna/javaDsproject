import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
public class window3 {
    public static void thirdWindow () {
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // Create the main frame
                        JFrame frame = new JFrame("Mon Application");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
                        frame.setSize(400, 300); // Set the size of the window
                        frame.setLocationRelativeTo(null); // Center the window
                        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                        // Create components
                        JPanel panel1 = new JPanel();
                        JLabel label1 = new JLabel("Username:");
                        JTextField textField1 = new JTextField(20);
                        panel1.add(label1);
                        panel1.add(textField1);

                        JPanel panel2 = new JPanel();
                        JLabel labelPassword = new JLabel("Password:");
                        JPasswordField passwordField = new JPasswordField(20);
                        panel2.add(labelPassword);
                        panel2.add(passwordField);

                        JPanel panel3 = new JPanel();
                        JLabel label3 = new JLabel("Email address:");
                        JTextField textField3 = new JTextField(20);
                        panel3.add(label3);
                        panel3.add(textField3);

                        JPanel panel4 = new JPanel();
                        JLabel label4 = new JLabel("Address (city):");
                        JTextField textField4 = new JTextField(20);
                        panel4.add(label4);
                        panel4.add(textField4);

                        JButton button = new JButton("Submit");
                        JButton ExitButton = new JButton("Exit");

                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Get the data from the text fields
                                String username = textField1.getText();
                                char[] passwordArray = passwordField.getPassword();
                                String password = new String(passwordArray);
                                String email = textField3.getText();
                                String address = textField4.getText();


                                if((username.isEmpty())|| (password.isEmpty())|| (email.isEmpty())|| (address.isEmpty()) ){
                                    JOptionPane.showMessageDialog(null,"all fields are obligatory");
                                }else{
                                    insertIntoDatabase(username, password, email, address);
                                    window8.eighthWindow();
                                }

                                frame.setVisible(false);
                            }
                        });
                        ExitButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                window2.secondWindow();
                                frame.dispose();
                            }
                        });


                        frame.add(panel1);
                        frame.add(panel2);
                        frame.add(panel3);
                        frame.add(panel4);
                        frame.add(button);
                        frame.add(ExitButton);

                        frame.setVisible(true);
                    }
                });
            }
    public static void insertIntoDatabase(String username, String password,String email,String address) {
        // Database credentials
        String url = "jdbc:mysql://127.0.0.1:3306/cars_schemas"; // Replace with your database URL
        String dbUsername = "root"; // Replace with your username
        String dbPassword = "lunacodes"; // Replace with your password

        // SQL Insert Query
        String sql = "INSERT INTO users (username, password, email , city) VALUES (?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);

            // Execute update
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Data inserted successfully!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Data not inserted!");
            }

        } catch (SQLException ex) {
            // Print stack trace for debugging
            ex.printStackTrace();
            // Show user-friendly error message
            JOptionPane.showMessageDialog(null, "Error inserting data into the database: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }

