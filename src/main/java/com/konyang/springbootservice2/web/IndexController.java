package com.konyang.springbootservice2.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
}

/*
'HtmlFileController와 비교해서 한번 더 점검해봐야 한다.  !!!! 삭제하면 안된다 !!!!
 */