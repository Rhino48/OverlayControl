import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;


public class OverlayControl extends JFrame implements ActionListener {
    private JSpinner spinnerTeam1, spinnerTeam2;
    private JTextField textTeam1, textTeam2;
    private JTextField textNameCast1, textNameCast2, textSocialCast1, textSocialCast2;

    public OverlayControl()
    {
        super("Overlay Control");
        JMenuBar bar            = new JMenuBar();
        JTabbedPane tabbedPane  = new JTabbedPane();
        JPanel panelMain        = panelMainCreator();
        JPanel panelCasters     = panelCastersCreator();

        tabbedPane.addTab("Main", panelMain);
        tabbedPane.addTab("Casters", panelCasters);
        tabbedPane.setPreferredSize(new Dimension(100, 110));
        bar.add(tabbedPane);

        panelMain.revalidate();
        setJMenuBar(bar);
        setSize(600, 150);
        setResizable(false);
        setVisible(true);
    }

    // Primary panel - Holds controls for teams and scores information
    public JPanel panelMainCreator(){
        JPanel panelMain        = new JPanel();
        SpinnerModel model1     = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerModel model2     = new SpinnerNumberModel(0, 0, 10, 1);
        JButton buttonConfirm   = buttonHelp("Confirm", "mainConfirm");
        buttonConfirm.setToolTipText("Make changes to the team files");
        JButton buttonSwap      = buttonHelp("Swap", "mainSwap");
        buttonSwap.setToolTipText("Swaps info in the UI - DOES NOT EDIT THE FILES");
        spinnerTeam1            = new JSpinner(model1);
        spinnerTeam2            = new JSpinner(model2);
        textTeam1               = new JTextField(10);
        textTeam2               = new JTextField(10);

        Image team1IconRaw = getScaledImage(new ImageIcon("./Assets/Team1Logo.png").getImage(), 40);
        Image team2IconRaw = getScaledImage(new ImageIcon("./Assets/Team2Logo.png").getImage(), 40);
        JLabel team1Icon = new JLabel(new ImageIcon(team1IconRaw));
        JLabel team2Icon = new JLabel(new ImageIcon(team2IconRaw));

        panelMain.setLayout(new FlowLayout());
        panelMain.add(team1Icon);
        panelMain.add(textTeam1);
        panelMain.add(spinnerTeam1);
        panelMain.add(buttonConfirm);
        panelMain.add(buttonSwap);
        panelMain.add(spinnerTeam2);
        panelMain.add(textTeam2);
        panelMain.add(team2Icon);

        return panelMain;
    }

    // Casters panel - Text for casters' information
    public JPanel panelCastersCreator(){
        int textFieldSize = 10;
        JPanel panelCasters = new JPanel();
        JPanel caster1      = new JPanel();
        JPanel caster2      = new JPanel();

        JButton casterConfirmButton = buttonHelp("Confirm", "casterConfirm");

        caster1.setBorder(BorderFactory.createTitledBorder("Caster 1"));
        caster1.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5));
        caster2.setBorder(BorderFactory.createTitledBorder("Caster 2"));
        caster2.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5));

        JLabel labelNameCast1 = new JLabel("Name:");
        JLabel labelSocialCast1 = new JLabel("Social:");
        JLabel labelNameCast2 = new JLabel("Name:");
        JLabel labelSocialCast2 = new JLabel("Social:");

        textNameCast1 = new JTextField(textFieldSize);
        textSocialCast1 = new JTextField(textFieldSize);
        textNameCast2 = new JTextField(textFieldSize);
        textSocialCast2 = new JTextField(textFieldSize);

        caster1.add(labelNameCast1);
        caster1.add(textNameCast1);
        caster1.add(labelSocialCast1);
        caster1.add(textSocialCast1);

        caster2.add(labelNameCast2);
        caster2.add(textNameCast2);
        caster2.add(labelSocialCast2);
        caster2.add(textSocialCast2);

        panelCasters.setLayout(new BoxLayout(panelCasters, BoxLayout.X_AXIS));

        panelCasters.add(caster1);
        panelCasters.add(caster2);
        panelCasters.add(Box.createHorizontalGlue());
        panelCasters.add(casterConfirmButton);

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
    public JButton buttonHelp(String name, String actionCode){
        JButton newButton = new JButton(name);

        newButton.addActionListener(this);
        newButton.setActionCommand(actionCode);
        newButton.setBackground(Color.white);
        newButton.setFont(Font.getFont("Times New Roman"));

        return newButton;
    }

    // Resize a given image
    private Image getScaledImage(Image srcImg, int scale){

        BufferedImage resizedImg = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, scale, scale, null);
        g2.dispose();

        return resizedImg;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        // Main Confirm button - takes app input and modifies the related text files
        if(action.equals("mainConfirm")){
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
        else if(action.equals("mainSwap")){
            String temp;
            Object tempObj;

            temp = textTeam1.getText();
            textTeam1.setText(textTeam2.getText());
            textTeam2.setText(temp);

            tempObj = spinnerTeam1.getValue();
            spinnerTeam1.setValue(spinnerTeam2.getValue());
            spinnerTeam2.setValue(tempObj);
        }

        // Caster Confirm button - takes app input and modifies the related caster text files
        else if(action.equals("casterConfirm")){
            writerHelp("./Assets/CasterInfo/Caster1Name.txt",   textNameCast1.getText());
            writerHelp("./Assets/CasterInfo/Caster1Social.txt", textSocialCast1.getText());
            writerHelp("./Assets/CasterInfo/Caster2Name.txt",   textNameCast2.getText());
            writerHelp("./Assets/CasterInfo/Caster2Social.txt", textSocialCast2.getText());
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