import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class TratadorDeMensagemDoCliente implements Runnable {

    private Socket cliente;
    private ServidorSimples servidor;

    public TratadorDeMensagemDoCliente(Socket cliente, ServidorSimples servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        try(Scanner s = new Scanner(this.cliente.getInputStream())) {
            while (s.hasNextLine()) {

                System.out.println("Digite seu email:");
                String email = s.nextLine();
                System.out.println(email);

                servidor.distribuiMensagem(this.cliente, s.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
