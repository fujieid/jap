package com.fujieid.jap.ids;

import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.service.IdsUserService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsUserServiceImpl implements IdsUserService {
    public List<UserInfo> userInfoList = new LinkedList<>();

    public IdsUserServiceImpl() {
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfoList.add(userInfo
                .setId(i + "")
                .setUsername("test"));
        }
    }

    @Override
    public UserInfo loginByUsernameAndPassword(String username, String password) {
        return userInfoList.stream().filter(userInfo -> userInfo.getUsername().equals(username)).findFirst().orElse(null);
    }

    /**
     * Get user info by userid.
     *
     * @param userId userId of the business system
     * @return JapUser
     */
    @Override
    public UserInfo getById(String userId) {
        return userInfoList.stream().filter(userInfo -> userInfo.getId().equals(userId)).findFirst().orElse(null);
    }

    /**
     * Get user info by username.
     * <p>
     * It is suitable for the {@code jap-simple} module
     *
     * @param username username of the business system
     * @return JapUser
     */
    @Override
    public UserInfo getByName(String username) {
        return userInfoList.stream().filter(userInfo -> userInfo.getUsername().equals(username)).findFirst().orElse(null);
    }
}
