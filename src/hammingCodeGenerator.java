import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Integer.parseInt;

public class hammingCodeGenerator {
    private JButton generateButton;
    private JPanel mainPanel;
    private JTextField inputBox;
    private JTextArea outputField;
    private JRadioButton evenParity;

    //boolean value to check even parity or odd parity should be used
    private boolean evenParityEncode = false;

    public hammingCodeGenerator() {
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //read user input from input box
                String userInput = inputBox.getText();
                //convert user input to binary
                String binaryUserInput = Integer.toBinaryString(parseInt(userInput));
                //store user input to an int instead of String
                int intUserInput = parseInt(binaryUserInput);

                //calculate number of parity bits required
                int parityBitRequired = 0;
                int lengthOfCode = Integer.valueOf(intUserInput).toString().length();
                for (int i = 0; i < lengthOfCode; i++) {
                    if (Math.pow(2, i) >= i + lengthOfCode + 1) {
                        parityBitRequired = i;
                        break;
                    }
                }
                outputField.setText("Number of parity bits required: " + String.valueOf(parityBitRequired));

                //create a arraylist to store binary code need to be encoded
                ArrayList<Integer> binaryCodeToEncode = new ArrayList<>();
                int temp = intUserInput;
                do {
                    binaryCodeToEncode.add(temp % 10);
                    temp /= 10;
                } while (temp > 0);

                // reverse the first time for user to read in binary
                Collections.reverse(binaryCodeToEncode);
                outputField.append("\nDecimal input converted to Binary: " + String.valueOf(binaryCodeToEncode));
                //reverse it again as bit position 1 in hamming is the most right hand side
                Collections.reverse(binaryCodeToEncode);

                int newCodeLength = lengthOfCode + parityBitRequired;
                ArrayList<Integer> encodingCode = new ArrayList<>();
                int parityBitPosition = 0;
                int dataBitPosition = 0;
                //create a new arraylist that include parity bit. For now parity bits are all 0
                for (int i = 0; i < newCodeLength; i++) {
                    if (i + 1 == (int) Math.pow(2, parityBitPosition)) {
                        encodingCode.add(0);
                        parityBitPosition++;
                    } else {
                        encodingCode.add(binaryCodeToEncode.get(dataBitPosition));
                        dataBitPosition++;
                    }
                }
                Collections.reverse(encodingCode);
                outputField.append("\nCode before hamming code: " + encodingCode);
                Collections.reverse(encodingCode);

                //Create an arraylist of an arraylist to store different parity bit
                ArrayList<ArrayList<Integer>> wholeHamming = new ArrayList<ArrayList<Integer>>();

                //running for loop to find what value should each parity bit hold
                parityBitPosition = 0;
                int powerNum = 1;
                for (int i = 0; i < encodingCode.size(); i++) {
                    if (i + 1 == (int) Math.pow(2, parityBitPosition)) {
                        int modNum = (int) Math.pow(10, powerNum);

                        //create an arraylist to store bits that covered by parity bit
                        ArrayList<Integer> bitCoveredByParityBit = new ArrayList<>();

                        //calculate which data bit is covered by parity bit
                        for (int j = 0; j < encodingCode.size(); j++) {
                            String binaryPosition = Integer.toBinaryString(j + 1);
                            int binaryPositionInt = parseInt(binaryPosition);
                            //calculate which bits are covered by parity bits
                            if ((binaryPositionInt % modNum) / (modNum / 10) == 1) {
                                bitCoveredByParityBit.add(encodingCode.get(j));
                            }
                        }

                        if (evenParityEncode) {
                            if (calculateParityBit(bitCoveredByParityBit, evenParityEncode)) {
                                wholeHamming.add(bitCoveredByParityBit);
                            } else {
                                bitCoveredByParityBit.set(0, 1);
                                wholeHamming.add(bitCoveredByParityBit);
                            }
                        } else {
                            if (calculateParityBit(bitCoveredByParityBit, evenParityEncode)) {
                                bitCoveredByParityBit.set(0, 1);
                                wholeHamming.add(bitCoveredByParityBit);
                            } else {
                                wholeHamming.add(bitCoveredByParityBit);
                            }
                        }
                        powerNum++;
                        parityBitPosition++;
                    }
                }

                //generate code after encode
                ArrayList<Integer> encodedCode = new ArrayList<>();
                parityBitPosition = 0;
                dataBitPosition = 0;
                //create a new arraylist that include parity bit. For now parity bits are all 0
                for (int i = 0; i < newCodeLength; i++) {
                    if (i + 1 == (int) Math.pow(2, parityBitPosition)) {
                        int parityValue = wholeHamming.get(parityBitPosition).get(0);
                        encodedCode.add(parityValue);
                        parityBitPosition++;
                    } else {
                        encodedCode.add(binaryCodeToEncode.get(dataBitPosition));
                        dataBitPosition++;
                    }
                }
                Collections.reverse(encodedCode);
                outputField.append("\nThe hamming code: " + encodedCode);
            }

            //method to calculate parity bits
            //if parity bit need to be set as 0, return true when even parity bit is selected
            //if parity bit need to be set as 1, return true when odd parity bit is selected
            public boolean calculateParityBit(ArrayList<Integer> list, boolean evenParity) {
                int numberOfOnes = 0;
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i) == 1) {
                        numberOfOnes++;
                    }
                }
                //if even Parity is selected
                if (evenParity) {
                    if (numberOfOnes % 2 == 0) {
                        System.out.println("Even Number of Ones! Parity bit would be 0!");
                        return true;
                    } else {
                        System.out.println("Odd Number of Ones! Parity bit would be 1!");
                        return false;
                    }
                }
                //if odd parity is selected
                else {
                    if (numberOfOnes % 2 == 0) {
                        System.out.println("Even Number of Ones! Parity bit would be 1!");
                        return true;
                    } else {
                        System.out.println("Odd Number of Ones! Parity bit would be 0!");
                        return false;
                    }
                }
            }
        });

        //method to change even or odd parity will be used
        evenParity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (evenParity.isSelected()) {
                    evenParityEncode = true;
                } else {
                    evenParityEncode = false;
                }
            }
        });
    }

    public static void main(String[] args) {
        //enable and run GUI
        JFrame frame = new JFrame("Hamming Code");
        frame.setContentPane(new hammingCodeGenerator().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}