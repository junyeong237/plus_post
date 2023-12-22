package com.example.plus_assignment.global.scheduler;

import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Transactional
    public void deleteOldPosts() {
        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
        List<Post> oldPosts = postRepository.findByCreatedAtBefore(ninetyDaysAgo);

        postRepository.deleteAll(oldPosts);
    }

}
