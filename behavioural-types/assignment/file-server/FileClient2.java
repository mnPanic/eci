import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileClient2")
public class FileClient2 extends FileClient {

  public boolean readNextLine() throws Exception {
    int lastReadByte = 0;
    while ((char) lastReadByte != '\n') {
      lastReadByte = in.read();
      System.out.print((char) lastReadByte);
      if (lastReadByte == 0) {
        return false;
      }
    }

    return true;
  }

  public static void main(String[] args) throws Exception {
    FileClient2 client = new FileClient2();
    if (client.start()) {
      System.out.println("File client 2 started!");
      client.request("pepe.txt");
      while(client.readNextLine()) {
      }
      client.close();
    } else {
      System.out.println("Could not start client!");
    }
  }
}
