package cn.duan.community.common.task;

import cn.duan.community.common.cache.HotTagCache;
import cn.duan.community.dto.HotTagDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.model.Question;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class HotTags {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(HotTags.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0 1 * * ? ")
//    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        //标签名  权重
        Map<String, Integer> map = new HashMap<>();
        List<Question> questionList = questionMapper.selectAll();
        questionList.forEach(question -> {
            String[] tags = StringUtils.split(question.getTag(), ",");
            for (String tag : tags) {
                Integer priority = map.get(tag);
                if (priority != null){
                    map.put(tag,priority +question.getCommentCount()+question.getViewCount()/2);
                }else {
                    map.put(tag,5+question.getCommentCount());
                }
            }
        });
//        hotTagCache.updateTags(map);

        //采用redis中zset进行存储
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            redisTemplate.boundZSetOps("HotTags").add(entry.getKey(),entry.getValue());
        }

        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
