package cn.duan.community.controller;

import cn.duan.community.common.cache.HotTagCache;
import cn.duan.community.dto.HotTagDTO;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.service.impl.QuestionDTOServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Id;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class indexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionDTOServiceImpl questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              @RequestParam(value = "search", required = false) String search,
                              @RequestParam(name = "tag", required = false) String tag,
                              @RequestParam(name = "sort", required = false) String sotrStr) throws IOException {
        PageInfo<QuestionDTO> pageInfo = questionService.list(page, size, search, tag, sotrStr);
        log.info("pageinfo", pageInfo.getList());
        ModelAndView mv = new ModelAndView();
        List<HotTagDTO> hots = hotTagCache.getHots();
        mv.addObject("tags", hots);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("search", search);
        mv.addObject("sort", sotrStr);
        mv.setViewName("index");
        return mv;
    }
}
