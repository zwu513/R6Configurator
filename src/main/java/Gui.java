package main.java;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/**
 * Gui for R6config
 */
public class Gui implements Runnable {

    private JButton selectFileButton;
    private JFileChooser fileChooser;
    private JTextField fpsLimitTextField;
    private JTextField fpsLabel;
    private JTextField datacenterLabel;
    private JComboBox<String> datacenterHintBox;
    private final String[] datacenter = {"default", "westus", "centralus", "southcentralus", "eastus", "brazilsouth",
            "northeurope", "westeurope", "southafricanorth", "eastasia", "southeastasia", "japanwest", "australiaeast",
            "australiasoutheast"};
    private JButton applyButton;
    private R6ConfigFile configurator;
    private File configFilePath;

    // main, starts GUI on a thread
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Gui());
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == applyButton) {
                // for FPS limit
                // checks for inputting nothing
                if (fpsLimitTextField.getText().length() > 0) {

                    try {
                        int FPS = Integer.parseInt(fpsLimitTextField.getText());
                        if (FPS < 30) {
                            // warning for under 30 fps limit
                            JOptionPane.showMessageDialog(null, "FPS limits below 30 will disable the FPS limiter", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            // change fps limit
                            configurator.changeFPSLimit(configFilePath, FPS);

                        }
                    } catch (Exception ex) {
                        // error message
                        JOptionPane.showMessageDialog(null, "Invalid FPS input", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                // for data center
                configurator.changeDataCenter(configFilePath, String.valueOf(datacenterHintBox.getSelectedItem()));
            }

            /* Copy and paste from example*/

            if (e.getSource() == selectFileButton) {
                //int returnVal = fc.showOpenDialog(FileChooserDemo.this);
                int returnVal = fileChooser.showOpenDialog(null);

                // if the user selects okay in the file chooser
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    // set file location
                    configFilePath = fileChooser.getSelectedFile();

                    // if there is an FPS limit found
                    if (configurator.detectFPS(configFilePath) != -1) {
                        fpsLimitTextField.setText("" + configurator.detectFPS(configFilePath));
                    }
                    // if the file already has a server selected, show up in the combo box
                    if (configurator.detectServer(configFilePath) != null) {
                        datacenterHintBox.setSelectedItem(datacenter[Arrays.asList(datacenter)
                                .indexOf(configurator.detectServer(configFilePath))]);
                    }
                }
            }
        }
    };

    @Override
    public void run() {
        /// setup jframe
        JFrame frame = new JFrame("R6 Configurator");

        // set icon
        ImageIcon img = new ImageIcon(this.getClass().getClassLoader().getResource("main/resources/40031.png"));
        frame.setIconImage(img.getImage());

        // create container with box layout
        Container content = frame.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // imitate native windows feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // config setup
        configurator = new R6ConfigFile();

        // reference font
        Font defaultFont = new Font("noto", Font.PLAIN, 16);


        /* Creating buttons */
        // create select file button
        selectFileButton = new JButton("Select settings file");
        selectFileButton.setFont(defaultFont);
        selectFileButton.addActionListener(actionListener);

        //Create a file chooser
        fileChooser = new JFileChooser();
        // only allow user to select .ini files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Config File", "ini");
        fileChooser.setFileFilter(filter);
        // open the file chooser where the profile are
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "\\Documents\\My Games\\Rainbow Six - Siege\\"));

        // fps label
        fpsLabel = new JTextField("FPS limit (Optional):");
        fpsLabel.setEditable(false);
        fpsLabel.setFont(defaultFont);
        fpsLabel.setBorder(BorderFactory.createEmptyBorder());

        // fps text field
        fpsLimitTextField = new JTextField();
        fpsLimitTextField.setColumns(10);

        // data center label text
        datacenterLabel = new JTextField("Select data center");
        datacenterLabel.setEditable(false);
        datacenterLabel.setFont(defaultFont);
        datacenterLabel.setBorder(BorderFactory.createEmptyBorder());

        // data center hint box
        datacenterHintBox = new JComboBox<>(datacenter);
        datacenterHintBox.setFont(defaultFont);
        datacenterHintBox.addActionListener(actionListener);

        // apply button
        applyButton = new JButton("Apply");
        applyButton.setFont(defaultFont);
        applyButton.addActionListener(actionListener);


        /* adding buttons to main frame */
        // file selection panel
        JPanel fileSelectPanel = new JPanel();
        fileSelectPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fileSelectPanel.add(selectFileButton);
        frame.getContentPane().add(fileSelectPanel);

        // FPS limit panel
        JPanel fpsPanel = new JPanel();
        fpsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fpsPanel.add(fpsLabel);
        fpsPanel.add(fpsLimitTextField);
        frame.getContentPane().add(fpsPanel);

        // data center hint panel
        JPanel dataPanel = new JPanel();
        dataPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dataPanel.add(datacenterLabel);
        dataPanel.add(datacenterHintBox);
        frame.getContentPane().add(dataPanel);

        // apply button panel
        JPanel applyPanel = new JPanel();
        applyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyPanel.add(applyButton);
        frame.getContentPane().add(applyPanel);

        // frame extras
        frame.setLocationRelativeTo(null);
        // exit with red X is hit
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // compact all the UI elements
        frame.pack();
        // SHow frame after everything is done
        frame.setVisible(true);

    }
}
