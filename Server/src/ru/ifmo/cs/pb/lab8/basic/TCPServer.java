package ru.ifmo.cs.pb.lab8.basic;

import org.apache.log4j.Logger;

import ru.ifmo.cs.pb.lab8.database.Collection;
import ru.ifmo.cs.pb.lab8.database.DBCollection;
import ru.ifmo.cs.pb.lab8.database.DBConfigure;
import ru.ifmo.cs.pb.lab8.database.DBController;
import ru.ifmo.cs.pb.lab8.database.DBUser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TCPServer {


      private static final int PORT = 9090;

      private static final Logger LOGGER = Logger.getLogger(TCPServer.class.getSimpleName());

      
      
      
      private void start() throws IOException {

            /* Opening Selector and ServerSocketChannel */
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            Selector selector = Selector.open();

            /* Setting settings ServerSocketChannel */
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            LOGGER.info("Server successfully connected to the Network...");

            /* Setting connection with Data Base */
            DBConfigure configure = DBConfigure.getConnection(configFilePath);

            DBCollection dbCollection = new DBCollection(configure.getConnection());
            DBUser dbUser = new DBUser(configure.getConnection());

            DBController controller = new DBController(dbCollection, dbUser);

            LOGGER.info("Server successfully connected to the Data Base...");

            /* Loading collection from the Data Base */
            Collection collection = controller.loadCollection();
            
            LOGGER.info("Collection successfully loaded from the Data Base...");

            /* ChannelSet of all available channels */
            Set<SocketChannel> channels = Collections.synchronizedSet(new HashSet<>());

            /* Creating Executor Services */
            ExecutorService receiver = Executors.newFixedThreadPool(10);
            ExecutorService executor = Executors.newFixedThreadPool(10);
            ExecutorService sender = Executors.newFixedThreadPool(10);

            Map<SelectionKey, Future<AQPackage>> receivedData = Collections.synchronizedMap(new HashMap<>());
            Map<SelectionKey, Future<AQPackage>> executedData = Collections.synchronizedMap(new HashMap<>());

            while (true) {
                  selector.select();
                  Iterator keys = selector.selectedKeys().iterator();
                  while (keys.hasNext()) {
                        SelectionKey key = (SelectionKey) keys.next();
                        keys.remove();

                        /* If key is not valid - continues loop */
                        if (!key.isValid()) continue;

                        /* If key if acceptable - registers client */
                        if (key.isAcceptable()) {
                              ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                              try {
                                    /* Setting new user's channel defaults */
                                    SocketChannel socketChannel = serverChannel.accept();
                                    socketChannel.configureBlocking(false);
                                    socketChannel.register(selector, SelectionKey.OP_READ);

                                    LOGGER.info("New user joined to the server " + socketChannel.getRemoteAddress());

                                    /* Adds new user to the set collection */
                                    channels.add(socketChannel);
                              } catch (IOException exception) {

                                    LOGGER.error("Server couldn't accept new user!");
                              }
                        } else

                        /* If key is readable - reads received data */
                        if (key.isReadable()) {
                              SocketChannel socketChannel = (SocketChannel) key.channel();
                              try {
                                    /* ExecutorService starts new callable thread for receiving data*/
                                    receivedData.put(key, receiver.submit(new TCPServerReceiver(socketChannel, channels)));

                                    /* Selection key open to write */
                                    key.interestOps(SelectionKey.OP_WRITE);
                              }  catch (RuntimeException exception) {
                                    LOGGER.warn("User left the server!");
                              }
                        } else

                        /* If key is writeable - writes executed data */
                        if (key.isWritable()) {
                              SocketChannel socketChannel = (SocketChannel) key.channel();
                              try {
                                    /* ExecutorService starts new callable thread for executing data */
                                    if (receivedData.containsKey(key) && receivedData.get(key).isDone()) {
                                          executedData.put(key, executor.submit(new TCPServerExecutor(receivedData.get(key).get(), collection, controller)));
                                          receivedData.remove(key);
                                    }
                                    /* ExecutorService starts new runnable thread for sending data */
                                    if (executedData.containsKey(key) && executedData.get(key).isDone()) {
                                          sender.submit(new TCPServerSender(socketChannel, executedData.get(key).get(), channels, collection));
                                          executedData.remove(key);

                                          /* Selection key open to read */
                                          key.interestOps(SelectionKey.OP_READ);
                                    }
                              } catch (RuntimeException exception) {
                                    LOGGER.warn("User left the server!");

                              } catch (InterruptedException | ExecutionException exception) {
                                   exception.printStackTrace();
                                    LOGGER.warn("Thread was interrupted!");
                              }
                        }
                  }
            }
      }

      /**
       * Absolute path to the .properties file
       */
      private static final String configFilePath = "D:/Projects/Java/#Programming_Course-1/UserInterfaceMode/Server/src/ru/ifmo/cs/pb/lab8/database/configuration.properties";

      public static void main(String[] args) throws IOException {
            new TCPServer().start();
      }
}
