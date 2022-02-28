package application;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class Generate {
	
	public static String formatNumber(Integer convert) {
        String formatted = NumberFormat.getNumberInstance(Locale.US).format(convert);
        return formatted;
    }

    public static String createRandomWord(int len) {  // Convert to serial integer
        String name = "";
        for (int i = 0; i < len; i++) {
            int v = 1 + (int) (Math.random() * 26);
            char c = (char) (v + (i == 0 ? 'A' : 'a') - 1);
            name += c;
        }   return name;   
    }
    
    public static String createRandomIP(int length) {
        Random r = new Random();
        return r.nextInt(length) + "." + r.nextInt(length) + "." + r.nextInt(length) + "." + r.nextInt(length);
    }

    
    static String seqString(int i) {
        return i < 0 ? "" : seqString((i / 26) - 1) + (char)(65 + i % 26);
    }


    

}
