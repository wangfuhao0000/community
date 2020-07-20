package cug.wangfh.community.controller;

import cug.wangfh.community.entity.User;
import cug.wangfh.community.service.LikeService;
import cug.wangfh.community.service.UserService;
import cug.wangfh.community.util.CommunityConstant;
import cug.wangfh.community.util.CommunityUtil;
import cug.wangfh.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/like", method = RequestMethod.GET)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
        User user = userService.findUserById(entityUserId);

        // 点赞
        likeService.like(hostHolder.getUser().getId(), entityType, entityId, entityUserId);

        // 点赞数量
        long likeCount = likeService.findEntityLkeCount(entityType, entityId);
        // 当前用户的点赞状态
        int likeStatus = likeService.findEntityLikeStatus(hostHolder.getUser().getId(), entityType, entityId);
        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        // 触发点赞事件

        // 计算帖子分数

        return CommunityUtil.getJSONString(0, null, map);
    }
}
