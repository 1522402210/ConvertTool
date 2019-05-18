package burp;

public class GetBody {
    public static String convert(IExtensionHelpers helpers, IHttpRequestResponse messageInfo) {
        try {
            byte[] request = messageInfo.getRequest();
            IRequestInfo analyzeRequest = helpers.analyzeRequest(request);
            int BodyOffset = analyzeRequest.getBodyOffset();
            int body_length = request.length - BodyOffset;
            String body = new String(request, BodyOffset, body_length, "UTF-8");
            return body;
        } catch (Exception e) {
            return null;
        }
    }
}