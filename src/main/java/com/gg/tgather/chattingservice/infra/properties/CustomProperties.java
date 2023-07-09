package com.gg.tgather.chattingservice.infra.properties;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("custom")
public class CustomProperties {

    private List<String> hosts;

}
