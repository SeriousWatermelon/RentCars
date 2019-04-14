package com.work.service.impl;

import com.work.dao.CommentDao;
import com.work.model.Comment;
import com.work.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 稻草人 on 2019/4/14.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment findCommentById(String orderId) {
        return commentDao.findCommentById(orderId);
    }

    @Transactional
    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Transactional
    @Override
    public int updateComment(Comment comment) {
        return commentDao.updateComment(comment);
    }
}
