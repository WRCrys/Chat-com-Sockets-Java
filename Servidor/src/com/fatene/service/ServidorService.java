/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fatene.service;

import com.fatene.bean.ChatMessage;
import com.fatene.bean.ChatMessage.Action;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cryst
 */
public class ServidorService {
    private ServerSocket serversocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
    
    public ServidorService(){
        try {
            serversocket = new ServerSocket(5555);
            System.out.println("Servidor ON!");
            while(true){
                socket = serversocket.accept();
                
                new Thread(new ListenerSocket(socket)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private class ListenerSocket implements Runnable{
        private ObjectOutputStream output;
        private ObjectInputStream input;
        
        public ListenerSocket(Socket socket){
            try {
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            ChatMessage message = null;
            try {
                
                while((message = (ChatMessage) input.readObject())!=null){
                    Action action = message.getAction();
                    if(action.equals(Action.CONNECT)){
                        boolean isConnect = connect(message, output);
                        if (isConnect){
                            mapOnlines.put(message.getNome(), output);
                        }
                    }else if(action.equals(Action.DISCONNECT)){
                        disconnect(message, output);
                        return;
                        
                    }else if(action.equals(Action.SEND_ONE)){
                        sendOne(message, output);
                        
                    }else if(action.equals(Action.SEND_ALL)){
                        sendAll(message);
                        
                    }else if(action.equals(Action.USERS_ONLINE)){
                        
                    }
                }
            } catch (IOException ex) {
                mapOnlines.remove(message.getNome());
                disconnect(message, output);
                System.out.println(message.getNome()+" deixou o chat!");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    private boolean connect(ChatMessage message, ObjectOutputStream output){
        if(mapOnlines.size() == 0){
            message.setTxt("YES");
            sendOne(message, output);
            return true;
        }
        for(Map.Entry<String, ObjectOutputStream> kv:mapOnlines.entrySet()){
            if(kv.getKey().equals(message.getNome())){
                message.setTxt("NO");
                sendOne(message, output);
                return false;
            } else {
                message.setTxt("YES");
                sendOne(message, output);
                return true;
            }
        }
        sendOne(message, output);
        return false;
    }
    private void disconnect(ChatMessage message, ObjectOutputStream output){
        mapOnlines.remove(message.getNome());
        
        message.setTxt(" deixou o chat!");
        
        message.setAction(Action.SEND_ONE);
        
        sendAll(message);
        
        System.out.println("User "+message.getNome()+" deixou a sala");
    }
    private void sendOne(ChatMessage message, ObjectOutputStream output){
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void sendAll(ChatMessage message) {
        for(Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()){
            if (!kv.getKey().equals(message.getNome())){
                message.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private void sendOnlines(){
        Set<String> setNames = new HashSet<String>();
        for(Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()){
            setNames.add(kv.getKey());
        }
        
        ChatMessage message = new ChatMessage(); 
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);
        for(Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()){
            message.setNome(kv.getKey());
            try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
    }
}
