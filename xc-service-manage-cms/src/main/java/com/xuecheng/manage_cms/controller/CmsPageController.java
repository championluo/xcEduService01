package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {

//        QueryResult queryResult = new QueryResult();
//        queryResult.setTotal(2);
//
//        List list = new ArrayList();
//        CmsPage cmsPage = new CmsPage();
//        cmsPage.setPageName("测试页面");
//        list.add(cmsPage);
//
//        queryResult.setList(list);
//
//        QueryResponseResult result = new QueryResponseResult(CommonCode.SUCCESS,queryResult);

        QueryResponseResult list = pageService.findList(page, size,queryPageRequest);

        return list;
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        CmsPageResult add = pageService.add(cmsPage);
        return add;
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        CmsPage cmsPage = pageService.findById(id);
        return cmsPage;
    }

    @Override
    @PutMapping("/edit/{id}") //这里使用put方法来更新表单,表单中的id通过url来传递,cmspage对象通过RequestBody获取
    public CmsPageResult edit(@PathVariable("id") String id,@RequestBody CmsPage cmsPage) {
        CmsPageResult result = pageService.update(id, cmsPage);
        return result;
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable  String id) {
        return pageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return pageService.postPage(pageId);
    }

}
