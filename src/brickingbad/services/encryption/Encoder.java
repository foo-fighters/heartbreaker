package brickingbad.services.encryption;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encoder {

    public static String encodeString(String JsonText){

        byte[] authBytes = JsonText.getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getEncoder().encodeToString(authBytes);

        return encoded;

    }

}
