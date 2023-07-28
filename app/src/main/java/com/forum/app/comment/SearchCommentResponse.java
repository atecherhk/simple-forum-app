package com.forum.app.comment;

import com.forum.app.comment.domain.PostComment;

import java.util.List;

public class SearchCommentResponse {
    public List<PostComment> comments;

    public long totalCount;
}
