package ${groupId}.appArchetype;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@ComponentScan({"${groupId}", "org.elastxy.core", "org.elastxy.distributed"})
public class TestConfig {

    @Bean(name = "springCM")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("${groupId}-cache");
    }
}
