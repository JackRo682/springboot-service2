package com.konyang.springbootservice2.service.posts;

import com.konyang.springbootservice2.domain.posts.HtmlFile;
import com.konyang.springbootservice2.domain.posts.HtmlFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class HtmlFileServiceImpl implements HtmlFileService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createTable(HtmlFile htmlFile) {
        String tableName = htmlFile.getName().replace(".html", "");
        jdbcTemplate.execute("CREATE TABLE " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY)");

        for (int i = 0; i < htmlFile.getInputCount(); i++) {
            String columnName = "input_" + i;
            jdbcTemplate.execute("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " VARCHAR(255)");
        }
    }

    @Override
    public void saveInputData(HtmlFile htmlFile, String inputName, String inputValue) {
        String tableName = htmlFile.getName().replace(".html", "");
        String query = "INSERT INTO " + tableName + " (" + inputName + ") VALUES ('" + inputValue + "')";
        jdbcTemplate.execute(query);
    }

    @Override
    public BufferedImage takeScreenshot(HtmlFile htmlFile) throws AWTException, IOException {
        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenshot = robot.createScreenCapture(screenRect);

        String screenshotFilename = "screenshot_" + htmlFile.getName().replace(".html", ".png");
        File screenshotFile = new File(screenshotFilename);
        ImageIO.write(screenshot, "png", screenshotFile);

        return screenshot;
    }
}

/*
-> Former 'PostsService' service code

package com.konyang.springbootservice2.service.posts;

import com.konyang.springbootservice2.domain.posts.Posts;
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
 */