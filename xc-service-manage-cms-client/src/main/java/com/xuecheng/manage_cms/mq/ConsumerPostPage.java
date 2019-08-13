package com.xuecheng.manage_cms.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class ConsumerPostPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);

    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private PageService pageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg) {

        //解析消息
        Map parseObject = JSON.parseObject(msg, Map.class);
        LOGGER.info("ConsumerPostPage.postPage 获取到的消息是: {}",msg.toString());

        //取出页面id
        String pageId = (String) parseObject.get("pageId");

        //查询页面信息
        Optional<CmsPage> pageOptional = cmsPageRepository.findById(pageId);
        if (!pageOptional.isPresent()) {
            LOGGER.error("ConsumerPostPage.postPage 获取到的消息无法获取页面信息 pageId:{}",pageId);
        }

        pageService.savePageToServerPath(pageId);
    }
}
