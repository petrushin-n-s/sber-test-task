package com.petrushin.sber;

import com.petrushin.sber.config.YAMLConfig;
import org.apache.camel.*;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс с тестами
 */
@CamelSpringBootTest
@SpringBootTest(classes = SberApplication.class)
class SberApplicationTests {
    @Autowired
    private ProducerTemplate producerTemplate;
    @Autowired
    private CamelContext context;
    @Autowired
    private YAMLConfig myConfig;

    /**
     * Должен отправится
     */
    @Test
    public void testSendTxt() {
        NotifyBuilder notification = new NotifyBuilder(context)
                .from("file://" + myConfig.getFrom() + "?includeExt=" + myConfig.getExt())
                .fromRoute("File processing")
                .wereSentTo("file://" + myConfig.getTo())
                .whenDone(1)
                .create();

        producerTemplate.sendBodyAndHeader("file://" + myConfig.getFrom(), "test", Exchange.FILE_NAME, "file.txt");

        boolean done = notification.matches(1, TimeUnit.SECONDS);
        assertTrue(done);
    }

    /**
     * Не должен отправится
     */
    @Test
    public void testSendJs() {
        NotifyBuilder notification = new NotifyBuilder(context)
                .from("file://" + myConfig.getFrom() + "?includeExt=" + myConfig.getExt())
                .fromRoute("File processing")
                .wereSentTo("file://" + myConfig.getTo())
                .whenDone(1)
                .create();

        producerTemplate.sendBodyAndHeader("file://" + myConfig.getFrom(), "other test", Exchange.FILE_NAME, "dev.js");

        boolean done = notification.matches(1, TimeUnit.SECONDS);
        assertFalse(done);
    }

    @Test
    public void shouldAutowireProducerTemplate() {
        assertNotNull(producerTemplate);
    }

}
