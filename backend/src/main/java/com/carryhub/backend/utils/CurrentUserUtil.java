package com.carryhub.backend.utils;

import com.carryhub.backend.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class CurrentUserUtil {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Long getCurrentUserId() {
        if (currentUser == null) {
            return null;
        }
        return currentUser.getId();
    }
}