package com.aliya.uimode.mode;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.view.View;

import com.aliya.uimode.R;
import com.aliya.uimode.intef.ApplyPolicy;
import com.aliya.uimode.intef.UiApply;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * UiMode相关操作
 *
 * @author a_liYa
 * @date 2017/6/23 11:07.
 */
public class UiMode {

    private static Map<Context, Set<WeakReference<View>>> sContextViewMap = new HashMap<>();
    private static Map<Context, Set<WeakReference<View>>> sActivityViewMap = new HashMap<>();
    private static ReferenceQueue<View> queue = new ReferenceQueue<>();
    public static final int NO_ATTR_ID = -1;

    public static void saveViewAndAttrIds(final Context ctx, View v, Map<String, Integer> attrs) {
        if (v != null && attrs != null) {
            Map<String, Integer> attrIds = new HashMap<>(attrs.size());
            attrIds.putAll(attrs);
            v.setTag(R.id.tag_ui_mode, attrIds);

            if (ctx instanceof Activity) {
                putView2Map(ctx, v, sActivityViewMap, null);
            } else {
                putView2Map(ctx, v, sContextViewMap, queue);
            }
        }
    }

    private static void putView2Map(Context ctx, View v, Map<Context, Set<WeakReference<View>>> map,
                                    ReferenceQueue<View> queue) {
        Set<WeakReference<View>> weakViewSet = map.get(ctx);
        if (weakViewSet == null) {
            weakViewSet = new HashSet<>();
            map.put(ctx, weakViewSet);
        }
        weakViewSet.add((queue == null) ? new WeakReference<>(v) : new WeakReference(v, queue));
    }

    public static void apply(View v, Theme theme, ApplyPolicy policy) {
        if (v == null) return;

        Map<String, Integer> attrIds = null;
        Object vTag = v.getTag(R.id.tag_ui_mode);
        if (vTag instanceof Map) {
            attrIds = (Map<String, Integer>) vTag;
        }

        if (attrIds != null && policy != null) {
            for (Map.Entry<String, Integer> entry : attrIds.entrySet()) {
                String key = entry.getKey();
                UiApply uiApply = policy.obtainApplyPolicy(key);
                if (uiApply != null) {
                    uiApply.onApply(v, entry.getValue(), theme);
                }
            }
        }
    }

    public static void removeUselessViews(Activity activity) {
//        Log.e("TAG", "开始时个数 " + num() + " ----- " + SystemClock.uptimeMillis());
        sActivityViewMap.remove(activity);
//        Log.e("TAG", " 中间时间 " + " ----- " + SystemClock.uptimeMillis());
        clearUselessContextViews();
//        Log.e("TAG", "结束时个数 " + num() + " ----- " + SystemClock.uptimeMillis());
    }

    private static int num() {
        int count = 0;

        for (Set set : sContextViewMap.values()) {
            if (set != null) {
                count += set.size();
            }
        }

        for (Set set : sActivityViewMap.values()) {
            if (set != null) {
                count += set.size();
            }
        }

        return count;
    }

    private static void clearUselessContextViews() {
        WeakReference<View> ref;
        while ((ref = (WeakReference) queue.poll()) != null) {
            Set<Context> ketSet = sContextViewMap.keySet();
            Iterator<Context> iterator = ketSet.iterator();
            while (iterator.hasNext()) {
                Context next = iterator.next();
                Set<WeakReference<View>> weakSet = sContextViewMap.get(next);
                weakSet.remove(ref);
                if (weakSet.isEmpty()) { // value Set is empty, remove it.
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 校验 attrId 是否有效
     *
     * @param attrId 资源id
     * @return true 有效资源, false 无效资源
     */
    public static boolean attrIdValid(int attrId) {
        return attrId != NO_ATTR_ID;
    }
}
