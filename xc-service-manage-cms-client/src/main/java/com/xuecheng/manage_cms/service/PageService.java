package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class PageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);

    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    //将页面html保存到页面的物理路径下
    public void  savePageToServerPath(String pageId){

        CmsPage cmsPage = this.findCmsPageById(pageId);

        //获取要从GridFs中下载文件的文件id
        String htmlFileId = cmsPage.getHtmlFileId();

        //从gridFs中查询html文件
        InputStream inputStream = this.findFileById(htmlFileId);
        if (inputStream == null) {
            //这里直接在日志中报错,不在抛出异常
            LOGGER.error("findFileById InputStream is null, htmlFileId:{}",htmlFileId);
            return ;
        }

        //获取页面所属站点
        CmsSite cmsSite = this.findCmsSiteById(cmsPage.getSiteId());
        //后去页面要保存的到服务器的物理路径
        String pagePath = cmsPage.getPagePhysicalPath()+cmsPage.getPageName();

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream,fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private CmsPage findCmsPageById(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    private CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(siteId);
        if (cmsSiteOptional.isPresent()) {
            return cmsSiteOptional.get();
        }
        return null;
    }

    private InputStream findFileById(String htmlFileId) {
        //文件对象
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        //打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //定义GridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
