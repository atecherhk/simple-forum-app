package com.forum.app;

import com.forum.app.comment.SearchCommentResponse;
import com.forum.app.comment.domain.PostComment;
import com.forum.app.comment.service.CommentService;
import com.forum.app.post.SearchPostResponse;
import com.forum.app.post.domain.Post;
import com.forum.app.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {
    private static final int PAGE_SIZE = 5;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @GetMapping("/")
    public String homepage(@RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex , Model model) {
        SearchPostResponse response = postService.searchPost((pageIndex - 1) * PAGE_SIZE, PAGE_SIZE);
        model.addAttribute("posts", response.posts);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("totalPages", Math.ceil(response.totalCount /(double) PAGE_SIZE));
        return "home";
    }

    @GetMapping("/post/create")
    public String createPostPage(Model model) {
        model.addAttribute("post", new Post());
        return "createPost";
    }

    @PostMapping("/post/create")
    public String createPost(@ModelAttribute Post post, Model model) {
        if (post.getTitle() == null || post.getContent() == null) {
            return "createPost";
        }
        postService.save(post);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable("id") long id, @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex , Model model) {
        Post post = postService.getPost(id);
        SearchCommentResponse response = commentService.searchComment(post.getId(), (pageIndex - 1) * PAGE_SIZE, PAGE_SIZE);

        model.addAttribute("post", post);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("comments", response.comments);
        model.addAttribute("totalCount", response.totalCount);
        model.addAttribute("totalPages", Math.ceil(response.totalCount /(double) PAGE_SIZE));
        return "post";
    }

    @GetMapping("/post/{id}/comment")
    public String createCommentPage(@PathVariable("id") long postId, Model model) {
        model.addAttribute("postId", postId);
        PostComment comment = new PostComment();
        comment.setPostId(postId);
        model.addAttribute("comment", comment);
        return "createComment";
    }

    @PostMapping("/post/comment")
    public String createComment(@ModelAttribute PostComment comment, Model model) {
        if (comment.getComment() == null) {
            throw new RuntimeException();
        }
        commentService.save(comment);
        return "redirect:/post/" + comment.getPostId();
    }
}
