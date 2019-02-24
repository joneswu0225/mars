package com.jones.mars.constant;

/**
 * Created by jones on 19-2-21.
 */
public enum MessageInfo {
    INVITED_TO_ENTERPRISE("加入企业", "恭喜您成为 %s 的企业员工！"),
    ADD_TO_PROJECT("加入项目", "恭喜您成为项目 %s 的协作人"),
    ADD_TO_PROJECT_MANAGER("成为项目负责人", "恭喜您成为项目 %s 的负责人"),
    MODIFY_PROJECT("项目更新", "%s 对项目 %s 进行了更新，请知晓"),
    SUBMIT_VERIFY_PROJECT("项目审核", "%s 对项目 %s 提交审核，请知晓"),
    VERIFY_PASS_PROJECT("项目审核通过", "项目 %s 已经通过审核并上线，请知晓"),
    VERIFY_FAIL_PROJECT("项目审核不通过", "项目 %s 审核不通过，请知晓")
    ;
    public final String title;

    public final String content;

    MessageInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
