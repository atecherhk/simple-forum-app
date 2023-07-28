package com.forum.app.comment.service;

import com.forum.app.comment.SearchCommentResponse;
import com.forum.app.comment.domain.PostComment;
import com.forum.app.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public SearchCommentResponse searchComment(long postId, int skip, int limit) {
        SearchCommentResponse response = new SearchCommentResponse();
        response.comments = commentRepository.search(postId, skip, limit);
        response.totalCount = commentRepository.count(postId);

        return response;
    }

    public void save(PostComment comment) {
        commentRepository.save(comment);
    }
}
