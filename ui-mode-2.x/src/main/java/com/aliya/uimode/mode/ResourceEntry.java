package com.aliya.uimode.mode;

/**
 * 资源实体类 - 包括：资源类型、资源id
 *
 * @author a_liYa
 * @date 2018/1/24 16:07.
 */
public class ResourceEntry {

    private int id;
    private String type;

    public ResourceEntry(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
