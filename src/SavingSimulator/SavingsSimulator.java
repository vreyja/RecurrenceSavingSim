package SavingSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class SavingsSimulator extends JFrame implements ActionListener {
    private JTextField initialDepositField;
    private JComboBox<String> yearsDropdown;
    private JComboBox<String> monthsDropdown;
    private JTextArea resultArea;
    private JButton saveInitialDepositButton;
    private JButton saveTimePeriodButton;
    private JButton calculateButton;
    private JButton resetButton;
    private double initialDeposit;
    private int totalMonths;

    public SavingsSimulator() {
        setTitle("Simulasi Tabungan");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Initial Deposit Input
        JLabel initialDepositLabel = new JLabel("Tabungan Mula-Mula (min 500.000):");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(initialDepositLabel, constraints);

        initialDepositField = new JTextField(20);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(initialDepositField, constraints);

        saveInitialDepositButton = new JButton("Simpan Tabungan Mula-Mula");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(saveInitialDepositButton, constraints);
        saveInitialDepositButton.addActionListener(this);

        // Time Period Input
        JLabel timePeriodLabel = new JLabel("Pilih Jangka Waktu:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        add(timePeriodLabel, constraints);

        yearsDropdown = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"});
        monthsDropdown = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        JPanel timePanel = new JPanel(new FlowLayout());
        timePanel.add(yearsDropdown);
        timePanel.add(new JLabel("Tahun"));
        timePanel.add(monthsDropdown);
        timePanel.add(new JLabel("Bulan"));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(timePanel, constraints);

        saveTimePeriodButton = new JButton("Simpan Jangka Waktu");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        add(saveTimePeriodButton, constraints);
        saveTimePeriodButton.addActionListener(this);

        // Calculate Button
        calculateButton = new JButton("Hitung Tabungan");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        add(calculateButton, constraints);
        calculateButton.addActionListener(this);

        // Reset Button
        resetButton = new JButton("Reset");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        add(resetButton, constraints);
        resetButton.addActionListener(this);

        // Result Area
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        add(scrollPane, constraints);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Simpan Tabungan Mula-Mula")) {
            saveInitialDeposit();
        } else if (command.equals("Simpan Jangka Waktu")) {
            saveTimePeriod();
        } else if (command.equals("Hitung Tabungan")) {
            calculateSavings();
        } else if (command.equals("Reset")) {
            resetFields();
        }
    }

    private void saveInitialDeposit() {
        try {
            initialDeposit = Double.parseDouble(initialDepositField.getText());
            if (initialDeposit < 500000) {
                JOptionPane.showMessageDialog(this, "Tabungan mula-mula harus minimal 500.000!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Tabungan mula-mula disimpan: " + formatCurrency(initialDeposit));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTimePeriod() {
        int years = Integer.parseInt((String) yearsDropdown.getSelectedItem());
        int months = Integer.parseInt((String) monthsDropdown.getSelectedItem());
        totalMonths = (years * 12) + months;
        JOptionPane.showMessageDialog(this, "Jangka waktu disimpan: " + years + " tahun " + months + " bulan (" + totalMonths + " bulan)");
    }

    private void calculateSavings() {
        if (initialDeposit < 500000) {
            JOptionPane.showMessageDialog(this, "Tabungan mula-mula harus minimal 500.000!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalSavings = initialDeposit;
        double initialSavings = initialDeposit; // Variable to determine interest rate condition
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Perhitungan Tabungan:\n");
        for (int i = 1; i <= totalMonths; i++) {
            double interestRate = (initialSavings <= 1000000) ? 0.005 : 0.01;
            double interest = totalSavings * interestRate;
            totalSavings += interest;
            totalSavings += initialDeposit; // Add initial deposit each month
            resultBuilder.append(String.format("Bulan %d: %s\n", i, formatCurrency(totalSavings)));
        }
        resultArea.setText(resultBuilder.toString());
    }

    private void resetFields() {
        initialDepositField.setText("");
        yearsDropdown.setSelectedIndex(0);
        monthsDropdown.setSelectedIndex(0);
        resultArea.setText("");
        initialDeposit = 0;
        totalMonths = 0;
    }

    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return "Rp " + formatter.format(amount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SavingsSimulator::new);
    }
}
