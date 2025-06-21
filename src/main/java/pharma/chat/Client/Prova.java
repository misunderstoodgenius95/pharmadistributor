package pharma.chat.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Prova {

private ExecutorService executors;

    public Prova() {
        executors=Executors.newCachedThreadPool();
        executors.submit(new ReadThread());
    }
    private  class ReadThread implements Runnable{


        @Override
        public void run() {
            while(true) {

                System.out.println("exit");
            }
        }
    }

    public static void main(String[] args) {
        Prova prova=new Prova();
    }



}
