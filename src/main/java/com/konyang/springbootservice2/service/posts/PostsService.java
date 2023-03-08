package com.konyang.springbootservice2.service.posts;

import com.konyang.springbootservice2.domain.posts.Posts;
import com.konyang.springbootservice2.domain.posts.PostsRepository;
import com.konyang.springbootservice2.web.dto.PostsListResponseDto;
import com.konyang.springbootservice2.web.dto.PostsResponseDto;
import com.konyang.springbootservice2.web.dto.PostsSaveRequestDto;
import com.konyang.springbootservice2.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    public final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        Posts entity = requestDto.toEntity();
        postsRepository.save(entity);

        try (FileWriter writer = new FileWriter("posts.txt", true)) {
            String line = entity.getId() + "," + entity.getTitle() + "," + entity.getContent() + "\n";
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity.getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다 id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다 id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }
}

/*
@Autowired
private DynamicEntityRepository dynamicEntityRepository;

public void createDynamicEntityFromHtmlFile(String htmlFilePath) {
    // Parse the HTML file and extract the input tags and their values
    Map<String, String> dynamicFields = new HashMap<>();
    dynamicFields.put("input1", "value1");
    dynamicFields.put("input2", "value2");
    // ...
    dynamicFields.put("inputN", "valueN");

    // Create a new DynamicEntity with the dynamic fields
    DynamicEntity dynamicEntity = new DynamicEntity();
    dynamicEntity.setDynamicFields(dynamicFields);

    // Save the entity to the database
    dynamicEntityRepository.save(dynamicEntity);
}

->> DynamicEntityRepository.java 파일의 코드인 것 같다.

*/