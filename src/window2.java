import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
    public class window2 {

        public static void secondWindow() {
            // Assure that the GUI is created on the Event Dispatch Thread
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Create the main frame
                    JFrame frame = new JFrame("Mon Application");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
                    frame.setSize(400, 300); // Set the size of the window
                    frame.setLocationRelativeTo(null); // Center the window
                    frame.setLayout(new FlowLayout());

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

                    JPanel panel4 = new JPanel();
                    JButton loginButton = new JButton("Login");
                    panel4.add(loginButton);
                    JPanel panel5 = new JPanel();
                    JButton SigninButton = new JButton("Signin");
                    panel4.add(SigninButton);
                    JPanel panel6 = new JPanel();
                    JButton ExitButton = new JButton("Exit");
                    panel5.add(ExitButton);
                    loginButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Action when button is clicked
                            String username = textField1.getText();
                            char[] passwordArray = passwordField.getPassword();
                            String password = new String(passwordArray);
                            if ((validateUser(username, password)) && (isAdmin(username, password))) {
                                // If both user validation and admin check pass
                                JOptionPane.showMessageDialog(frame, "Login successful!");
                                window4.openWindow4(); // Open admin window (Window 4)
                            } else if (validateUser(username, password)) {
                                // If only user validation passes (but not admin)
                                window8.eighthWindow(); // Open non-admin window (Window 8)
                            } else {
                                // If username or password is invalid
                                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    SigninButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            window3.thirdWindow();

                            // Optionally hide the current window
                            frame.setVisible(false);

                    }});

                    ExitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            window1.main(null);
                            frame.dispose();
                        }
                    });
                    frame.add(panel1);
                    frame.add(panel2);
                    frame.add(panel4);
                    frame.add(panel5);
                    frame.add(panel6);

                    // Make the frame visible
                    frame.setVisible(true);
                }
            });
        }

        private static boolean validateUser(String username, String password) {
            // SQL Select Query
            String sql = "SELECT * FROM cars_schemas.users WHERE username = ? AND password = ?;";

            try (Connection connection1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes");
                 PreparedStatement preparedStatement = connection1.prepareStatement(sql)) { // Use PreparedStatement

                // Set parameters
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                // Execute the query and get the result
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // If a record is found, return true
                    if (resultSet.next()) {
                        return true; // Username and password are valid
                    } else {
                        return false; // No match found
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error while checking the database!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        private static void setToAdmin(String username, String password) {
            String sql = "UPDATE Users SET isAdmin = ? WHERE username = ? AND password = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes");
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Set the isAdmin field to true (1 in SQL)
                pstmt.setBoolean(1, true);
                pstmt.setString(2, username);
                pstmt.setString(3, password); // Be cautious with plaintext passwords (consider hashing)

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("User set as admin successfully.");
                } else {
                    System.out.println("User not found or password incorrect.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static boolean isAdmin(String username, String password) {
            String query = "SELECT isAdmin FROM users WHERE username = ? AND password = ?";
            boolean isAdmin = false;

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes");
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        isAdmin = rs.getInt("isAdmin") == 1; // isAdmin = 1 means the user is an admin
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de la vérification de l'utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

            return isAdmin;
        }


    }
   /* idusers int AI PK
    username varchar(45)
    password varchar(45)
    isAdmin tinyint
    city*/