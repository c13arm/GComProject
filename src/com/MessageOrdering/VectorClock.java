package com.MessageOrdering;

import com.GroupManagement.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VectorClock{
    //List<Integer> clock;
    HashMap<String, Integer> clock;
    public VectorClock(List<User> users)
    {
        clock = new HashMap<>(users.size());
    }

    public void increaseClock(User user)
    {
        Integer oldVal = clock.get(user.getName());
        if(oldVal == null)
            oldVal = 0;
        clock.put(user.getName(), oldVal + 1);
    }

    public void update(VectorClock otherClock)
    {
        Set<String> keys = clock.keySet();
        keys.addAll(otherClock.clock.keySet());
        for (String key: keys)
        {
            Integer otherValue = otherClock.clock.get(key);
            if(otherValue == null) {
                otherValue = 0;
            }
            Integer ourValue = clock.get(key);
            if(ourValue == null) {
                ourValue = 0;
            }
            clock.put(key, Math.max(otherValue, ourValue));
        }
    }

    public int compareTo(VectorClock otherClock)
    {
        int lessThan = 0;
        int greaterThan = 0;
        Set<String> keys = clock.keySet();
        keys.addAll(otherClock.clock.keySet());
        for (String key: keys)
        {
            Integer otherValue = otherClock.clock.get(key);
            if(otherValue == null) {
                otherValue = 0;
            }
            Integer ourValue = clock.get(key);
            if(ourValue == null) {
                ourValue = 0;
            }
            if(otherValue < ourValue) {
                greaterThan++;
            } else if (ourValue < otherValue) {
                lessThan++;
            }
        }
        if(greaterThan > 0 && lessThan > 0) {
            throw new IllegalArgumentException();
        }
        return greaterThan - lessThan;
    }


}
