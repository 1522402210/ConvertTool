package burp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Menu implements IContextMenuFactory {
    private IBurpExtenderCallbacks callbacks;
    private final IExtensionHelpers helpers;
    private PrintWriter stdout;
    private PrintWriter stderr;

    public Menu(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.stdout = new PrintWriter(callbacks.getStdout(),true);
        this.stderr = new PrintWriter(callbacks.getStderr(),true);
    }

    public void showText(IContextMenuInvocation invocation,byte[] selectedByte,byte[] replaceByte){
        int invContext = invocation.getInvocationContext();
        if ((invocation.getToolFlag() == 64) && (invContext == 0)){
            ReplaceText.replace(invocation,selectedByte,replaceByte);
        }
        else { showTextGUI(replaceByte); }
    }

    public void showTextGUI(byte[] replaceByte){
        EncryptTextGUI encDlg = new EncryptTextGUI(replaceByte);
        callbacks.customizeUiComponent(encDlg);
        encDlg.setVisible(true);
    }

    public List<JMenuItem> createMenuItems(final IContextMenuInvocation invocation)
    {
        List<JMenuItem> menus = new ArrayList();

        JMenu toolMenu = new JMenu("Convert Tool");
        JMenuItem Parm2xml = new JMenuItem("Parm to XML");
        JMenuItem Parm2json = new JMenuItem("Parm to JSON");
        JMenuItem Parm2body = new JMenuItem("Parm to Body");
        JMenuItem Unicode2CN = new JMenuItem("Unicode to Chinese");
        JMenuItem URLencode = new JMenuItem("URL Encode");
        JMenuItem Chunked = new JMenuItem("Chunked Encode");
        JMenuItem Settings = new JMenuItem("Settings");

        toolMenu.add(URLencode);
        toolMenu.add(Unicode2CN);
        toolMenu.add(Chunked);
        toolMenu.add(Parm2xml);
        toolMenu.add(Parm2json);
        toolMenu.add(Parm2body);
        toolMenu.addSeparator();
        toolMenu.add(Settings);

        int invContext = invocation.getInvocationContext();
        byte[] selectedByte;
        if ((invContext == 1)&&(invContext == 3)){
            selectedByte = SelectedText.getByte(invocation,false);
        }
        else {
            selectedByte = SelectedText.getByte(invocation,true);
        }

        Unicode2CN.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent argv) {
                byte[] replaceByte =  UnicodeToCN.Encrypt(selectedByte);
                showTextGUI(replaceByte);
            }
        });

        URLencode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent argv) {
                byte[] replaceByte = UrlEncode.Encrypt(selectedByte,callbacks);
                showText(invocation,selectedByte,replaceByte);
            }
        });

        Chunked.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent argv) {
                byte[] replaceByte = ChunkedEncode.Encrypt(selectedByte,callbacks);
                showText(invocation,selectedByte,replaceByte);
                if ((invocation.getToolFlag() == 64) && (invContext == 0)){
                    IHttpRequestResponse messageInfo = invocation.getSelectedMessages()[0];
                    List<String> headers = SetHeader.convert(helpers,messageInfo,"Transfer-Encoding","chunked");
                    String body = GetBody.convert(helpers,messageInfo);
                    byte[] request = helpers.buildHttpMessage(headers, body.getBytes());
                    if (request != null) {
                        messageInfo.setRequest(request);
                    }
                }
            }
        });

        Settings.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent argv) {
                SettingsGUI SettingDlg = new SettingsGUI(callbacks);
                callbacks.customizeUiComponent(SettingDlg);
                SettingDlg.setVisible(true);
            }
        });

        IHttpRequestResponse messageInfo = invocation.getSelectedMessages()[0];
        Parm2xml.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent argv) {
                try {
                    List<String> headers = SetHeader.convert(helpers,messageInfo,"Content-Type","application/xml;charset=UTF-8");
                    String body = ParmToXML.convert(helpers,messageInfo);
                    byte[] request = helpers.buildHttpMessage(headers, body.getBytes());
                    if (request != null) {
                        messageInfo.setRequest(request);
                    }
                } catch (Exception e) {
                    stderr.println(e);
                }
            }
        });

        Parm2json.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent argv) {
                try {
                    List<String> headers = SetHeader.convert(helpers,messageInfo,"Content-Type","application/json;charset=UTF-8");
                    String body = ParmToJSON.convert(helpers,messageInfo);
                    byte[] request = helpers.buildHttpMessage(headers, body.getBytes());
                    if (request != null) {
                        messageInfo.setRequest(request);
                    }
                } catch (Exception e) {
                    stderr.println(e);
                }
            }
        });

        Parm2body.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent argv) {
                try {
                    List<String> headers = SetHeader.convert(helpers,messageInfo,"Content-Type","application/plain;charset=UTF-8");
                    String body = ParmToBody.convert(helpers,messageInfo);
                    byte[] request = helpers.buildHttpMessage(headers, body.getBytes());
                    if (request != null) {
                        messageInfo.setRequest(request);
                    }
                } catch (Exception e) {
                    stderr.println(e);
                }
            }
        });

        menus.add(toolMenu);
        return menus;
    }
}