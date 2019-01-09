package android.support.v7.app;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.LongSparseArray;

import java.lang.reflect.Field;

/**
 * ResourcesFlusher 兼容类
 *
 * @author a_liYa
 * @date 2018/11/14 19:38.
 */
public class ResourcesFlusherCompat {

    /**
     * 强制刷新 Resources 资源缓存
     *
     * @param resources .
     * @return .
     * @see android.support.v7.app.ResourcesFlusher 参考自
     */
    public static void flush(@NonNull final Resources resources) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ResourcesFlusher.flush(resources);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            flushJellyBeans(resources);
        }
    }

    private static Field sDrawableCacheField;
    private static boolean sDrawableCacheFieldFetched;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    static boolean flushJellyBeans(@NonNull final Resources resources) {
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                // no-op
            }
            sDrawableCacheFieldFetched = true;
        }
        if (sDrawableCacheField != null) {
            LongSparseArray drawableCache = null;
            try {
                drawableCache = (LongSparseArray) sDrawableCacheField.get(resources);
            } catch (IllegalAccessException e) {
                // no-op
            }
            if (drawableCache != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    drawableCache.clear();
                    return true;
                }
            }
        }
        return false;
    }

}
