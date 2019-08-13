package com.example.testrabbitmqproducer;

import com.example.testrabbitmqproducer.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topics_springboot {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send_mail(){
        for(int i=0;i<5;i++) {
            String message = "sms email inform to user"+i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPCIS_INFORM,"inform.sms.email",message);
            System.out.println("Send message is: '"+ message +"'");
        }
    }
}
