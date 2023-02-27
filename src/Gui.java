import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import static java.awt.Font.BOLD;

public class Gui extends JFrame
{
    JFileChooser fileSave;
    int priceMax_01 = 99999;
    int amountMax_01 = 100000;
    int priceMax_02 = 99;
    int amountMax_02 = 100;
    JMenuItem mILoad, mISave, mICalculator, mINotiz, mIihkKey;
    JMenuBar menuBar;
    JMenu data, tools;
    public static final Color VERY_OLD_BLUE = new Color(163, 184, 204);
    Font largeFont = new Font("Ink Free", BOLD, 25);
    Font mediumFont = new Font("Ink Free", BOLD, 15);
    Font smallFont = new Font("Ink Free", BOLD, 12);
    DataBank dataBank = new DataBank();
    int pointerDb = dataBank.fzg.length;
    JFrame mainFrame;
    JLabel topLabel;
    JPanel topPanel;
    JLabel labBtnOne;
    JButton backBtn;
    JPanel switchPanel;
    JButton actionBtn;
    JComboBox<String> attributOne;
    JComboBox<String> attributTwo;
    JComboBox<String> attributThree;
    JComboBox<String> attributFour;
    JComboBox<String> attributFive;
    JComboBox<String> attributSix_1;
    JLabel labComma, labEuro;
    JComboBox<String> attributSix_2;
    JButton deleteRowBtn;
    JPanel centerPanel;
    JTextArea mainTxArea;
    JButton[] rowBtns = new JButton[5];
    JPanel tabelPanel;
    int amountRow = 50;
    JToggleButton[] txFdRowBtns = new JToggleButton[amountRow];
    JTextField[] rowtxFdOne = new JTextField[amountRow];
    JTextField[] rowtxFdTwo = new JTextField[amountRow];
    JTextField[] rowtxFdThree = new JTextField[amountRow];
    JTextField[] rowtxFdFour = new JTextField[amountRow];
    JTextField[] rowtxFdFive = new JTextField[amountRow];
    JTextField[] rowtxFdSix = new JTextField[amountRow];
    JLabel errorLabel;
    String error = "Fehler:  ";
    String errStatusNon = "keine";
    String errStatusMissinField = "es sind nicht alle Attribute angegeben";
    String errStatusOneFieldOn = "wählen sie nur ein Attribut aus";
    String errStatusNoTypeOf = "Keine Attribute dieses Typs vorhanden";
    String errStatusDbEmpty = "Die Datenbank ist lehr";
    String errStatusLineEmpty = "Die Zeile ist lehr";
    String errStatusDbFull = "";
    JPanel errorPanel;
    boolean hmView = false;
    boolean addView = false;
    boolean setView = false;
    boolean deleteView = false;
    boolean searchView = false;
    boolean pkw = false;
    boolean lkw = false;
    boolean motorrad = false;
    boolean boot = false;
    String openerPath = "C:\\Users\\paxel\\Desktop\\Java\\Projekte\\OOP_Projekt_Axel_Marstalerz\\txt";



    PrintWriter saveOutput;
    String path;
    String dataName;
    String dataTyp;
    String dataTypTxt = "txt";
    String dataTypCsv = "csv";
    private void fileChooserLoad()
    {
        JFileChooser loadFileChooser = new JFileChooser();
        loadFileChooser.setDialogTitle("Laden");
        FileNameExtensionFilter filterCsv = new FileNameExtensionFilter("Csv", dataTypCsv);
        loadFileChooser.setAcceptAllFileFilterUsed(false);
        loadFileChooser.addChoosableFileFilter(filterCsv);
        loadFileChooser.setCurrentDirectory(new File(openerPath));
        int result = loadFileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION)
        {

            path = loadFileChooser.getCurrentDirectory().getAbsolutePath();
            dataName = loadFileChooser.getSelectedFile().getName();

            File selectedFile = loadFileChooser.getSelectedFile();
            errorLabel.setText("Datei geladen: " + selectedFile.getAbsolutePath());
            getDataTyp(path,dataName);
        }
    }
    private JFileChooser fileChooserSave()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Speichern");
        FileNameExtensionFilter filterTxt = new FileNameExtensionFilter("Txt [nur für Print]", dataTypTxt);
        FileNameExtensionFilter filterCsv = new FileNameExtensionFilter("Csv [für späteres laden]", dataTypCsv);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(filterTxt);
        fileChooser.addChoosableFileFilter(filterCsv);
        try
        {
            Path pathCheck = Paths.get(openerPath);
            Files.createDirectories(pathCheck);
        }
        catch (Exception e)
        {
            System.out.println("ERROR");
        }
        fileChooser.setCurrentDirectory(new File(openerPath));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showSaveDialog(mainFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION)
        {
            path = fileChooser.getCurrentDirectory().getAbsolutePath();
            dataName = fileChooser.getSelectedFile().getName();

            if (Objects.equals(fileChooser.getFileFilter().getDescription(), "Txt [nur für Print]"))
            {
                dataTyp = dataTypTxt;
            }
            if (Objects.equals(fileChooser.getFileFilter().getDescription(), "Csv [für späteres laden]"))
            {
                dataTyp = dataTypCsv;
            }
            errorLabel.setText("Datei gespeichert unter:  " + path + "   DateiName:  " + dataName + "   DateiTyp:  ." +  dataTyp);
            setDataTyp(path, dataName, dataTyp);
        }
        return fileChooser;
    }
    private void delDataBank()
    {
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            dataBank.fzg = new Vehicle[50];
        }
    }
    private void delDataBankPane()
    {
        if (!dbEmpty())
        {
            int result = JOptionPane.showConfirmDialog(null, "wenn sie eine Datei laden gehen die momentanen werte verloren", "Eintrag Löschen", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION)
            {
                delDataBank();
                fileChooserLoad();
            }
        }
        else
        {
            fileChooserLoad();
        }
    }

    private void getDataTyp(String path, String dataName)
    {
        String loadData = path  + "\\" + dataName;
        String line;
        int indexCounter = 0;
        delDataBank();
        parseInTable();
        try
        {
            BufferedReader buffRead = new BufferedReader(new FileReader(loadData));
            while ((line = buffRead.readLine()) != null)
            {
                String[] value = line.split(";");
                dataBank.setFzg(indexCounter, new Vehicle(value[1], value[2], value[3], value[4], value[5], value[6]));
                indexCounter++;
            }
            parseInTable();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void setDataTyp(String path, String dataName, String dataTyp)
    {
        int vehicle = 0;
        String[][] stringsArray = new String[dataBank.fzg.length][6];

        String saveData = path  + "\\" + dataName + "." + dataTyp;
        try
        {
            saveOutput = new PrintWriter(saveData);
        }
        catch (Exception Error)
        {
            System.out.println("Error");
        }
        for(int i = 0; i < dataBank.fzg.length; i++)
        {
            if(dataBank.getFzg(i) != null)
            {
                vehicle++;
            }
        }
        if (Objects.equals(dataTyp, "txt"))
        {
            for (int i = 0; i < vehicle; i++)
            {
                stringsArray[i][0] = dataBank.getFzg(i).getVihicleTyp();
                stringsArray[i][1] = dataBank.getFzg(i).getLabel();
                stringsArray[i][2] = dataBank.getFzg(i).getTyp();
                stringsArray[i][3] = dataBank.getFzg(i).getConstDate();
                stringsArray[i][4] = dataBank.getFzg(i).getColor();
                stringsArray[i][5] = dataBank.getFzg(i).getPrice();
            }
            for (int i = 0; i < vehicle; i++)
            {
                saveOutput.print("ID: [" + (i + 1) + "]  ");
                for (int j = 0; j < 6; j++)
                {
                    saveOutput.print(stringsArray[i][j] + " | ");
                    System.out.print(stringsArray[i][j] + " | ");
                    if (j == 5) {
                        saveOutput.print("\n");
                    }
                }
            }
        }
        if (Objects.equals(dataTyp, "csv"))
        {
            for (int i = 0; i < vehicle; i++)
            {
                stringsArray[i][0] = dataBank.getFzg(i).getVihicleTyp();
                stringsArray[i][1] = dataBank.getFzg(i).getLabel();
                stringsArray[i][2] = dataBank.getFzg(i).getTyp();
                stringsArray[i][3] = dataBank.getFzg(i).getConstDate();
                stringsArray[i][4] = dataBank.getFzg(i).getColor();
                stringsArray[i][5] = dataBank.getFzg(i).getPrice();
            }
            for (int i = 0; i < vehicle; i++)
            {
                saveOutput.print("ID: [" + (i + 1) + "];");
                for (int j = 0; j < 6; j++)
                {
                    saveOutput.print(stringsArray[i][j] + ";");
                    System.out.print(stringsArray[i][j] + " | ");
                    if (j == 5)
                    {
                        saveOutput.print("\n");
                    }
                }
            }
        }
        saveOutput.close();
    }
    //------------------------------------- create 5 buttons as row on a panel -----------------------------------------
    private JPanel panelRowBtn()
    {
        JPanel rowBtnPanel = new JPanel();
        rowBtnPanel.setLayout(new GridLayout(5, 1, 15, 15));
        rowBtnPanel.setBackground(Color.WHITE);
        for (int i = 0; i < 5; i++) {
            this.rowBtns[i] = new JButton(String.valueOf(i + 1));
            this.rowBtns[i].setFont(smallFont);
            this.rowBtns[i].setFocusable(false);
            this.rowBtns[i].addActionListener(new ActionListenerRowBtn());
            rowBtnPanel.add(rowBtns[i]);
        }
        return rowBtnPanel;
    }
    //------------------------------------- create a table -------------------------------------------------------------
        // 50xButton as row on a panel
    private JPanel panelTxFdRowBtn()
    {
        JPanel rowBtnPanel = new JPanel();
        rowBtnPanel.setLayout(new GridLayout(50, 1));
        rowBtnPanel.setBackground(Color.WHITE);
        for (int i = 0; i < 50; i++) {
            this.txFdRowBtns[i] = new JToggleButton(String.valueOf(i + 1));
            this.txFdRowBtns[i].setFont(smallFont);
            this.txFdRowBtns[i].setFocusable(false);
            this.txFdRowBtns[i].addActionListener(new ActionListenerTabelBtn());
            rowBtnPanel.add(txFdRowBtns[i]);
        }
        return rowBtnPanel;
    }
        // 6x50xTextFiel as row on a panel
    private JPanel panelTxFdOne() {
        JPanel txFdPanel = new JPanel();
        txFdPanel.setLayout(new GridLayout(50, 1));
        for (int i = 0; i < 50; i++) {
            this.rowtxFdOne[i] = new JTextField();
            this.rowtxFdOne[i].setFont(smallFont);
            this.rowtxFdOne[i].setFocusable(false);
            this.rowtxFdOne[i].setEditable(true);
            this.rowtxFdOne[i].addActionListener(new ActionListenerRowBtn());
            txFdPanel.add(rowtxFdOne[i]);
        }
        return txFdPanel;
    }
    public JPanel panelTxFdTwo() {
        JPanel txFdPanel = new JPanel();
        txFdPanel.setLayout(new GridLayout(50, 1));
        for (int i = 0; i < 50; i++) {
            this.rowtxFdTwo[i] = new JTextField();
            this.rowtxFdTwo[i].setFont(smallFont);
            this.rowtxFdTwo[i].setFocusable(false);
            this.rowtxFdTwo[i].setEditable(true);
            this.rowtxFdTwo[i].addActionListener(new ActionListenerRowBtn());
            txFdPanel.add(rowtxFdTwo[i]);
        }
        return txFdPanel;
    }
    public JPanel panelTxFdThree() {
        JPanel txFdPanel = new JPanel();
        txFdPanel.setLayout(new GridLayout(50, 1));
        for (int i = 0; i < 50; i++) {
            this.rowtxFdThree[i] = new JTextField();
            this.rowtxFdThree[i].setFont(smallFont);
            this.rowtxFdThree[i].setFocusable(false);
            this.rowtxFdThree[i].setEditable(true);
            this.rowtxFdThree[i].addActionListener(new ActionListenerRowBtn());
            txFdPanel.add(rowtxFdThree[i]);
        }
        return txFdPanel;
    }
    public JPanel panelTxFdFour() {
        JPanel txFdPanel = new JPanel();
        txFdPanel.setLayout(new GridLayout(50, 1));
        for (int i = 0; i < 50; i++) {
            this.rowtxFdFour[i] = new JTextField();
            this.rowtxFdFour[i].setFont(smallFont);
            this.rowtxFdFour[i].setFocusable(false);
            this.rowtxFdFour[i].setEditable(true);
            this.rowtxFdFour[i].addActionListener(new ActionListenerRowBtn());
            txFdPanel.add(rowtxFdFour[i]);
        }
        return txFdPanel;
    }
    public JPanel panelTxFdFive() {
        JPanel txFdPanel = new JPanel();
        txFdPanel.setLayout(new GridLayout(50, 1));
        for (int i = 0; i < 50; i++) {
            this.rowtxFdFive[i] = new JTextField();
            this.rowtxFdFive[i].setFont(smallFont);
            this.rowtxFdFive[i].setFocusable(false);
            this.rowtxFdFive[i].setEditable(true);
            this.rowtxFdFive[i].addActionListener(new ActionListenerRowBtn());
            txFdPanel.add(rowtxFdFive[i]);
        }
        return txFdPanel;
    }
    public JPanel panelTxFdSix() {
        JPanel txFdPanel = new JPanel();
        txFdPanel.setLayout(new GridLayout(50, 1));
        for (int i = 0; i < 50; i++) {
            this.rowtxFdSix[i] = new JTextField();
            this.rowtxFdSix[i].setFont(smallFont);
            this.rowtxFdSix[i].setFocusable(false);
            this.rowtxFdSix[i].setEditable(true);
            this.rowtxFdSix[i].setHorizontalAlignment(JLabel.RIGHT);
            this.rowtxFdSix[i].addActionListener(new ActionListenerRowBtn());
            txFdPanel.add(rowtxFdSix[i]);
        }
        return txFdPanel;
    }
        // combine them all
    public JPanel createPanelTabel() {
        JPanel tabelPanel = new JPanel();
        tabelPanel.setLayout(new GridLayout(1, 7));
        JPanel txFdPanelBtns = panelTxFdRowBtn();
        JPanel txFdPanelOne = panelTxFdOne();
        JPanel txFdPanelTwo = panelTxFdTwo();
        JPanel txFdPanelThree = panelTxFdThree();
        JPanel txFdPanelFour = panelTxFdFour();
        JPanel txFdPanelFive = panelTxFdFive();
        JPanel txFdPanelSix = panelTxFdSix();
        tabelPanel.add(txFdPanelBtns);
        tabelPanel.add(txFdPanelOne);
        tabelPanel.add(txFdPanelTwo);
        tabelPanel.add(txFdPanelThree);
        tabelPanel.add(txFdPanelFour);
        tabelPanel.add(txFdPanelFive);
        tabelPanel.add(txFdPanelSix);
        return tabelPanel;
    }
    int pointerTxFd = rowtxFdOne.length;
    //------------------------------------- create a one combobox fi multiple times ------------------------------------
    private JComboBox<String> jComboBox(String[] stringArray, String toolTip)
    {
        JComboBox<String> jcomboBox = new JComboBox<>(stringArray);
        jcomboBox.setFont(smallFont);
        jcomboBox.setToolTipText(toolTip);
        jcomboBox.setVisible(true);
        return jcomboBox;
    }
    //------------------------------------- ThE MaInFrAme^^ ------------------------------------------------------------
    public void mainFrame() {


        File file = new File("yourfileName");
        String path1 = file.getAbsolutePath();
        // set mainframe
        mainFrame = new JFrame("OOP_Projekt_Axel_Marstalerz");
        mainFrame.setSize(1000, 600);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);

        // create MenuBar
        menuBar = new JMenuBar();

        // create Menue's
        data = new JMenu("Data");
        data.setFont(smallFont);

        tools = new JMenu("Tool's");
        tools.setFont(smallFont);

        // create MenuItem's
        mILoad = new JMenuItem("Load");
        mILoad.setFont(smallFont);
        mILoad.addActionListener(new ActionListenerMenuBar());

        mISave = new JMenuItem("Save");
        mISave.setFont(smallFont);
        mISave.addActionListener(new ActionListenerMenuBar());

        mICalculator = new JMenuItem("Calculator");
        mICalculator.setFont(smallFont);
        mICalculator.addActionListener(new ActionListenerMenuBar());

        mINotiz = new JMenuItem("Memo");
        mINotiz.setFont(smallFont);
        mINotiz.addActionListener(new ActionListenerMenuBar());

        mIihkKey = new JMenuItem("Ihk-Key");
        mIihkKey.setFont(smallFont);
        mIihkKey.addActionListener(new ActionListenerMenuBar());

        // add MenuItem's to Menu's
        data.add(mILoad);
        data.add(mISave);
        tools.add(mINotiz);
        tools.add(mIihkKey);
        tools.add(mICalculator);

        // add Menu's to MenuBar
        menuBar.add(data);
        menuBar.add(tools);
        menuBar.setVisible(true);

        // set HeadLabel
        topLabel = new JLabel("OOP_Projekt_Axel_Marstalerz");
        topLabel.setBounds(300, 5, 400, 60);
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topLabel.setFont(largeFont);

        // set topPanel
        topPanel = new JPanel();
        topPanel.setBounds(20, 60, 940, 120);
        topPanel.setLayout(null);
        topPanel.setVisible(false);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // set/add subPanelOne + Label and Button to topPanel
        switchPanel = new JPanel();
        switchPanel.setBounds(10, 10, 920, 100);
        switchPanel.setLayout(null);
        switchPanel.setBackground(Color.WHITE);
        topPanel.add(switchPanel);

        // multiFunctionLabel
        labBtnOne = new JLabel();
        labBtnOne.setBounds(20, 1, 500, 30);
        labBtnOne.setFont(smallFont);
        labBtnOne.setText("Fahrzeug hinzufügen:  lege die Attribute fest, die du hinzufügen möchtest");
        labBtnOne.setHorizontalAlignment(SwingConstants.LEFT);
        labBtnOne.setVisible(true);
        switchPanel.add(labBtnOne);

        // multiFunctionButton
        actionBtn = new JButton();
        actionBtn.setBounds(750, 10, 160, 30);
        actionBtn.setText("Fahrzeug hinzufügen");
        actionBtn.setFont(smallFont);
        actionBtn.setVisible(true);
        actionBtn.addActionListener(new ActionListenerActionBtn());
        actionBtn.addActionListener(new ActionListenerTabelBtn());
        switchPanel.add(actionBtn);

        // set comboboxes out of nothing^^
        attributOne = jComboBox(new Layout().getVihiclTyp(), "Fahrzeug Typ");
        attributOne.setBounds(10, 60, 130, 30);
        attributOne.addItemListener(new ItemListenerBoxOne());
        attributOne.addActionListener(new ActionListenerActionBtn());
        switchPanel.add(attributOne);

        attributTwo = jComboBox(new Layout().getPkwLabel(), "Marke");
        attributTwo.setBounds(160, 60, 130, 30);
        attributTwo.addItemListener(new ItemListenerBoxTwo());
        attributTwo.addActionListener(new ActionListenerActionBtn());
        switchPanel.add(attributTwo);

        attributThree = jComboBox(new Layout().getMercedes_Benz(), "Typ");
        attributThree.setBounds(310, 60, 130, 30);
        attributThree.addItemListener(new ItemListenerBoxThree());
        attributThree.addActionListener(new ActionListenerActionBtn());
        switchPanel.add(attributThree);

        attributFour = jComboBox(new Layout().carConstDate(), "Baujahr");
        attributFour.setBounds(460, 60, 130, 30);
        attributFour.addItemListener(new ItemListenerBoxFour());
        attributFour.addActionListener(new ActionListenerActionBtn());
        switchPanel.add(attributFour);

        attributFive = jComboBox(new Layout().getVihicleColor(), "Farbe");
        attributFive.setBounds(610, 60, 130, 30);
        attributFive.addItemListener(new ItemListenerBoxFive());
        attributFive.addActionListener(new ActionListenerActionBtn());
        switchPanel.add(attributFive);

        attributSix_1 = jComboBox(new Layout().vclPreis(priceMax_01, amountMax_01), "Preis Vorkommastellen");
        attributSix_1.setBounds(750, 60, 70, 30);
        ((JLabel) attributSix_1.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
        attributSix_1.addItemListener(new ItemListenerBoxSix_1());
        attributSix_1.addActionListener(new ActionListenerActionBtn());
        switchPanel.add(attributSix_1);

        // a label again. what shell that be??? ","^^
        labComma = new JLabel(",");
        labComma.setBounds(828, 65, 30, 30);
        labComma.setFont(largeFont);
        labComma.setVisible(true);
        switchPanel.add(labComma);

        // and the last comboboxcreation
        attributSix_2 = jComboBox(new Layout().vclPreis(priceMax_02, amountMax_02), "Preis Nachkommastellen");
        attributSix_2.setBounds(840, 60, 50, 30);
        attributSix_2.addActionListener(new ActionListenerActionBtn());
        switchPanel.add(attributSix_2);

        // also with label^^  => "€" ...hm?
        labEuro = new JLabel("€");
        labEuro.setBounds(895, 60, 30, 30);
        labEuro.setFont(largeFont);
        labEuro.setVisible(true);
        switchPanel.add(labEuro);

        // --------------------------------- GUI deletePart End --------------------------------------------------------
        // jes two buttons again
        backBtn = new JButton("zurück");
        backBtn.setBounds(20, 190, 137, 30);
        backBtn.setFont(smallFont);
        backBtn.setVisible(false);
        backBtn.addActionListener(new ActionListenerBackBtn());

        deleteRowBtn = new JButton("markierte Zeile löschen");
        deleteRowBtn.setBounds(800, 190, 160, 30);
        deleteRowBtn.setFont(smallFont);
        deleteRowBtn.setVisible(false);
        deleteRowBtn.addActionListener(new ActionListenerTabelBtn());
        deleteRowBtn.addActionListener(new ActionListenerActionBtn());

        // and a mysterious Panel is appearing
        JPanel createPanelTabel = createPanelTabel();

        // and is getting set soon^^
        tabelPanel = new JPanel();
        tabelPanel.setBounds(0, 30, 940, 300);
        tabelPanel.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.GRAY));
        tabelPanel.setLayout(new BorderLayout());
        tabelPanel.setVisible(false);
        this.tabelPanel.add(BorderLayout.CENTER, new JScrollPane(createPanelTabel));

        // a second mysterious Panel is appearing. is it her to switch it with the first one???
        centerPanel = new JPanel();
        centerPanel.setBounds(20, 200, 940, 311);
        centerPanel.setLayout(null);
        centerPanel.add(BorderLayout.CENTER, this.tabelPanel);

        // some kind of MainTextField getting set???
        mainTxArea = new JTextArea();
        mainTxArea.setBounds(0, 0, 940, 300);
        mainTxArea.setFont(mediumFont);
        mainTxArea.setEditable(false);
        mainTxArea.setBorder(BorderFactory.createBevelBorder(1, Color.LIGHT_GRAY, Color.GRAY));
        mainTxArea.setText(new Layout().getMainMenue());
        mainTxArea.setVisible(true);
        centerPanel.add(mainTxArea);

        // last component for table => ...its cald rowButtonPanel???
        JPanel rowBtnPanel = panelRowBtn();
        rowBtnPanel.setBounds(30, 75, 45, 175);
        mainTxArea.add(rowBtnPanel);

        // ahhh. this must be the errorpart^^
        errorPanel = new JPanel();
        errorPanel.setBounds(0, 512, 990, 30);
        errorPanel.setLayout(new BorderLayout());
        errorPanel.setBorder(BorderFactory.createBevelBorder(1, Color.LIGHT_GRAY, Color.LIGHT_GRAY));

        errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(SwingConstants.LEFT);
        errorLabel.setText(error + errStatusNon);
        errorLabel.setFont(smallFont);
        errorPanel.add(errorLabel);

        // add and set each component to mainFrame I think so
        mainFrame.setJMenuBar(menuBar);

        mainFrame.add(topLabel);

        mainFrame.add(topPanel);
        mainFrame.add(backBtn);
        mainFrame.add(deleteRowBtn);
        mainFrame.add(centerPanel);
        mainFrame.add(errorPanel);
        mainFrame.setVisible(true);
    }

    private void setRowBtnOn()
    {
        for (int i = 0; i < txFdRowBtns.length; i++)
        {
            txFdRowBtns[i].setEnabled(true);
        }
    }
    private void setRowBtnOff() {
        for (int i = 0; i < txFdRowBtns.length; i++)
        {
            txFdRowBtns[i].setEnabled(false);
        }
    }
    private void allBtnsOff()
    {
        for (JToggleButton txFdRowBtn : txFdRowBtns)
        {
            txFdRowBtn.setSelected(false);
        }
    }
    // Five different kind of Views with Four smale helper Booleans to now what's on or not
    private void switchToMainView()
    {
        parseInTable();
        setRowBtnOff();

        hmView = true;
        addView = false;
        setView = false;
        searchView = false;
        deleteView = false;

        topPanel.setVisible(false);

        attributOne.setSelectedIndex(0);
        attributTwo.setSelectedIndex(0);
        attributThree.setSelectedIndex(0);
        attributFour.setSelectedIndex(0);
        attributFive.setSelectedIndex(0);
        attributSix_1.setSelectedIndex(0);
        attributSix_2.setSelectedIndex(0);

        attributOne.setVisible(false);
        attributTwo.setVisible(false);
        attributThree.setVisible(false);
        attributFour.setVisible(false);
        attributFive.setVisible(false);
        attributSix_1.setVisible(false);
        attributSix_2.setVisible(false);

        labComma.setVisible(false);
        labEuro.setVisible(false);

        allBtnsOff();

        backBtn.setVisible(false);
        deleteRowBtn.setVisible(false);
        tabelPanel.setVisible(false);
        mainTxArea.setVisible(true);

        actionBtn.setVisible(true);
        errorLabel.setText(error + errStatusNon);
    }
    private void switchToAddView()
    {
        parseInTable();
        setRowBtnOff();
        resetBgTxFd(whatIsToggled());

        hmView = false;
        addView = true;
        setView = false;
        searchView = false;
        deleteView = false;

        topPanel.setVisible(true);

        attributOne.setSelectedIndex(0);
        attributTwo.setSelectedIndex(0);
        attributThree.setSelectedIndex(0);
        attributFour.setSelectedIndex(0);
        attributFive.setSelectedIndex(0);
        attributSix_1.setSelectedIndex(0);
        attributSix_2.setSelectedIndex(0);

        attributOne.setVisible(true);
        attributTwo.setVisible(true);
        attributThree.setVisible(true);
        attributFour.setVisible(true);
        attributFive.setVisible(true);
        attributSix_1.setVisible(true);
        attributSix_2.setVisible(true);

        attributOne.setEnabled(true);
        attributTwo.setEnabled(false);
        attributThree.setEnabled(false);
        attributFour.setEnabled(false);
        attributFive.setEnabled(false);
        attributSix_1.setEnabled(false);
        attributSix_2.setEnabled(false);

        labComma.setVisible(true);
        labEuro.setVisible(true);

        allBtnsOff();

        backBtn.setVisible(true);
        deleteRowBtn.setVisible(false);
        tabelPanel.setVisible(true);
        mainTxArea.setVisible(false);

        labBtnOne.setText("Fahrzeug hinzufügen:  lege die Attribute fest, die du hinzufügen möchtest");
        actionBtn.setText("Fahrzeug hinzufügen");
    }
    private void switchToSetView()
    {
        parseInTable();
        setRowBtnOn();
        resetBgTxFd(whatIsToggled());

        hmView = false;
        addView = false;
        setView = true;
        searchView = false;
        deleteView = false;

        topPanel.setVisible(true);

        attributOne.setSelectedIndex(0);
        attributTwo.setSelectedIndex(0);
        attributThree.setSelectedIndex(0);
        attributFour.setSelectedIndex(0);
        attributFive.setSelectedIndex(0);
        attributSix_1.setSelectedIndex(0);
        attributSix_2.setSelectedIndex(0);

        attributOne.setVisible(true);
        attributTwo.setVisible(true);
        attributThree.setVisible(true);
        attributFour.setVisible(true);
        attributFive.setVisible(true);
        attributSix_1.setVisible(true);
        attributSix_2.setVisible(true);

        labComma.setVisible(true);
        labEuro.setVisible(true);

        allBtnsOff();

        backBtn.setVisible(true);
        deleteRowBtn.setVisible(false);
        tabelPanel.setVisible(true);
        mainTxArea.setVisible(false);

        labBtnOne.setText("Fahrzeug bearbeiten:  markiere die Zeile und lege die Attribute fest, die du bearbeiten möchtest");
        actionBtn.setText("Fahrzeug bearbeiten");
    }
    private void switchToSearchView()
    {
        parseInTable();
        setRowBtnOff();
        resetBgTxFd(whatIsToggled());

        hmView = false;
        addView = false;
        setView = false;
        searchView = true;
        deleteView = false;

        topPanel.setVisible(true);

        attributOne.setSelectedIndex(0);
        attributTwo.setSelectedIndex(0);
        attributThree.setSelectedIndex(0);
        attributFour.setSelectedIndex(0);
        attributFive.setSelectedIndex(0);
        attributSix_1.setSelectedIndex(0);
        attributSix_2.setSelectedIndex(0);

        attributOne.setVisible(true);
        attributTwo.setVisible(true);
        attributThree.setVisible(true);
        attributFour.setVisible(true);
        attributFive.setVisible(true);
        attributSix_1.setVisible(true);
        attributSix_2.setVisible(true);

        labComma.setVisible(true);
        labEuro.setVisible(true);

        allBtnsOff();

        attributOne.setEnabled(true);
        attributTwo.setEnabled(true);
        attributThree.setEnabled(false);
        attributFour.setEnabled(true);
        attributFive.setEnabled(true);
        attributSix_1.setEnabled(true);
        attributSix_2.setEnabled(true);

        backBtn.setVisible(true);
        deleteRowBtn.setVisible(true);
        mainTxArea.setVisible(false);
        tabelPanel.setVisible(true);

        labBtnOne.setText("Fahrzeug suchen:  wähle ein Attribut welches du suchen möchtest");

        actionBtn.setText("Fahrzeug suchen");
        actionBtn.setVisible(true);
        actionBtn.setEnabled(false);

        deleteRowBtn.setText("Tabelle zurücksetzen");
    }
    private void switchToDeleteView() {
        parseInTable();
        setRowBtnOn();
        resetBgTxFd(whatIsToggled());

        hmView = false;
        addView = false;
        setView = false;
        searchView = false;
        deleteView = true;

        topPanel.setVisible(true);

        attributOne.setSelectedIndex(0);
        attributTwo.setSelectedIndex(0);
        attributThree.setSelectedIndex(0);
        attributFour.setSelectedIndex(0);
        attributFive.setSelectedIndex(0);
        attributSix_1.setSelectedIndex(0);
        attributSix_2.setSelectedIndex(0);

        attributOne.setVisible(false);
        attributTwo.setVisible(false);
        attributThree.setVisible(false);
        attributFour.setVisible(false);
        attributFive.setVisible(false);
        attributSix_1.setVisible(false);
        attributSix_2.setVisible(false);

        labComma.setVisible(false);
        labEuro.setVisible(false);

        allBtnsOff();

        backBtn.setVisible(true);
        deleteRowBtn.setVisible(true);
        mainTxArea.setVisible(false);
        tabelPanel.setVisible(true);

        labBtnOne.setText("Fahrzeug entfernen:  markiere die Zeile die du löschen möchtest oder lösche Alles");

        actionBtn.setVisible(true);
        actionBtn.setEnabled(true);
        actionBtn.setText("Alles löschen");

        deleteRowBtn.setText("markierte Zeile löschen");
    }
    // consoloutput of db input
    private void testOutput() {
        System.out.println("--------------------------------------------------------------------------------------------");
        for (int i = 0; i < dataBank.fzg.length; i++) {
            if (dataBank.getFzg(i) != null) {
                System.out.print(dataBank.getFzg(i).getVihicleTyp() + "\t\t\t\t");
                System.out.print(dataBank.getFzg(i).getLabel() + "\t\t\t\t");
                System.out.print(dataBank.getFzg(i).getTyp() + "\t\t\t\t");
                System.out.print(dataBank.getFzg(i).getConstDate() + "\t\t\t\t");
                System.out.print(dataBank.getFzg(i).getColor() + "\t\t\t\t");
                System.out.print(dataBank.getFzg(i).getPrice() + "\n");
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }
    // set Tabel with db input
    private void parseInTable() {
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                this.rowtxFdOne[i].setText(dataBank.getFzg(i).getVihicleTyp());
                this.rowtxFdTwo[i].setText(dataBank.getFzg(i).getLabel());
                this.rowtxFdThree[i].setText(dataBank.getFzg(i).getTyp());
                this.rowtxFdFour[i].setText(dataBank.getFzg(i).getConstDate());
                this.rowtxFdFive[i].setText(dataBank.getFzg(i).getColor());
                this.rowtxFdSix[i].setText(dataBank.getFzg(i).getPrice());
            }
            else
            {
                this.rowtxFdOne[i].setText("");
                this.rowtxFdTwo[i].setText("");
                this.rowtxFdThree[i].setText("");
                this.rowtxFdFour[i].setText("");
                this.rowtxFdFive[i].setText("");
                this.rowtxFdSix[i].setText("");
            }
        }
    }
    // parse in db
    private void parseInDataBank()
    {
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) == null)
            {
                String vcl_vlcTyp = String.valueOf(attributOne.getSelectedItem());
                String vcl_Label = String.valueOf(attributTwo.getSelectedItem());
                String vcl_Typ = String.valueOf(attributThree.getSelectedItem());
                String vcl_ConstDate = String.valueOf(attributFour.getSelectedItem());
                String vcl_Color = String.valueOf(attributFive.getSelectedItem());

                String str_pricePartTwo = String.valueOf(attributSix_2.getSelectedItem());
                int int_pricePartTwo = Integer.parseInt(str_pricePartTwo);
                if ( int_pricePartTwo < 10)
                {
                    str_pricePartTwo = "0" + str_pricePartTwo;
                }
                else
                {
                    str_pricePartTwo = String.valueOf(attributSix_2.getSelectedItem());
                }
                String vcl_Price = (attributSix_1.getSelectedItem() + "," + str_pricePartTwo + "  €");
                dataBank.setFzg(i, new Vehicle(vcl_vlcTyp, vcl_Label, vcl_Typ, vcl_ConstDate, vcl_Color, vcl_Price));
                parseInTable();
                break;
            }
            if (i > 49)
            {
                JOptionPane.showMessageDialog(mainFrame, "Tabelle ist leider voll");
                errorLabel.setText(error + errStatusDbFull);
            }
        }
    }
    // set db new on specific column
    private void setDataBank(int index)
    {
        if (dataBank.getFzg(index) != null)
        {
            String vcl_vlcTyp = String.valueOf(attributOne.getSelectedItem());
            String vcl_Label = String.valueOf(attributTwo.getSelectedItem());
            String vcl_Typ = String.valueOf(attributThree.getSelectedItem());
            String vcl_ConstDate = String.valueOf(attributFour.getSelectedItem());
            String vcl_Color = String.valueOf(attributFive.getSelectedItem());
            String vcl_Price = (attributSix_1.getSelectedItem() + "," + attributSix_2.getSelectedItem() + "  €");
            dataBank.setFzg(index, new Vehicle(vcl_vlcTyp, vcl_Label, vcl_Typ, vcl_ConstDate, vcl_Color, vcl_Price));
            parseInTable();
        }
        else
        {
            errorLabel.setText(error + errStatusLineEmpty);
        }
    }
    // line up the db if needed
    private void lineUpDb(int index)
    {
        if (dataBank.getFzg(index) != null)
        {
            for (int i = index; i < pointerDb - 1; i++)
            {
                dataBank.fzg[i] = dataBank.fzg[i + 1];
            }
            dataBank.fzg[pointerDb - 1] = null;
            pointerDb--;
            parseInTable();
        }
    }

    // change background if column is selected
    private void markBgTxFd(int index) {
        rowtxFdOne[index].setBackground(VERY_OLD_BLUE);
        rowtxFdOne[index].setForeground(Color.WHITE);
        rowtxFdTwo[index].setBackground(VERY_OLD_BLUE);
        rowtxFdTwo[index].setForeground(Color.WHITE);
        rowtxFdThree[index].setBackground(VERY_OLD_BLUE);
        rowtxFdThree[index].setForeground(Color.WHITE);
        rowtxFdFour[index].setBackground(VERY_OLD_BLUE);
        rowtxFdFour[index].setForeground(Color.WHITE);
        rowtxFdFive[index].setBackground(VERY_OLD_BLUE);
        rowtxFdFive[index].setForeground(Color.WHITE);
        rowtxFdSix[index].setBackground(VERY_OLD_BLUE);
        rowtxFdSix[index].setForeground(Color.WHITE);
    }
    // set it back to normal
    private void resetBgTxFd(int index) {
        rowtxFdOne[index].setBackground(Color.WHITE);
        rowtxFdOne[index].setForeground(Color.BLACK);
        rowtxFdTwo[index].setBackground(Color.WHITE);
        rowtxFdTwo[index].setForeground(Color.BLACK);
        rowtxFdThree[index].setBackground(Color.WHITE);
        rowtxFdThree[index].setForeground(Color.BLACK);
        rowtxFdFour[index].setBackground(Color.WHITE);
        rowtxFdFour[index].setForeground(Color.BLACK);
        rowtxFdFive[index].setBackground(Color.WHITE);
        rowtxFdFive[index].setForeground(Color.BLACK);
        rowtxFdSix[index].setBackground(Color.WHITE);
        rowtxFdSix[index].setForeground(Color.BLACK);
    }
    // give back what is toggled as int
    private int whatIsToggled() {
        int indexToggleBtn = 0;
        for (int i = 0; i < txFdRowBtns.length; i++)
        {
            if (txFdRowBtns[i].isSelected())
            {
                indexToggleBtn = i;
                markBgTxFd(i);
            }
            else
            {
                resetBgTxFd(i);
            }
        }
        return indexToggleBtn;
    }
    // bool to check if db is empty
    private boolean dbEmpty()
    {
        boolean slh = true;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                slh = false;
            }
        }
        return slh;
    }
    // set table empty on specific index
    private void delTableIndexUp(int index) {
        for (int i = index; i < rowtxFdOne.length; i++)
        {
            this.rowtxFdOne[index].setText("");
            this.rowtxFdTwo[index].setText("");
            this.rowtxFdThree[index].setText("");
            this.rowtxFdFour[index].setText("");
            this.rowtxFdFive[index].setText("");
            this.rowtxFdSix[index].setText("");
        }
    }
    // set howl table empty
    private void delTable() {
        for (int i = 0; i < rowtxFdOne.length; i++)
        {
            this.rowtxFdOne[i].setText("");
            this.rowtxFdTwo[i].setText("");
            this.rowtxFdThree[i].setText("");
            this.rowtxFdFour[i].setText("");
            this.rowtxFdFive[i].setText("");
            this.rowtxFdSix[i].setText("");
        }
    }
    private boolean checkVehicleTypIfInDb()
    {
        boolean slh = false;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributOne.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getVihicleTyp(), select))
                {
                    slh = true;
                }
            }
        }
        return slh;
    }
    private boolean checkLabelIfInDb()
    {
        boolean slh = false;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributTwo.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getLabel(), select))
                {
                    slh = true;
                }
            }
        }
        return slh;
    }
    private boolean checkTypIfInDb()
    {
        boolean slh = false;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributThree.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getLabel(), select))
                {
                    slh = true;
                }
            }
        }
        return slh;
    }
    private boolean checkConstDataIfInDb()
    {
        boolean slh = false;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributFour.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getConstDate(), select))
                {
                    slh = true;
                }
            }
        }
        return slh;
    }
    private boolean checkColorIfInDb()
    {
        boolean slh = false;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributFive.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getColor(), select))
                {
                    slh = true;
                }
            }
        }
        return slh;
    }
    private boolean checkPriceIfInDb()
    {
        boolean slh = false;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            String str_pricePartTwo = String.valueOf(attributSix_2.getSelectedItem());
            int int_pricePartTwo = Integer.parseInt(str_pricePartTwo);
            if ( int_pricePartTwo < 10)
            {
                str_pricePartTwo = "0" + str_pricePartTwo;
            }
            else
            {
                str_pricePartTwo = String.valueOf(attributSix_2.getSelectedItem());
            }
            if (dataBank.getFzg(i) != null)
            {
                String select = (attributSix_1.getSelectedItem() + "," + str_pricePartTwo + "  €");
                if (Objects.equals(dataBank.getFzg(i).getPrice(), select))
                {
                    slh = true;
                }
            }
        }
        return slh;
    }
    private void searchVihicleTyp()
    {
        // check databankInput => user coise
        int j = 0;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributOne.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getVihicleTyp(), select))
                {
                    this.rowtxFdOne[j].setText(dataBank.getFzg(i).getVihicleTyp());
                    this.rowtxFdTwo[j].setText(dataBank.getFzg(i).getLabel());
                    this.rowtxFdThree[j].setText(dataBank.getFzg(i).getTyp());
                    this.rowtxFdFour[j].setText(dataBank.getFzg(i).getConstDate());
                    this.rowtxFdFive[j].setText(dataBank.getFzg(i).getColor());
                    this.rowtxFdSix[j].setText(dataBank.getFzg(i).getPrice());
                    j = j + 1;
                    errorLabel.setText(error + errStatusNon);
                }
                delTableIndexUp(j + 1);
            }
            delTableIndexUp(i + 1);
        }
    }
    private void searchLabel()
    {
        // check databankInput => user coise
        int j = 0;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributTwo.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getLabel(), select))
                {
                    this.rowtxFdOne[j].setText(dataBank.getFzg(i).getVihicleTyp());
                    this.rowtxFdTwo[j].setText(dataBank.getFzg(i).getLabel());
                    this.rowtxFdThree[j].setText(dataBank.getFzg(i).getTyp());
                    this.rowtxFdFour[j].setText(dataBank.getFzg(i).getConstDate());
                    this.rowtxFdFive[j].setText(dataBank.getFzg(i).getColor());
                    this.rowtxFdSix[j].setText(dataBank.getFzg(i).getPrice());
                    j = j + 1;
                    errorLabel.setText(error + errStatusNon);
                }
                delTableIndexUp(j + 1);
            }
            delTableIndexUp(i + 1);
        }
    }
    private void searchTyp()
    {
        // check databankInput => user coise
        int j = 0;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributThree.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getTyp(), select))
                {
                    this.rowtxFdOne[j].setText(dataBank.getFzg(i).getVihicleTyp());
                    this.rowtxFdTwo[j].setText(dataBank.getFzg(i).getLabel());
                    this.rowtxFdThree[j].setText(dataBank.getFzg(i).getTyp());
                    this.rowtxFdFour[j].setText(dataBank.getFzg(i).getConstDate());
                    this.rowtxFdFive[j].setText(dataBank.getFzg(i).getColor());
                    this.rowtxFdSix[j].setText(dataBank.getFzg(i).getPrice());
                    j = j + 1;
                    errorLabel.setText(error + errStatusNon);
                }
                delTableIndexUp(j + 1);
            }
            delTableIndexUp(i + 1);
        }
    }
    private void searchConstData()
    {
        // check databankInput => user coise
        int j = 0;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if(dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributFour.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getConstDate(), select))
                {
                    this.rowtxFdOne[j].setText(dataBank.getFzg(i).getVihicleTyp());
                    this.rowtxFdTwo[j].setText(dataBank.getFzg(i).getLabel());
                    this.rowtxFdThree[j].setText(dataBank.getFzg(i).getTyp());
                    this.rowtxFdFour[j].setText(dataBank.getFzg(i).getConstDate());
                    this.rowtxFdFive[j].setText(dataBank.getFzg(i).getColor());
                    this.rowtxFdSix[j].setText(dataBank.getFzg(i).getPrice());
                    j = j + 1;
                    errorLabel.setText(error + errStatusNon);
                }
                delTableIndexUp(j + 1);
            }
            delTableIndexUp(i + 1);
        }
    }
    private void searchColor()
    {
        int j = 0;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            if (dataBank.getFzg(i) != null)
            {
                String select = String.valueOf(attributFive.getSelectedItem());
                if (Objects.equals(dataBank.getFzg(i).getColor(), select))
                {
                    this.rowtxFdOne[j].setText(dataBank.getFzg(i).getVihicleTyp());
                    this.rowtxFdTwo[j].setText(dataBank.getFzg(i).getLabel());
                    this.rowtxFdThree[j].setText(dataBank.getFzg(i).getTyp());
                    this.rowtxFdFour[j].setText(dataBank.getFzg(i).getConstDate());
                    this.rowtxFdFive[j].setText(dataBank.getFzg(i).getColor());
                    this.rowtxFdSix[j].setText(dataBank.getFzg(i).getPrice());
                    j = j + 1;
                    errorLabel.setText(error + errStatusNon);
                }
                delTableIndexUp(j + 1);
            }
            delTableIndexUp(i + 1);
        }
    }
    private void searchPrice()
    {
        // check databankInput => user coise
        int j = 0;
        for (int i = 0; i < dataBank.fzg.length; i++)
        {
            String str_pricePartTwo = String.valueOf(attributSix_2.getSelectedItem());
            int int_pricePartTwo = Integer.parseInt(str_pricePartTwo);
            if ( int_pricePartTwo < 10)
            {
                str_pricePartTwo = "0" + str_pricePartTwo;
            }
            else
            {
                str_pricePartTwo = String.valueOf(attributSix_2.getSelectedItem());
            }
            if (dataBank.getFzg(i) != null)
            {
                String select = (attributSix_1.getSelectedItem() + "," + str_pricePartTwo + "  €");

                if (Objects.equals(dataBank.getFzg(i).getPrice(), select))
                {
                    this.rowtxFdOne[j].setText(dataBank.getFzg(i).getVihicleTyp());
                    this.rowtxFdTwo[j].setText(dataBank.getFzg(i).getLabel());
                    this.rowtxFdThree[j].setText(dataBank.getFzg(i).getTyp());
                    this.rowtxFdFour[j].setText(dataBank.getFzg(i).getConstDate());
                    this.rowtxFdFive[j].setText(dataBank.getFzg(i).getColor());
                    this.rowtxFdSix[j].setText(dataBank.getFzg(i).getPrice());
                    j = j + 1;
                    errorLabel.setText(error + errStatusNon);
                }
                delTableIndexUp(j + 1);
            }
            delTableIndexUp(i + 1);
        }
    }
    // Five bools to now what's selected
    private boolean boolVehicleTyp()
    {
        boolean slh;
        slh = attributOne.getSelectedItem() == "";
        return slh;
    }
    private boolean boolLabel()
    {
        boolean slh;
        slh = attributTwo.getSelectedItem() == "";
        return slh;
    }
    private boolean boolTyps()
    {
        boolean slh;
        slh = attributThree.getSelectedItem() == "";
        return slh;
    }
    private boolean boolConstData()
    {
        boolean slh;
        slh = attributFour.getSelectedItem() == "";
        return slh;
    }
    private boolean boolColor()
    {
        boolean slh;
        slh = attributFive.getSelectedItem() == "";
        return slh;
    }
    private boolean boolPrice_1()
    {
        boolean slh;
        slh = attributSix_1.getSelectedItem() == "";
        return slh;
    }
    private boolean boolPrice_2()
    {
        boolean slh;
        slh = attributSix_2.getSelectedItem() == "";
        return slh;
    }
    // Exit Pane
    private void quitPane()
    {
        int result = JOptionPane.showConfirmDialog(null, "Beenden?", "Beenden?", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
        {
            System.exit(0);
        }
    }
    // manage the menuBar
    private class ActionListenerMenuBar implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
        // MenuBar functions
            // action MenuItem Load @ MenueBar
            if (e.getSource() == mILoad)
            {
                delDataBankPane();
            }
            // action MenuItem Save @ MenueBar
            if (e.getSource() == mISave)
            {
                fileSave = fileChooserSave();
            }
            // action MenuItem Calculator @ MenueBar
            if (e.getSource() == mICalculator)
            {
                if (Calculator.calculatorFrame != null)
                {
                    Calculator.setCalcVisOn();
                }
                else
                {
                    Calculator calculator = new Calculator();
                    calculator.calculator();
                    Calculator.setCalcVisOn();
                }
            }
            // action MenuItem Notiz @ MenuBar
            if (e.getSource() == mINotiz)
            {
                if (Notiz.notizFrame != null)
                {
                    Notiz.setNotizVisOn();
                }
                else
                {
                    Notiz notiz = new Notiz();
                    notiz.notiz();
                    Notiz.setNotizVisOn();
                }
            }
            // action MenuItem IHK @ MenuBar
            if (e.getSource() == mIihkKey) {
                if (IhkMarkKey.ihkFrame != null)
                {
                    IhkMarkKey.setIhkVisOn();
                }
                else
                {
                    IhkMarkKey ihkMarkKey = new IhkMarkKey();
                    ihkMarkKey.ihkMarkKey();
                    IhkMarkKey.setIhkVisOn();
                }
            }
        }
    }
    // manage the Five RowButtons
    private class ActionListenerRowBtn implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // RowBtn functions
            if (e.getSource() == rowBtns[0])
            {
                switchToAddView();
            }
            if (e.getSource() == rowBtns[1])
            {
                switchToSetView();
            }
            if (e.getSource() == rowBtns[2])
            {
                switchToSearchView();
            }
            if (e.getSource() == rowBtns[3])
            {
                switchToDeleteView();
            }
            if (e.getSource() == rowBtns[4])
            {
                quitPane();
            }
        }
    }
    // manage the column buttons
    private class ActionListenerTabelBtn implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
        // delete Vehicle
            // toggle just on button On
            if (deleteView)
            {
                if (!dbEmpty())
                {
                    for (JToggleButton txFdRowBtn : txFdRowBtns)
                    {
                        if (e.getSource() == txFdRowBtn)
                        {
                            allBtnsOff();
                            txFdRowBtn.setSelected(true);
                        }
                    }
                    // happened if DataBank full
                    if (whatIsToggled() > 0 && whatIsToggled() < 51)
                    {
                        if (e.getSource() == deleteRowBtn && whatIsToggled() < 51)
                        {
                            lineUpDb(whatIsToggled());
                            resetBgTxFd(whatIsToggled());
                            allBtnsOff();
                        }
                    }
                    if (e.getSource() == actionBtn)
                    {
                        delDataBank();
                        parseInTable();
                    }
                }
                else
                {
                    errorLabel.setText(error + errStatusDbEmpty);
                }
            }
        }
    }
    // manage Combobox 1
    private class ItemListenerBoxOne implements java.awt.event.ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if (addView || setView)
            {
                if (attributOne.getSelectedItem() == "PKW")
                {
                    pkw = true;
                    lkw = false;
                    motorrad = false;
                    boot = false;
                    DefaultComboBoxModel<String> pkw = new DefaultComboBoxModel<>(new Layout().getPkwLabel());
                    attributTwo.setModel(pkw);
                    attributTwo.setEnabled(true);
                }
                if (attributOne.getSelectedItem() == "LKW")
                {
                    pkw = false;
                    lkw = true;
                    motorrad = false;
                    boot = false;
                    DefaultComboBoxModel<String> pkw = new DefaultComboBoxModel<>(new Layout().getLkwLabel());
                    attributTwo.setModel(pkw);
                    attributTwo.setEnabled(true);
                    errorLabel.setText(error + errStatusNon);
                }
                if (attributOne.getSelectedItem() == "Motorrad")
                {
                    pkw = false;
                    lkw = false;
                    motorrad = true;
                    boot = false;
                    DefaultComboBoxModel<String> pkw = new DefaultComboBoxModel<>(new Layout().getMotorradLabel());
                    attributTwo.setModel(pkw);
                    attributTwo.setEnabled(true);
                    errorLabel.setText(error + errStatusNon);
                }
                if (attributOne.getSelectedItem() == "Boot")
                {
                    pkw = false;
                    lkw = false;
                    motorrad = false;
                    boot = true;
                    DefaultComboBoxModel<String> boot = new DefaultComboBoxModel<>(new Layout().getBootLabel());
                    attributTwo.setModel(boot);
                    attributTwo.setEnabled(true);
                    errorLabel.setText(error + errStatusNon);
                }
                if (attributOne.getSelectedItem() == "")
                {
                    attributTwo.setEnabled(false);
                    errorLabel.setText(error + errStatusMissinField);
                }
            }
        }
    }
// manage Combobox 2
    private class ItemListenerBoxTwo implements java.awt.event.ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (addView || setView)
            {
                if (pkw)
                {
                    if (attributTwo.getSelectedItem() == "Mercedes-Benz") {
                        DefaultComboBoxModel<String> mercedesBenz = new DefaultComboBoxModel<>(new Layout().getMercedes_Benz());
                        attributThree.setModel(mercedesBenz);
                        attributThree.setSelectedItem(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Volkswagen") {
                        DefaultComboBoxModel<String> volkswagen = new DefaultComboBoxModel<>(new Layout().getVolkswagen());
                        attributThree.setModel(volkswagen);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "BMW") {
                        DefaultComboBoxModel<String> bmw = new DefaultComboBoxModel<>(new Layout().getBmw());
                        attributThree.setModel(bmw);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Opel") {
                        DefaultComboBoxModel<String> opel = new DefaultComboBoxModel<>(new Layout().getOpel());
                        attributThree.setModel(opel);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Audi") {
                        DefaultComboBoxModel<String> audi = new DefaultComboBoxModel<>(new Layout().getAudi());
                        attributThree.setModel(audi);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Ford") {
                        DefaultComboBoxModel<String> ford = new DefaultComboBoxModel<>(new Layout().getFord());
                        attributThree.setModel(ford);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Renault") {
                        DefaultComboBoxModel<String> renault = new DefaultComboBoxModel<>(new Layout().getRenault());
                        attributThree.setModel(renault);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Peugeot") {
                        DefaultComboBoxModel<String> peugeot = new DefaultComboBoxModel<>(new Layout().getPeugeot());
                        attributThree.setModel(peugeot);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Hyundai") {
                        DefaultComboBoxModel<String> hyundai = new DefaultComboBoxModel<>(new Layout().getHyundai());
                        attributThree.setModel(hyundai);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Toyota") {
                        DefaultComboBoxModel<String> toyota = new DefaultComboBoxModel<>(new Layout().getToyota());
                        attributThree.setModel(toyota);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Mazda") {
                        DefaultComboBoxModel<String> mazda = new DefaultComboBoxModel<>(new Layout().getMazda());
                        attributThree.setModel(mazda);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Citroën") {
                        DefaultComboBoxModel<String> citroen = new DefaultComboBoxModel<>(new Layout().getCitroen());
                        attributThree.setModel(citroen);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Skoda") {
                        DefaultComboBoxModel<String> skoda = new DefaultComboBoxModel<>(new Layout().getSkoda());
                        attributThree.setModel(skoda);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Nissan") {
                        DefaultComboBoxModel<String> nissa = new DefaultComboBoxModel<>(new Layout().getNissan());
                        attributThree.setModel(nissa);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Volvo") {
                        DefaultComboBoxModel<String> volvo = new DefaultComboBoxModel<>(new Layout().getVolvo());
                        attributThree.setModel(volvo);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Seat") {
                        DefaultComboBoxModel<String> seat = new DefaultComboBoxModel<>(new Layout().getSeat());
                        attributThree.setModel(seat);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Kia") {
                        DefaultComboBoxModel<String> kia = new DefaultComboBoxModel<>(new Layout().getKia());
                        attributThree.setModel(kia);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Fiat") {
                        DefaultComboBoxModel<String> fiat = new DefaultComboBoxModel<>(new Layout().getFiat());
                        attributThree.setModel(fiat);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Honda") {
                        DefaultComboBoxModel<String> honda = new DefaultComboBoxModel<>(new Layout().getHonda());
                        attributThree.setModel(honda);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Mitsubishi") {
                        DefaultComboBoxModel<String> mitsubishi = new DefaultComboBoxModel<>(new Layout().getMitsubishi());
                        attributThree.setModel(mitsubishi);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Suzuki") {
                        DefaultComboBoxModel<String> suzuki = new DefaultComboBoxModel<>(new Layout().getSuzuki());
                        attributThree.setModel(suzuki);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Dacia") {
                        DefaultComboBoxModel<String> dacia = new DefaultComboBoxModel<>(new Layout().getDacia());
                        attributThree.setModel(dacia);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Dacia") {

                        DefaultComboBoxModel<String> dacia = new DefaultComboBoxModel<>(new Layout().getDacia());
                        attributThree.setModel(dacia);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Chevrolet") {
                        DefaultComboBoxModel<String> chevrolet = new DefaultComboBoxModel<>(new Layout().getChevrolet());
                        attributThree.setModel(chevrolet);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Alfa Romeo") {
                        DefaultComboBoxModel<String> alfaRomeo = new DefaultComboBoxModel<>(new Layout().getAlfa_romeo());
                        attributThree.setModel(alfaRomeo);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Porsche") {
                        DefaultComboBoxModel<String> porsche = new DefaultComboBoxModel<>(new Layout().getPorsche());
                        attributThree.setModel(porsche);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Subaru") {
                        DefaultComboBoxModel<String> subaru = new DefaultComboBoxModel<>(new Layout().getSubaru());
                        attributThree.setModel(subaru);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jaguar") {
                        DefaultComboBoxModel<String> jaguar = new DefaultComboBoxModel<>(new Layout().getJaguar());
                        attributThree.setModel(jaguar);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jeep") {
                        DefaultComboBoxModel<String> jeep = new DefaultComboBoxModel<>(new Layout().getJeep());
                        attributThree.setModel(jeep);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Mini") {
                        DefaultComboBoxModel<String> mini = new DefaultComboBoxModel<>(new Layout().getMini());
                        attributThree.setModel(mini);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Land Rover")
                    {
                        DefaultComboBoxModel<String> landRover = new DefaultComboBoxModel<>(new Layout().getLand_rover());
                        attributThree.setModel(landRover);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Chrysler")
                    {
                        DefaultComboBoxModel<String> chrysler = new DefaultComboBoxModel<>(new Layout().getChrysler());
                        attributThree.setModel(chrysler);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Dodge")
                    {
                        DefaultComboBoxModel<String> dodge = new DefaultComboBoxModel<>(new Layout().getDodge());
                        attributThree.setModel(dodge);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Saab")
                    {
                        DefaultComboBoxModel<String> saab = new DefaultComboBoxModel<>(new Layout().getSaab());
                        attributThree.setModel(saab);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Ssangyong")
                    {
                        DefaultComboBoxModel<String> ssangyong = new DefaultComboBoxModel<>(new Layout().getSsangyong());
                        attributThree.setModel(ssangyong);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Lexus")
                    {
                        DefaultComboBoxModel<String> lexus = new DefaultComboBoxModel<>(new Layout().getLexus());
                        attributThree.setModel(lexus);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Lada")
                    {
                        DefaultComboBoxModel<String> lada = new DefaultComboBoxModel<>(new Layout().getLada());
                        attributThree.setModel(lada);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Cadillac")
                    {
                        DefaultComboBoxModel<String> cadillac = new DefaultComboBoxModel<>(new Layout().getCadillac());
                        attributThree.setModel(cadillac);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Maserati")
                    {
                        DefaultComboBoxModel<String> maserati = new DefaultComboBoxModel<>(new Layout().getMaserati());
                        attributThree.setModel(maserati);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Infiniti")
                    {
                        DefaultComboBoxModel<String> infiniti = new DefaultComboBoxModel<>(new Layout().getInfinity());
                        attributThree.setModel(infiniti);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Alpina")
                    {
                        DefaultComboBoxModel<String> alpina = new DefaultComboBoxModel<>(new Layout().getAlpina());
                        attributThree.setModel(alpina);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Lamborghini")
                    {
                        DefaultComboBoxModel<String> lamborghini = new DefaultComboBoxModel<>(new Layout().getLamborghini());
                        attributThree.setModel(lamborghini);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Aston Martin")
                    {
                        DefaultComboBoxModel<String> astonMartin = new DefaultComboBoxModel<>(new Layout().getAston_martin());
                        attributThree.setModel(astonMartin);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Bentley")
                    {
                        DefaultComboBoxModel<String> bentley = new DefaultComboBoxModel<>(new Layout().getBentley());
                        attributThree.setModel(bentley);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Rolls-Royce")
                    {
                        DefaultComboBoxModel<String> rollsRoyce = new DefaultComboBoxModel<>(new Layout().getRolls_royce());
                        attributThree.setModel(rollsRoyce);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Lotus")
                    {
                        DefaultComboBoxModel<String> lotus = new DefaultComboBoxModel<>(new Layout().getLotus());
                        attributThree.setModel(lotus);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "McLaren")
                    {
                        DefaultComboBoxModel<String> mcLaren = new DefaultComboBoxModel<>(new Layout().getMclaren());
                        attributThree.setModel(mcLaren);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Ferrari")
                    {
                        DefaultComboBoxModel<String> ferrari = new DefaultComboBoxModel<>(new Layout().getFerrari());
                        attributThree.setModel(ferrari);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Tesla")
                    {
                        DefaultComboBoxModel<String> tesla = new DefaultComboBoxModel<>(new Layout().getTesla());
                        attributThree.setModel(tesla);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (boolLabel())
                    {
                        attributThree.setEnabled(false);
                        errorLabel.setText(error + errStatusMissinField);
                    }
                }
                if(lkw)
                {
                    if (attributTwo.getSelectedItem() == "Mercedes-Benz")
                    {
                        DefaultComboBoxModel<String> mercedesBenz = new DefaultComboBoxModel<>(new Layout().getMercedisLkw());
                        attributThree.setModel(mercedesBenz);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Volkswagen")
                    {
                        DefaultComboBoxModel<String> volkswagen = new DefaultComboBoxModel<>(new Layout().getVolkswagenLkw());
                        attributThree.setModel(volkswagen);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Renault")
                    {
                        DefaultComboBoxModel<String> renault = new DefaultComboBoxModel<>(new Layout().getRenaultLkw());
                        attributThree.setModel(renault);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Volvo")
                    {
                        DefaultComboBoxModel<String> volvo = new DefaultComboBoxModel<>(new Layout().getVolvoLkw());
                        attributThree.setModel(volvo);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Scania")
                    {
                        DefaultComboBoxModel<String> scania = new DefaultComboBoxModel<>(new Layout().getScaniaLkw());
                        attributThree.setModel(scania);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Citroën")
                    {
                        DefaultComboBoxModel<String> citroen = new DefaultComboBoxModel<>(new Layout().getCitroenLkw());
                        attributThree.setModel(citroen);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Isutzu")
                    {
                        DefaultComboBoxModel<String> isutzu = new DefaultComboBoxModel<>(new Layout().getIsutzuLkw());
                        attributThree.setModel(isutzu);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "MAN")
                    {
                        DefaultComboBoxModel<String> man = new DefaultComboBoxModel<>(new Layout().getManLkw());
                        attributThree.setModel(man);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Tata")
                    {
                        DefaultComboBoxModel<String> tata = new DefaultComboBoxModel<>(new Layout().getTataLkw());
                        attributThree.setModel(tata);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Shaanxi")
                    {
                        DefaultComboBoxModel<String> shaanxi = new DefaultComboBoxModel<>(new Layout().getShaanxiLkw());
                        attributThree.setModel(shaanxi);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Paccar")
                    {
                        DefaultComboBoxModel<String> paccar = new DefaultComboBoxModel<>(new Layout().getPaccarLkw());
                        attributThree.setModel(paccar);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Dongfeng")
                    {
                        DefaultComboBoxModel<String> dongfeng = new DefaultComboBoxModel<>(new Layout().getDongfengLkw());
                        attributThree.setModel(dongfeng);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (boolLabel())
                    {
                        attributThree.setEnabled(false);
                        errorLabel.setText(error + errStatusMissinField);
                    }
                }
                if(motorrad)
                {
                    if (attributTwo.getSelectedItem() == "Adly")
                    {
                        DefaultComboBoxModel<String> adly = new DefaultComboBoxModel<>(new Layout().getAdlyMotorrad());
                        attributThree.setModel(adly);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Aeon Motors")
                    {
                        DefaultComboBoxModel<String> aeonMotors = new DefaultComboBoxModel<>(new Layout().getAeonMotorsMotorrad());
                        attributThree.setModel(aeonMotors);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Alisze")
                    {
                        DefaultComboBoxModel<String> alisze = new DefaultComboBoxModel<>(new Layout().getAliszeMotorrad());
                        attributThree.setModel(alisze);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "American IronHorse")
                    {
                        DefaultComboBoxModel<String> americanIronHorse = new DefaultComboBoxModel<>(new Layout().getAmericanIronHorseMotorrad());
                        attributThree.setModel(americanIronHorse);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Aprilia")
                    {
                        DefaultComboBoxModel<String> aprilia = new DefaultComboBoxModel<>(new Layout().getApriliaMotorrad());
                        attributThree.setModel(aprilia);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Askoll")
                    {
                        DefaultComboBoxModel<String> askoll = new DefaultComboBoxModel<>(new Layout().getAskollMotorrad());
                        attributThree.setModel(askoll);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Aspes")
                    {
                        DefaultComboBoxModel<String> aspes = new DefaultComboBoxModel<>(new Layout().getAspesMotorrad());
                        attributThree.setModel(aspes);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Bajaj")
                    {
                        DefaultComboBoxModel<String> bajaj = new DefaultComboBoxModel<>(new Layout().getBajajMotorrad());
                        attributThree.setModel(bajaj);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Benelli")
                    {
                        DefaultComboBoxModel<String> benelli = new DefaultComboBoxModel<>(new Layout().getBenelliMotorrad());
                        attributThree.setModel(benelli);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Benzhou")
                    {
                        DefaultComboBoxModel<String> benzhou = new DefaultComboBoxModel<>(new Layout().getBenzhouMotorrad());
                        attributThree.setModel(benzhou);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Beta")
                    {
                        DefaultComboBoxModel<String> beta = new DefaultComboBoxModel<>(new Layout().getBetaMotorrad());
                        attributThree.setModel(beta);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Big Panther")
                    {
                        DefaultComboBoxModel<String> bigPanther = new DefaultComboBoxModel<>(new Layout().getBigPantherMotorrad());
                        attributThree.setModel(bigPanther);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Bimota")
                    {
                        DefaultComboBoxModel<String> bimota = new DefaultComboBoxModel<>(new Layout().getBimotaMotorrad());
                        attributThree.setModel(bimota);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "BMW-Motorrad")
                    {
                        DefaultComboBoxModel<String> bmwMotorrad = new DefaultComboBoxModel<>(new Layout().getBmwMotorrad());
                        attributThree.setModel(bmwMotorrad);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Borile")
                    {
                        DefaultComboBoxModel<String> borile = new DefaultComboBoxModel<>(new Layout().getBorileMotorrad());
                        attributThree.setModel(borile);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Boss Hoss")
                    {
                        DefaultComboBoxModel<String> bossHoss = new DefaultComboBoxModel<>(new Layout().getBossHossMotorrad());
                        attributThree.setModel(bossHoss);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Brixton Motorcycles")
                    {
                        DefaultComboBoxModel<String> brixtonMotorcycles = new DefaultComboBoxModel<>(new Layout().getBrixtonMotorrad());
                        attributThree.setModel(brixtonMotorcycles);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Buell")
                    {
                        DefaultComboBoxModel<String> buell = new DefaultComboBoxModel<>(new Layout().getBuelldMotorrad());
                        attributThree.setModel(buell);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Bultaco")
                    {
                        DefaultComboBoxModel<String> bultaco = new DefaultComboBoxModel<>(new Layout().getBultacoMotorrad());
                        attributThree.setModel(bultaco);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Cagiva")
                    {
                        DefaultComboBoxModel<String> cagiva = new DefaultComboBoxModel<>(new Layout().getCagivaMotorrad());
                        attributThree.setModel(cagiva);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Capriolo")
                    {
                        DefaultComboBoxModel<String> capriolo = new DefaultComboBoxModel<>(new Layout().getCaprioloMotorrad());
                        attributThree.setModel(capriolo);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "CFMOTO")
                    {
                        DefaultComboBoxModel<String> cFMOTO = new DefaultComboBoxModel<>(new Layout().getCfMotoMotorrad());
                        attributThree.setModel(cFMOTO);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "CPI Moped")
                    {
                        DefaultComboBoxModel<String> cpiMoped = new DefaultComboBoxModel<>(new Layout().getCpiMopedMotorrad());
                        attributThree.setModel(cpiMoped);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Confederate Motors")
                    {
                        DefaultComboBoxModel<String> confederateMotors = new DefaultComboBoxModel<>(new Layout().getConfederateMotorsMotorrad());
                        attributThree.setModel(confederateMotors);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Daelim")
                    {
                        DefaultComboBoxModel<String> daelim = new DefaultComboBoxModel<>(new Layout().getDaelimMotorrad());
                        attributThree.setModel(daelim);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Da Changjiang")
                    {
                        DefaultComboBoxModel<String> daChangjiang = new DefaultComboBoxModel<>(new Layout().getDaChangjiangMotorrad());
                        attributThree.setModel(daChangjiang);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Dayang")
                    {
                        DefaultComboBoxModel<String> dayang = new DefaultComboBoxModel<>(new Layout().getDayangMotorrad());
                        attributThree.setModel(dayang);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Derbi")
                    {
                        DefaultComboBoxModel<String> derbi = new DefaultComboBoxModel<>(new Layout().getDerbiMotorrad());
                        attributThree.setModel(derbi);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "De Dion-Bouton")
                    {
                        DefaultComboBoxModel<String> deDionBouton = new DefaultComboBoxModel<>(new Layout().getDeDionBoutonMotorrad());
                        attributThree.setModel(deDionBouton);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Dnepr")
                    {
                        DefaultComboBoxModel<String> dnepr = new DefaultComboBoxModel<>(new Layout().getDneprMotorrad());
                        attributThree.setModel(dnepr);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Ducati")
                    {
                        DefaultComboBoxModel<String> ducati = new DefaultComboBoxModel<>(new Layout().getDucatiMotorrad());
                        attributThree.setModel(ducati);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Ering")
                    {
                        DefaultComboBoxModel<String> ering = new DefaultComboBoxModel<>(new Layout().getEringMotorrad());
                        attributThree.setModel(ering);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "E-TON")
                    {
                        DefaultComboBoxModel<String> eTON = new DefaultComboBoxModel<>(new Layout().geteTonMotorrad());
                        attributThree.setModel(eTON);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Flex Tech")
                    {
                        DefaultComboBoxModel<String> flexTech = new DefaultComboBoxModel<>(new Layout().getFlexTechMotorrad());
                        attributThree.setModel(flexTech);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "GasGas")
                    {
                        DefaultComboBoxModel<String> GasGas = new DefaultComboBoxModel<>(new Layout().getGasGasMotorrad());
                        attributThree.setModel(GasGas);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Garelli")
                    {
                        DefaultComboBoxModel<String> garelli = new DefaultComboBoxModel<>(new Layout().getGarelliMotorrad());
                        attributThree.setModel(garelli);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Geely")
                    {
                        DefaultComboBoxModel<String> geely = new DefaultComboBoxModel<>(new Layout().getGeelyMotorrad());
                        attributThree.setModel(geely);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Gilera")
                    {
                        DefaultComboBoxModel<String> gilera = new DefaultComboBoxModel<>(new Layout().getGileraMotorrad());
                        attributThree.setModel(gilera);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Haojue")
                    {
                        DefaultComboBoxModel<String> haojue = new DefaultComboBoxModel<>(new Layout().getHaojueMotorrad());
                        attributThree.setModel(haojue);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Harley-Davidson")
                    {
                        DefaultComboBoxModel<String> harleyDavidson = new DefaultComboBoxModel<>(new Layout().getHarleyDavidsonMotorrad());
                        attributThree.setModel(harleyDavidson);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Hartford")
                    {
                        DefaultComboBoxModel<String> hartford = new DefaultComboBoxModel<>(new Layout().getHartfordMotorrad());
                        attributThree.setModel(hartford);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Her Chee")
                    {
                        DefaultComboBoxModel<String> herChee = new DefaultComboBoxModel<>(new Layout().getHerCheeMotorrad());
                        attributThree.setModel(herChee);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Hercules")
                    {
                        DefaultComboBoxModel<String> hercules = new DefaultComboBoxModel<>(new Layout().getHerculesMotorrad());
                        attributThree.setModel(hercules);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Hero")
                    {
                        DefaultComboBoxModel<String> hero = new DefaultComboBoxModel<>(new Layout().getHeroMotorrad());
                        attributThree.setModel(hero);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Hisun")
                    {
                        DefaultComboBoxModel<String> hisun = new DefaultComboBoxModel<>(new Layout().getHisunMotorrad());
                        attributThree.setModel(hisun);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Honda Motors")
                    {
                        DefaultComboBoxModel<String> hondaMotors = new DefaultComboBoxModel<>(new Layout().getHondaMotorrad());
                        attributThree.setModel(hondaMotors);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Horex")
                    {
                        DefaultComboBoxModel<String> horex = new DefaultComboBoxModel<>(new Layout().getHorexMotorrad());
                        attributThree.setModel(horex);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Husqvarna")
                    {
                        DefaultComboBoxModel<String> husqvarna = new DefaultComboBoxModel<>(new Layout().getHusqvarnaMotorrad());
                        attributThree.setModel(husqvarna);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Hyosung")
                    {
                        DefaultComboBoxModel<String> hyosung = new DefaultComboBoxModel<>(new Layout().getHyosungMotorrad());
                        attributThree.setModel(hyosung);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Indian")
                    {
                        DefaultComboBoxModel<String> indian = new DefaultComboBoxModel<>(new Layout().getIndianMotorrad());
                        attributThree.setModel(indian);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jawa")
                    {
                        DefaultComboBoxModel<String> jawa = new DefaultComboBoxModel<>(new Layout().getJawaMotorrad());
                        attributThree.setModel(jawa);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jianshe")
                    {
                        DefaultComboBoxModel<String> jianshe = new DefaultComboBoxModel<>(new Layout().getJiansheMotorrad());
                        attributThree.setModel(jianshe);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jinlun")
                    {
                        DefaultComboBoxModel<String> jinlun = new DefaultComboBoxModel<>(new Layout().getJinlunMotorrad());
                        attributThree.setModel(jinlun);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jmstar")
                    {
                        DefaultComboBoxModel<String> jmstar = new DefaultComboBoxModel<>(new Layout().getJmstarMotorrad());
                        attributThree.setModel(jmstar);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jonway")
                    {
                        DefaultComboBoxModel<String> jonway = new DefaultComboBoxModel<>(new Layout().getJonwayMotorrad());
                        attributThree.setModel(jonway);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Jinan Qingqi")
                    {
                        DefaultComboBoxModel<String> jinanQingqi = new DefaultComboBoxModel<>(new Layout().getJinanQingqiMotorrad());
                        attributThree.setModel(jinanQingqi);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Kanuni")
                    {
                        DefaultComboBoxModel<String> kanuni = new DefaultComboBoxModel<>(new Layout().getKanuniMotorrad());
                        attributThree.setModel(kanuni);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Kawasaki")
                    {
                        DefaultComboBoxModel<String> kawasaki = new DefaultComboBoxModel<>(new Layout().getKawasakiMotorrad());
                        attributThree.setModel(kawasaki);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Keeway")
                    {
                        DefaultComboBoxModel<String> keeway = new DefaultComboBoxModel<>(new Layout().getKeewayMotorrad());
                        attributThree.setModel(keeway);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "KTM")
                    {
                        DefaultComboBoxModel<String> kTM = new DefaultComboBoxModel<>(new Layout().getKtmMotorrad());
                        attributThree.setModel(kTM);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Kreidler")
                    {
                        DefaultComboBoxModel<String> kreidler = new DefaultComboBoxModel<>(new Layout().getKreidlerMotorrad());
                        attributThree.setModel(kreidler);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "KSR Moto")
                    {
                        DefaultComboBoxModel<String> ksrMoto = new DefaultComboBoxModel<>(new Layout().getKsrMotorrad());
                        attributThree.setModel(ksrMoto);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Kymco")
                    {
                        DefaultComboBoxModel<String> kymco = new DefaultComboBoxModel<>(new Layout().getKymcoMotorrad());
                        attributThree.setModel(kymco);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Laverda")
                    {
                        DefaultComboBoxModel<String> ksrMoto = new DefaultComboBoxModel<>(new Layout().getLaverdaMotorrad());
                        attributThree.setModel(ksrMoto);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Lifan")
                    {
                        DefaultComboBoxModel<String> lifan = new DefaultComboBoxModel<>(new Layout().getLifanMotorrad());
                        attributThree.setModel(lifan);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "LML")
                    {
                        DefaultComboBoxModel<String> lML = new DefaultComboBoxModel<>(new Layout().getLmlMotorrad());
                        attributThree.setModel(lML);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Loncin")
                    {
                        DefaultComboBoxModel<String> lML = new DefaultComboBoxModel<>(new Layout().getLoncinMotorrad());
                        attributThree.setModel(lML);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Longjia")
                    {
                        DefaultComboBoxModel<String> lML = new DefaultComboBoxModel<>(new Layout().getLongjiaMotorrad());
                        attributThree.setModel(lML);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "LuXXon")
                    {
                        DefaultComboBoxModel<String> lML = new DefaultComboBoxModel<>(new Layout().getLuxxonMotorrad());
                        attributThree.setModel(lML);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Magni")
                    {
                        DefaultComboBoxModel<String> lML = new DefaultComboBoxModel<>(new Layout().getMagniMotorrad());
                        attributThree.setModel(lML);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Maico")
                    {
                        DefaultComboBoxModel<String> maico = new DefaultComboBoxModel<>(new Layout().getMaicoMotorrad());
                        attributThree.setModel(maico);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Malaguti")
                    {
                        DefaultComboBoxModel<String> malaguti = new DefaultComboBoxModel<>(new Layout().getMalagutiMotorrad());
                        attributThree.setModel(malaguti);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Malanca")
                    {
                        DefaultComboBoxModel<String> malanca = new DefaultComboBoxModel<>(new Layout().getMalancaMotorrad());
                        attributThree.setModel(malanca);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Megelli")
                    {
                        DefaultComboBoxModel<String> megelli = new DefaultComboBoxModel<>(new Layout().getMegelliMotorrad());
                        attributThree.setModel(megelli);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Minsk")
                    {
                        DefaultComboBoxModel<String> minsk = new DefaultComboBoxModel<>(new Layout().getMinskMotorrad());
                        attributThree.setModel(minsk);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Modenas")
                    {
                        DefaultComboBoxModel<String> modenas = new DefaultComboBoxModel<>(new Layout().getModenasMotorrad());
                        attributThree.setModel(modenas);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Mondial")
                    {
                        DefaultComboBoxModel<String> mondial = new DefaultComboBoxModel<>(new Layout().getMondialMotorrad());
                        attributThree.setModel(mondial);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Montesa")
                    {
                        DefaultComboBoxModel<String> montesa = new DefaultComboBoxModel<>(new Layout().getMontesaMotorrad());
                        attributThree.setModel(montesa);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Moto Guzzi")
                    {
                        DefaultComboBoxModel<String> motoGuzzi = new DefaultComboBoxModel<>(new Layout().getMotoGuzziMotorrad());
                        attributThree.setModel(motoGuzzi);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Moto Morini")
                    {
                        DefaultComboBoxModel<String> motoMorini = new DefaultComboBoxModel<>(new Layout().getMotoMoriniMotorrad());
                        attributThree.setModel(motoMorini);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Motorhispania")
                    {
                        DefaultComboBoxModel<String> motorhispania = new DefaultComboBoxModel<>(new Layout().getMotorhispaniaMotorrad());
                        attributThree.setModel(motorhispania);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "MV Agusta")
                    {
                        DefaultComboBoxModel<String> mvAgusta = new DefaultComboBoxModel<>(new Layout().getMvAgustaMotorrad());
                        attributThree.setModel(mvAgusta);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Nipponia")
                    {
                        DefaultComboBoxModel<String> nipponia = new DefaultComboBoxModel<>(new Layout().getNipponiaMotorrad());
                        attributThree.setModel(nipponia);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Norton Motorcycles")
                    {
                        DefaultComboBoxModel<String> nortonMotorcycles = new DefaultComboBoxModel<>(new Layout().getNortonMotorcyclesMotorrad());
                        attributThree.setModel(nortonMotorcycles);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Nova Motors")
                    {
                        DefaultComboBoxModel<String> novaMotors = new DefaultComboBoxModel<>(new Layout().getNovaMotorsMotorrad());
                        attributThree.setModel(novaMotors);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Peugeot")
                    {
                        DefaultComboBoxModel<String> peugeot = new DefaultComboBoxModel<>(new Layout().getPeugeotMotorrad());
                        attributThree.setModel(peugeot);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Piaggio")
                    {
                        DefaultComboBoxModel<String> piaggio = new DefaultComboBoxModel<>(new Layout().getPiaggioMotorrad());
                        attributThree.setModel(piaggio);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Puch")
                    {
                        DefaultComboBoxModel<String> puch = new DefaultComboBoxModel<>(new Layout().getPuchMotorrad());
                        attributThree.setModel(puch);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Presto")
                    {
                        DefaultComboBoxModel<String> presto = new DefaultComboBoxModel<>(new Layout().getPrestoMotorrad());
                        attributThree.setModel(presto);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Rieju")
                    {
                        DefaultComboBoxModel<String> rieju = new DefaultComboBoxModel<>(new Layout().getRiejuMotorrad());
                        attributThree.setModel(rieju);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Romet")
                    {
                        DefaultComboBoxModel<String> romet = new DefaultComboBoxModel<>(new Layout().getRometMotorrad());
                        attributThree.setModel(romet);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Royal Enfield")
                    {
                        DefaultComboBoxModel<String> royalEnfield = new DefaultComboBoxModel<>(new Layout().getRoyalEnfieldMotorrad());
                        attributThree.setModel(royalEnfield);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Sachs Bikes")
                    {
                        DefaultComboBoxModel<String> sachsBikes = new DefaultComboBoxModel<>(new Layout().getSachsBikesMotorrad());
                        attributThree.setModel(sachsBikes);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Sanben")
                    {
                        DefaultComboBoxModel<String> sanben = new DefaultComboBoxModel<>(new Layout().getSanbenMotorrad());
                        attributThree.setModel(sanben);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Sandi")
                    {
                        DefaultComboBoxModel<String> sandi = new DefaultComboBoxModel<>(new Layout().getSandiMotorrad());
                        attributThree.setModel(sandi);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Schüttoff")
                    {
                        DefaultComboBoxModel<String> schuettoff = new DefaultComboBoxModel<>(new Layout().getSchuettoffMotorrad());
                        attributThree.setModel(schuettoff);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Shandong Pioneer")
                    {
                        DefaultComboBoxModel<String> shandongPioneer = new DefaultComboBoxModel<>(new Layout().getShandongPioneerMotorrad());
                        attributThree.setModel(shandongPioneer);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Sherco")
                    {
                        DefaultComboBoxModel<String> schuettoff = new DefaultComboBoxModel<>(new Layout().getShercoMotorrad());
                        attributThree.setModel(schuettoff);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Simson")
                    {
                        DefaultComboBoxModel<String> simson = new DefaultComboBoxModel<>(new Layout().getSimsonMotorrad());
                        attributThree.setModel(simson);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Sundiro")
                    {
                        DefaultComboBoxModel<String> hainanSundiro = new DefaultComboBoxModel<>(new Layout().getHainanSundiroMotorrad());
                        attributThree.setModel(hainanSundiro);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Suzuki")
                    {
                        DefaultComboBoxModel<String> suzuki = new DefaultComboBoxModel<>(new Layout().getSuzukiMotorrad());
                        attributThree.setModel(suzuki);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "SYM")
                    {
                        DefaultComboBoxModel<String> sYM = new DefaultComboBoxModel<>(new Layout().getSymMotorrad());
                        attributThree.setModel(sYM);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Triumph")
                    {
                        DefaultComboBoxModel<String> triumph = new DefaultComboBoxModel<>(new Layout().getTriumphMotorrad());
                        attributThree.setModel(triumph);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Ural")
                    {
                        DefaultComboBoxModel<String> ural = new DefaultComboBoxModel<>(new Layout().getUralMotorrad());
                        attributThree.setModel(ural);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Vespa")
                    {
                        DefaultComboBoxModel<String> vespa = new DefaultComboBoxModel<>(new Layout().getVespaMotorrad());
                        attributThree.setModel(vespa);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Voge")
                    {
                        DefaultComboBoxModel<String> voge = new DefaultComboBoxModel<>(new Layout().getVogeMotorrad());
                        attributThree.setModel(voge);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Voxan")
                    {
                        DefaultComboBoxModel<String> voxan = new DefaultComboBoxModel<>(new Layout().getVoxanMotorrad());
                        attributThree.setModel(voxan);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Yamaha")
                    {
                        DefaultComboBoxModel<String> yamaha = new DefaultComboBoxModel<>(new Layout().getYamahaMotorrad());
                        attributThree.setModel(yamaha);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Zanella")
                    {
                        DefaultComboBoxModel<String> zanella = new DefaultComboBoxModel<>(new Layout().getZanellaMotorrad());
                        attributThree.setModel(zanella);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "ZID")
                    {
                        DefaultComboBoxModel<String> zID = new DefaultComboBoxModel<>(new Layout().getZidMotorrad());
                        attributThree.setModel(zID);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Zongshen")
                    {
                        DefaultComboBoxModel<String> zongshen = new DefaultComboBoxModel<>(new Layout().getZongshenMotorrad());
                        attributThree.setModel(zongshen);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (boolLabel())
                    {
                        attributThree.setEnabled(false);
                        errorLabel.setText(error + errStatusMissinField);
                    }
                }
                if (boot)
                {
                    if (attributTwo.getSelectedItem() == "Katamaran")
                    {
                        DefaultComboBoxModel<String> dongfeng = new DefaultComboBoxModel<>(new Layout().getKatamaran());
                        attributThree.setModel(dongfeng);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Segel-Jacht")
                    {
                        DefaultComboBoxModel<String> dongfeng = new DefaultComboBoxModel<>(new Layout().getSegelyacht());
                        attributThree.setModel(dongfeng);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Trimaran")
                    {
                        DefaultComboBoxModel<String> dongfeng = new DefaultComboBoxModel<>(new Layout().getTrimaran());
                        attributThree.setModel(dongfeng);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Ketch")
                    {
                        DefaultComboBoxModel<String> dongfeng = new DefaultComboBoxModel<>(new Layout().getKetch());
                        attributThree.setModel(dongfeng);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (attributTwo.getSelectedItem() == "Kreuzer")
                    {
                        DefaultComboBoxModel<String> dongfeng = new DefaultComboBoxModel<>(new Layout().getKreuzer());
                        attributThree.setModel(dongfeng);
                        attributThree.setSelectedIndex(0);
                        attributThree.setEnabled(true);
                    }
                    if (boolLabel())
                    {
                        attributThree.setEnabled(false);
                        errorLabel.setText(error + errStatusMissinField);
                    }
                }
            }
        }
    }
// manage Combobox 3
    private class ItemListenerBoxThree implements java.awt.event.ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(!boolVehicleTyp() && !boolLabel() && !boolTyps() && addView || setView)
            {
                attributFour.setEnabled(true);
            }
        }
    }
// manage Combobox 4
    private class ItemListenerBoxFour implements java.awt.event.ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(!boolVehicleTyp() && !boolLabel() && !boolTyps() && !boolConstData() && addView || setView)
            {
                attributFive.setEnabled(true);
            }
        }
    }
// manage Combobox 5
    private class ItemListenerBoxFive implements java.awt.event.ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(!boolVehicleTyp() && !boolLabel() && !boolTyps() && !boolConstData() && !boolColor() && addView || setView)
            {
                attributSix_1.setEnabled(true);
            }
        }
    }
// manage Combobox 6
    private class ItemListenerBoxSix_1 implements java.awt.event.ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(!boolVehicleTyp() && !boolLabel() && !boolTyps() && !boolConstData() && !boolColor() && !boolPrice_1() && addView || setView)
            {
                attributSix_2.setEnabled(true);
            }
        }
    }
// manage actionbutton functions
    private class ActionListenerActionBtn implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // add Vehicle
            if (addView)
            {
                if (!boolVehicleTyp() && !boolLabel() && !boolTyps() && !boolConstData() && !boolColor() && !boolPrice_1() && !boolPrice_2())
                {
                    actionBtn.setEnabled(true);
                    errorLabel.setText(error + errStatusNon);
                    if (e.getSource() == actionBtn)
                    {
                        parseInDataBank();
                        parseInTable();
                        testOutput();
                    }
                }
                else
                {
                    actionBtn.setEnabled(false);
                    errorLabel.setText(error + errStatusMissinField);
                }
            }
            // set Vehicle
            if (setView)
            {
                if (!boolVehicleTyp() && !boolLabel() && !boolTyps() && !boolConstData() && !boolColor() && !boolPrice_1() && !boolPrice_2())
                {
                    actionBtn.setEnabled(true);
                    errorLabel.setText(error + errStatusNon);
                    if (e.getSource() == actionBtn)
                    {
                        if (dbEmpty())
                        {
                            errorLabel.setText(error + errStatusDbEmpty);
                        }
                        else
                        {
                            setDataBank(whatIsToggled() - 1);
                            parseInTable();
                            testOutput();
                            allBtnsOff();
                            errorLabel.setText(error + errStatusNon);
                        }
                    }
                }
                else
                {
                    actionBtn.setEnabled(false);
                    errorLabel.setText(error + errStatusMissinField);
                }
            }
            // search Vehicle
            if(searchView)
            {
                // reset Table
                if (e.getSource() == deleteRowBtn)
                {
                    parseInTable();
                }
            }
            if(searchView)
            {
                // search for VehicleTyp
                if (!boolVehicleTyp() && boolLabel() && boolTyps() && boolConstData() && boolColor() && boolPrice_1() && boolPrice_2())
                {
                    actionBtn.setEnabled(true);
                    errorLabel.setText(error + errStatusNon);
                    if (e.getSource() == actionBtn)
                    {
                        if (dbEmpty())
                        {
                            errorLabel.setText(error + errStatusDbEmpty);
                        }
                        else
                        {
                            if (checkVehicleTypIfInDb())
                            {
                                searchVihicleTyp();
                            }
                            else
                            {
                                delTable();
                                errorLabel.setText(error + errStatusNoTypeOf);
                            }
                        }
                    }
                }
                // search for label
                else if (boolVehicleTyp() && !boolLabel() && boolTyps() && boolConstData() && boolColor() && boolPrice_1() && boolPrice_2())
                {
                    actionBtn.setEnabled(true);
                    errorLabel.setText(error + errStatusNon);
                    if (e.getSource() == actionBtn)
                    {

                        if (dbEmpty())
                        {
                            errorLabel.setText(error + errStatusDbEmpty);
                        }
                        else
                        {
                            if (checkLabelIfInDb())
                            {
                                searchLabel();
                            }
                            else
                            {
                                delTable();
                                errorLabel.setText(error + errStatusNoTypeOf);
                            }
                        }
                    }
                }
                // search for typ
                else if (boolVehicleTyp() && boolLabel() && !boolTyps() && boolConstData() && boolColor() && boolPrice_1() && boolPrice_2())
                {
                    errorLabel.setText(error + errStatusNon);
                    actionBtn.setEnabled(true);
                    if (e.getSource() == actionBtn)
                    {
                        if (dbEmpty())
                        {
                            errorLabel.setText(error + errStatusDbEmpty);
                        }
                        else
                        {
                            if (checkTypIfInDb())
                            {
                                searchTyp();
                            }
                            else
                            {
                                delTable();
                                errorLabel.setText(error + errStatusNoTypeOf);
                            }
                        }
                    }
                }
                // search for constData
                else if (boolVehicleTyp() && boolLabel() && boolTyps() && !boolConstData() && boolColor() && boolPrice_1() && boolPrice_2())
                {
                    errorLabel.setText(error + errStatusNon);
                    actionBtn.setEnabled(true);
                    if (e.getSource() == actionBtn)
                    {
                        if (dbEmpty())
                        {
                            errorLabel.setText(error + errStatusDbEmpty);
                        }
                        else
                        {
                            if (checkConstDataIfInDb())
                            {
                                searchConstData();
                            }
                            else
                            {
                                delTable();
                                errorLabel.setText(error + errStatusNoTypeOf);
                            }
                        }
                    }
                }
                // search for color
                else if (boolVehicleTyp() && boolLabel() && boolTyps() && boolConstData() && !boolColor() && boolPrice_1() && boolPrice_2())
                {
                    errorLabel.setText(error + errStatusNon);
                    actionBtn.setEnabled(true);
                    if (e.getSource() == actionBtn)
                    {
                        if (dbEmpty())
                        {
                            errorLabel.setText(error + errStatusDbEmpty);
                        }
                        else
                        {
                            if (checkColorIfInDb())
                            {
                                searchColor();
                            }
                            else
                            {
                                delTable();
                                errorLabel.setText(error + errStatusNoTypeOf);
                            }
                        }
                    }
                }
                // search for price
                else if (boolVehicleTyp() && boolLabel() && boolTyps() && boolConstData() && boolColor() && !boolPrice_1() && !boolPrice_2())
                {
                    errorLabel.setText(error + errStatusNon);
                    actionBtn.setEnabled(true);
                    if (e.getSource() == actionBtn)
                    {
                        if (dbEmpty())
                        {
                            errorLabel.setText(error + errStatusDbEmpty);
                        }
                        else
                        {
                            if (checkPriceIfInDb())
                            {
                                searchPrice();
                            }
                            else
                            {
                                delTable();
                                errorLabel.setText(error + errStatusNoTypeOf);
                            }
                        }
                    }
                }
                else
                {
                    errorLabel.setText(error + errStatusOneFieldOn);
                    actionBtn.setEnabled(false);
                }
                if(!boolVehicleTyp() && !boolLabel() && !boolTyps() && !boolConstData() && !boolColor() && !boolPrice_1() && !boolPrice_2())
                {
                    errorLabel.setText(error + errStatusOneFieldOn);
                    actionBtn.setEnabled(false);
                }
            }
        }
    }
// manage backbutton
    private class ActionListenerBackBtn implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //BackButton function
            if(e.getSource() == backBtn)
            {
                switchToMainView();
            }
        }
    }
}

