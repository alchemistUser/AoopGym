/*
    
todo:

age calculation label logic
    
*/

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class Main {
    
    public static void main(String[] args) { 
        frmStartFrame.setVisible(true);
    }
    
    // Register Frame - RF
    static void btnRegisterPressedRF(){
        pnlContainerRF.setLayout(new BorderLayout());
        pnlContainerRF.add(pnlRegisterPage1);
        frmStartFrame.dispose();
        frmRegisterFrame.setVisible(true);
        registerFrameProgressRefresher();
    }
    static void btnBackToLoginRFPressed(){
        btnBackRFPressed();
        frmRegisterFrame.dispose();
        frmStartFrame.setVisible(true);
    }
    static void btnDateRP1Pressed(){
        String[] arrMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        cbMonthsMC.removeAllItems();
        for(String items : arrMonths){
            cbMonthsMC.addItem(items);
        }
        tfDayMC.setText(dob.substring(0, 2));
        tfYearMC.setText(dob.substring(4, 8));
        switch(dob.substring(2, 4)){
            case "01" -> {
                cbMonthsMC.setSelectedItem("January");
            }
            case "02" -> {
                cbMonthsMC.setSelectedItem("February");
            }
            case "03" -> {
                cbMonthsMC.setSelectedItem("March");
            }
            case "04" -> {
                cbMonthsMC.setSelectedItem("April");
            }
            case "05" -> {
                cbMonthsMC.setSelectedItem("May");
            }
            case "06" -> {
                cbMonthsMC.setSelectedItem("June");
            }
            case "07" -> {
                cbMonthsMC.setSelectedItem("July");
            }
            case "08" -> {
                cbMonthsMC.setSelectedItem("August");
            }
            case "09" -> {
                cbMonthsMC.setSelectedItem("September");
            }
            case "10" -> {
                cbMonthsMC.setSelectedItem("October");
            }
            case "11" -> {
                cbMonthsMC.setSelectedItem("November");
            }
            case "12" -> {
                cbMonthsMC.setSelectedItem("December");
            }
        }
        frmMonthChooser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmMonthChooser.setVisible(true);
    }
    static void btnSaveMCPressed(){
        StringBuilder sb = new StringBuilder();
        if(Integer.parseInt(tfDayMC.getText())<10){
            sb.append("0");
        }
        sb.append(tfDayMC.getText());
        switch(cbMonthsMC.getSelectedIndex()){
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
        sb.append(tfYearMC.getText());
        dob = String.valueOf(sb);
        frmMonthChooser.dispose();
        dobRefresher();
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
        btnDateRP1.setText(String.valueOf(sb));
    }
    static void btnNextRFPressed(){
        switch(pageCounterRF){
            case 0 -> {
                pnlContainerRF.removeAll();
                pnlContainerRF.add(pnlRegisterPage2);
                pnlContainerRF.repaint();
                pnlContainerRF.revalidate();
                pageCounterRF = 1;
                registerFrameProgressRefresher();
            }
            case 1 -> {
                
            }
        }
    }
    static void btnBackRFPressed(){
        pnlContainerRF.removeAll();
        pnlContainerRF.add(pnlRegisterPage1);
        pnlContainerRF.repaint();
        pnlContainerRF.revalidate();
        pageCounterRF = 0;
        registerFrameProgressRefresher();
    }
    static void registerFrameProgressRefresher(){
        switch(pageCounterRF){
            case 0 -> {
                btnBackRF.setEnabled(false);
                btnNextRF.setText("Next");
            }
            case 1 -> {
                btnBackRF.setEnabled(true);
                btnNextRF.setText("Register");
            }
        }
    }
    
    // Components
    
    static JFrame frmStartFrame = new StartFrame();
    
    //RegisterFrame - RF
    static JFrame frmRegisterFrame = new RegisterFrame();
    static JPanel pnlContainerRF = RegisterFrame.getContainerPanel();
    static JButton btnNextRF = RegisterFrame.getNextButton();
    static JButton btnBackRF = RegisterFrame.getBackButton();
    static int pageCounterRF = 0;
    //RegisterPanel1 - RP1
    static JPanel pnlRegisterPage1 = new RegisterPage1Panel();
    static JTextField tfNameInRP1 = RegisterPage1Panel.getTfName();
    static JButton btnDateRP1 = RegisterPage1Panel.getBtnDate();
    static JLabel lblAgeRP1 = RegisterPage1Panel.getLblAge();
    static JTextField tfHeightRP1 = RegisterPage1Panel.getTfHeight();
    static JTextField tfWeightRP1 = RegisterPage1Panel.getTfWeight();    
    static JLabel lblBMIRp1 = RegisterPage1Panel.getLblBMI();
    //MonthChooser - MC
    static String dob = "01011970";
    static JFrame frmMonthChooser = new MonthChooserFrame();
    static JTextField tfDayMC = MonthChooserFrame.getDayTextField();
    static JComboBox cbMonthsMC = MonthChooserFrame.getMonthComboBox();
    static JTextField tfYearMC = MonthChooserFrame.getYearTextField();
    
    //RegisterPanel2 - RP2
    static JPanel pnlRegisterPage2 = new RegisterPage2Panel();
}
