package org.elastxy.web.distributed;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messaging.properties")
public class DistributedMessagingConfig {

}
