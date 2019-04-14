package com.work.service;

import com.work.model.Comment;

/**
 * Created by 稻草人 on 2019/4/14.
 */

public interface CommentService {

    /**
     * 根据订单id查询评论
     * @param orderId
     * @return
     */
    Comment findCommentById(String orderId);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    int addComment(Comment comment);

    /**
     * 修改评论
     * @param comment
     * @return
     */
    int updateComment(Comment comment);


}
