package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    public QueryResponseResult findList(int page, int size, QueryPageRequest request){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();

        if (StringUtils.isNotEmpty(request.getSiteId())) {
            cmsPage.setSiteId(request.getSiteId());
        }
        //设置模板id作为查询条件
        if(StringUtils.isNotEmpty(request.getTemplateId())){
            cmsPage.setTemplateId(request.getTemplateId());
        }
        //设置页面别名作为查询条件
        if(StringUtils.isNotEmpty(request.getPageAliase())){
            cmsPage.setPageAliase(request.getPageAliase());
        }

        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);

        if (page<=0){
            page = 1;
        }
        page = page -1;
        if (size<=0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> pages = cmsPageRepository.findAll(example,pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pages.getContent());
        queryResult.setTotal(pages.getTotalElements());
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    public CmsPageResult add(CmsPage cmsPage) {

//        CmsPageResult cmsPageResult = new CmsPageResult();

        //校验页面是否存在
        /*
        * 这里最好要进行入参校验
        * */
        CmsPage page = cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath());
        if (page!=null) {
            return new CmsPageResult(CommonCode.FAIL, null);
        }

        //这行代码的主要用途是用来保证插入的主键有MongoDB来自动生成
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        CmsPageResult pageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        return pageResult;
    }


}
