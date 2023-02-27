import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static java.awt.Font.BOLD;

public class IhkMarkKey extends JFrame implements ActionListener
{
    static JFrame ihkFrame;
    JLabel maxLab;
    JTextField maxPoint;
    JLabel istLab;
    JTextField istPoint;
    JButton calculateBtn;
    JTextArea resultTxAr;
    Font mediumFont = new Font("Ink Free", BOLD, 16);
    public static void setIhkVisOn()
    {
        ihkFrame.setVisible(true);
    }
    public void ihkMarkKey()
    {
        ihkFrame = new JFrame();
        ihkFrame.setSize(320,600);
        ihkFrame.setLocationRelativeTo(null);
        ihkFrame.setDefaultCloseOperation(ihkFrame.HIDE_ON_CLOSE);
        ihkFrame.setResizable(false);
        ihkFrame.setLayout(null);

        maxLab = new JLabel("Eingabe Maximale Punktzahl");
        maxLab.setBounds(27,50,250,30);
        maxLab.setFont(mediumFont);
        maxLab.setHorizontalAlignment(SwingConstants.CENTER);
        maxLab.setVisible(true);

        maxPoint= new JTextField();
        maxPoint.setBounds(50,100,200,30);
        maxPoint.setFont(mediumFont);
        maxPoint.setToolTipText("Eingabe Maximale Punktzahl");
        maxPoint.setHorizontalAlignment(SwingConstants.CENTER);
        maxPoint.addActionListener(this);
        maxPoint.setVisible(true);

        istLab = new JLabel("Eingabe erreichte Punktzahl");
        istLab.setBounds(27,150,250,30);
        istLab.setFont(mediumFont);
        istLab.setHorizontalAlignment(SwingConstants.CENTER);
        istLab.setVisible(true);

        istPoint= new JTextField();
        istPoint.setBounds(50,200,200,30);
        istPoint.setFont(mediumFont);
        istPoint.setToolTipText("Eingabe erreichte Punktzahl");
        istPoint.setHorizontalAlignment(SwingConstants.CENTER);
        istPoint.addActionListener(this);
        istPoint.setVisible(true);

        calculateBtn = new JButton("Berechnen");
        calculateBtn.setBounds(50,300,200,30);
        calculateBtn.setFont(mediumFont);
        calculateBtn.setHorizontalAlignment(SwingConstants.CENTER);
        calculateBtn.addActionListener(this);
        calculateBtn.setVisible(true);

        resultTxAr = new JTextArea(10,10);
        resultTxAr.setBounds(50,350,200,170);
        resultTxAr.setFont(mediumFont);
        resultTxAr.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resultTxAr.setEditable(false);
        resultTxAr.setAlignmentX(SwingConstants.CENTER);
        resultTxAr.setVisible(true);

        ihkFrame.add(maxLab);
        ihkFrame.add(maxPoint);
        ihkFrame.add(istLab);
        ihkFrame.add(istPoint);
        ihkFrame.add(calculateBtn);
        ihkFrame.add(resultTxAr);

        ihkFrame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if ( e.getSource() == calculateBtn )
        {
            if (Objects.equals(maxPoint.getText(), "") )
            {
                JOptionPane.showMessageDialog(ihkFrame, "Textfeld leer");
            }
            else
            {
                if ( Objects.equals(istPoint.getText(), ""))
                {
                    JOptionPane.showMessageDialog(ihkFrame, "Textfeld leer");
                }
                else
                {
                    String maxPunkte = maxPoint.getText();
                    int max_Punkte = Integer.parseInt(maxPunkte);
                    String istPunkte = istPoint.getText();
                    int ist_Punkte = Integer.parseInt(istPunkte);
                    float prozentsatz = Math.round((ist_Punkte * 100 / max_Punkte));
                    String s1 = "\n       Sie haben nach\n";
                    String s2 = "       mathematischer-\n           berechnung\n";
                    String s3 = "       ";
                    String s4 = "% erreicht!\n";
                    String s5 = "        Das entspricht\n";
                    String s6 = "         der ";


                    if (prozentsatz <= 100 && prozentsatz >= 98)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 1.0");
                    }
                    else if (prozentsatz <= 97 && prozentsatz >= 92)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 1.4");
                    }
                    else if (prozentsatz <= 91 && prozentsatz >= 89)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 1.6");
                    }
                    else if (prozentsatz <= 88 && prozentsatz >= 84)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 2.0");
                    }
                    else if (prozentsatz <= 83 && prozentsatz >= 81)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 2.4");
                    }
                    else if (prozentsatz <= 80 && prozentsatz >= 79)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 2.6");
                    }
                    else if (prozentsatz <= 78 && prozentsatz >= 73)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 3.0");
                    }
                    else if (prozentsatz <= 72 && prozentsatz >= 67)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 3.4");
                    }
                    else if (prozentsatz <= 66 && prozentsatz >= 64)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 3.6");
                    }
                    else if (prozentsatz <= 63 && prozentsatz >= 57)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 4.0");
                    }
                    else if (prozentsatz <= 56 && prozentsatz >= 50)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 4.4");
                    }
                    else if (prozentsatz <= 49 && prozentsatz >= 46)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 4.6");
                    }
                    else if (prozentsatz <= 45 && prozentsatz >= 38)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 5.0");
                    }
                    else if (prozentsatz <= 37 && prozentsatz >= 30)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 5.4");
                    }
                    else if (prozentsatz <= 29 && prozentsatz >= 19)
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 5.6");
                    }
                    else
                    {
                        resultTxAr.setText(s1 + s2 + s3 + prozentsatz + s4 + s5 + s6 + "Note: 6.0");
                    }
                }
            }
        }
    }
}

