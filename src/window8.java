 import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.sql.*;
public class window8  {

    public static void eighthWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Voitures à Louer");
            frame.setSize(800, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            // Table model and table
            DefaultTableModel tableModel = new DefaultTableModel();
            JTable carTable = new JTable(tableModel);

            // Add columns to the table model
            tableModel.addColumn("ID");
            tableModel.addColumn("Marque");
            tableModel.addColumn("Modèle");
            tableModel.addColumn("Âge");
            tableModel.addColumn("Prix");
            tableModel.addColumn("Statut");

            // Panel for the button
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton rentButton = new JButton("Louer");
            buttonPanel.add(rentButton);
            JPanel panel6 = new JPanel();
            JButton ExitButton = new JButton("Exit");
            panel6.add(ExitButton);

            // Fetch available cars
            fetchAvailableCars(tableModel);

            // Rent button action
            rentButton.addActionListener(e -> {
                int selectedRow = carTable.getSelectedRow();

                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0); // Get car ID
                    rentCar(id, frame);
                    fetchAvailableCars(tableModel); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une voiture à louer.", "Alerte", JOptionPane.WARNING_MESSAGE);
                }
            });

            // Add components to the frame
            JScrollPane scrollPane = new JScrollPane(carTable);
            frame.add(scrollPane);
            frame.add(buttonPanel);

            frame.add(panel6);

            // Show the frame
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
            ExitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();


                }});
        });

    }

    // Method to fetch cars available for rent
    private static void fetchAvailableCars(DefaultTableModel tableModel) {
        String query = "SELECT idcar, brand, model, age, price, status FROM cars WHERE status = 1"; // Status 1 = "À louer"

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes");
             PreparedStatement stmt = conn.prepareStatement(query)) {

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
                String status = rs.getInt("status") == 1 ? "À louer" : "Loué";

                tableModel.addRow(new Object[]{id, brand, model, age, price, status});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the status of a car to "Loué"
    private static void rentCar(int carId, JFrame frame) {
        String query = "UPDATE cars SET status = 0 WHERE idcar = ?"; // Status 0 = "Loué"

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, carId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "La voiture a été louée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Échec de la location de la voiture.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors de la mise à jour du statut.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
