package com.forum.app.post.repository;

import com.forum.app.post.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(nativeQuery = true, value = "select * from post limit :skip, :limit")
    List<Post> search(@Param("skip") int skip, @Param("limit") int limit);

    @Query(nativeQuery = true, value = "select count(1) from post")
    long count();
}
