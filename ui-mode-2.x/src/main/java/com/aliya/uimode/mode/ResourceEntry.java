package com.aliya.uimode.mode;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AnyRes;

/**
 * 资源实体类 - 包括：资源类型、资源id
 *
 * @author a_liYa
 * @date 2018/1/24 16:07.
 */
public class ResourceEntry {

    private int id;
    private String type;

    /**
     * 构造方法
     *
     * @param id   resource reference identifier.
     * @param type attr type {@link Type}
     * @see #ResourceEntry(int, Context)
     */
    public ResourceEntry(@AnyRes int id, String type) {
        this.id = id;
        this.type = type;
    }

    public ResourceEntry(@AnyRes int id, Context ctx) {
        this.id = id;
        if (ctx != null) {
            try {
                this.type = ctx.getResources().getResourceTypeName(id);
            } catch (Resources.NotFoundException e) {
                // no-op
            }
        }
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
