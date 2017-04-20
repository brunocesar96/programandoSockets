import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//class Connection extends Thread {
//    DataInputStream in;
//    DataOutputStream out;
//    Socket clientSocket;
//    public Connection (Socket aClientSocket) {
//        try {
//            clientSocket = aClientSocket;
//            System.out.println("Nova conexão com o cliente " +
//                    clientSocket.getInetAddress().getHostAddress());
//            in = new DataInputStream( clientSocket.getInputStream());
//            out =new DataOutputStream( clientSocket.getOutputStream());
//            this.start();
//        } catch(IOException e)  {System.out.println("Connection:"+e.getMessage());}
//    }
//    public void run(){
//        try {
//            // an echo server
//            String data = in.readUTF();
//            out.writeUTF(data);
//        } catch(EOFException e) {System.out.println("EOF:"+e.getMessage());
//        } catch(IOException e) {System.out.println("IO:"+e.getMessage());}
//        finally{
//            try {clientSocket.close();}catch (IOException e){/*close failed*/}
//        }
//    }
//}
public class ServidorSimples {

    private int porta;
    private List<Socket> clientes;
    private List<ListaDeContatos> lista = new ArrayList<ListaDeContatos>();

    public ServidorSimples(int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<>();
    }

    public void executa() throws IOException  {
        try(ServerSocket servidor = new ServerSocket(this.porta)){
            System.out.println("Porta 12345 aberta!");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Nova conexão com o cliente " +
                        cliente.getInetAddress().getHostAddress());

                this.clientes.add(cliente);

                TratadorDeMensagemDoCliente tc = new TratadorDeMensagemDoCliente(cliente, this);
                new Thread(tc).start();
            }
        }
    }

    public void distribuiMensagem(Socket clienteQueEnviou, String msg) {
        for (Socket cliente : this.clientes) {
            if(!cliente.equals(clienteQueEnviou)){
                try {
                    PrintStream ps = new PrintStream(cliente.getOutputStream());
                    ps.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ListaDeContatos getContato(String email){

        for(int i=0 ; i< lista.size() ;i++){

            if(lista.get(i).getEmail().equals(email)) return lista.get(i);

        }

        return null;
    }

    public void registrar(ListaDeContatos l){

        lista.add(l);

    }

}
