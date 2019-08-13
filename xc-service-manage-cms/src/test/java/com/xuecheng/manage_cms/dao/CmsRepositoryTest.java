package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
//        List<CmsPage> cmsPages = cmsPageRepository.findAll();
//        System.out.println(cmsPages);

        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher.withMatcher("pageAlise",ExampleMatcher.GenericPropertyMatchers.contains());

        //页面别名模糊查询,需要自定义字符串的匹配器实现模糊查询
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含
        //ExampleMatcher.GenericPropertyMatchers.startsWith(); //开头匹配

        //条件值
        CmsPage cmsPage = new CmsPage();
        //站点ID
        cmsPage.setSiteId("");
        //模板ID
        cmsPage.setTemplateId("");
//        cmsPage.setPageAliase("分类导航");

        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Pageable pageable = new PageRequest(0, 10);
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        System.out.println(all);
    }

    @Test
    public void testFindByPage(){
        Pageable pageable = PageRequest.of(1, 10);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);
        System.out.println(cmsPages.getTotalPages());

    }

    @Test
    public void testUpdate(){
        Optional<CmsPage> optional = cmsPageRepository.findById("");
        if (!optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面2");
            cmsPageRepository.save(cmsPage);
        }
    }

    @Test
    public void testAbc() {
        System.out.println(CmsRepositoryTest.class);
    }
}
