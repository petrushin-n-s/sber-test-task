package com.petrushin.sber;

import com.petrushin.sber.config.YAMLConfig;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger(MyRoute.class);
    @Autowired
    private YAMLConfig myConfig;

    @Override
    public void configure() {
        logger.info(">>> Configuring...");
        logger.info(String.format(">>> from: %s, to: %s, ext: %s",
                myConfig.getFrom(), myConfig.getTo(), myConfig.getExt()));
        from("file://" + myConfig.getFrom() + "?includeExt=" + myConfig.getExt())
                .routeId("File processing")
                .log(">>> Moving file: ${in.headers.CamelFileName}")
                .to("log:?showBody=true&showHeaders=true")
                .to("file://C:/Users/User/IdeaProjects/sber/files/to");
    }
}