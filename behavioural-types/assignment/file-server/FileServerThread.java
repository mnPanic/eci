import java.net.*;

public class FileServerThread extends Thread {
  private Socket socket;

  public FileServerThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      FileServer server = new FileServer();
      if (!server.start(socket)) {
        System.out.println("Could not start server!");
        return;
      }

      System.out.println("File server started!");
      while(server.hasRequest()) {
        String fileName = server.readFileName();
        if (!server.fileExists(fileName)) {
          server.sendFileEnd();
          break;
        }
       
      char[] fileContent = "first line\nsecond line\nbye!".toCharArray();

      /* This check makes no sense, but without it jatyc returns the error
      
        file-server/FileServerThread.java:1: error: Cannot access field [length] of null
        import java.net.*;
        ^
        1 error
      
      */
      if (fileContent == null) {
        throw new Exception("unexpected null file content");
      }
    
      for (char c : fileContent) {
        server.sendByte((byte) c);
      }

      server.sendFileEnd();
    }
  
    server.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
