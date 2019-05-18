package burp;

import java.util.List;

public class ChunkedEncode {
    public static byte[] Encrypt(byte[] selectedByte,IBurpExtenderCallbacks callbacks){
        try{
            boolean isComment;
            int min_split_len;
            int max_split_len;
            int min_comment_len;
            int max_comment_len;

            //Check if need to encode special symbols
            if (callbacks.loadExtensionSetting("spMinSplitLen") != null){
                min_split_len = Integer.parseInt(callbacks.loadExtensionSetting("spMinSplitLen"));
            }
            else{ min_split_len = Config.min_split_len; }

            if (callbacks.loadExtensionSetting("spMaxSplitLen") != null){
                max_split_len = Integer.parseInt(callbacks.loadExtensionSetting("spMaxSplitLen"));
            }
            else{ max_split_len = Config.max_split_len; }

            if (callbacks.loadExtensionSetting("cbComment") != null){
                isComment = Boolean.parseBoolean(callbacks.loadExtensionSetting("cbComment"));
            }
            else{ isComment = Config.isComment; }

            if (callbacks.loadExtensionSetting("spMinCommentLen") != null){
                min_comment_len = Integer.parseInt(callbacks.loadExtensionSetting("spMinCommentLen"));
            }
            else{ min_comment_len = Config.min_comment_len; }

            if (callbacks.loadExtensionSetting("spMaxCommentLen") != null){
                max_comment_len = Integer.parseInt(callbacks.loadExtensionSetting("spMaxCommentLen"));
            }
            else{ max_comment_len = Config.max_comment_len; }

            String selectedText = new String(selectedByte);
            if (selectedText.length() == 0){
                return selectedByte;
            }
            List<String> str_list = ChunkedUtil.getStrRandomLenList(selectedText,min_split_len,max_split_len);
            String replaceText = "";
            for(String str:str_list){
                if(isComment){
                    int commentLen = ChunkedUtil.getRandomNum(min_comment_len,max_comment_len);
                    replaceText += String.format("%s;%s",ChunkedUtil.decimalToHex(str.length()),ChunkedUtil.getRandomString(commentLen));
                }else{
                    replaceText += ChunkedUtil.decimalToHex(str.length());
                }
                replaceText += "\r\n";
                replaceText += str;
                replaceText += "\r\n";
            }
            replaceText += "0\r\n\r\n";


            byte[] replaceByte = replaceText.getBytes();
            return replaceByte;
        } catch (Exception e) {
            return selectedByte;
        }
    }
}
