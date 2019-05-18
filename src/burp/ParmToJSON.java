package burp;

import org.json.JSONObject;
import org.json.XML;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ParmToJSON {
    public static String convert(IExtensionHelpers helpers, IHttpRequestResponse messageInfo) throws UnsupportedEncodingException {
        JSONObject Parms = new JSONObject();
        byte[] request = messageInfo.getRequest();
        IRequestInfo analyzeRequest = helpers.analyzeRequest(request);
        int content_type = analyzeRequest.getContentType();
        List<IParameter> Parameters = analyzeRequest.getParameters();

        if (content_type == 1) {                      //        PARAM_BODY
            for (IParameter i : Parameters){
                if(((int)i.getType()) == 1){
                    Parms.put(i.getName(),i.getValue());
                }
            }
            return Parms.toString();
        }
        else if (content_type == 3) {                //         PARAM_XML
            Parms = XML.toJSONObject(GetBody.convert(helpers,messageInfo));
            if (Parms.has("root")){            //          Delete root
                Parms = Parms.getJSONObject("root");
            }
            return Parms.toString();
        }
        else {
            return GetBody.convert(helpers,messageInfo);
        }
    }
}

