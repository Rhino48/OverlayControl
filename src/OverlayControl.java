import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;


public class OverlayControl extends JFrame implements ActionListener {
    private JSpinner spinnerTeam1, spinnerTeam2;
    private JTextField textTeam1, textTeam2;

    public OverlayControl()
    {
        super("Overlay Control");
        JMenuBar bar            = new JMenuBar();
        JTabbedPane tabbedPane  = new JTabbedPane();
        JPanel panelMain        = panelMainCreator();
        JPanel panelCasters     = panelCastersCreator();

        tabbedPane.addTab("Main", panelMain);
        tabbedPane.addTab("Casters", panelCasters);
        bar.add(tabbedPane);

        panelMain.revalidate();
        getContentPane().setLayout(new FlowLayout());
        setJMenuBar(bar);
        setSize(550, 200);
        setResizable(false);
        setVisible(true);
    }

    // Primary panel - Holds controls for teams and scores information
    public JPanel panelMainCreator(){
        JPanel panelMain        = new JPanel();
        SpinnerModel model1     = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerModel model2     = new SpinnerNumberModel(0, 0, 10, 1);
        JButton buttonConfirm   = buttonHelp("Confirm");
        JButton buttonSwap      = buttonHelp("Swap");
        spinnerTeam1            = new JSpinner(model1);
        spinnerTeam2            = new JSpinner(model2);
        textTeam1               = new JTextField(10);
        textTeam2               = new JTextField(10);

        panelMain.setLayout(new FlowLayout());
        panelMain.add(textTeam1);
        panelMain.add(spinnerTeam1);
        panelMain.add(buttonConfirm);
        panelMain.add(buttonSwap);
        panelMain.add(spinnerTeam2);
        panelMain.add(textTeam2);

        return panelMain;
    }

    // Casters panel - Text for casters' information
    public JPanel panelCastersCreator(){
        JPanel panelCasters = new JPanel();



        panelCasters.setLayout(new FlowLayout());
        return  panelCasters;
    }

    public void writerHelp(String path, String input){
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(input);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a button with the coded template
    public JButton buttonHelp(String name){
        JButton newButton = new JButton(name);

        newButton.addActionListener(this);
        newButton.setActionCommand(name);
        newButton.setBackground(Color.white);
        newButton.setFont(Font.getFont("Times New Roman"));

        return newButton;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        // Confirm button - takes app input and modifies the related text files
        if(action.equals("Confirm")){
            try{
                spinnerTeam1.commitEdit();
                spinnerTeam2.commitEdit();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Integer int1 = (Integer) spinnerTeam1.getValue();
            Integer int2 = (Integer) spinnerTeam2.getValue();
            String string1 = int1.toString();
            String string2 = int2.toString();

            writerHelp("./Assets/Team1Name.txt", textTeam1.getText());
            writerHelp("./Assets/Team1Score.txt", string1);
            writerHelp("./Assets/Team2Score.txt", string2);
            writerHelp("./Assets/Team2Name.txt", textTeam2.getText());
        }

        // Swap button - swaps Team1 and Team2 app inputs
        else if(action.equals("Swap")){
            String temp;
            Object tempObj;

            temp = textTeam1.getText();
            textTeam1.setText(textTeam2.getText());
            textTeam2.setText(temp);

            tempObj = spinnerTeam1.getValue();
            spinnerTeam1.setValue(spinnerTeam2.getValue());
            spinnerTeam2.setValue(tempObj);
        }
    }


    public static void main(String[] args)
    {
        OverlayControl app = new OverlayControl();

        app.addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
                System.exit( 0 );
            }
        });
    }
}