package burp;

import java.net.URLEncoder;

public class UrlEncode {
    public static byte[] Encrypt(byte[] selectedByte,IBurpExtenderCallbacks callbacks){
        try {
            boolean EncodeStar;
            boolean EncodeDot;
            boolean EncodeMinus;
            boolean EncodeUnderline;
            boolean EncodePlus;

            //Check if need to encode special symbols
            if (callbacks.loadExtensionSetting("cbUrlEncStar") != null){
                EncodeStar = Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncStar"));
            }
            else{ EncodeStar = Config.URLencode_star; }

            if (callbacks.loadExtensionSetting("cbUrlEncDot") != null){
                EncodeDot = Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncDot"));
            }
            else{ EncodeDot = Config.URLencode_dot; }

            if (callbacks.loadExtensionSetting("cbUrlEncMinus") != null){
                EncodeMinus = Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncMinus"));
            }
            else{ EncodeMinus = Config.URLencode_minus; }

            if (callbacks.loadExtensionSetting("cbUrlEncUnderline") != null){
                EncodeUnderline = Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncUnderline"));
            }
            else{ EncodeUnderline = Config.URLencode_underline; }

            if (callbacks.loadExtensionSetting("cbUrlEncPlus") != null){
                EncodePlus = Boolean.parseBoolean(callbacks.loadExtensionSetting("cbUrlEncPlus"));
            }
            else{ EncodePlus = Config.URLencode_plus; }

            String selectedText = new String(selectedByte);
            selectedText = URLEncoder.encode(selectedText, "utf-8");

            if (EncodeStar){ selectedText = selectedText.replace("*","%2A");}
            if (EncodeDot){ selectedText = selectedText.replace(".","%2E");}
            if (EncodeMinus){ selectedText = selectedText.replace("-","%2D");}
            if (EncodeUnderline){ selectedText = selectedText.replace("_","%5F");}
            if (EncodePlus){ selectedText = selectedText.replace("+","%20");}

            byte[] replaceByte = selectedText.getBytes();
            return replaceByte;
        } catch (Exception e) {
            return selectedByte;
        }
    }
}
