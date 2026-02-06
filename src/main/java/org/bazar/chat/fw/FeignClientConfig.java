package org.bazar.chat.fw;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("org.bazar.chat.adapter")
public class FeignClientConfig {
}
