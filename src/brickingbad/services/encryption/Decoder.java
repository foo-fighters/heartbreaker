package brickingbad.services.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Decoder {

    public static String decodeString(String encodedText){

        byte[] authBytes = Base64.getDecoder().decode(encodedText);
        String str = new String(authBytes);

        return str;
    }
}
