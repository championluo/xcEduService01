package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.Response;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

//,description = "cms页面管理接口，提供页面的增、删、改、查"
@Api(value="cms页面管理接口")
public interface CmsPageControllerApi {
    @ApiOperation(value="分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation(value="新增页面接口")
    public CmsPageResult add(CmsPage cmsPage);

    @ApiOperation(value="根据id查询页面")
    public CmsPage findById(String id);

    @ApiOperation(value="编辑页面")
    public CmsPageResult edit(String id,CmsPage cmsPage);

    @ApiOperation(value="删除页面")
    public ResponseResult delete(String id);

    @ApiOperation(value="页面发布")
    public ResponseResult post(String pageId);
}
