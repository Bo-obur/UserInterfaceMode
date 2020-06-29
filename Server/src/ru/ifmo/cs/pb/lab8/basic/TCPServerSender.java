package ru.ifmo.cs.pb.lab8.basic;

import org.apache.log4j.Logger;
import ru.ifmo.cs.pb.lab8.command.Show;
import ru.ifmo.cs.pb.lab8.command.Type;
import ru.ifmo.cs.pb.lab8.database.Collection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class TCPServerSender implements Runnable {

      private final SocketChannel socketChannel;
      private AQPackage aqPackage;
      private final Set<SocketChannel> channels;
      private final Collection collection;

      private static final Logger LOGGER = Logger.getLogger(TCPServerSender.class.getSimpleName());

      public TCPServerSender(SocketChannel socketChannel, AQPackage aqPackage,
                             Set<SocketChannel> channels, Collection collection) {
            this.socketChannel = socketChannel;
            this.aqPackage = aqPackage;
            this.channels = channels;
            this.collection = collection;
      }

      @Override
      public void run() {
            try {
                  /* Serializing answer pack and sending to the client */
                  ByteBuffer buffer = ByteBuffer.wrap(serialize(aqPackage));
                  socketChannel.write(buffer);
                  LOGGER.info("Sent answer pack to the user " + socketChannel.getRemoteAddress());

                  /*
                   * Sending result of command 'show' to all online clients
                   */
                  if (aqPackage.getCommand().getType().equals(Type.MODIFY)) {
                        try {
                              aqPackage = new Show().execute(collection, null, null);
                              for (SocketChannel socketChannel : channels) {
                                    ByteBuffer byteBuffer = ByteBuffer.wrap(serialize(aqPackage));
                                    socketChannel.write(byteBuffer);
                              }
                        } catch (Exception exception) {
                              LOGGER.error("Sending failed!");
                        }
                  }

            } catch (IOException exception) {
                  SocketAddress socketAddress = null;
                  try {
                        socketAddress = socketChannel.getRemoteAddress();
                        /* Closing connection with user and removing */
                        channels.remove(socketChannel);
                        socketChannel.close();
                  } catch (IOException e) {
                        LOGGER.error("An error occurred!");
                  }

                  LOGGER.warn("User left the server!" + socketAddress);
                  throw new RuntimeException();
            }
      }

      /**
       * Serializes an object to byte array
       */
      private static byte[] serialize(Object object) throws IOException {

            /* Creating buffer to write object */
            ByteArrayOutputStream byteArrOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objOutStream = new ObjectOutputStream(byteArrOutStream);

            /* Creating an array of bytes from object */
            objOutStream.writeObject(object);
            byte[] objBytes = byteArrOutStream.toByteArray();
            objOutStream.flush();
            objOutStream.close();

            /* Returning an array of bytes */
            return objBytes;
      }
}
