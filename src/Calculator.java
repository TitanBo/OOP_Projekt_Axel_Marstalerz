
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



public class Calculator implements ActionListener
{

    Font smallFont = new Font("Ink Free", 1, 16);
    Font largeFont = new Font("Ink Free", 1, 25);
    static JFrame calculatorFrame;
    JTextField calculatorTextField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[12];
    JButton addButton, subButton, mulButton, divButton, decButton, equButton, delButton, clrButton, negButton, binButton, oktButton, hexButton;
    JPanel calculatorPanel;
    double num1 = 0.0D;
    double num2 = 0.0D;
    double result = 0.0D;
    char operator;
    public static void setCalcVisOn()
    {
        calculatorFrame.setVisible(true);
    }
    public void calculator()
    {

        calculatorFrame = new JFrame("Rechner");
        calculatorFrame.setDefaultCloseOperation(calculatorFrame.HIDE_ON_CLOSE);
        calculatorFrame.setResizable(false);
        calculatorFrame.setSize(420, 600);
        calculatorFrame.setLayout(null);
        calculatorFrame.setLocationRelativeTo(null);

        calculatorTextField = new JTextField();
        calculatorTextField.setBounds(50, 40, 300, 50);
        calculatorTextField.setEditable(true);
        calculatorTextField.setFont(smallFont);
        calculatorTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        calculatorTextField.setForeground(Color.green);
        calculatorTextField.setBackground(Color.black);
        calculatorTextField.setCaretColor(Color.green);
        calculatorTextField.setFocusable(true);

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("C");
        clrButton = new JButton("CE");
        negButton = new JButton("(-)");
        binButton = new JButton("BIN");
        oktButton = new JButton("OKT");
        hexButton = new JButton("HEX");
        functionButtons[0] = this.addButton;
        functionButtons[1] = this.subButton;
        functionButtons[2] = this.mulButton;
        functionButtons[3] = this.divButton;
        functionButtons[4] = this.decButton;
        functionButtons[5] = this.equButton;
        functionButtons[6] = this.delButton;
        functionButtons[7] = this.clrButton;
        functionButtons[8] = this.negButton;
        functionButtons[9] = this.binButton;
        functionButtons[10] = this.oktButton;
        functionButtons[11] = this.hexButton;

        int i;
        for(i = 0; i < 12; ++i)
        {
            this.functionButtons[i].addActionListener(this);
            this.functionButtons[i].setFont(smallFont);
            this.functionButtons[i].setFocusable(false);
        }

        for(i = 0; i < 10; ++i)
        {
            this.numberButtons[i] = new JButton(String.valueOf(i));
            this.numberButtons[i].addActionListener(this);
            this.numberButtons[i].setFont(largeFont);
            this.numberButtons[i].setFocusable(false);
        }

        negButton.setBounds(50, 410, 90, 40);
        delButton.setBounds(155, 410, 90, 40);
        clrButton.setBounds(260, 410, 90, 40);
        binButton.setBounds(50, 460, 90, 40);
        oktButton.setBounds(155, 460, 90, 40);
        hexButton.setBounds(260, 460, 90, 40);

        calculatorPanel = new JPanel();

//  =====================    nur für die erkennbarkeit des Grids    ======================
        //this.CalculatorPanel.setBackground(Color.red);
//  ======================================================================================

        calculatorPanel.setBounds(50, 100, 300, 300);
        calculatorPanel.setLayout(new GridLayout(4, 4, 10, 10));
        calculatorPanel.add(this.numberButtons[1]);
        calculatorPanel.add(this.numberButtons[2]);
        calculatorPanel.add(this.numberButtons[3]);
        calculatorPanel.add(this.addButton);
        calculatorPanel.add(this.numberButtons[4]);
        calculatorPanel.add(this.numberButtons[5]);
        calculatorPanel.add(this.numberButtons[6]);
        calculatorPanel.add(this.subButton);
        calculatorPanel.add(this.numberButtons[7]);
        calculatorPanel.add(this.numberButtons[8]);
        calculatorPanel.add(this.numberButtons[9]);

        calculatorPanel.add(this.mulButton);
        calculatorPanel.add(this.decButton);
        calculatorPanel.add(this.numberButtons[0]);
        calculatorPanel.add(this.equButton);
        calculatorPanel.add(this.divButton);
        calculatorFrame.add(this.calculatorPanel);
        calculatorFrame.add(this.negButton);
        calculatorFrame.add(this.delButton);
        calculatorFrame.add(this.clrButton);
        calculatorFrame.add(this.binButton);
        calculatorFrame.add(this.oktButton);
        calculatorFrame.add(this.hexButton);
        calculatorFrame.add(this.calculatorTextField);

        calculatorFrame.setVisible(false);
    }


    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < 10; ++i)
        {
            if (e.getSource() == this.numberButtons[i])
            {
                this.calculatorTextField.setText(this.calculatorTextField.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == this.decButton)
        {
            this.calculatorTextField.setText(this.calculatorTextField.getText().concat("."));
        }

        if (e.getSource() == this.addButton)
        {
            this.num1 = Double.parseDouble(this.calculatorTextField.getText());
            this.operator = '+';
            this.calculatorTextField.setText("");
        }

        if (e.getSource() == this.subButton)
        {
            this.num1 = Double.parseDouble(this.calculatorTextField.getText());
            this.operator = '-';
            this.calculatorTextField.setText("");
        }

        if (e.getSource() == this.mulButton)
        {
            this.num1 = Double.parseDouble(this.calculatorTextField.getText());
            this.operator = '*';
            this.calculatorTextField.setText("");
        }

        if (e.getSource() == this.divButton)
        {
            this.num1 = Double.parseDouble(this.calculatorTextField.getText());
            this.operator = '/';
            this.calculatorTextField.setText("");
        }

        if (e.getSource() == this.equButton)
        {
            this.num2 = Double.parseDouble(this.calculatorTextField.getText());
            switch(this.operator) {
                case '*':
                    this.result = this.num1 * this.num2;
                    break;
                case '+':
                    this.result = this.num1 + this.num2;
                case ',':
                case '.':
                default:
                    break;
                case '-':
                    this.result = this.num1 - this.num2;
                    break;
                case '/':
                    this.result = this.num1 / this.num2;
            }

            this.calculatorTextField.setText(String.valueOf(this.result));
            this.num1 = this.result;
        }

        if (e.getSource() == this.clrButton) {
            this.calculatorTextField.setText("");
        }

        if (e.getSource() == this.delButton) {
            String delString = this.calculatorTextField.getText();
            this.calculatorTextField.setText("");

            for(int i = 0; i < delString.length() - 1; ++i) {
                JTextField var10000 = this.calculatorTextField;
                String var10001 = this.calculatorTextField.getText();
                var10000.setText(var10001 + delString.charAt(i));
            }
        }
        if (e.getSource() == this.negButton)
        {
            double temp1 = Double.parseDouble(this.calculatorTextField.getText());
            temp1 *= -1.0D;
            this.calculatorTextField.setText(String.valueOf(temp1));
        }
        if (e.getSource() == this.binButton)
        {
            Double temp1 = Double.parseDouble(this.calculatorTextField.getText());
            int temp2 = temp1.intValue();
            this.calculatorTextField.setText(Integer.toBinaryString(temp2));
        }

        if (e.getSource() == this.oktButton)
        {
            Double temp1 = Double.parseDouble(this.calculatorTextField.getText());
            int temp2 = temp1.intValue();
            this.calculatorTextField.setText(Integer.toOctalString(temp2));
        }

        if (e.getSource() == this.hexButton)
        {
            Double temp1 = Double.parseDouble(this.calculatorTextField.getText());
            int temp2 = temp1.intValue();
            this.calculatorTextField.setText(Integer.toHexString(temp2));
        }
    }
}