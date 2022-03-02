package com.nowcoder.community.util;

public interface CommunityConstant {
    //激活成功
    int ACTIVATION_SUCESS = 1;

    //重复激活
    int ACTIVATION_REPEATE = 2;

    //激活失败
    int ACTIVATION_FALURE = 3;

    //默认状态的登录凭证超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    //记住状态下的登录凭证超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    // 实体类型:帖子
    int ENTITY_TYPE_POST = 1;

    // 实体类型:评论
    int ENTITY_TYPE_COMMENT = 2;

}
