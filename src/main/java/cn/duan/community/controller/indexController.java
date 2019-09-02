package cn.duan.community.controller;

import cn.duan.community.common.cache.HotTagCache;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.dto.ResultDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.service.impl.QuestionDTOServiceImpl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/")
    public String index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", required = false) String sotrStr,
                        Model model) throws IOException {
        PageInfo<QuestionDTO> pageInfo = questionService.list(page, size, search, tag, sotrStr);
        log.info("pageinfo", pageInfo.getList());
//        List<HotTagDTO> hots = hotTagCache.getHots();
        Set hotTags = redisTemplate.boundZSetOps("HotTags").reverseRange(0, 9);
        model.addAttribute("tags", hotTags);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("search", search);
        model.addAttribute("sort", sotrStr);
        return "index";
    }

    @GetMapping("/ajax")
    @ResponseBody
    public ResultDTO ajax(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search,
                          @RequestParam(name = "tag", required = false) String tag,
                          @RequestParam(name = "sort", required = false) String sotrStr) throws IOException {
        PageInfo<QuestionDTO> pageInfo = questionService.list(page, size, search, tag, sotrStr);
        return ResultDTO.okOf(pageInfo);
    }

}
