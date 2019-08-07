package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    CmsPage findByPageName(String pageName) ;

    CmsPage findByPageNameAndPageType(String pageName,String pageType);

    int countBySiteIdAndPageType(String siteId,String pageType);

    Page<CmsPage> findBySiteIdAndPageType(String siteId, String pageType, Pageable pageable);

    CmsPage findBySiteIdAndPageNameAndPageWebPath(String siteId,String pageName,String pageWebPath);
}
