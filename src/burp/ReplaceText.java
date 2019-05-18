package burp;

public class ReplaceText {
    public static void replace(IContextMenuInvocation invocation,byte[] selectedByte,byte[] replaceByte){
        IHttpRequestResponse messageInfo = invocation.getSelectedMessages()[0];
        byte[] req = messageInfo.getRequest();

        byte[] newReq = new byte[req.length+replaceByte.length-selectedByte.length];
        int offset = invocation.getSelectionBounds()[0];

        System.arraycopy(req, 0, newReq, 0, offset);
        System.arraycopy(replaceByte, 0, newReq, offset, replaceByte.length);
        System.arraycopy(req, selectedByte.length+offset, newReq, replaceByte.length+offset, req.length-offset-selectedByte.length);
        if (req != null) {
            messageInfo.setRequest(newReq);
        }
    }
}
