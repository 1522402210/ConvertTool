package burp;

import org.json.JSONObject;
import org.json.XML;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ParmToXML {
    public static String convert(IExtensionHelpers helpers, IHttpRequestResponse messageInfo) throws UnsupportedEncodingException {
        byte[] request = messageInfo.getRequest();
        IRequestInfo analyzeRequest = helpers.analyzeRequest(request);
        int content_type = analyzeRequest.getContentType();
        List<IParameter> Parameters = analyzeRequest.getParameters();
        JSONObject Parms = new JSONObject();

        if (content_type == 1) {                            //        PARAM_BODY
            for (IParameter i : Parameters){
                if(((int)i.getType()) == 1){
                    Parms.put(i.getName(),i.getValue());
                }
            }
            return PrettyXML.prettyFormat(XML.toString(Parms,"root"));
        }
        else if (content_type == 4) {                      //        PARAM_JSON
            JSONObject jsonObj = new JSONObject(GetBody.convert(helpers, messageInfo));
            return PrettyXML.prettyFormat(XML.toString(jsonObj,"root"));
        }
        else{
            return GetBody.convert(helpers,messageInfo);
        }
    }
}

