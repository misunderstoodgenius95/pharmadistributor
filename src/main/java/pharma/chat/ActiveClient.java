/*
package pharma.chat;

import pharma.chat.Server.ChatMsg;
import pharma.chat.Server.ThreadServerManager;
//import pharma.chat.Server.ThreadServerManager;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class ActiveClient {
    //                            Seller  Pharmacists
    private  static  ConcurrentMap<String, List<String>> chat_map =new ConcurrentHashMap<>();
    private  static ConcurrentMap<String, ThreadServerManager>  seller_map=new ConcurrentHashMap<>();
    private static  ConcurrentMap<String,ThreadServerManager> pharmacist_map=new ConcurrentHashMap<>();



    public  static List<String> getListPharmacistBySeller(String seller){

        if(!chat_map.containsKey(seller)){

             throw new  IllegalArgumentException("seller is not present");
        }
        return  chat_map.get(seller);
    }
    public static  boolean is_chat_empty(){

        return chat_map.isEmpty();

    }
    public static ThreadServerManager  getInstanceThreadPharmacistByEmail(String email){
         return pharmacist_map.get(email);


    }    public static ThreadServerManager  getInstanceSellerByEmail(String email){
        return seller_map.get(email);


    }
    public static  String get_random_seller(){
        if(ActiveClient.seller_map.isEmpty()){
            throw  new RuntimeException("Seller is not present");

        }
       ArrayList<String> keys=new ArrayList<>(ActiveClient.seller_map.keySet());
        Random random=new Random();
        int random_value=random.nextInt(ActiveClient.seller_map.size());
            return  keys.get(random_value);


    }
    public static void setJoinChat(String pharmacist,String seller){
        if(chat_map.containsKey(seller)){
            List<String> pharmacists= chat_map.get(seller);
            pharmacists.add(pharmacist);
        }else {
            List<String> list=new ArrayList<>();
            list.add(pharmacist);
            chat_map.put(seller,list);

        }


    }
    public static  List<String> wait_pharmacist(){

        if(pharmacist_map.isEmpty()){
             return Collections.emptyList();
        }
        if(chat_map.isEmpty()){
            return pharmacist_map.keySet().stream().toList();
        }
        List<String> pharmacist=new ArrayList<>();
        chat_map.keySet().forEach(
                seller_key->{
                    List<String> pharmacist_list=chat_map.get(seller_key);
                    pharmacist.addAll(pharmacist_map.keySet().stream().filter(value->!pharmacist_list.contains(value)).toList());
                }

        );
            return pharmacist;

    }

    public static ConcurrentMap<String, ThreadServerManager> getSeller_map() {
        return seller_map;
    }

    public static boolean  setSeller_map(String email,ThreadServerManager threadServerManager) {
           return seller_map.putIfAbsent(email, threadServerManager) == null;
    }

    public static ConcurrentMap<String, ThreadServerManager> getPharmacist_map() {
        return pharmacist_map;
    }

    public static boolean setPharmacist_map(String email,ThreadServerManager threadServerManager) {
        return ActiveClient.pharmacist_map.putIfAbsent(email,threadServerManager)==null;
    }

    public static  void removeSeller(String email){
        seller_map.remove(email);

    }
    public static  void removePharmacist(String email){
        pharmacist_map.remove(email);
    }

    public static void removeSellerChat(String email){
        chat_map.remove(email);

    }
    public static  void removePharmacistIntoChat(String email){
        chat_map.keySet().forEach(
                seller_key->{
                    List<String> pharmacist_list=chat_map.get(seller_key);
                    if(pharmacist_list.contains(email)){
                        pharmacist_list.remove(email);

                    }

                }

        );
    }
}
*/
