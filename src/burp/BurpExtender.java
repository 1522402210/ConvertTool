package burp;

import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender {
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private String extensionName = "ConvertTool";
    private String version ="v0.1";
    private PrintWriter stdout;
    private PrintWriter stderr;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
    {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName(extensionName);
        callbacks.registerContextMenuFactory(new Menu(callbacks));
        stdout = new PrintWriter(callbacks.getStdout(),true);
        stderr = new PrintWriter(callbacks.getStderr(),true);
        stdout.println(String.format("Successfully Loaded %s %s",extensionName,version));
    }
}