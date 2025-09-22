package pharma.chat;

//import pharma.chat.Server.ThreadServerManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

public class ActiveClient {
    //                            Seller  Pharmacists
    private  static  ConcurrentMap<String, List<String>> chat_map =new ConcurrentHashMap<>();
    private  static ConcurrentMap<String, ThreadServerManager>  seller_map=new ConcurrentHashMap<>();
    private static  ConcurrentMap<String, ThreadServerManager> pharmacist_map=new ConcurrentHashMap<>();
    private static  final Logger log=Logger.getLogger(ActiveClient.class.getName());

    public  static List<String> getListPharmacistBySeller(String seller){

        if(!chat_map.containsKey(seller)){

             throw new  IllegalArgumentException("seller is not present");
        }
        return  chat_map.get(seller);
    }
    public static  boolean is_chat_empty(){

        return chat_map.isEmpty();

    }
    public static boolean is_pharmacist_is_present(String pharmacist){
        return pharmacist_map.containsKey(pharmacist);

    }





    public static ThreadServerManager getInstanceThreadPharmacistByEmail(String email){
         return pharmacist_map.get(email);


    }    public static ThreadServerManager getInstanceSellerByEmail(String email){
        return seller_map.get(email);


    }
    public static  Optional<String> get_random_seller(){
        if(ActiveClient.seller_map.isEmpty()){
            return Optional.empty();

        }
        Random random=new Random();
        ArrayList<String> keys=new ArrayList<>(ActiveClient.seller_map.keySet());
        int random_value=random.nextInt(ActiveClient.seller_map.size());
        return  Optional.of(keys.get(random_value));


    }
    public static void setJoinChat(String pharmacist,String seller)  {
        if(pharmacist_map.get(pharmacist)==null && seller_map.get(seller)==null){
            throw new IllegalArgumentException("Seller or Pharmacist cannot Present ");
        }
        // if seller is present -> obtein the seller and add the pharmacist
        if(chat_map.containsKey(seller)){
            List<String> pharmacists= chat_map.get(seller);
            pharmacists.add(pharmacist);
        }else {
            List<String> list=new ArrayList<>();
            list.add(pharmacist);
            chat_map.put(seller,list);
        }
        listener_msg(pharmacist,seller);
    }
    private static void listener_msg(String pharmacist,String seller){
    ThreadServerManager serverManager_seller=ActiveClient.getInstanceSellerByEmail(seller);
    ThreadServerManager serverManager_pharmacist=ActiveClient.getInstanceThreadPharmacistByEmail(pharmacist);
    serverManager_pharmacist.sendMessage(ActiveClient.create_msg("Join With Seller:"+seller, Command.JOIN_SUCCESS,"","SERVER",pharmacist));
    serverManager_seller.sendMessage(ActiveClient.create_msg("Join with Pharmacist:"+pharmacist, Command.JOIN_SUCCESS,"","SERVER",seller));




    }
    private static ChatMsg create_msg(String message, Command command, String jwt, String sender, String receiver){
        return new ChatMsg(jwt,message,command,sender,receiver);

    }






    public static  String  get_SellerJoinChat(String pharma_email){

            if (pharmacist_map.get(pharma_email) == null) {
                throw new IllegalArgumentException("pharma email not found");

            }

         return ActiveClient.chat_map.keySet().stream().filter(seller->chat_map.get(seller).contains(pharma_email)).findFirst().get();

    }

    public static List<String> get_pharmacists_byseller(String seller_email){
        if(ActiveClient.seller_map.get(seller_email)==null){
            throw new IllegalArgumentException("Seller not present");

        }
        return ActiveClient.chat_map.get(seller_email);

    }
    /**
     * Get Random pharmacist  that do not joined into chat
     * @return
     */
    public static  Optional<String> random_pharmacist(){
        Random random=new Random();
        if(pharmacist_map.isEmpty()){
             return Optional.empty();
        }
        if(chat_map.isEmpty() &&  !pharmacist_map.isEmpty()){
            List<String> list=new ArrayList<>(pharmacist_map.keySet());
            return Optional.of( list.get(random.nextInt(0,list.size())));

        }else{
            // Find pharmacist that not in list
            Set<String> pharmacist_joined=new HashSet<>();
            // ChatPharmacist cannot emptys
            for(String seller: chat_map.keySet()){
                pharmacist_joined.addAll(chat_map.get(seller));

            }
            List<String> pharmacist_not_joined=pharmacist_map.keySet().stream().filter(pharmacist->!pharmacist_joined.contains(pharmacist)).toList();
           return  Optional.of(pharmacist_not_joined.get(random.nextInt(0, pharmacist_not_joined.size())));

        }


    }




    public static ConcurrentMap<String, ThreadServerManager> getSeller_map() {
        return seller_map;
    }

    public static void  setSeller_map(String email, ThreadServerManager threadServerManager) {


       seller_map.putIfAbsent(email, threadServerManager);

    }

    public static ConcurrentMap<String, ThreadServerManager> getPharmacist_map() {
        return pharmacist_map;
    }

    public static void setPharmacist_map(String email, ThreadServerManager threadServerManager) {
         ActiveClient.pharmacist_map.putIfAbsent(email,threadServerManager);
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
