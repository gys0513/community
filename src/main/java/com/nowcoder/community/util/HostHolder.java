package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/*用于持有用户的信息，用于代替session对象   线程隔离*/
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUsers(User user){
        users.set(user);
    }

    public User getUsers(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
