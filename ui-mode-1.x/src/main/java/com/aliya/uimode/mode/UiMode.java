package com.aliya.uimode.mode;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.view.View;

import com.aliya.uimode.R;
import com.aliya.uimode.intef.ApplyPolicy;
import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.intef.UiModeChangeListener;

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
public final class UiMode {

    private static Map<Context, Set<WeakReference<View>>> sContextViewMap = new HashMap<>();
    private static Map<Context, Set<WeakReference<View>>> sActivityViewMap = new HashMap<>();
    private static ReferenceQueue<View> queue = new ReferenceQueue<>();
    public static final int NO_ATTR_ID = -1;

    private UiMode() {
    }

    /**
     * 供外部使用，添加 通过new创建具有UiMode属性的View
     *
     * @param v     a view
     * @param attrs 建议通过 {@link com.aliya.uimode.mode.Attr.Builder} 创建
     */
    public static void putUiModeView(View v, Map<String, Integer> attrs) {
        if (v == null) return;
        saveViewAndAttrIds(v.getContext(), v, attrs);
    }

    public static void saveViewAndAttrIds(Context ctx, View v, Map<String, Integer> attrs) {
        if (v != null && attrs != null) {
            Map<String, Integer> attrIds;
            Object tag = v.getTag(R.id.tag_ui_mode);
            if (tag instanceof HashMap) {
                attrIds = (Map<String, Integer>) tag;
            } else {
                attrIds = new HashMap<>(attrs.size());
                v.setTag(R.id.tag_ui_mode, attrIds);
            }
            attrIds.putAll(attrs);

            saveView(ctx, v);
        }
    }

    public static void saveView(Context ctx, View v) {
        if (ctx == null || v == null) return;

        if (ctx instanceof Application) {
            putView2Map(ctx, v, sContextViewMap, queue);
        } else {
            while (!(ctx instanceof Activity)) {
                if (ctx instanceof ContextWrapper) {
                    ctx = ((ContextWrapper) ctx).getBaseContext();
                } else {
                    break;
                }
            }
            if (ctx instanceof Activity) {
                putView2Map(ctx, v, sActivityViewMap, null);
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
        weakViewSet.add((queue == null) ? new WeakReference<>(v) : new WeakReference<>(v, queue));
    }

    /**
     * 执行全部 UiMode
     *
     * @param policy  apply 策略
     */
    public static void applyUiMode(ApplyPolicy policy) {

        // 1、先执行Activity相关的View
        for (Map.Entry<Context, Set<WeakReference<View>>> entry :
                sActivityViewMap.entrySet()) {
            Set<WeakReference<View>> weakViewSet = entry.getValue();
            onApplyUiMode(policy, weakViewSet);
        }

        // 2、在执行ApplicationContext相关的View
        for (Map.Entry<Context, Set<WeakReference<View>>> entry :
                sContextViewMap.entrySet()) {
            onApplyUiMode(policy, entry.getValue());
        }

    }

    /**
     * apply UiMode to Sets View
     *
     * @param policy      apply 策略
     * @param weakViewSet view sets WeakReference
     */
    private static void onApplyUiMode(ApplyPolicy policy, Set<WeakReference<View>>
            weakViewSet) {
        if (weakViewSet != null) {
            Iterator<WeakReference<View>> iterator = weakViewSet.iterator();
            while (iterator.hasNext()) {
                WeakReference<View> next = iterator.next();
                if (next != null) {
                    View view = next.get();
                    if (view != null) {
                        apply(view, policy);
                    } else {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * apply UiMode to the View
     *
     * @param v      this view
     * @param policy apply 策略
     */
    private static void apply(View v, ApplyPolicy policy) {
        if (v == null) return;

        Map<String, Integer> attrIds = null;
        Object vTag = v.getTag(R.id.tag_ui_mode);
        if (vTag instanceof Map) {
            attrIds = (Map<String, Integer>) vTag;
        }

        // 1、执行View自带属性
        if (attrIds != null && policy != null) {
            // 先执行View的android:theme属性
            if (attrIds.containsKey(Attr.NAME_THEME)) {
                UiApply uiApply = policy.obtainApplyPolicy(Attr.NAME_THEME);
                if (uiApply != null) {
                    uiApply.onApply(v, attrIds.get(Attr.NAME_THEME));
                }
            }

            for (Map.Entry<String, Integer> entry : attrIds.entrySet()) {
                String key = entry.getKey();

                if (TextUtils.equals(key, Attr.NAME_THEME)) continue; // 主题被先执行，此处忽略

                UiApply uiApply = policy.obtainApplyPolicy(key);
                if (uiApply != null) {
                    uiApply.onApply(v, entry.getValue());
                }
            }
        }

        // 2、执行View实现UiModeChangeListener接口onUiModeChange()方法
        if (v instanceof UiModeChangeListener) {
            ((UiModeChangeListener) v).onUiModeChange();
        }
    }

    public static void removeUselessViews(Activity activity) {
//        Log.e("TAG", "开始时个数 " + num());
        sActivityViewMap.remove(activity);
        clearUselessContextViews();
//        Log.e("TAG", "结束时个数 " + num());
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
