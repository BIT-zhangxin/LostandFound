package com.example.lostandfound.component;

public class MyMessage {

    private int main_event_id;

    private int main_event_type;

    private int object_id;

    private String name;

    private String location;

    private String time;

    private String description;

    public int getMain_event_id() {
        return main_event_id;
    }

    public void setMain_event_id(int main_event_id) {
        this.main_event_id = main_event_id;
    }

    public int getMain_event_type() {
        return main_event_type;
    }

    public void setMain_event_type(int main_event_type) {
        this.main_event_type = main_event_type;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
