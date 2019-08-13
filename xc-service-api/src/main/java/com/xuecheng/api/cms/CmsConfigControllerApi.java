package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="cms配置管理接口")
public interface CmsConfigControllerApi {

    @ApiOperation(value="通过id查询cms配置信息")
    public CmsConfig getModel(String id);
}
