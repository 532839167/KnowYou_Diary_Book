package com.diarybook.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.diarybook.util.Diary;

/**
 * provide user functions
 */

public class UsersGUI extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    // selecting files
    private JFileChooser chooser;

    /*每个注册用户所记录的日记都位于自己的文件夹下，pathname用于保存用户的文件夹路径*/
    private static String pathname;

    public static void init(String path) {
        pathname = path;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UsersGUI frame = new UsersGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UsersGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setToolTipText("KonwYou");
        tabbedPane.setBounds(0, 0, 574, 67);
        contentPane.add(tabbedPane);

        final JPanel panel = new JPanel();
        tabbedPane.addTab("Management Journal", null, panel, null);

        chooser = new JFileChooser(".\\"+pathname);//初始化JFileChooser，并设置默认目录为用户目录
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Allowed","txt");//only .txt
        chooser.setFileFilter(filter);

        JButton readButton = new JButton("Read the diary");
        readButton.addMouseListener(new MouseAdapter() {
            @Override
            //when mouse is clicked，create a internal frame
            public void mouseClicked(MouseEvent e) {

                int value = chooser.showOpenDialog(panel);// check if user has selected a file

                // internal frame
                JInternalFrame internalFrame_Read = new JInternalFrame("Read the diary", false, true, false, false);
                internalFrame_Read.setBounds(0, 77, 584, 275);
                contentPane.add(internalFrame_Read);
                internalFrame_Read.getContentPane().setLayout(null);
                JTextPane txtDiary = new JTextPane();
                txtDiary.setBounds(0, 0, 568, 246);
                internalFrame_Read.getContentPane().add(txtDiary);

                //JTextPane没有append方法，所以使用Document来不断插入文本
                javax.swing.text.Document doc=txtDiary.getDocument();
                //txtDiary.setBackground(Color.GREEN);
                txtDiary.setEditable(false);// set to not editable

                //if value == JFileChooser.APPROVE_OPTION, 证明user选择了文件
                if (value == JFileChooser.APPROVE_OPTION) {

                    //get the seleted file
                    File file = chooser.getSelectedFile();

                    // if exist
                    if(file.exists()) {

                        // read
                        Diary.read(file, doc);

                        internalFrame_Read.setVisible(true);
                    }
                }
            }
        });

        panel.add(readButton);

        // create new diary
        JButton addButton = new JButton("Create a diary");
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // create a internal frame for new diary
                final JInternalFrame internalFrame_Write = new JInternalFrame("Create a diary",false, true, false, false);


                internalFrame_Write.setBounds(0, 77, 584, 275);
                contentPane.add(internalFrame_Write);
                internalFrame_Write.getContentPane().setLayout(null);

                textField = new JTextField();
                textField.setBounds(76, 0, 492, 21);
                internalFrame_Write.getContentPane().add(textField);
                textField.setColumns(10);

                JLabel label = new JLabel("Title");

                label.setFont(new Font("楷体", Font.PLAIN, 12));
                label.setBounds(46, 3, 52, 15);
                internalFrame_Write.getContentPane().add(label);

                // editor area
                final JEditorPane editorPane = new JEditorPane();
                editorPane.setBounds(0, 31, 568, 179);
                internalFrame_Write.getContentPane().add(editorPane);

                // Save button
                JButton save = new JButton("SAVE");
                save.setBounds(465, 213, 93, 23);
                save.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        // get title
                        String title = textField.getText();
                        // get content
                        String txt = editorPane.getText();
                        //add new diary
                        Diary.addDiary(pathname, title, txt);

                        // hide current window
                        internalFrame_Write.setVisible(false);
                    }
                });
                internalFrame_Write.getContentPane().add(save);
                internalFrame_Write.setVisible(true);
            }
        });

        panel.add(addButton);

        // delete button
        JButton delButton = new JButton("Delete");
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file=null;
                int value=chooser.showOpenDialog(panel);
                if(value==JFileChooser.APPROVE_OPTION)
                {
                    file=chooser.getSelectedFile();

                    // confirmation
                    int x= JOptionPane.showConfirmDialog(panel,"Confirm delete?","Please confirm",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);

                    if(file.exists())
                    {
                        //if ok, delete
                        if(x==JOptionPane.OK_OPTION) {
                            file.delete();

                            // print success message
                            JOptionPane.showMessageDialog(panel, "Delete Success!","information", JOptionPane.PLAIN_MESSAGE);
                        }
                    }

                }

            }
        });

        panel.add(delButton);

        // back button
        JButton back = new JButton("BACK");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IndexGUI.init();
                setVisible(false);
            }
        });

        panel.add(back);
    }
}
