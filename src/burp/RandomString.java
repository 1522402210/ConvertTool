package burp;

import java.util.Random;

public class RandomString {
    public static String getRandomString(int length){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<length;i++){
            int number = random.nextInt(2);
            long result = 0;
            switch(number){
                case 0:
                    result = Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    result = Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
            }
        }
        return sb.toString();
    }
}
