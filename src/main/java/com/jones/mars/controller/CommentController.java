package com.jones.mars.controller;

import com.jones.mars.model.Comment;
import com.jones.mars.model.GeneralResponse;
import com.jones.mars.model.Thumbup;
import com.jones.mars.service.CommentService;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/comment"})
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public Page list(@RequestParam(value = "hotspotCode", required = false) String hotspotCode, @RequestParam(value = "sceneCode", required = false) String sceneCode, @RequestParam(value = "size", required = false, defaultValue = "10") int size, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Comment comment = new Comment();
        comment.getHotspot().setCode(hotspotCode);
        comment.getHotspot().setSceneCode(sceneCode);
        return this.commentService.findByPage(getQuery(page, size, comment));
    }

    @ResponseBody
    @RequestMapping(value = {"/add"}, method = {RequestMethod.POST})
    GeneralResponse addComment(Comment comment) {
        this.commentService.saveComment(comment);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/thumbUp"}, method = {RequestMethod.POST})
    GeneralResponse changeLikeFlag(@RequestParam("sceneCode") String sceneCode, @RequestParam("thumbUp") boolean thumbUp) {
        Thumbup thumbup = new Thumbup();
        thumbup.setSceneCode(sceneCode);
        thumbup.setUserId(getLoginUser().getUserId());
        this.commentService.saveThumbup(thumbup);
        return new GeneralResponse(true, "success");
    }
}
