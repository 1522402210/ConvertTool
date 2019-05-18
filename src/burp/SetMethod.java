package burp;

import java.util.*;

public class SetMethod {
    public static List<String> convert(IExtensionHelpers helpers, IHttpRequestResponse messageInfo, String value){
        byte[] request = messageInfo.getRequest();
        IRequestInfo analyzeRequest = helpers.analyzeRequest(request);
        List<String> headers = analyzeRequest.getHeaders();
        String method = analyzeRequest.getMethod();

        ListIterator<String> iter = headers.listIterator();
        while (iter.hasNext()) {
            String iterText = iter.next();
            if (iterText.contains(method)) {
                iter.set(iterText.replace(method,value));
            }
        }
        return headers;
    }
}