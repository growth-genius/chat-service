package com.gg.tgather.chattingservice.infra.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("chat")
public class ChatProperties {

    private String roomName;

}
