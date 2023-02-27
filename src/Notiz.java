import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Font.BOLD;

public class Notiz extends JFrame implements ActionListener
{
    static Font notizLargeFont = new Font("Ink Free", BOLD, 80);
    static Font notizMediumFont = new Font("Ink Free", BOLD, 20);
    static Font notizMedSmalFont = new Font("Ink Free", BOLD, 15);
    static Font notizSmallFont = new Font("Ink Free", BOLD, 12);
    static JFrame notizFrame;
    JButton notizClearBtn, notizTxSmallBtn, notizTxLargeBtn;
    JLabel notizTxSizeLab, notizErrorLab;
    JTextArea notizTxArea;
    public static void setNotizVisOn()
    {
        notizFrame.setVisible(true);
    }
    public void notiz()
    {

        notizFrame = new JFrame("Memo");
        notizFrame.setDefaultCloseOperation(notizFrame.HIDE_ON_CLOSE);
        notizFrame.setSize(420, 600);
        notizFrame.setLocationRelativeTo(null);
        notizFrame.setResizable(false);
        notizFrame.setLayout(null);

        notizClearBtn = new JButton("Delete");
        notizClearBtn.setBounds(10,10, 100, 40);
        notizClearBtn.setFont(notizSmallFont);
        notizClearBtn.setVisible(true);
        notizClearBtn.addActionListener(this);

        notizTxSizeLab = new JLabel("Text Size");
        notizTxSizeLab.setBounds(120, 5, 100, 50);
        notizTxSizeLab.setFont(notizSmallFont);

        notizTxSmallBtn = new JButton("<<");
        notizTxSmallBtn.setBounds(220,10, 50, 40);
        notizTxSmallBtn.setFont(notizSmallFont);
        notizTxSmallBtn.setVisible(true);
        notizTxSmallBtn.addActionListener(this);

        notizTxLargeBtn = new JButton(">>");
        notizTxLargeBtn.setBounds(280,10, 50, 40);
        notizTxLargeBtn.setFont(notizSmallFont);
        notizTxLargeBtn.setVisible(true);
        notizTxLargeBtn.addActionListener(this);

        notizTxArea = new JTextArea();
        notizTxArea.setBounds(10,60,385, 400);
        notizTxArea.setFont(notizMediumFont);
        notizTxArea.setWrapStyleWord(true);
        notizTxArea.setLineWrap(true);
        notizTxArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        notizTxArea.setVisible(true);

        notizErrorLab = new JLabel("ERROR:  none");
        notizErrorLab.setBounds(10, 460, 300, 50);
        notizErrorLab.setFont(notizMedSmalFont);

        notizFrame.add(notizClearBtn);
        notizFrame.add(notizTxSizeLab);
        notizFrame.add(notizTxSmallBtn);
        notizFrame.add(notizTxLargeBtn);
        notizFrame.add(notizTxArea);
        notizFrame.add(notizErrorLab);

        notizFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if ( e.getSource() == notizClearBtn)
        {
            notizTxArea.setText("");
        }
        if ( e.getSource() == notizTxSmallBtn)
        {
            if ( notizTxArea.getFont() == notizSmallFont)
            {
                notizTxSmallBtn.setEnabled(false);
                notizTxLargeBtn.setEnabled(true);
                notizErrorLab.setText("ERROR:  lowest Size (Schwähmer) is reached");
            }
            else if ( notizTxArea.getFont() == notizMediumFont)
            {
                notizTxArea.setFont(notizSmallFont);
                notizTxSmallBtn.setEnabled(true);
                notizTxLargeBtn.setEnabled(true);
                notizErrorLab.setText("ERROR:  none");
            }
            else if ( notizTxArea.getFont() == notizLargeFont)
            {
                notizTxArea.setFont(notizMediumFont);
                notizTxSmallBtn.setEnabled(true);
                notizTxLargeBtn.setEnabled(true);
                notizErrorLab.setText("ERROR:  none");
            }
        }
        if ( e.getSource() == notizTxLargeBtn)
        {
            if ( notizTxArea.getFont() == notizSmallFont)
            {
                notizTxArea.setFont(notizMediumFont);
                notizTxLargeBtn.setEnabled(true);
                notizTxSmallBtn.setEnabled(true);
                notizErrorLab.setText("ERROR:  none");
            }
            else if ( notizTxArea.getFont() == notizMediumFont)
            {
                notizTxArea.setFont(notizLargeFont);
                notizTxLargeBtn.setEnabled(true);
                notizTxSmallBtn.setEnabled(true);
                notizErrorLab.setText("ERROR:  none");
            }
            else if ( notizTxArea.getFont() == notizLargeFont)
            {
                notizTxLargeBtn.setEnabled(false);
                notizTxSmallBtn.setEnabled(true);
                notizErrorLab.setText("ERROR:  highest Size (Hanel) is reached");
            }
        }
    }
}