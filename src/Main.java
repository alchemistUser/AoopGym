/*
    
todo:

age calculation label logic
    
*/

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Random;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPasswordField;

public class Main {
    
    public static void main(String[] args) { 
        frmStartFrame.setVisible(true);
    }
    
    // functions
    
    // Register Frame - RF
    static void refreshRegisterFrame(){        
        dob = dmyToday();
        tfRP1Name.setText("");
        tfRP1Height.setText("");
        tfRP1Weight.setText("");
        lblRP1Age.setText("");        
        registerFrameProgressRefresher();
        dobRefresher();
        BMIKeyPressed();
    }
    static void btnRFRegisterPressed(){
        pnlRFContainer.setLayout(new BorderLayout());
        pnlRFContainer.add(pnlRegisterPage1);
        frmStartFrame.dispose();
        frmRegisterFrame.setVisible(true);
        refreshRegisterFrame();
    }
    static void btnRFBackToLoginPressed(){
        btnRFBackPressed();
        frmRegisterFrame.dispose();
        frmStartFrame.setVisible(true);
    }
    static void btnRFNextPressed(){
        // conditions
        if(RFPageCounter==0){
            if(tfRP1Name.getText().equals("")){
                JOptionPane.showMessageDialog(frmRegisterFrame, "Please input your name.");
                return;
            }
            if(lblRP1Age.getText().isEmpty()){
                JOptionPane.showMessageDialog(frmRegisterFrame, "Invalid birth date.");
                return;
            }
            if(tfRP1Height.getText().equals("") || !canConvertToDouble(tfRP1Height.getText())){
                JOptionPane.showMessageDialog(frmRegisterFrame, "Invalid height input.");
                return;
            }
            if(tfRP1Weight.getText().equals("") || !canConvertToDouble(tfRP1Weight.getText())){
                JOptionPane.showMessageDialog(frmRegisterFrame, "Invalid height input.");
                return;
            }
        }

        // proceed
        switch(RFPageCounter){
            case 0 -> {
                pnlRFContainer.removeAll();
                pnlRFContainer.add(pnlRegisterPage2);
                pnlRFContainer.repaint();
                pnlRFContainer.revalidate();
                RFPageCounter = 1;
                registerFrameProgressRefresher();
                
                int n;
                do{
                    n = generateSixDigitRandomInt();
                }while(doesUserExist("userID", n));
                lblRP2UserID.setText(String.valueOf(n));
                
            }
            case 1 -> {
                btnRFNextRegisterPressed();
            }
        }
    }
    static void btnRFBackPressed(){
        pnlRFContainer.removeAll();
        pnlRFContainer.add(pnlRegisterPage1);
        pnlRFContainer.repaint();
        pnlRFContainer.revalidate();
        RFPageCounter = 0;
        registerFrameProgressRefresher();
    }
    static void registerFrameProgressRefresher(){
        switch(RFPageCounter){
            case 0 -> {
                btnRFBack.setEnabled(false);
                btnRFNext.setText("Next");
            }
            case 1 -> {
                btnRFBack.setEnabled(true);
                btnRFNext.setText("Register");
            }
        }
    }
    // Register page 1 - RP1
    static void btnDateRP1Pressed(){
        String[] arrMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        cbMCMonths.removeAllItems();
        for(String items : arrMonths){
            cbMCMonths.addItem(items);
        }
        tfMCDay.setText(dob.substring(0, 2));
        tfMCYear.setText(dob.substring(4, 8));
        switch(dob.substring(2, 4)){
            case "01" -> {
                cbMCMonths.setSelectedItem("January");
            }
            case "02" -> {
                cbMCMonths.setSelectedItem("February");
            }
            case "03" -> {
                cbMCMonths.setSelectedItem("March");
            }
            case "04" -> {
                cbMCMonths.setSelectedItem("April");
            }
            case "05" -> {
                cbMCMonths.setSelectedItem("May");
            }
            case "06" -> {
                cbMCMonths.setSelectedItem("June");
            }
            case "07" -> {
                cbMCMonths.setSelectedItem("July");
            }
            case "08" -> {
                cbMCMonths.setSelectedItem("August");
            }
            case "09" -> {
                cbMCMonths.setSelectedItem("September");
            }
            case "10" -> {
                cbMCMonths.setSelectedItem("October");
            }
            case "11" -> {
                cbMCMonths.setSelectedItem("November");
            }
            case "12" -> {
                cbMCMonths.setSelectedItem("December");
            }
        }
        frmDateChooser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmDateChooser.setVisible(true);
    }
    static void btnMCSavePressed(){
        StringBuilder sb = new StringBuilder();
        if(Integer.parseInt(tfMCDay.getText())<10){
            if(!tfMCDay.getText().substring(0, 1).equals("0")){
            sb.append("0");
            }
        }
        sb.append(tfMCDay.getText());
        switch(cbMCMonths.getSelectedIndex()){
            case 0 -> {
                sb.append("01");
            }
            case 1 -> {
                sb.append("02");
            }
            case 2 -> {
                sb.append("03");
            }
            case 3 -> {
                sb.append("04");
            }
            case 4 -> {
                sb.append("05");
            }
            case 5 -> {
                sb.append("06");
            }
            case 6 -> {
                sb.append("07");
            }
            case 7 -> {
                sb.append("08");
            }
            case 8 -> {
                sb.append("09");
            }
            case 9 -> {
                sb.append("10");
            }
            case 10 -> {
                sb.append("11");
            }
            case 11 -> {
                sb.append("12");
            }
        }
        sb.append(tfMCYear.getText());
        dob = String.valueOf(sb);        
        if(Integer.parseInt(tfMCDay.getText())>31 || tfMCYear.getText().length()>4){
            dob = dmyToday();
            JOptionPane.showMessageDialog(frmDateChooser, "Invalid date!");
        }
        frmDateChooser.dispose();
        dobRefresher();
        frmRegisterFrame.setEnabled(true);
    }
    static void dobRefresher(){
        StringBuilder sb = new StringBuilder();
        sb.append(dob.substring(0, 2));
        sb.append(" ");
        switch(dob.substring(2, 4)){
            case "01" -> {
                sb.append("January");
            }
            case "02" -> {
                sb.append("February");
            }
            case "03" -> {
                sb.append("March");
            }
            case "04" -> {
                sb.append("April");
            }
            case "05" -> {
                sb.append("May");
            }
            case "06" -> {
                sb.append("June");
            }
            case "07" -> {
                sb.append("July");
            }
            case "08" -> {
                sb.append("August");
            }
            case "09" -> {
                sb.append("September");
            }
            case "10" -> {
                sb.append("October");
            }
            case "11" -> {
                sb.append("November");
            }
            case "12" -> {
                sb.append("December");
            }
        }        
        sb.append(" ");
        sb.append(dob.substring(4, 8));
        btnRP1Date.setText(String.valueOf(sb));
        lblRP1Age.setText(calculateAge(dob));
        if(Integer.parseInt(calculateAge(dob))<=-1||dob.equals(dmyToday())){
            lblRP1Age.setText(new String());
        }
    }
    static String dmyToday(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
    static String calculateAge(String dob) {
        String currentDate = dmyToday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        // Parse the date of birth and current date
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        LocalDate current = LocalDate.parse(currentDate, formatter);

        // Calculate age
        int age = current.getYear() - birthDate.getYear();

        // Adjust age if the current date is before the birthday in the current year
        if (current.getDayOfYear() < birthDate.getDayOfYear()) {
            age--;
        }

        return String.valueOf(age);
    }
    static void BMIKeyPressed() {
        try {
            double height = Double.parseDouble(tfRP1Height.getText());
            double weight = Double.parseDouble(tfRP1Weight.getText());

            // Convert height from centimeters to meters
            height /= 100;

            // Calculate BMI
            double bmi = weight / (height * height) *10;

            // Display BMI rounded to 2 decimal places
            lblRP1BMI.setText(String.format("%.2f", bmi));

            // Determine BMI category
            if (bmi < 18.5) {
                lblRP1BMIRemark.setText("Underweight");
            } else if (bmi >= 18.5 && bmi < 25.0) {
                lblRP1BMIRemark.setText("Normal");
            } else if (bmi >= 25.0 && bmi < 30.0) {
                lblRP1BMIRemark.setText("Overweight");
            } else {
                lblRP1BMIRemark.setText("Obese");
            }
        } catch (NumberFormatException e) {
            lblRP1BMIRemark.setText("Remark");
        }
    }
    // Register page 2 - RP2
    static void lblRP2UserIDPressed(){
        int n;
        do{
            n = generateSixDigitRandomInt();
        }while(doesUserExist("userID", n));
        lblRP2UserID.setText(String.valueOf(n));
    }
    static void btnRFNextRegisterPressed(){
        String username = tfRP2UserName.getText();
        if(username.equals("")){
            JOptionPane.showMessageDialog(frmRegisterFrame, "Please enter a username.");
            return;
        }
        if(doesUserExist("username", username)){
            JOptionPane.showMessageDialog(frmRegisterFrame, "Username already exists. Choose another username.");
            return;
        }
        String password = tfRP2Password.getText();
        if(password.length()<8){
            JOptionPane.showMessageDialog(frmRegisterFrame, "Password length must be 8 characters long.");
            return;
        }
        String confirmPassword = tfRP2ConfirmPassword.getText();
        if(!confirmPassword.equals(password)){
            JOptionPane.showMessageDialog(frmRegisterFrame, "Passwords must match.");
            return;
        }
        insertUser(lblRP2UserID.getText(), password, username, tfRP1Name.getText(), dob, tfRP1Height.getText(), tfRP1Weight.getText(), lblRP1BMI.getText());
    }
        
    // Login
    static void btnSFLoginPressed(){
        if(tfSFUser.getText().equals("")){
            JOptionPane.showMessageDialog(frmStartFrame, "Please enter UserID or Username.");
            return;
        }
        if(tfSFPassword.getText().equals("") || (tfSFPassword.getText().length()<8 && !(tfSFUser.getText().equals("admin")||tfSFUser.getText().equals("1")))){
            JOptionPane.showMessageDialog(frmStartFrame, "Invalid password.");
            return;
        }
        System.out.println(String.valueOf(loginAuth(tfSFUser.getText(), tfSFPassword.getText())));
    }
static int loginAuth(String userInput, String password) {
    String queryUser = "SELECT userID, username, password FROM useraccounts WHERE userID = ? OR username = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(queryUser)) {

        // Check if userInput is numeric (possible userID) or text (username)
        try {
            pstmt.setInt(1, Integer.parseInt(userInput)); // If numeric, set as userID
        } catch (NumberFormatException e) {
            pstmt.setString(1, ""); // Empty string to avoid mismatch if userInput is not a number
        }

        pstmt.setString(2, userInput); // Always set as username for the second parameter

        // Execute query
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            // Check if the provided password matches
            String storedPassword = rs.getString("password");
            if (storedPassword.equals(password)) {
                System.out.println("Login successful for user: " + rs.getString("username"));
                return 0; // Success
            } else {
                System.out.println("Incorrect password.");
                return 1; // Incorrect password
            }
        } else {
            System.out.println("No userID or username found.");
            return 2; // No userID or username found
        }

    } catch (SQLException e) {
        System.err.println("Database error: " + e.getMessage());
        return -1; // -1 indicates a system error
    }
}


    //variables
    static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlite:database.db");
    }
    
    // general functions
    static void insertUser (String userID, String password, String username, String name, String birthdate, String height, String weight, String bmi) {
        
        String sql = "INSERT INTO useraccounts ("
                + "userID,"
                + "password,"
                + "username,"
                + "name,"
                + "birthdate,"
                + "height,"
                + "weight,"
                + "bmi) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            // Set the values for the placeholders in the SQL query
            pstmt.setString(1, userID);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setString(4, name);
            pstmt.setString(5, birthdate);
            pstmt.setString(6, height);
            pstmt.setString(7, weight);
            pstmt.setString(8, bmi);
            
            // Execute the insert statement
            pstmt.executeUpdate();
            System.out.println("User  inserted successfully.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Method to check if a userID exists in the useraccounts table
    static boolean doesUserExist(String columnName, int userId) {
        String query = "SELECT COUNT(*) FROM useraccounts WHERE " + columnName + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId); // Assuming userID is an integer in the database
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count is greater than 0, user exists
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return false; // Return false if an exception occurs or user does not exist
    }
    static boolean doesUserExist(String columnName, String userId) {
        String query = "SELECT COUNT(*) FROM useraccounts WHERE " + columnName + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userId); // Assuming userID is an integer in the database
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count is greater than 0, user exists
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return false; // Return false if an exception occurs or user does not exist
    }
    static boolean canConvertToDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false; // Null or empty strings cannot be converted
        }
        try {
            return true; // If successful, return true
        } catch (NumberFormatException e) {
            return false; // If an exception is thrown, return false
        }
    }
    static int generateSixDigitRandomInt() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
    }
    
    // Components
    
    
    //RegisterFrame - RF
    static JFrame frmRegisterFrame = new RegisterFrame();
    static JPanel pnlRFContainer = RegisterFrame.getContainerPanel();
    static JButton btnRFNext = RegisterFrame.getNextButton();
    static JButton btnRFBack=  RegisterFrame.getBackButton();
    static int RFPageCounter = 0;
    //RegisterPanel1 - RP1
    static JPanel pnlRegisterPage1 = new RegisterPage1Panel();
    static JTextField tfRP1Name = RegisterPage1Panel.getTfName();
    static JButton btnRP1Date = RegisterPage1Panel.getBtnDate();
    static JLabel lblRP1Age = RegisterPage1Panel.getLblAge();
    static JTextField tfRP1Height = RegisterPage1Panel.getTfHeight();
    static JTextField tfRP1Weight = RegisterPage1Panel.getTfWeight();    
    static JLabel lblRP1BMI = RegisterPage1Panel.getLblBMI();
    static JLabel lblRP1BMIRemark = RegisterPage1Panel.getLblBMIRemark();
    //MonthChooser - MC
    static String dob = dmyToday();
    static JFrame frmDateChooser = new MonthChooserFrame();
    static JTextField tfMCDay = MonthChooserFrame.getDayTextField();
    static JComboBox cbMCMonths = MonthChooserFrame.getMonthComboBox();
    static JTextField tfMCYear = MonthChooserFrame.getYearTextField();    
    //RegisterPanel2 - RP2
    static JPanel pnlRegisterPage2 = new RegisterPage2Panel();
    static JLabel lblRP2UserID = RegisterPage2Panel.getUserIDLabel();
    static JTextField tfRP2UserName = RegisterPage2Panel.getUserNameTextField();
    static JPasswordField tfRP2Password = RegisterPage2Panel.getPasswordTextField();
    static JPasswordField tfRP2ConfirmPassword = RegisterPage2Panel.getConfirmPasswordTextField();
    
    // StartFrame
    static JFrame frmStartFrame = new StartFrame();
    static JTextField tfSFUser = StartFrame.getTfUser();
    static JTextField tfSFPassword = StartFrame.getTfPassword();
}
