import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class window5 {
    public static void fifthWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Ajouter une Nouvelle Voiture");
                frame.setSize(400, 300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new GridLayout(6, 2));
                frame.setLocationRelativeTo(null);

                JButton ExitButton = new JButton("Exit");


                // Add labels and text fields
                JLabel brandLabel = new JLabel("Marque:");
                JTextField brandField = new JTextField();

                JLabel modelLabel = new JLabel("Modèle:");
                JTextField modelField = new JTextField();

                JLabel ageLabel = new JLabel("Âge (yyyy-MM-dd):");
                JTextField ageField = new JTextField();

                JLabel priceLabel = new JLabel("Prix:");
                JTextField priceField = new JTextField();

                JButton addButton = new JButton("Ajouter");
                JLabel statusLabel = new JLabel("");




                // Add components to the frame

                frame.add(brandLabel);
                frame.add(brandField);
                frame.add(modelLabel);
                frame.add(modelField);
                frame.add(ageLabel);
                frame.add(ageField);
                frame.add(priceLabel);
                frame.add(priceField);
                frame.add(addButton);
                frame.add(ExitButton);
                frame.add(statusLabel);
                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String brand = brandField.getText();
                        String model = modelField.getText();
                        String age = ageField.getText();
                        String price = priceField.getText();
                        carAdding(brand , model , age , price );

                    }
                });
                ExitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        window4.openWindow4();
                        frame.dispose();

                    }
                });
                frame.setVisible(true);
            }});

    }
     public static void carAdding(String brand , String model , String age , String price ){
            if (brand.isEmpty() || model.isEmpty() || age.isEmpty() || price.isEmpty() ) {
                JLabel statusLabel=new JLabel("Tous les champs sont obligatoires.");
                return;
            }

            try {
            float price1 = Float.parseFloat(price);

        // Insert data into the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars_schemas", "root", "lunacodes")) {
            String query = "INSERT INTO cars (brand, model, age, price, status) VALUES (?, ?, ?, ?, 1)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setString(3, age);
            stmt.setFloat(4, price1);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null,"Voiture ajoutée avec succès!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Erreur lors de l'ajout.");
        }
    } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,"Le prix doit être un nombre.");
    }
}
}

