package kr.kw.matcher.module.chat;

import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.UUID;

@Setter @Getter
public class ChatRoom implements Serializable {


    private static final long serialVersionUID = -8726302915666558061L;

    private String roomId;
    private String name;

    public static ChatRoom create(String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}
