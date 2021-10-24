package com.labs.caesar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;

public class CaesarFrame extends JFrame {
    int lastFocus =-1;
    JPanel topRow;
    JPanel bottomRow;
    JTextField inputField = new JTextField(20);
    JTextField outputField = new JTextField(20);;
    JLabel outputLabel = new JLabel("Output:");
    JButton codeButton = new JButton("Code!");
    JComboBox jComboBox = new JComboBox<Character>();;
    public CaesarFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 110);
        setTitle("SwingLab");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        topRow = new JPanel();
        topRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < 26 ; i++){
            jComboBox.addItem((char) ('A' + i));
        }
        codeButton.addActionListener(new OkButtonActionListener());
        inputField.addActionListener(new OkButtonActionListener());
        inputField.getDocument().addDocumentListener(new OnChangedListener(0));
        inputField.addFocusListener(new TextFieldFocusListener(0));
        topRow.add(jComboBox);
        topRow.add(inputField);
        topRow.add(codeButton);
        add(topRow);




        bottomRow = new JPanel();
        bottomRow.setLayout(new FlowLayout(FlowLayout.LEFT));
//        outputField.setEditable(false);
        outputField.addFocusListener(new TextFieldFocusListener(1));
        bottomRow.add(outputLabel);
        bottomRow.add(outputField);
        add(bottomRow);
        setResizable(false);
    }

    private String caesarEncode(String input, char offset){
        int actualOffset = offset-'A';
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : input.toUpperCase(Locale.ROOT).toCharArray()) {
            char decodedChar = (char) ((character - 'A' + actualOffset) % 26 + 'A');
//            System.out.println("original: " + character + ", decoded:" + decodedChar);
            stringBuilder.append(decodedChar);
        }
        return stringBuilder.toString();
    }

    private String caesarDecode(String input, char offset){
        int actualOffset = offset-'A';
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : input.toUpperCase(Locale.ROOT).toCharArray()) {
            int tmp = (character - 'A' - actualOffset) % 26;
            tmp = tmp >= 0 ? tmp : tmp + 26;
            char decodedChar = (char) ( tmp + 'A');
////            System.out.println("original: " + character + ", decoded:" + decodedChar);
            stringBuilder.append(decodedChar);
        }
        return stringBuilder.toString();
    }


    class OnChangedListener implements DocumentListener{
        int focusId;

        public OnChangedListener(int focusId) {
            this.focusId = focusId;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (focusId == lastFocus) {
                setText();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (focusId == lastFocus) {
                setText();
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (focusId == lastFocus) {
                setText();
            }
        }
    }

    class OkButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setText();
        }
    }

    class TextFieldFocusListener implements FocusListener{
        private int focusId;

        public TextFieldFocusListener(int focusId) {
            this.focusId = focusId;
        }

        @Override
        public void focusGained(FocusEvent e) {
            lastFocus = focusId;
        }

        @Override
        public void focusLost(FocusEvent e) {

        }
    }

    private void setText(){
        switch (lastFocus) {
            case 0:
                outputField.setText(caesarEncode(inputField.getText(), (char) jComboBox.getSelectedItem()));
                break;
            case 1:
                inputField.setText(caesarDecode(outputField.getText(), (char) jComboBox.getSelectedItem()));
                break;
            default:
                System.out.println("Last focus is unknown");
                break;
        }
    }
}
