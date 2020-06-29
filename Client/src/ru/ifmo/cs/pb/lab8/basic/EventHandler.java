package ru.ifmo.cs.pb.lab8.basic;

import ru.ifmo.cs.pb.lab8.command.*;
import ru.ifmo.cs.pb.lab8.gui.EventListener;
import ru.ifmo.cs.pb.lab8.object.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class EventHandler implements Runnable {

      public static User user = null;

      private final SocketAddress socketAddress;
      private SocketChannel socketChannel;

      public EventHandler(InetSocketAddress socketAddress) throws IOException {

            /* Setting connection details */
            this.socketAddress = socketAddress;
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);

            /* Setting instance of the EventHandler */
            eventHandler = this;
      }


      /* An instance of the class */
      private static EventHandler eventHandler;

      /* Gets the instance of the current class */
      public static EventHandler getInstance() {
            return eventHandler;
      }


      //****************************************************************************//

      @Override
      public void run() {

            ByteBuffer buffer = ByteBuffer.allocate(Short.MAX_VALUE);
            while (!Thread.currentThread().isInterrupted()) {
                  try {
                        if (!socketChannel.isConnected()) throw new IOException();
                        int i = socketChannel.read(buffer);
                        if (i <= 0) continue;
                        buffer.clear();
                        AQPackage aqPack = (AQPackage) Serializer.deserialize(buffer.array());

                        new Thread(() -> sendByListener(aqPack)).start();

                  } catch (IOException exception) {
                        try {
                              socketChannel = SocketChannel.open(socketAddress);
                        } catch (IOException ignored) { }
                  } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                  }
            }
      }


      //****************************************************************************//


      private void sendByListener(AQPackage aqPackage) {

            if (aqPackage.getCommand() instanceof Login) {
                  if (!aqPackage.getFlag()) loginListener.onError();
                  else loginListener.onSuccessful(aqPackage.getObject());
            } else

            if (aqPackage.getCommand() instanceof Register) {
                  if (!aqPackage.getFlag()) registerListener.onError();
                  else registerListener.onSuccessful(aqPackage.getObject());
            } else

            if (aqPackage.getCommand() instanceof Show) {
                  if (!aqPackage.getFlag()) showListener.onError();
                  else showListener.onSuccessful(aqPackage.getObject());
            } else

            if (aqPackage.getCommand() instanceof Add) {
                  if (!aqPackage.getFlag()) addListener.onError();
                  else addListener.onSuccessful(aqPackage.getObject());
            } else

            if (aqPackage.getCommand() instanceof Clear) {
                  if (!aqPackage.getFlag()) clearListener.onError();
                  else clearListener.onSuccessful(aqPackage.getObject());
            } else

            if (aqPackage.getCommand() instanceof Info) {
                  if (!aqPackage.getFlag()) infoListener.onError();
                  else infoListener.onSuccessful(aqPackage.getObject());
            } else

            if (aqPackage.getCommand() instanceof RemoveByID) {
                  if (!aqPackage.getFlag()) removeIDListener.onError();
                  else removeIDListener.onSuccessful(aqPackage.getObject());
            } else

            if (aqPackage.getCommand() instanceof UpdateByID) {
                  if (!aqPackage.getFlag()) updateListener.onError();
                  else updateListener.onSuccessful(aqPackage.getObject());
            }
      }

      /**
       * Sends AQPack to the bound server in new thread
       */
      private synchronized void send(AbstractCommand command) {

            /* Creating new thread */
            new Thread(() -> {

                  /* Creating new AQPack for sending data and commands */
                  AQPackage aqPack = new AQPackage();
                  aqPack.setCommand(command);
                  aqPack.setUser(user);

                  try { /* Serializing AQPack and sending to the server */
                        socketChannel.write(ByteBuffer.wrap(Serializer.serialize(aqPack)));
                  } catch (Exception ignored) { }
            }).start();
      }


      //****************************************************************************//



      public void sentCommandToServer(AbstractCommand command, EventListener listener) {
            this.setListener(command, listener);
            this.send(command);
      }


      //****************************************************************************//

      private EventListener loginListener;

      private EventListener showListener;

      private EventListener registerListener;

      private EventListener addListener;

      private EventListener clearListener;

      private EventListener infoListener;

      private EventListener removeIDListener;

      private EventListener updateListener;


      private void setListener(AbstractCommand command, EventListener listener) {

            if (command instanceof Login) this.loginListener = listener;

            if (command instanceof Show) this.showListener = listener;

            if (command instanceof Register) this.registerListener = listener;

            if (command instanceof Add) this.addListener = listener;

            if (command instanceof Clear) this.clearListener = listener;

            if (command instanceof Info) this.infoListener = listener;

            if (command instanceof RemoveByID) this.removeIDListener = listener;

            if (command instanceof UpdateByID) this.updateListener = listener;
      }

      //****************************************************************************//

      static class Serializer {

            /**
             * Serializes an object to byte array
             */
            static byte[] serialize(Object object) throws IOException {

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

            /**
             * Deserializes an array bytes to object
             */
            static Object deserialize(byte[] objectBytes)
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
}
