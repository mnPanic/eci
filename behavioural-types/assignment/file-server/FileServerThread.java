import java.net.*;

public class FileServerThread extends Thread {
  private Socket socket;

  public FileServerThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      FileServer server = new FileServer();
      if (server.start(socket)) {
        System.out.println("File server started!");
        if (!server.hasRequest()) {
          server.close();
          return;
        }

        String fileName = server.readFileName();
        if (!server.fileExists(fileName)) {
          server.sendFileEnd();
          return;
        }

        byte[] fileContent = {
          (byte) 0xDE,
          (byte) 0xAD,
          (byte) 0xBE,
          (byte) 0xEF,
        };

        for (byte b : fileContent) {
          server.sendByte(b);
        }

        server.sendFileEnd();
      
        server.close();
      } else {
        System.out.println("Could not start server!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
