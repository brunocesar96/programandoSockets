import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClienteSimples {

    private String host;
    private int porta;

    public ClienteSimples(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void executa() throws UnknownHostException, IOException {
        try(Socket cliente = new Socket(this.host, this.porta);
            Scanner teclado = new Scanner(System.in);
            PrintStream saida = new PrintStream(cliente.getOutputStream())) {
            System.out.println("O cliente se conectou ao servidor!");

            RecebedorDeMensagemDoServidor r = new RecebedorDeMensagemDoServidor(cliente.getInputStream());
            new Thread(r).start();

            while (teclado.hasNextLine()) {
                saida.println(teclado.nextLine());
            }
        }
    }

}

//
//        Registrar-se no Servidor informando: identificador do usuário (e.g., e-mail), senha
//        , IP e Porta utilizados para receber mensagens de outros clientes;
//        Consultar o servidor, informando o identificador de um outro usuário,
//        para obter o endereço IP e o número de porta associados à instância da aplicação cliente daquele usuário quando desejar estabelecer comunicação com ele(a);
//        Usar TCP para se comunicar com o servidor e UDP para se comunicar com outros clientes.

