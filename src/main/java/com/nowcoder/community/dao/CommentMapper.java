package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 分页查询
    List<Comment> selectCommentByEntity(int entityType, int entityId, int offset, int limit);

    //数据条目数
    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);
}
