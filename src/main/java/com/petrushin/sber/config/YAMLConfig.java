package com.petrushin.sber.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "config")
@PropertySource(value = "classpath:config.yml", factory = YAMLPropertySourceFactory.class)
public class YAMLConfig {
    /**
     * Откуда брать файлы
     */
    @Getter
    @Setter
    private String to;
    /**
     * Куда перемещать
     */
    @Getter
    @Setter
    private String from;
    /**
     * С каким расширением брать файлы
     */
    @Getter
    @Setter
    private String ext;
}
