import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileClient")
public class FileClient {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected int lastByte;

  public boolean start() {
    try {
      socket = new Socket("localhost", 1234);
      out = socket.getOutputStream();
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public void request(String filename) throws Exception {
    System.out.println("[client] Sending REQUEST command");
    out.write("REQUEST\n".getBytes());
    System.out.println(String.format("[client] Sending filename '%s'", filename));
    out.write((filename+"\n").getBytes());
  }

  public boolean readNextByte() throws Exception {
    lastByte = in.read();
    System.out.print((char) lastByte);
    return lastByte != 0;
  }

  public void close() throws Exception {
    System.out.println("[client] Sending CLOSE command");
    out.write("CLOSE\n".getBytes());
    socket.close();
    in.close();
    out.close();
  }

  public static void main(String[] args) throws Exception {
    FileClient client = new FileClient();
    if (client.start()) {
      System.out.println("File client started!");
      client.request("pepe.txt");
      while(client.readNextByte()) {}
      client.close();
    } else {
      System.out.println("Could not start client!");
    }
  }
}
