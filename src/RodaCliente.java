import java.io.IOException;
import java.net.UnknownHostException;

public class RodaCliente {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        new ClienteSimples("127.0.0.1", 12345).executa();
    }
}