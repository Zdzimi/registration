package com.zdzimi.registrationapp.comparator;

import com.zdzimi.registrationapp.model.entities.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        int result = o1.getSurname().compareTo(o2.getSurname());
        if (result == 0) {
            result = o1.getUsername().compareTo(o2.getUsername());
            if (result == 0) {
                result = Long.compare(o1.getUserId(), o2.getUserId());
            }
        }
        return result;
    }
}
