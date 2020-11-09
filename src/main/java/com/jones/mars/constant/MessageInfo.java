package com.jones.mars.constant;

/**
 * Created by jones on 19-2-21.
 */
public enum MessageInfo {


    INVITED_TO_ENTERPRISE("加入企业", "恭喜您成为 %s 的企业员工！", MessageInfo.MESSAGE_ENTERPRISE),

    ADD_TO_PROJECT("加入项目", "恭喜您成为项目 [ %s ] 的协作人", MessageInfo.MESSAGE_PROJECT),
    ADD_TO_PROJECT_MANAGER("成为项目负责人", "恭喜您成为项目 [ %s ] 的负责人", MessageInfo.MESSAGE_PROJECT),
    MODIFY_PROJECT("项目更新", "%s 对项目 [ %s ] 进行了更新，请知晓", MessageInfo.MESSAGE_PROJECT),
    SUBMIT_VERIFY_PROJECT("项目审核", "%s 对项目 [ %s ] 提交审核，请知晓", MessageInfo.MESSAGE_PROJECT),
    VERIFY_PASS_PROJECT("项目审核通过", "项目 [ %s ] 已经通过审核并上线，请知晓", MessageInfo.MESSAGE_PROJECT),
    VERIFY_FAIL_PROJECT("项目审核不通过", "项目 [ %s ] 审核不通过，请知晓", MessageInfo.MESSAGE_PROJECT),

    TASK_PROJECT_MODIFY("课件制作", "您收到了课件制作任务 [ %s ]， 需要在 %s 前完成，请知晓", MessageInfo.MESSAGE_TASK),
    TASK_PROJECT_TRAINNING("培训任务", "您收到了培训任务 [ %s ]， 需要在 %s 前完成，请知晓", MessageInfo.MESSAGE_TASK),
    TASK_EXPIRED_ADMIN("任务过期提醒", "任务 [ %s ]　已过期，请告知相关人员进行处理", MessageInfo.MESSAGE_TASK),
    TASK_EXPIRED_WORKER("任务过期提醒", "您的任务 [ %s ]　已过期，请尽快处理", MessageInfo.MESSAGE_TASK),

    ;
    public final String title;

    public final String content;
    public final String messageType;
    public static final String MESSAGE_PROJECT = "PROJECT";
    public static final String MESSAGE_NEWS = "NEWS";
    public static final String MESSAGE_TASK = "TASK";
    public static final String MESSAGE_DEPARTMENT = "DEPARTMENT";
    public static final String MESSAGE_ROLE = "ROLE";
    public static final String MESSAGE_ENTERPRISE = "ENTERPRISE";



    MessageInfo(String title, String content, String messageType) {
        this.title = title;
        this.content = content;
        this.messageType = messageType;
    }
}
