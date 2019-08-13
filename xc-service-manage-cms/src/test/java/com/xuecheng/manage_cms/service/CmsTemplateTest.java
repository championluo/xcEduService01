package com.xuecheng.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsTemplateTest {

    @Autowired
    PageService pageService;

    @Test
    public void testFreemarker(){
        String html = pageService.getPageHtml("5d4d7dcfba748f1c20fea91e");
        System.out.println(html);
    }
}
