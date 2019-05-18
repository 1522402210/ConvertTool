package burp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsGUI extends JDialog {
    private final JPanel mainPanel = new JPanel();
    private final JPanel editPanel =  new JPanel();
    private final JPanel bottomPanel = new JPanel();;
    private final Color FONT_COLOR = new Color(0xE58925);

    private final GroupLayout layout = new GroupLayout(editPanel);
    private final JLabel lbUrlEnc = new JLabel("URL Encode Settings");
    private final JCheckBox cbUrlEncStar = new JCheckBox("Star(*)");
    private final JCheckBox cbUrlEncDot = new JCheckBox("Dot(.)");
    private final JCheckBox cbUrlEncMinus = new JCheckBox("Minus(-)");
    private final JCheckBox cbUrlEncUnderline = new JCheckBox("Underline(_)");
    private final JCheckBox cbUrlEncPlus = new JCheckBox("+ to %20");

    private final JLabel lbChunkEnc = new JLabel("Chunked Encode Settings");
    private final JLabel lbSplitLen = new JLabel("Length of chunked:");;
    private final JLabel lbMinus = new JLabel("-");
    private final JSpinner spMinSplitLen = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    private final JSpinner spMaxSplitLen = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
    private final JLabel lbSplitRange = new JLabel("(1-100)");
    private final JCheckBox cbComment = new JCheckBox("Add comments");
    private final JLabel lbCommentLen = new JLabel("Length of comment:");;
    private final JSpinner spMinCommentLen = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
    private final JSpinner spMaxCommentLen = new JSpinner(new SpinnerNumberModel(25, 1, 50, 1));
    private final JLabel lbCommentRange = new JLabel("(1-50)");

    private final JButton btSaveSettings = new JButton("Save Settings");
    private final JButton btCancel = new JButton("Cancel");


    public SettingsGUI(IBurpExtenderCallbacks callbacks){
        initGUI();
        initEvent(callbacks);
        initValue(callbacks);
        this.setTitle("ConvertTool Settings");
    }
    private void initGUI(){
        lbUrlEnc.setFont(new Font(lbUrlEnc.getFont().getName(), Font.BOLD, 13));
        lbUrlEnc.setForeground(FONT_COLOR);
        lbChunkEnc.setFont(new Font(lbChunkEnc.getFont().getName(), Font.BOLD, 13));
        lbChunkEnc.setForeground(FONT_COLOR);

        editPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(15)
                .addGroup(layout.createParallelGroup()
                        .addComponent(lbUrlEnc)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(cbUrlEncStar)
                                .addComponent(cbUrlEncDot)
                                .addComponent(cbUrlEncMinus)
                                .addComponent(cbUrlEncUnderline)
                                .addComponent(cbUrlEncPlus))
                        .addComponent(lbChunkEnc)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lbSplitLen)
                                .addComponent(spMinSplitLen)
                                .addComponent(lbMinus)
                                .addComponent(spMaxSplitLen)
                                .addComponent(lbSplitRange)
                                .addGap(9999))
                        .addComponent(cbComment)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lbCommentLen)
                                .addComponent(spMinCommentLen)
                                .addComponent(lbMinus)
                                .addComponent(spMaxCommentLen)
                                .addComponent(lbCommentRange)
                                .addGap(9999))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addComponent(lbUrlEnc)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(cbUrlEncStar)
                        .addComponent(cbUrlEncDot)
                        .addComponent(cbUrlEncMinus)
                        .addComponent(cbUrlEncUnderline)
                        .addComponent(cbUrlEncPlus))
                .addGap(10)
                .addComponent(lbChunkEnc)
                .addGap(8)
                .addGroup(layout.createParallelGroup()
                        .addComponent(lbSplitLen)
                        .addComponent(spMinSplitLen)
                        .addComponent(lbMinus)
                        .addComponent(spMaxSplitLen)
                        .addComponent(lbSplitRange))
                .addGap(8)
                .addComponent(cbComment)
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(lbCommentLen)
                        .addComponent(spMinCommentLen)
                        .addComponent(lbMinus)
                        .addComponent(spMaxCommentLen)
                        .addComponent(lbCommentRange)));

        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btSaveSettings);
        bottomPanel.add(btCancel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(editPanel,BorderLayout.NORTH);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        this.setModal(true);
        this.setSize(420,280);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screensize.width/2-this.getWidth()/2,screensize.height/2-this.getHeight()/2,this.getWidth(),this.getHeight());
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.add(mainPanel);
    }

    private void initEvent(IBurpExtenderCallbacks callbacks){
        btSaveSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callbacks.saveExtensionSetting("cbUrlEncStar", Boolean.toString(cbUrlEncStar.isSelected()));
                callbacks.saveExtensionSetting("cbUrlEncDot", Boolean.toString(cbUrlEncDot.isSelected()));
                callbacks.saveExtensionSetting("cbUrlEncMinus", Boolean.toString(cbUrlEncMinus.isSelected()));
                callbacks.saveExtensionSetting("cbUrlEncUnderline", Boolean.toString(cbUrlEncUnderline.isSelected()));
                callbacks.saveExtensionSetting("cbUrlEncPlus", Boolean.toString(cbUrlEncPlus.isSelected()));
                callbacks.saveExtensionSetting("spMinSplitLen", Integer.toString(((int)spMinSplitLen.getValue())));
                callbacks.saveExtensionSetting("spMaxSplitLen", Integer.toString(((int)spMaxSplitLen.getValue())));
                callbacks.saveExtensionSetting("cbComment", Boolean.toString(cbComment.isSelected()));
                callbacks.saveExtensionSetting("spMinCommentLen", Integer.toString(((int)spMinCommentLen.getValue())));
                callbacks.saveExtensionSetting("spMaxCommentLen", Integer.toString(((int)spMaxCommentLen.getValue())));
                SettingsGUI.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsGUI.this.dispose();
            }
        });
    }

    public void initValue(IBurpExtenderCallbacks callbacks){
        if (callbacks.loadExtensionSetting("cbUrlEncStar") != null) {
            cbUrlEncStar.setSelected(Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncStar")));
        }
        else{ cbUrlEncStar.setSelected(Config.URLencode_star); }

        if (callbacks.loadExtensionSetting("cbUrlEncDot") != null) {
            cbUrlEncDot.setSelected(Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncDot")));
        }
        else{ cbUrlEncDot.setSelected(Config.URLencode_dot); }

        if (callbacks.loadExtensionSetting("cbUrlEncMinus") != null) {
            cbUrlEncMinus.setSelected(Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncMinus")));
        }
        else{ cbUrlEncMinus.setSelected(Config.URLencode_minus); }

        if (callbacks.loadExtensionSetting("cbUrlEncUnderline") != null) {
            cbUrlEncUnderline.setSelected(Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncUnderline")));
        }
        else{ cbUrlEncUnderline.setSelected(Config.URLencode_underline); }

        if (callbacks.loadExtensionSetting("cbUrlEncPlus") != null) {
            cbUrlEncPlus.setSelected(Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncPlus")));
        }
        else{ cbUrlEncPlus.setSelected(Config.URLencode_plus); }

        if (callbacks.loadExtensionSetting("spMinSplitLen") != null) {
            spMinSplitLen.setValue(Integer.parseInt(callbacks.loadExtensionSetting("spMinSplitLen")));
        }
        else{ spMinSplitLen.setValue(Config.min_split_len); }

        if (callbacks.loadExtensionSetting("spMaxSplitLen") != null) {
            spMaxSplitLen.setValue(Integer.parseInt(callbacks.loadExtensionSetting("spMaxSplitLen")));
        }
        else{ spMaxSplitLen.setValue(Config.max_split_len); }

        if (callbacks.loadExtensionSetting("cbComment") != null){
            cbComment.setSelected(Boolean.parseBoolean(callbacks.loadExtensionSetting("cbComment")));
        }
        else{ cbComment.setSelected(Config.isComment); }

        if (callbacks.loadExtensionSetting("spMinCommentLen") != null) {
            spMinCommentLen.setValue(Integer.parseInt(callbacks.loadExtensionSetting("spMinCommentLen")));
        }
        else{ spMinCommentLen.setValue(Config.min_comment_len); }

        if (callbacks.loadExtensionSetting("spMaxCommentLen") != null) {
            spMaxCommentLen.setValue(Integer.parseInt(callbacks.loadExtensionSetting("spMaxCommentLen")));
        }
        else{ spMaxCommentLen.setValue(Config.max_comment_len); }
    }
}
