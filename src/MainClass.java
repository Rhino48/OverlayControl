import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;


public class MainClass extends JFrame implements ActionListener {
    private final JSpinner spinnerTeam1, spinnerTeam2;
    private final JTextField textTeam1, textTeam2;

    public MainClass()
    {
        super( "Overlay Control" );

        Container c = getContentPane();
        c.setLayout( new FlowLayout() );

        SpinnerModel model1     = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerModel model2     = new SpinnerNumberModel(0, 0, 10, 1);
        spinnerTeam1            = new JSpinner(model1);
        spinnerTeam2            = new JSpinner(model2);

        textTeam1               = new JTextField(10);
        textTeam2               = new JTextField(10);

        JButton buttonConfirm   = new JButton( "Confirm" );
        buttonConfirm.addActionListener(this);
        buttonConfirm.setActionCommand("Confirm");
        buttonConfirm.setBackground(Color.white);
        buttonConfirm.setFont(Font.getFont("Times New Roman"));

        JButton buttonSwap      = new JButton( "Swap" );
        buttonSwap.addActionListener(this);
        buttonSwap.setActionCommand("Swap");
        buttonSwap.setBackground(Color.white);
        buttonSwap.setFont(Font.getFont("Times New Roman"));


        c.add(textTeam1);
        c.add(spinnerTeam1);
        c.add(buttonConfirm);
        c.add(buttonSwap);
        c.add(spinnerTeam2);
        c.add(textTeam2);

        setSize( 550, 100 );
        setResizable(false);
        setVisible(true);
    }

    public void writer(String path, String input){
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(input);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            writer("./Assets/Team1Name.txt", textTeam1.getText());
            writer("./Assets/Team1Score.txt", string1);
            writer("./Assets/Team2Score.txt", string2);
            writer("./Assets/Team2Name.txt", textTeam2.getText());
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


    public static void main( String args[] )
    {
        MainClass app = new MainClass();

        app.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing( WindowEvent e )
                    {
                        System.exit( 0 );
                    }
                }
        );
    }
}