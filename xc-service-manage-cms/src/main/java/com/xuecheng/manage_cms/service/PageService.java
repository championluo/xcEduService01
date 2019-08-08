package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public CmsPage findById(String id) {
        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    public CmsPageResult update(String id,CmsPage cmsPage) {
        CmsPage page = this.findById(id);

        if (page != null) {
            //准备更新数据
            //设置要修改的数据
            //更新模板id
            page.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            page.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            page.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            page.setPageName(cmsPage.getPageName());
            //更新访问路径
            page.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            //更新成功后返回更新的字段
            CmsPage save = cmsPageRepository.save(page);
            if (save != null) {
                return new CmsPageResult(CommonCode.SUCCESS,save);
            }
        }
        //返回失败
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    public ResponseResult delete(String id) {
        CmsPage page = this.findById(id);

        if (page != null) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
