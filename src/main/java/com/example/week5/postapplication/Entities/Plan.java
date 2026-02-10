package com.example.week5.postapplication.Entities;

public enum Plan {

    BASIC(1),
    STANDARD(2),
    PREMIUM(4);

    private final int maxSession;

    Plan(int value){
        this.maxSession = value;
    }

    public int getMaxSessions(){
        return maxSession;
    }

    public static Plan getRoleFromValue(int value){
        for(Plan plan : Plan.values()){
            if(plan.maxSession == value)
                return plan;
        }
        return null;
    }
}
