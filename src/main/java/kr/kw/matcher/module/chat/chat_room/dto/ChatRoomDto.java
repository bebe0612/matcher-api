package kr.kw.matcher.module.chat.chat_room.dto;

import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {

    private Long id;
    private Long hostId;
    private String name;
    private Long memberCount;
    private LocalDateTime createdDt;
    private Boolean deleted;

    public static ChatRoomDto of(Long id, Long hostId, String name, Long memberCount, LocalDateTime date, Boolean deleted) {
        return ChatRoomDto.builder()
                .id(id)
                .hostId(hostId)
                .name(name)
                .memberCount(memberCount)
                .createdDt(date)
                .deleted(deleted)
                .build();
    }

    public ChatRoom toEntity(){
        return ChatRoom.of(hostId, name);
    }

    public static ChatRoomDto from(ChatRoom chatRoom){
        return ChatRoomDto.of(chatRoom.getId(), chatRoom.getHostId(), chatRoom.getName(),
                                chatRoom.getMemberCount(), chatRoom.getCreatedDt(), chatRoom.getDeleted());
    }
}
