package com.pbd.ifttw.database;

public class Routine {
    public Routine() {
        this.id = null;
        this.name = null;
        this.condition_type = null;
        this.condition_value = null;
        this.action_type = null;
        this.action_value = null;
    }

    public Routine(String id, String name, String condition_type, String condition_value, String action_type, String action_value) {
        this.id = id;
        this.name = name;
        this.condition_type = condition_type;
        this.condition_value = condition_value;
        this.action_type = action_type;
        this.action_value = action_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition_type() {
        return condition_type;
    }

    public void setCondition_type(String condition_type) {
        this.condition_type = condition_type;
    }

    public String getCondition_value() {
        return condition_value;
    }

    public void setCondition_value(String condition_value) {
        this.condition_value = condition_value;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getAction_value() {
        return action_value;
    }

    public void setAction_value(String action_value) {
        this.action_value = action_value;
    }

    private String id;
    private String name;
    private String condition_type;
    private String condition_value;
    private String action_type;
    private String action_value;
}
