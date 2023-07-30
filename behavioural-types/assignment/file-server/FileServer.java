import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileServer")
public class FileServer {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected String lastFilename;

  public boolean start(Socket s) {
    try {
      socket = s;
      out = socket.getOutputStream();
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean hasRequest() throws Exception {
    String command = in.readLine();
    if (command == null) {
      throw new Exception("unexpected end of stream");
    }

    if (command.equals("REQUEST")) {
      return true;
    }

    if (command.equals("CLOSE")) {
      return false;
    }

    throw new Exception("invalid command received");
  }

  public String readFileName() throws Exception {
    String filename = in.readLine();
    if (filename == null) {
      return "";
    }

    return filename;
  }

  public boolean fileExists(String filename) {
    return filename.equals("pepe.txt");
  }

  public void sendFileEnd() throws Exception {
    System.out.println("sending file end");
    this.out.write(0);
  }

  public void sendByte(byte b) throws Exception {
    System.out.println(String.format("sending %s", (char) b));

    out.write(b);
  }

  public void close() throws Exception {
    socket.close();
    in.close();
    out.close();
  }

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(1234);
    while (true) {
      new FileServerThread(serverSocket.accept()).start();
    }
  }
}
