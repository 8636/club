package cn.duan.community.mapper;

import cn.duan.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void add(Question question);


    @Select("select * from question")
    List<Question> list();

    @Select("select * from question where creator =#{id}")
    List<Question> findQuestionById(@Param("id") String id);
}
