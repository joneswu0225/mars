package com.jones.mars.service;

import com.jones.mars.model.Block;
import com.jones.mars.model.Enterprise;
import com.jones.mars.model.Project;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.query.EnterpriseQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.query.ProjectQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BlockMapper;
import com.jones.mars.repository.EnterpriseMapper;
import com.jones.mars.repository.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomePageService{
    @Autowired
    private EnterpriseMapper mapper;
    @Autowired
    private BlockMapper blockMapper;
    @Autowired
    private ProjectMapper projectMapper;

    private Map<Integer, Enterprise> plateformEnterpriseMap;
    private List<Project> recommendProjects;


    @PostConstruct
    public void refreshInitData(){
        refreshPlateformEnterprise();
        refreshRecommendProjects();
    }

    private void refreshPlateformEnterprise(){
        Map<Integer, Enterprise> plateformEnterpriseMap = mapper.findAllName(EnterpriseQuery.builder().plateformFlg(CommonConstant.PLATEFROM).build())
                .stream().collect(Collectors.toMap(Enterprise::getId, p->p));
        List<Block> blockList = blockMapper.findList(new Query(Block.builder().plateformFlg(CommonConstant.PLATEFROM).build()));
        blockList.forEach(p->{
            if(plateformEnterpriseMap.containsKey(p.getEnterpriseId())){
                plateformEnterpriseMap.get(p.getEnterpriseId()).getBlockList().add(p);
            }
        });
        this.plateformEnterpriseMap = plateformEnterpriseMap;
    }

    private void refreshRecommendProjects(){
        List<Project> projects =new ArrayList<>();
        ProjectQuery query = new ProjectQuery();
        query.setSize(1);
        for(Integer enterpriseId : this.plateformEnterpriseMap.keySet()){
            query.setEnterpriseId(enterpriseId);
            projects.addAll(projectMapper.findList(query));
        }
        this.recommendProjects = projects;
    }


    public BaseResponse plateformEnterprise(){
        return BaseResponse.builder().data(this.plateformEnterpriseMap.values()).build();
    }
    public BaseResponse recommendProjects(){
        return BaseResponse.builder().data(this.recommendProjects).build();
    }

}

