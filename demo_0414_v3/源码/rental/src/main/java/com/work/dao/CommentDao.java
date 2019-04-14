package com.work.dao;

import com.work.model.Comment;
import org.springframework.stereotype.Repository;

/**
 * Created by 稻草人 on 2019/4/14.
 */
@Repository
public interface CommentDao {

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
