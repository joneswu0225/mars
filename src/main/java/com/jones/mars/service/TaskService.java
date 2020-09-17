package com.jones.mars.service;

import com.jones.mars.config.LoginUser;
import com.jones.mars.model.Project;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.Task;
import com.jones.mars.model.param.TaskParam;
import com.jones.mars.model.query.ProjectUserQuery;
import com.jones.mars.model.query.TaskQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.ProjectMapper;
import com.jones.mars.repository.ProjectUserMapper;
import com.jones.mars.repository.TaskMapper;
import com.jones.mars.util.DateUtil;
import com.jones.mars.util.LoginUtil;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService extends BaseService{

    @Autowired
    private TaskMapper mapper;

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse add(TaskParam param){
        List<ProjectUser> projectUserList = projectUserMapper.findList(ProjectUserQuery.builder().projectId(param.getProjectId()).build());
        if(projectUserList.size() > 0) {
            Task task = mapper.findMaxVersionTask(TaskQuery.builder().type(param.getType()).projectId(param.getProjectId()).build());
            if(task == null || task.getCurrentFlg()==Task.OLD_TASK){
                param.setVersion(task==null ? 0 : task.getVersion() + 1);
            } else {
                param.setVersion(task.getVersion());
            }
            param.setCurrentFlg(Task.CURRENT_TASK);
            param.setStatus(Task.CREATING);
            param.setUserIds(projectUserList.stream().map(p -> p.getUserId()).collect(Collectors.toList()));
            param.setCreateBy(LoginUtil.getInstance().getUser().getId());
            param.setUpdateBy(LoginUtil.getInstance().getUser().getId());
            mapper.insert(param);
        }
        return BaseResponse.builder().build();
    }


    public BaseResponse findTaskList(TaskQuery query){
        Page<Task> taskPage = findPage(query);
        if(taskPage.getContent().size() > 0){
            List<Integer> projectIds = taskPage.getContent().parallelStream().map(p->p.getProject().getId()).collect(Collectors.toList());
            List<ProjectUser> projectUsers = projectUserMapper.findList(ProjectUserQuery.builder().projectIds(projectIds).build());
            Map<Integer, List<ProjectUser>> projectUserMap = new HashMap<>();
            projectIds.forEach(p->projectUserMap.put(p, new ArrayList<>()));
            projectUsers.forEach(p->projectUserMap.get(p.getProjectId()).add(p));
            taskPage.getContent().forEach(task -> task.getProject().setUserList(projectUserMap.get(task.getProject().getId())));
        }
        return BaseResponse.builder().data(taskPage).build();
    }

    public BaseResponse findAllTasks(TaskQuery query){
        List<Task> taskList = mapper.findAll(query);
        if(taskList.size() > 0){
            List<Integer> projectIds = taskList.parallelStream().map(p->p.getProject().getId()).collect(Collectors.toList());
            List<ProjectUser> projectUsers = projectUserMapper.findList(ProjectUserQuery.builder().projectIds(projectIds).build());
            Map<Integer, List<ProjectUser>> projectUserMap = new HashMap<>();
            projectIds.forEach(p->projectUserMap.put(p, new ArrayList<>()));
            projectUsers.forEach(p->projectUserMap.get(p.getProjectId()).add(p));
            taskList.forEach(task -> task.getProject().setUserList(projectUserMap.get(task.getProject().getId())));
        }
        return BaseResponse.builder().data(taskList).build();
    }

    public BaseResponse findPrivateTask(Integer taskId){
        Map<String, Object> query = new HashMap<>();
        query.put("id", taskId);
        query.put("userId", LoginUtil.getInstance().getUser().getId());
        Task task = mapper.findPrivateTask(query);
        return BaseResponse.builder().data(task).build();
    }

    public BaseResponse getCalendar(TaskQuery query){
        Date startDate = query.getStartDate();
        Date endDate = query.getEndDate();
        List<Task> taskList = mapper.findAll(query);
        Set<Date> dates = new HashSet<>();
        for(Task task : taskList){
            Date start = task.getStartDate();
            start = start.before(startDate) ? startDate : start;
            Date end = task.getExpireDate();
            end = end.after(endDate) ? endDate : end;
            while(start.before(end)){
                dates.add(start);
                start = DateUtil.dateAdd(start, 1);
            }
        }
        return BaseResponse.builder().data(dates).build();
    }

    /*public static void main(String[] args) throws ParseException {
        Date first = DateUtil.parseDate("2020-05-31");
        Date last = DateUtil.parseDate("2020-07-01");

        List<Map<String, Date>> dateList = new ArrayList<>();
        List<Map<String, Date>> result = new ArrayList<>();
        dateList.add(new HashMap<String, Date>(){{
            put("startDate",DateUtil.parseDate("2020-05-01"));
            put("endDate",DateUtil.parseDate("2020-06-05"));
        }});
        dateList.add(new HashMap<String, Date>(){{
            put("startDate",DateUtil.parseDate("2020-06-01"));
            put("endDate",DateUtil.parseDate("2020-06-05"));
        }});
        dateList.add(new HashMap<String, Date>(){{
            put("startDate",DateUtil.parseDate("2020-06-03"));
            put("endDate",DateUtil.parseDate("2020-06-04"));
        }});
        dateList.add(new HashMap<String, Date>(){{
            put("startDate",DateUtil.parseDate("2020-06-07"));
            put("endDate",DateUtil.parseDate("2020-06-12"));
        }});
        dateList.add(new HashMap<String, Date>(){{
            put("startDate",DateUtil.parseDate("2020-06-07"));
            put("endDate",DateUtil.parseDate("2020-06-19"));
        }});
        long max_gap = last.getTime() - first.getTime();
        Date min = last;
        Date max = first;
        Set<Date> dateSet = new HashSet<>();
        for(Map<String, Date> dates: dateList){
            Date start = dates.get("startDate");
            start = start.before(first) ? first : start;
            Date end = dates.get("endDate");
            end = end.after(last) ? last : end;
            while(start.before(end)){
                dateSet.add(start);
                start = DateUtil.dateAdd(start, 1);
            }
        }
        System.out.println(dateSet.size());


    }*/


}

