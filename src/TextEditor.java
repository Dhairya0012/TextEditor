import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;


    public class TextEditor implements ActionListener {
        JFrame frame;
        JMenuBar menubar;
        JMenu menu_file, menu_edit, menu_format, menu_help;
        JMenuItem FileItems[];
        JMenuItem EditItems[];
        JMenuItem FormatItems[];
        JMenuItem HelpItems[];
        JTextArea textArea;
        JScrollPane scrollPane;
        File selectedFile;
        JFrame fontFrame;
        JComboBox fontFamily;
        JButton ok;
        JButton cancel;
        JComboBox fontStyle;
        JComboBox fontSize;


        TextEditor() {
            //Adding Native Look and Feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }

            //Creating the main frame
            frame = new JFrame("Untitled");
            frame.setSize(500, 500); //Setting dimensions of frame
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(3);

            //Creating Menu
            menubar = new JMenuBar();                      //Creating MenuBar
            menu_file = new JMenu("File");              //Creating Menu Components
            menu_edit = new JMenu("Edit");
            menu_format = new JMenu("Format");
            menu_help = new JMenu("Help");
            menubar.add(menu_file);                        //Inserting Menu Items in MenuBar
            menubar.add(menu_edit);
            menubar.add(menu_format);
            menubar.add(menu_help);
            frame.setJMenuBar(menubar);                    //Adding MenuBar in frame
            FileItems = new JMenuItem[5];                  //Creating MenuItems for menu_file
            FileItems[0] = new JMenuItem("New");
            FileItems[1] = new JMenuItem("Open");
            FileItems[2] = new JMenuItem("Save");
            FileItems[3] = new JMenuItem("Save As");
            FileItems[4] = new JMenuItem("Exit");
            EditItems = new JMenuItem[3];                   //Creating MenuItems for menu_edit
            EditItems[0] = new JMenuItem("Cut");
            EditItems[1] = new JMenuItem("Copy");
            EditItems[2] = new JMenuItem("Paste");
            FormatItems = new JMenuItem[3];                     //Creating MenuItems for menu_format.
            FormatItems[0] = new JMenuItem("Font Color");
            FormatItems[1] = new JMenuItem("Background Color");
            FormatItems[2] = new JMenuItem("Font Settings");
            HelpItems=new JMenuItem[2];
            HelpItems[0]=new JMenuItem("Shortcut Keys");
            HelpItems[1]=new JMenuItem("About Creators");

            for (int i = 0; i < FileItems.length; i++) {        //Adding MenuItems and Action Listeners to Menu Components
                menu_file.add(FileItems[i]);
                FileItems[i].addActionListener(this);
            }
            for (int i = 0; i < EditItems.length; i++) {
                menu_edit.add(EditItems[i]);
                EditItems[i].addActionListener(this);
            }
            for (int i = 0; i < FormatItems.length; i++) {
                menu_format.add(FormatItems[i]);
                FormatItems[i].addActionListener(this);
            }
            for (int i=0;i<HelpItems.length;i++){
                menu_help.add(HelpItems[i]);
                HelpItems[i].addActionListener(this);
            }


            //Creating Text Area
            textArea = new JTextArea();
            //Creating scrollPane for textArea
            scrollPane = new JScrollPane(textArea);//Adding Text Area on Scroll Pane
            frame.add(scrollPane);                  //Adding scrollPane to frame
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);



            //Setting Shortcut Keys
            KeyStroke keyStrokeToNew = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
            KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
            KeyStroke keyStrokeToSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
            KeyStroke keyStrokeToExit = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
            KeyStroke keyStrokeToCut = KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
            KeyStroke keyStrokeToCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
            KeyStroke keyStrokeToPaste = KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());

            FileItems[0].setAccelerator(keyStrokeToNew);
            FileItems[1].setAccelerator(keyStrokeToOpen);
            FileItems[2].setAccelerator(keyStrokeToSave);
            FileItems[4].setAccelerator(keyStrokeToExit);
            EditItems[0].setAccelerator((keyStrokeToCut));
            EditItems[1].setAccelerator((keyStrokeToCopy));
            EditItems[2].setAccelerator(keyStrokeToPaste);

            //Making frame visible
            frame.setVisible(true);
        }

        public static void main(String[] args) {
            new TextEditor();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == FileItems[0]) {
                New();
            } else if (source == FileItems[1]) {
                openFile();
            } else if (source == FileItems[2]) {
                saveFile();
            } else if (source == FileItems[3]) {
                saveAs();
            } else if (source == FileItems[4]) {
                System.exit(0);
            } else if (source == EditItems[0]) {
                textArea.cut();
            } else if (source == EditItems[1]) {
                textArea.copy();
            } else if (source == EditItems[2]) {
                textArea.paste();
            } else if (source == FormatItems[0]) {
                changeFontColor();
            } else if (source == FormatItems[1]) {
                changeBackgroundColor();
            } else if (source == FormatItems[2]) {
                chooseFont();
            }else if(source==HelpItems[0]){
                displayShortcuts();
            }
            else if (source==HelpItems[1]){
                displayCreator();
            }
            else if (source==ok){
                applyFont();
                fontFrame.setVisible(false);
            }
            else if (source==cancel){
                fontFrame.setVisible(false);
            }
        }

        public void openFile() {
            JFileChooser chooser = new JFileChooser();
            int isSelected = chooser.showOpenDialog(frame);       //Opening Dialog Box
            if (isSelected == 0) {
                File file = chooser.getSelectedFile();    //Adding File
                try {
                    textArea.setText("");
                    FileInputStream inputStream = new FileInputStream(file);
                    while (inputStream.read() != -1) {
                        textArea.append((String.valueOf((char) inputStream.read())));
                    }
                } catch (Exception ee) {
                    System.out.println(ee);
                }
            }
        }

        public void saveFile() {
            if (selectedFile == null || (!selectedFile.exists())) {
                saveAs();
                System.out.println(selectedFile);
            } else {
                try {
                    FileOutputStream outputStream = new FileOutputStream(selectedFile);
                    outputStream.write(textArea.getText().getBytes(StandardCharsets.UTF_8));
                    outputStream.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }

        public void New() {
            if (!textArea.getText().equals("")) {
                int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save changes the file?");
                if (confirm == 0) {
                    saveFile();
                }
            }
            textArea.setText("");
            frame.setTitle("Untitled");

        }

        public void saveAs() {
            JFileChooser chooser = new JFileChooser();
            chooser.setApproveButtonText("Run Application");
            int isSelected = chooser.showSaveDialog(frame);
            if (isSelected == 0) {
                try {
                    selectedFile = chooser.getSelectedFile();
                    if (!selectedFile.exists()) {
                        FileOutputStream outputStream = new FileOutputStream(selectedFile);
                        outputStream.write(textArea.getText().getBytes(StandardCharsets.UTF_8));
                        outputStream.close();
                        frame.setTitle(chooser.getSelectedFile().getName());
                    } else {
                        JOptionPane.showMessageDialog(chooser, "The file with same name already exists");
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        public void changeFontColor() {
            Color fgColor = JColorChooser.showDialog(frame, "Choose Font Color", Color.BLACK);
            textArea.setForeground(fgColor);
        }

        public void changeBackgroundColor() {
            Color bgColor = JColorChooser.showDialog(frame, "Choose Font Color", Color.WHITE);
            textArea.setBackground(bgColor);
        }

        public void chooseFont() {
            fontFrame = new JFrame("Choose Font");
            fontFrame.setSize(550, 250);
            fontFrame.setLocationRelativeTo(frame);
            fontFrame.setLayout(null);


            fontFamily = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
            fontFamily.setBounds(50, 50, 170, 40);
            fontFrame.add(fontFamily);

            String style[] = {"Plain", "Bold", "Italic"};
            fontStyle = new JComboBox(style);
            fontStyle.setBounds(275, 50, 100, 40);
            fontFrame.add(fontStyle);

            String size[] = {"10", "16", "20", "30", "40", "50", "60"};
            fontSize = new JComboBox(size);
            fontSize.setBounds(430, 50, 70, 40);
            fontFrame.add(fontSize);

            ok = new JButton("Ok");
            ok.setBounds(150, 150, 80, 40);
            ok.addActionListener(this);
            fontFrame.add(ok);

            cancel = new JButton("Cancel");
            cancel.setBounds(250, 150, 80, 40);
            cancel.addActionListener(this);
            fontFrame.add(cancel);

            fontFrame.setVisible(true);

        }

        public void applyFont() {
            String fFamily = (String) fontFamily.getSelectedItem();
            String fSize = (String) fontSize.getSelectedItem();
            String fStyle = (String) fontSize.getSelectedItem();
            int style=0;
            if (fStyle.equals("plain")) {
                style = 0;
            } else if (fStyle.equals("Bold")) {
                style = 1;
            } else if (fStyle.equals("Italic")) {
                style = 2;
            }

            Font F = new Font(fFamily, style, Integer.parseInt(fSize));
            textArea.setFont(F);

        }

        public void displayShortcuts(){
            JFrame shortcutFrame=new JFrame("Shortcuts List");
            shortcutFrame.setSize(450,250);
            shortcutFrame.setLocationRelativeTo(frame);
            JPanel panel=new JPanel();
            shortcutFrame.add(panel);
            JLabel label=new JLabel(
                    "<html>" +
                            "<b>"+
                            "<br>"+
                            "<p>New=Ctrl+N</p>" +
                            "<br>"+
                            "<p>Open=Ctrl+O</p>" +
                            "<br>"+
                            "<p>Save=Ctrl+S</p>" +
                            "<br>"+
                            "<p>Exit=Esc</p>" +
                            "<br>"+
                            "<p>Cut=Ctrl+X</p>" +
                            "<br>"+
                            "<p>Copy=Ctrl+C</p>" +
                            "<br>"+
                            "<p>Paste=Ctrl+V</p>"+
                            "<b>"+
                            "</html>");
            panel.add(label);
            shortcutFrame.setVisible(true);




        }

        public void displayCreator(){
            JFrame displayFrame=new JFrame("");
            displayFrame.setSize(800,250);
            displayFrame.setLocationRelativeTo(frame);
            JPanel panel=new JPanel();
            displayFrame.add(panel);
            JLabel label=new JLabel(
                    "<html>" +
                            "<b>"+
                            "<center>"+
                            "<br>"+
                            "<font size=20>"+
                            "<p>Developed with ❤ By:Dhairya0012</p>" +"</font>"+
                            "<br>"+

                            "<p>You can contact me at:dhairya0192.be20@chitkara.edu.in</p>" +
                            "<br>"+
                            "<p>I can create:Swings,Tkinter,React,Flutter projects</p>" +
                            "<br>"+
                            "</font>"+
                            "<font size=16.5>"+
                            "<p>If you are looking for a partner for any project based on these, the please feel free to contact me.</p>" +
                            "</font>"+
                            "<b>"+
                            "</center>"+
                            "</html>");
            panel.add(label);
            displayFrame.setVisible(true);

        }
    }




