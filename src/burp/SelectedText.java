package burp;

public class SelectedText {
    public static byte[] getByte(IContextMenuInvocation invocation,boolean isRequest){
        IHttpRequestResponse messageInfo = invocation.getSelectedMessages()[0];
        int startOffset = invocation.getSelectionBounds()[0];
        int endOffset = invocation.getSelectionBounds()[1];
        byte[] lastInfo;
        if(isRequest){
            lastInfo = messageInfo.getRequest();
        }
        else{
            lastInfo = messageInfo.getResponse();
        }

        byte[] newInfo = new byte[endOffset-startOffset];
        System.arraycopy(lastInfo, startOffset, newInfo, 0,endOffset-startOffset);
        return newInfo;
    }
}
