package burp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeToCN {
    public static byte[] Encrypt(byte[] selectedByte){
        try {
            String selectedText = new String(selectedByte);
            String reg = "(\\\\u[\\d\\w]{4})+";
            Pattern pattern = Pattern.compile(reg);
            Matcher match = pattern.matcher(selectedText);
            int countAll = match.groupCount();
            while (match.find()) {
                for (int i = 0; i < countAll; i++) {
                    String[] strGroup = (match.group(i)).split("\\\\u");
                    String strCN = "";
                    for (int j = 1; j < strGroup.length; j++) {
                        strCN += (char) Integer.valueOf(strGroup[j], 16).intValue();
                    }
                    selectedText = selectedText.replace(match.group(i), strCN);
                }
            }
            byte[] replaceByte = selectedText.getBytes();
            return replaceByte;
        } catch (Exception e) {
            return selectedByte;
        }
    }
}
