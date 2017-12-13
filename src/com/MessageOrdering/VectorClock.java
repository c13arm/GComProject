package com.MessageOrdering;

import com.GroupManagement.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VectorClock implements Serializable{
    //List<Integer> clock;
    HashMap<String, Integer> clock;
    public VectorClock(int initalSize)
    {
        clock = new HashMap<>(initalSize);
    }

    public VectorClock(HashMap<String, Integer> clock) {
        this.clock = clock;
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
        ArrayList<String> keys = new ArrayList<>(clock.keySet());
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
        ArrayList<String> keys = new ArrayList<>(clock.keySet());
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

    protected boolean isNext(VectorClock otherClock, User user) {
        ArrayList<String> keys = new ArrayList<>(clock.keySet());
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
            if(key.equals(user.getName())) {
                if(otherValue != (ourValue + 1)) {
                    return false;
                }
            } else if(otherValue > ourValue) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return clock.toString();
    }

    @Override
    protected VectorClock clone() throws CloneNotSupportedException
    {
        return new VectorClock((HashMap<String, Integer>)this.clock.clone());
    }
}
