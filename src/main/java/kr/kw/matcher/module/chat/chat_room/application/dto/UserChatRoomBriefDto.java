package kr.kw.matcher.module.chat.chat_room.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import kr.kw.matcher.module.chat.chat.application.dto.ChatBriefDto;
import kr.kw.matcher.module.file.domain_image.domain.DomainType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserChatRoomBriefDto {
    private Long id;
    private Long domainId;
    private DomainType domainType;
    private Long hostUserId;
    private String imageUrl;
    private String name;
    private Boolean allowChatPushYn;
    private ChatBriefDto lastChat;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDt;
    private Long memberCount;

    public static QBean<UserChatRoomBriefDto> getBean(QChatRoomDomain chatRoomDomain,
                                                      QChatRoom chatRoom, QChat chat, QChatRoomMember chatRoomMember) {
        return Projections.bean(UserChatRoomBriefDto.class,
                chatRoom.id,
                chatRoomDomain.domainId,
                chatRoomDomain.domainType,
                chatRoomDomain.userId.as("hostUserId"),
                chatRoom.imageUrl,
                chatRoom.name,
                chatRoomMember.allowChatPushYn,
                ChatBriefDto.getBean(chat).as("lastChat"),
                chatRoom.createdDt,
                chatRoom.memberCount);
    }
}
