package burp;

import java.util.*;

public class SetHeader {
    public static List<String> convert(IExtensionHelpers helpers, IHttpRequestResponse messageInfo, String name, String value){
        byte[] request = messageInfo.getRequest();
        IRequestInfo analyzeRequest = helpers.analyzeRequest(request);
        List<String> headers = analyzeRequest.getHeaders();
        ListIterator<String> iter = headers.listIterator();
        boolean inHeader = false;

        while (iter.hasNext()) {
            if (((String) iter.next()).contains(name)) {
                iter.set(String.format("%s: %s",name,value));
                inHeader = true;
            }
        }
        if (!inHeader) {
            headers.add(String.format("%s: %s",name,value));
        }
        return headers;
    }
}