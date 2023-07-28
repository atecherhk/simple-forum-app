package com.forum.app.comment.repository;

import com.forum.app.comment.domain.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<PostComment, Long> {
    @Query(nativeQuery = true, value = "select * from post_comment where post_id = :postId limit :skip, :limit")
    List<PostComment> search(@Param("postId") long postId, @Param("skip") int skip, @Param("limit") int limit);

    @Query(nativeQuery = true, value = "select count(1) from post_comment where post_id = :postId")
    long count(@Param("postId") long postId);
}
