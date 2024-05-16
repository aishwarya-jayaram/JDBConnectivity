package mystudentapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class MyStudentApplication extends JFrame {

    private JTextField firstNameField, lastNameField, emailField, USN_NoField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JButton registerButton, viewAllButton, updateButton, deleteButton;

    public MyStudentApplication() {
        setTitle("REGISTRATION FORM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(217, 0, 0));
        panel.setLayout(null);
        add(panel);

        JLabel titleLabel = new JLabel("REGISTRATION FORM");
        titleLabel.setForeground(new Color(12, 12, 12));
        titleLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(100, 10, 220, 30);
        panel.add(titleLabel);

        // First Name
        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(new Color(0, 0, 0));
        firstNameLabel.setBounds(20, 50, 100, 30);
        panel.add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(20, 75, 150, 30);
        panel.add(firstNameField);

        // Last Name
        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(new Color(0, 0, 0));
        lastNameLabel.setBounds(200, 50, 100, 30);
        panel.add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(200, 75, 150, 30);
        panel.add(lastNameField);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(new Color(0, 0, 0));
        emailLabel.setBounds(20, 110, 100, 30);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(20, 135, 330, 30);
        panel.add(emailField);

        // USN No
        JLabel USN_NoLabel = new JLabel("USN No");
        USN_NoLabel.setForeground(new Color(0, 0, 0));
        USN_NoLabel.setBounds(20, 170, 100, 30);
        panel.add(USN_NoLabel);

        USN_NoField = new JTextField();
        USN_NoField.setBounds(20, 205, 150, 30);
        panel.add(USN_NoField);

        // Gender
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setForeground(new Color(0, 0, 0));
        genderLabel.setBounds(20, 240, 100, 30);
        panel.add(genderLabel);

        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setBackground(Color.yellow);
        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBackground(Color.yellow);
        maleRadioButton.setBounds(100, 255, 70, 20);
        femaleRadioButton.setBounds(200, 255, 80, 20);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        panel.add(maleRadioButton);
        panel.add(femaleRadioButton);

        // Register Button
        registerButton = new JButton("Register Now");
        registerButton.setBounds(20, 300, 150, 30);
        panel.add(registerButton);

        // View All Button
        viewAllButton = new JButton("View All");
        viewAllButton.setBounds(190, 300, 150, 30);
        panel.add(viewAllButton);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.setBounds(20, 350, 150, 30);
        panel.add(updateButton);

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(190, 350, 150, 30);
        panel.add(deleteButton);

        // Action Listeners
        registerButton.addActionListener(e -> register());
        viewAllButton.addActionListener(e -> viewAll());
        updateButton.addActionListener(e -> update());
        deleteButton.addActionListener(e -> delete());

        setVisible(true);
    }

    private void register() {
        // Perform registration operation and save data to the database
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String USN_No = USN_NoField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "Aishu@2692002");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO university.registrationform (first_name, last_name, email, USN_No, gender) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, USN_No);
            ps.setString(5, gender);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewAll() {
        // Retrieve all records from the database and display them in the console
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "Aishu@2692002");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM registrationForm");

            System.out.println("All Records:");
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String USN_No = resultSet.getString("USN_No");
                String gender = resultSet.getString("gender");

                System.out.println("Name: " + firstName + " " + lastName + ", Email: " + email + ", Gender: " + gender + ", USN_No: " + USN_No);
            }
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void update() {
        // Perform update operation on a selected record
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String USN_No = USN_NoField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";

        String updateQuery = "UPDATE registrationForm SET first_name=?, last_name=?, email=?, gender=? WHERE USN_No=?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "Aishu@2692002");
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, USN_No);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with the specified USN No.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void delete() {
        // Perform delete operation on a selected record
        String USN_No = USN_NoField.getText();

        String deleteQuery = "DELETE FROM registrationForm WHERE USN_No=?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university", "root", "Aishu@2692002");
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, USN_No);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with the specified USN No.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        MyStudentApplication form = new MyStudentApplication();
    }
}