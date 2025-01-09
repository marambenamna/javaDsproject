import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

    public class window6 {
        public static void sixthWindow() {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Consulter la Liste des Utilisateurs");
                frame.setSize(600, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                // Table model and table
                DefaultTableModel tableModel = new DefaultTableModel();
                JTable userTable = new JTable(tableModel);

                // Add columns to the table model
                tableModel.addColumn("ID");
                tableModel.addColumn("Nom d'utilisateur");
                tableModel.addColumn("Mot de passe");
                tableModel.addColumn("Admin");
                tableModel.addColumn("Ville");
                tableModel.addColumn("Email");
                JButton ExitButton = new JButton("Exit");

                // Fetch data from database and populate the table
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes")) {
                    String query = "SELECT idusers, username, password, isAdmin, city, email FROM users";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()) {
                        int id = rs.getInt("idusers");
                        String username = rs.getString("username");
                        String password = rs.getString("password");
                        boolean isAdmin = rs.getBoolean("isAdmin");
                        String city = rs.getString("city");
                        String email = rs.getString("email");

                        // Add row to the table model
                        tableModel.addRow(new Object[]{id, username, password, isAdmin ? "Oui" : "Non", city, email});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                ExitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();


                    }});


                // Add the table to a scroll pane
                JScrollPane scrollPane = new JScrollPane(userTable);
                frame.add(scrollPane, BorderLayout.CENTER);

                frame.add(ExitButton, BorderLayout.SOUTH);

                // Make the frame visible
                frame.setLocationRelativeTo(null); // Center the window on the screen
                frame.setVisible(true);
            });
        }
    }


