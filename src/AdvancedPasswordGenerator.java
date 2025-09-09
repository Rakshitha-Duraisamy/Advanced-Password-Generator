import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class AdvancedPasswordGenerator {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Advanced Password Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(0, 1, 5, 5));

        
        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Password Length:");
        JTextField lengthField = new JTextField("12", 5);

        
        JCheckBox includeUpper = new JCheckBox("Uppercase A-Z", true);
        JCheckBox includeLower = new JCheckBox("Lowercase a-z", true);
        JCheckBox includeNumbers = new JCheckBox("Numbers 0-9", true);
        JCheckBox includeSymbols = new JCheckBox("Symbols !@#$%", true);

        
        JButton generateBtn = new JButton("Generate Password");

        
        JPasswordField output = new JPasswordField(20);
        output.setEditable(false);

        
        JCheckBox showPassword = new JCheckBox("Show Password");

   
        JButton copyBtn = new JButton("Copy to Clipboard");

        
        JLabel strengthLabel = new JLabel("Strength: -");

        
        topPanel.add(label);
        topPanel.add(lengthField);
        frame.add(topPanel);
        frame.add(includeUpper);
        frame.add(includeLower);
        frame.add(includeNumbers);
        frame.add(includeSymbols);
        frame.add(generateBtn);
        frame.add(output);
        frame.add(showPassword);
        frame.add(copyBtn);
        frame.add(strengthLabel);

        
        generateBtn.addActionListener(e -> {
            int length;
            try {
                length = Integer.parseInt(lengthField.getText());
                if (length < 4) {
                    JOptionPane.showMessageDialog(frame, "Length must be at least 4");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter a valid number");
                return;
            }

            String password = generatePassword(length,
                    includeUpper.isSelected(),
                    includeLower.isSelected(),
                    includeNumbers.isSelected(),
                    includeSymbols.isSelected());

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Select at least one option!");
                return;
            }

            output.setText(password);
            strengthLabel.setText("Strength: " + checkStrength(password));
        });

        // Show/Hide password
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                output.setEchoChar((char) 0); // show
            } else {
                output.setEchoChar('•'); // hide
            }
        });

        // Copy to clipboard
        copyBtn.addActionListener(e -> {
            StringSelection selection = new StringSelection(new String(output.getPassword()));
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(frame, "Password copied to clipboard!");
        });

        
        frame.setVisible(true);
    }

     choices
    public static String generatePassword(int length, boolean upper, boolean lower, boolean numbers, boolean symbols) {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numChars = "0123456789";
        String symChars = "!@#$%^&*()_+";
        StringBuilder pool = new StringBuilder();

        if (upper) pool.append(upperChars);
        if (lower) pool.append(lowerChars);
        if (numbers) pool.append(numChars);
        if (symbols) pool.append(symChars);

        if (pool.length() == 0) return "";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(pool.charAt(random.nextInt(pool.length())));
        }
        return sb.toString();
    }

    
    public static String checkStrength(String password) {
        int score = 0;
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;
        if (password.matches(".[A-Z].")) score++;
        if (password.matches(".[a-z].")) score++;
        if (password.matches(".[0-9].")) score++;
        if (password.matches(".[!@#$%^&()_+].*")) score++;

        if (score <= 2) return "Weak ❌";
        else if (score <= 4) return "Medium ⚠";
        else return "Strong ✅";
    }
}