package com.jones.mars.service;

import com.jones.mars.model.*;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.query.*;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HomePageService{
    @Autowired
    private EnterpriseMapper mapper;
    @Autowired
    private BlockMapper blockMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private EnterpriseShownMapper enterpriseShownMapper;
    @Autowired
    private AppConstMapper appConstMapper;
    @Autowired
    private NewsMapper newsMapper;

    private Map<Integer, Enterprise> plateformEnterpriseMap;
    private List<Project> recommendProjects;
    private List<Block> defaultBlocks;
    private List<EnterpriseShown> enterpriseShowns;
    private List<String> serviceSuperiority;
    private List<String> choiceReason;
    private List<String> serviceProvide;
    private List<String> guideHotspotImage;
    private List<String> navImage;
    private List<String> newsDefaultImage;
    private List<BlockModule> blockModuleList;
    private Map<String, List<String>> constMap = new HashMap<>();
    private Map<String, String> contactInfo;
    private Map<String, List<String>> appDefaultInfo;
    private String icpInfo;


    @PostConstruct
    @Scheduled(cron = "0 0 2 * * ?")
    public void refreshInitData(){
        refreshAppConst();
        refreshPlateformEnterprise();
        refreshNavImage();
        refreshRecommendProjects();
        refreshEnterpriseShowns();
        refreshICPInfo();
        refreshContactInfo();
        refreshGuideHotspotImage();
        refreshServiceProvide();
        refreshServiceSuperiority();
        refreshChoiceReason();
        refreshBlockModule();
        refreshNewsDefaultImage();
        refreshAppDefaultInfo();
        refreshDefaultBlocks();
    }

    private void refreshPlateformEnterprise(){
        Map<Integer, Enterprise> plateformEnterpriseMap = mapper.findAllName(EnterpriseQuery.builder().plateformFlg(CommonConstant.PLATEFROM).build())
                .stream().collect(Collectors.toMap(Enterprise::getId, p->p));
        if(plateformEnterpriseMap.keySet().size() > 0) {
            List<Block> blockList = blockMapper.findAll(BlockQuery.builder().enterpriseIds(new ArrayList<>(plateformEnterpriseMap.keySet())).build());
            blockList.forEach(p -> {
                if (plateformEnterpriseMap.containsKey(p.getEnterpriseId())) {
                    plateformEnterpriseMap.get(p.getEnterpriseId()).getBlockList().add(p);
                }
            });
        }
        this.plateformEnterpriseMap = plateformEnterpriseMap;
    }

    /**
     * 默认船只
     */
    private void refreshDefaultBlocks(){
        List<Integer> ids = constMap.get(AppConst.APP_DEFAULT_BLOCK).stream().map(p-> Integer.parseInt(p)).collect(Collectors.toList());//.stream().map(p-> Integer.parseInt(p.getValue())).collect(Collectors.toList());
        List<Block> blocks = blockMapper.findAll(BlockQuery.builder().blockIds(ids).build());
        for(int i=0; i<ids.size(); i++){
            for(Block block: blocks){
                if(block.getId().equals(ids.get(i))){
                    block.setSeq(i);
                    break;
                }
            }
        }
        this.defaultBlocks = blocks;
    }

    /**
     * 船福推荐
     */
    private void refreshRecommendProjects(){
        List<Integer> ids = constMap.get(AppConst.HOME_RECOMMEND_PROJECT).stream().map(p-> Integer.parseInt(p)).collect(Collectors.toList());//.stream().map(p-> Integer.parseInt(p.getValue())).collect(Collectors.toList());
        List<Project> projects = projectMapper.findAll(ProjectQuery.builder().ids(ids).build());
        List<Project> results = new ArrayList<>();
        if(projects.size() > 0) {
            Map<Integer, Project> projectIdMap = projects.stream().collect(Collectors.toMap(Project::getId, p -> p));
            ids.forEach(id -> {
                if (projectIdMap.get(id).getEnterprisePlateformFlg().equals(CommonConstant.PLATEFROM) || projectIdMap.get(id).getPublicFlg().equals(Project.PUBLIC)) {
                    results.add(projectIdMap.get(id));
                }
            });
        }
        this.recommendProjects = results;
    }

    /**
     * 服务优势
     */
    private void refreshServiceSuperiority(){
        this.serviceSuperiority = constMap.get(AppConst.HOME_SERVICE_SUPERIORITY);
    }

    /**
     * 提供服务
     */
    private void refreshServiceProvide(){
        this.serviceProvide = constMap.get(AppConst.HOME_SERVICE_PROVIDE);
    }

    /**
     * 选择理由
     */
    private void refreshChoiceReason(){
        this.choiceReason = constMap.get(AppConst.HOME_CHOICE_REASON);
    }
    /**
     * 资讯默认图片
     */
    private void refreshNewsDefaultImage(){
        this.newsDefaultImage = constMap.get(AppConst.NEWS_DEFAULT_IMAGE);
    }

    /**
     * 选择理由
     */
    private void refreshNavImage(){
        this.navImage = constMap.get(AppConst.HOME_NAV_IMAGE);
    }

    private void refreshContactInfo(){
        Map<String, String> contactInfo = new HashMap<>();
        contactInfo.put("name", constMap.get(AppConst.HOME_CONTACT_ENTERPRISE_NAME).get(0));
        contactInfo.put("address", constMap.get(AppConst.HOME_CONTACT_ADDRESS).get(0));
        contactInfo.put("mobile", constMap.get(AppConst.HOME_CONTACT_MOBILE).get(0));
        contactInfo.put("email", constMap.get(AppConst.HOME_CONTACT_EMAIL).get(0));
        contactInfo.put("wechat", constMap.get(AppConst.HOME_ENTERPRISE_WECHAT).get(0));
        this.contactInfo = contactInfo;
    }

    private void refreshICPInfo(){
        this.icpInfo = constMap.get(AppConst.HOME_INTERNET_CONTENT_PROVIDER).get(0);
    }

    private void refreshGuideHotspotImage(){
        this.guideHotspotImage = constMap.get(AppConst.PROJECT_GUIDE_HOTSPOT_IMAGE);
    }
    private void refreshBlockModule(){
        List<Block> blocks = blockMapper.findBlockModule(BlockQuery.builder().build());
        List<BlockModule> modules = new ArrayList<>();
        for(Block block:blocks){
            for(BlockModule module: block.getModuleList()){
                module.setBlockName(block.getName());
            }
            modules.addAll(block.getModuleList());
        }
        this.blockModuleList = modules;
    }
    /**
     * 企业入驻
     */
    private void refreshEnterpriseShowns(){
        List<Integer> ids = constMap.get(AppConst.HOME_ENTERPRISE_SHOWN).stream().map(p-> Integer.parseInt(p)).collect(Collectors.toList());//.stream().map(p-> Integer.parseInt(p.getValue())).collect(Collectors.toList());
        this.enterpriseShowns = enterpriseShownMapper.findAll(EnterpriseShownQuery.builder().ids(ids).build());
    }

    private void refreshAppDefaultInfo(){
        Map<String, List<String>> result = new HashMap<>();
        for(String key : constMap.keySet()){
            if(key.startsWith(AppConst.APP_DEFAULT_PREFIX)){
                result.put(key, constMap.get(key));
            }
        }
        this.appDefaultInfo = result;
    }

    private void refreshAppConst(){
        List<AppConst> appConsts = appConstMapper.findAll(AppConstQuery.builder().build());
        Map<String, List<String>> result = new HashMap<>();
        appConsts.forEach(p->{
            if(!result.containsKey(p.getName())){
                result.put(p.getName(), new ArrayList<>());
            }
            result.get(p.getName()).add(p.getValue());
        });
        this.constMap = result;
    }


    public BaseResponse enterpriseShown(){
        return BaseResponse.builder().data(this.enterpriseShowns).build();
    }

    public BaseResponse plateformEnterprise(){
        return BaseResponse.builder().data(this.plateformEnterpriseMap.values()).build();
    }
    public BaseResponse serviceSuperiority(){
        return BaseResponse.builder().data(this.serviceSuperiority).build();
    }
    public BaseResponse recommendProjects(){
        return BaseResponse.builder().data(this.recommendProjects).build();
    }
    public BaseResponse appDefaultInfo(){
        return BaseResponse.builder().data(this.appDefaultInfo).build();
    }
    public BaseResponse appDefaultBlocks(){
        return BaseResponse.builder().data(this.defaultBlocks).build();
    }

    public BaseResponse homePageInfo(){
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("recommendProject", this.recommendProjects);
        pageInfo.put("serviceSuperiority", this.serviceSuperiority);
        pageInfo.put("serviceProvide", this.serviceProvide);
        pageInfo.put("choiceReason", this.choiceReason);
        pageInfo.put("enterpriseShown", this.enterpriseShowns);
        pageInfo.put("moduleInfo", this.plateformEnterpriseMap.values());
        pageInfo.put("guideHotspotImage", this.guideHotspotImage);
        pageInfo.put("icpInfo", this.icpInfo);
        pageInfo.put("contactInfo", this.contactInfo);
        pageInfo.put("appDefaultInfo", this.appDefaultInfo);
        pageInfo.put("defaultBlocks", this.defaultBlocks);
        return BaseResponse.builder().data(pageInfo).build();
    }

    public BaseResponse appPageInfo(){
        Map<String, Object> pageInfo = new HashMap<>();
        NewsQuery newsQuery = NewsQuery.builder().status(News.STATUS_PUBLISHED).build();
        newsQuery.setSize(3);
        pageInfo.put("newsList", newsMapper.findList(newsQuery));
        pageInfo.put("blockModuleList", this.blockModuleList);
        pageInfo.put("recommendProject", this.recommendProjects);
        pageInfo.put("navImage", this.navImage);
        pageInfo.put("newsDefaultImage", this.newsDefaultImage);
        pageInfo.put("appDefaultInfo", this.appDefaultInfo);
        pageInfo.put("defaultBlocks", this.defaultBlocks);
        return BaseResponse.builder().data(pageInfo).build();
    }

}

