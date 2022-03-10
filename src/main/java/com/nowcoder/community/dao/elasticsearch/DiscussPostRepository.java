package com.nowcoder.community.dao.elasticsearch;

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository //es可以被看成一个特殊的数据库， mapper是mybatis专有的注解， Repository是针对数据访问层的注解
//声明实体类类型，声明主键类型
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

}
