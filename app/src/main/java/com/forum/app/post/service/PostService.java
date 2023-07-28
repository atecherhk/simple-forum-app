package com.forum.app.post.service;

import com.forum.app.post.SearchPostResponse;
import com.forum.app.post.domain.Post;
import com.forum.app.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public SearchPostResponse searchPost(int skip, int limit) {
        List<Post> posts = postRepository.search(skip, limit);

        SearchPostResponse response = new SearchPostResponse();
        response.posts = posts;
        response.totalCount = postRepository.count();
        return response;
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Post getPost(long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new RuntimeException();
        }
        return postOptional.get();
    }
}
