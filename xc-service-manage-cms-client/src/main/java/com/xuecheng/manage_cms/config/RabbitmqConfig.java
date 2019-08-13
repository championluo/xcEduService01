package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    //队列bean的名称
    private static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //与队列对应得交换价的名称
    private static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    //队列的名称
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;

    //路由key
    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    //设置交换机会direct模式 也就是 routing模式
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EX_ROUTING_CMS_POSTPAGE(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE(){
        return new Queue(queue_cms_postpage_name);
    }

    //绑定交换机
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_CMS_POSTPAGE) Queue queue,@Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

}
