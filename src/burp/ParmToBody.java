package burp;

import org.json.JSONObject;
import org.json.XML;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParmToBody {
    public static String convert(IExtensionHelpers helpers, IHttpRequestResponse messageInfo) throws UnsupportedEncodingException {
        byte[] request = messageInfo.getRequest();
        IRequestInfo analyzeRequest = helpers.analyzeRequest(request);
        int content_type = analyzeRequest.getContentType();
        List bodyList = new ArrayList<>();

        if (content_type == 3) {                            //         PARAM_XML
            JSONObject Parms = new JSONObject((XML.toJSONObject(GetBody.convert(helpers, messageInfo))).toString());
            if (Parms.has("root")) {                  //          Delete root
                Parms = Parms.getJSONObject("root");
            }
            Iterator<String> iterator = Parms.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                bodyList.add(String.format("%s=%s",key,Parms.get(key)));
            }
            return String.join("&",bodyList);
        }
        else if (content_type == 4) {                       //           PARAM_JSON
            JSONObject jsonObj = new JSONObject(GetBody.convert(helpers, messageInfo));
            if (jsonObj.has("root")) {                //          Delete root
                jsonObj = jsonObj.getJSONObject("root");
            }
            Iterator<String> iterator = jsonObj.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                bodyList.add(String.format("%s=%s",key,jsonObj.get(key)));
            }
            return String.join("&",bodyList);
        }
        else{
            return GetBody.convert(helpers,messageInfo);
        }
    }
}
