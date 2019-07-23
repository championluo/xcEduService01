package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;

//封装页面查询条件
@Data
public class QueryPageRequest extends RequestData {

    private String siteId;

    private String pageId;

    private String pageName;

    private String pageAlaes;

    private String templateId;
}
