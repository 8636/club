package cn.duan.community.service;

import cn.duan.community.dto.PaginationDTO;
import cn.duan.community.dto.QuestionDTO;
import cn.duan.community.mapper.QuestionMapper;
import cn.duan.community.mapper.UserMapper;
import cn.duan.community.model.Question;
import cn.duan.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获得全部问题 列表
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO<QuestionDTO> list(Integer page, Integer size) {
        /*Integer totalPage;
        //总记录数
        Integer totalCount = questionMapper.count();
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }*/
//        paginationDTO.setPagination(totalPage, page);
//        Integer offset = page < 1 ? 0 : size * (page - 1);
        PageHelper.startPage(page,size);
        List<Question> list = questionMapper.list();
       return this.findPage(list,page);
    }

    public PaginationDTO<QuestionDTO> findQuestionById(String id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Question> questionList = questionMapper.findQuestionById(id);
        return this.findPage(questionList,page);
    }

    private PaginationDTO<QuestionDTO> findPage(List<Question> list,Integer page){
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        PageInfo<Question> pageInfo = new PageInfo<Question>(list);
        Long total = pageInfo.getTotal();
        paginationDTO.setPagination(total.intValue(),page);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : list) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }
}
