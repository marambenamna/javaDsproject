import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class window7  {
    public static void seventhWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Consulter la Liste des Voitures");
            frame.setSize(800, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Panel for filter input and buttons
            JPanel filterPanel = new JPanel(new FlowLayout());
            JLabel filterLabel = new JLabel("Filtrer par marque:");
            JTextField filterField = new JTextField(15);
            JButton filterButton = new JButton("Rechercher");
            JButton resetButton = new JButton("Voir Tout");
            JButton ExitButton = new JButton("Exit");

            filterPanel.add(filterLabel);
            filterPanel.add(filterField);
            filterPanel.add(filterButton);
            filterPanel.add(resetButton);
            filterPanel.add(ExitButton);

            // Table to display cars
            DefaultTableModel tableModel = new DefaultTableModel();
            JTable carTable = new JTable(tableModel);

            // Add columns to the table model
            tableModel.addColumn("ID");
            tableModel.addColumn("Marque");
            tableModel.addColumn("Modèle");
            tableModel.addColumn("Âge");
            tableModel.addColumn("Prix");
            tableModel.addColumn("Status");



            // Function to fetch cars from database
            Runnable fetchCars = () -> {
                String filter = filterField.getText().trim();
                String query = "SELECT idcar, brand, model, age, price, status FROM cars";
                if (!filter.isEmpty()) {
                    query += " WHERE brand LIKE ?";
                }

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes");
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    if (!filter.isEmpty()) {
                        stmt.setString(1, "%" + filter + "%");
                    }

                    ResultSet rs = stmt.executeQuery();

                    // Clear existing rows
                    tableModel.setRowCount(0);

                    // Add rows to the table model
                    while (rs.next()) {
                        int id = rs.getInt("idcar");
                        String brand = rs.getString("brand");
                        String model = rs.getString("model");
                        String age = rs.getString("age");
                        float price = rs.getFloat("price");
                        String status = rs.getInt("status") == 1 ? "Disponible" : "Indisponible";

                        tableModel.addRow(new Object[]{id, brand, model, age, price, status});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            };

            // Button actions
            filterButton.addActionListener(e -> fetchCars.run());
            resetButton.addActionListener(e -> {
                filterField.setText("");
                fetchCars.run();
            });
            ExitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    window2.secondWindow();


                }});
            // Add components to the frame
            JScrollPane scrollPane = new JScrollPane(carTable);
            frame.add(filterPanel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Fetch all cars on startup
            fetchCars.run();

            // Show the frame
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }
}

