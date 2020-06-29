package ru.ifmo.cs.pb.lab8.basic;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.Callable;

public class TCPServerReceiver implements Callable<AQPackage> {

      private final SocketChannel socketChannel;
      private final Set<SocketChannel> channels;

      private static final Logger LOGGER = Logger.getLogger(TCPServerReceiver.class.getSimpleName());

      public TCPServerReceiver(SocketChannel socketChannel, Set<SocketChannel> channels) {
            this.socketChannel = socketChannel;
            this.channels = channels;
      }

      @Override
      public AQPackage call() {

            ByteBuffer buffer = ByteBuffer.allocate(Short.MAX_VALUE);
            try {
                  /* Receiving new package from clients */
                  socketChannel.read(buffer);

                  /* Deserializing package bytes to an object */
                  AQPackage aqPack = (AQPackage) deserialize(buffer.array());
                  String usernameOfUser = aqPack.getUser() != null ? aqPack.getUser().getUsername() : "?";

                  LOGGER.info("Received new package from user '" + usernameOfUser + "'");
                  return aqPack;

            } catch (IOException | ClassNotFoundException exception) {
                  SocketAddress socketAddress = null;
                  try {
                        socketAddress = socketChannel.getRemoteAddress();

                        /* Closing connection with user and removing */
                        channels.remove(socketChannel);
                        socketChannel.close();
                  } catch (IOException e) {
                        LOGGER.error("An error occurred!");
                  }

                  LOGGER.warn("User left the server or sent unable pack: " + socketAddress);
                  throw new RuntimeException();
            }
      }

      /**
       * Deserialize an byte array to object
       */
      private static Object deserialize(byte[] objectBytes)
              throws IOException, ClassNotFoundException {

            /* Creating buffer to read bytes */
            ByteArrayInputStream byteArrInStream = new ByteArrayInputStream(objectBytes);
            ObjectInputStream objInStream = new ObjectInputStream(byteArrInStream);

            /* Creating an object from objectStream */
            Object object = objInStream.readObject();
            objInStream.close();

            /* Returning an object */
            return object;
      }
}
