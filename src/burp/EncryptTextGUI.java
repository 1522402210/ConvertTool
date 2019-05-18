package burp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncryptTextGUI extends JDialog {
    private final JPanel mainPanel = new JPanel();
    private final JPanel bottomPanel =  new JPanel();
    private final JPanel textPanel = new JPanel();;
    private final JButton btCopy = new JButton("Copy to clipboard");
    private final JButton btCancel = new JButton("Close");
    private final JTextArea textArea = new JTextArea(8, 15);
    private final JScrollPane scrollPane=new JScrollPane(textArea);

    public EncryptTextGUI(byte[] replaceByte){
        initGUI(replaceByte);
        initEvent();
        this.setTitle("ConvertTool Text");
    }
    private void initGUI(byte[] replaceByte){
        String replaceText = new String(replaceByte);
        textArea.setText(replaceText);
        textArea.setLineWrap(true);
        textArea.setEditable(true);
        textPanel.setBorder(new EmptyBorder(5, 8, -1, 8));
        textPanel.setLayout(new BorderLayout());
        textPanel.add(scrollPane);

        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btCopy);
        bottomPanel.add(btCancel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(textPanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        this.setModal(true);
        this.setSize(600,280);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screensize.width/2-this.getWidth()/2,screensize.height/2-this.getHeight()/2,this.getWidth(),this.getHeight());
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.add(mainPanel);
    }

    private void initEvent(){
        btCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String copyText = textArea.getText();
                StringSelection selection = new StringSelection(copyText);
                clipboard.setContents(selection, null);
                EncryptTextGUI.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncryptTextGUI.this.dispose();
            }
        });
    }

}
