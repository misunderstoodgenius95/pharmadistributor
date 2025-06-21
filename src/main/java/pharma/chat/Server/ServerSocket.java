/*
package pharma.chat.Server;

import org.w3c.dom.ls.LSSerializerFilter;
import pharma.Storage.FileStorage;
import pharma.config.auth.UserService;
import pharma.security.Stytch.StytchClient;
import pharma.testChat.ChatMsg;



import javax.net.ServerSocketFactory;
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerSocket {
    private InputStream fileInputStream;;
    private UserService userService;
    private ExecutorService executorService;
    private final AtomicBoolean running = new AtomicBoolean(false);
    public ServerSocket(InputStream fileInputStream, UserService userService) {
        this.fileInputStream = fileInputStream;
        this.userService=userService;
        this.executorService=Executors.newCachedThreadPool();


        try {
            SSLServerSocketFactory socketFactory=createSSlContext();
            start(socketFactory);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }


    }


    public  void start(SSLServerSocketFactory sslServerSocketFactory){


        while(true){

            try(SSLServerSocket serverSocket=(SSLServerSocket)  sslServerSocketFactory.createServerSocket(3000)) {
                System.out.println("Wait Users!");
                SSLSocket sslSocket=(SSLSocket) serverSocket.accept();
                System.out.println("new Client");
                ThreadServerManager serverManager=new ThreadServerManager(sslSocket,userService);
                executorService.submit(serverManager);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }




    }



















    private  SSLServerSocketFactory  createSSlContext(){
        try {
            char[] password = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("serverkeystore.jks"), password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);
            SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
            sslContext.init(kmf.getKeyManagers(), null, null);
            return sslContext.getServerSocketFactory();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        return null;
    }

    private KeyManagerFactory generate_KeyManagmentFactory(KeyStore keyStore){
        KeyManagerFactory km= null;
        try {
            km = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            km.init(keyStore, "NZLR#z*B%3e*t#".toCharArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  km;

    }
    private TrustManagerFactory get_TrustManagmentFactory(KeyStore keyStore){
        TrustManagerFactory tf=null;
        try {
            tf=TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            tf.init(keyStore);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return  tf;
    }

    private KeyStore generate_keyStore(){
        KeyStore keyStore= null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fileInputStream,"NZLR#z*B%3e*t#".toCharArray());
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("Keystore contains alias: " + alias);
                System.out.println("Is key entry: " + keyStore.isKeyEntry(alias));
                System.out.println("Is certificate entry: " + keyStore.isCertificateEntry(alias));
            }


            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return  keyStore;

    }








}
*//*


package pharma.chat.Server;

import pharma.Storage.FileStorage;
import pharma.config.auth.UserService;
import pharma.security.Stytch.StytchClient;

import javax.net.ServerSocketFactory;
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerSocket {
    private InputStream fileInputStream;;
    private SSLServerSocket serverSocket;
    private ExecutorService thread_pool;
    private UserService userService;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public ServerSocket(InputStream fileInputStream, UserService userService) {
        this.fileInputStream = fileInputStream;
        this.userService=userService;
        SSLContext sslContext=createSSlContext();
        ServerSocketFactory socketFactory=sslContext.getServerSocketFactory();

        try {
            serverSocket=(SSLServerSocket) socketFactory.createServerSocket(3000);
            System.out.println(serverSocket.getInetAddress().getHostAddress());
            serverSocket.setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.3"});
            serverSocket.setNeedClientAuth(false); // Set to true for mutual TLS
            serverSocket.setWantClientAuth(false);
            thread_pool=Executors.newCachedThreadPool();
            start();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public  void start(){
        running.set(true);

        while(running.get()){

            try {
                System.out.println("Wait Users!");
                SSLSocket sslSocket=(SSLSocket) serverSocket.accept();
                System.out.println("new Client");
                ThreadServerManager serverManager=new ThreadServerManager(sslSocket,userService);
                thread_pool.execute(serverManager);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }


    }

















    private  SSLContext  createSSlContext(){
        SSLContext sslContext=null;
        KeyStore keyStore=generate_keyStore();
        KeyManagerFactory km=generate_KeyManagmentFactory(keyStore);
        TrustManagerFactory tm=get_TrustManagmentFactory(keyStore);
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(km.getKeyManagers(), tm.getTrustManagers(), new SecureRandom());
        }catch (Exception e){
            e.printStackTrace();
        }
        return sslContext;


    }

    private KeyManagerFactory generate_KeyManagmentFactory(KeyStore keyStore){
        KeyManagerFactory km= null;
        try {
            km = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            km.init(keyStore, "NZLR#z*B%3e*t#".toCharArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  km;

    }
    private TrustManagerFactory get_TrustManagmentFactory(KeyStore keyStore){
        TrustManagerFactory tf=null;
        try {
            tf=TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            tf.init(keyStore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  tf;
    }

    private KeyStore generate_keyStore(){
        KeyStore keyStore= null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fileInputStream,"NZLR#z*B%3e*t#".toCharArray());
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("Keystore contains alias: " + alias);
                System.out.println("Is key entry: " + keyStore.isKeyEntry(alias));
                System.out.println("Is certificate entry: " + keyStore.isCertificateEntry(alias));
            }


            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return  keyStore;

    }








}

*/
