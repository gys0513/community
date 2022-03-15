package com.nowcoder.community.actuator;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Endpoint(id = "database")
public class DatabaseEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseEndpoint.class);

    // 如何判断数据库当前的连接是否正常？ 尝试访问数据库，尝试获取连接
    @Autowired
    private DataSource dataSource;

    @ReadOperation  // 这个方法是通过一个get请求来访问的， ji
    public String checkConnection(){
        try(
                Connection conn = dataSource.getConnection();
        ) {
            return CommunityUtil.getJSONString(0, "获取连接成功");
        } catch (SQLException e) {
            logger.error("获取连接失败： " + e.getMessage());
            return CommunityUtil.getJSONString(0, "获取连接失败");
        }
    }
}
