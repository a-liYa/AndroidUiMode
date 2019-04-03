package com.aliya.uimode;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.LongSparseArray;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * ResourcesFlusher 兼容类
 *
 * @author a_liYa
 * @date 2018/11/14 19:38.
 */
final class ResourcesFlusher {

    private static final String TAG = "ResourcesFlusher";

    /**
     * 强制刷新 Resources 资源缓存
     *
     * @param resources .
     * @see android.support.v7.app.ResourcesFlusher 参考自
     */
    static void flush(@NonNull final Resources resources) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                flushNougats(resources);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flushMarshmallows(resources);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                flushLollipops(resources);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                flushJellyBeans(resources);
            }
        }
    }

    private static Field sDrawableCacheField;
    private static boolean sDrawableCacheFieldFetched;

    private static Class sThemedResourceCacheClazz;
    private static boolean sThemedResourceCacheClazzFetched;

    private static Field sThemedResourceCache_mUnthemedEntriesField;
    private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;

    private static Field sResourcesImplField;
    private static boolean sResourcesImplFieldFetched;

    @RequiresApi(21)
    private static boolean flushLollipops(@NonNull final Resources resources) {
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                HideLog.e(TAG, "Could not retrieve Resources#mDrawableCache field", e);
            }
            sDrawableCacheFieldFetched = true;
        }
        if (sDrawableCacheField != null) {
            Map drawableCache = null;
            try {
                drawableCache = (Map) sDrawableCacheField.get(resources);
            } catch (IllegalAccessException e) {
                HideLog.e(TAG, "Could not retrieve value from Resources#mDrawableCache", e);
            }
            if (drawableCache != null) {
                drawableCache.clear();
                return true;
            }
        }
        return false;
    }

    @RequiresApi(23)
    private static boolean flushMarshmallows(@NonNull final Resources resources) {
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                HideLog.e(TAG, "Could not retrieve Resources#mDrawableCache field", e);
            }
            sDrawableCacheFieldFetched = true;
        }

        Object drawableCache = null;
        if (sDrawableCacheField != null) {
            try {
                drawableCache = sDrawableCacheField.get(resources);
            } catch (IllegalAccessException e) {
                HideLog.e(TAG, "Could not retrieve value from Resources#mDrawableCache", e);
            }
        }

        if (drawableCache == null) {
            // If there is no drawable cache, there's nothing to flush...
            return false;
        }

        return drawableCache != null && flushThemedResourcesCache(drawableCache);
    }

    @RequiresApi(24)
    private static boolean flushNougats(@NonNull final Resources resources) {
        if (!sResourcesImplFieldFetched) {
            try {
                sResourcesImplField = Resources.class.getDeclaredField("mResourcesImpl");
                sResourcesImplField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                HideLog.e(TAG, "Could not retrieve Resources#mResourcesImpl field", e);
            }
            sResourcesImplFieldFetched = true;
        }

        if (sResourcesImplField == null) {
            // If the mResourcesImpl field isn't available, bail out now
            return false;
        }

        Object resourcesImpl = null;
        try {
            resourcesImpl = sResourcesImplField.get(resources);
        } catch (IllegalAccessException e) {
            HideLog.e(TAG, "Could not retrieve value from Resources#mResourcesImpl", e);
        }

        if (resourcesImpl == null) {
            // If there is no impl instance, bail out now
            return false;
        }

        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = resourcesImpl.getClass().getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                HideLog.e(TAG, "Could not retrieve ResourcesImpl#mDrawableCache field", e);
            }
            sDrawableCacheFieldFetched = true;
        }

        Object drawableCache = null;
        if (sDrawableCacheField != null) {
            try {
                drawableCache = sDrawableCacheField.get(resourcesImpl);
            } catch (IllegalAccessException e) {
                HideLog.e(TAG, "Could not retrieve value from ResourcesImpl#mDrawableCache", e);
            }
        }

        return drawableCache != null && flushThemedResourcesCache(drawableCache);
    }

    @RequiresApi(16)
    private static boolean flushThemedResourcesCache(@NonNull final Object cache) {
        if (!sThemedResourceCacheClazzFetched) {
            try {
                sThemedResourceCacheClazz = Class.forName("android.content.res" +
                        ".ThemedResourceCache");
            } catch (ClassNotFoundException e) {
                HideLog.e(TAG, "Could not find ThemedResourceCache class", e);
            }
            sThemedResourceCacheClazzFetched = true;
        }

        if (sThemedResourceCacheClazz == null) {
            // If the ThemedResourceCache class isn't available, bail out now
            return false;
        }

        if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
            try {
                sThemedResourceCache_mUnthemedEntriesField =
                        sThemedResourceCacheClazz.getDeclaredField("mUnthemedEntries");
                sThemedResourceCache_mUnthemedEntriesField.setAccessible(true);
            } catch (NoSuchFieldException ee) {
                HideLog.e(TAG, "Could not retrieve ThemedResourceCache#mUnthemedEntries field", ee);
            }
            sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
        }

        if (sThemedResourceCache_mUnthemedEntriesField == null) {
            // Didn't get mUnthemedEntries field, bail out...
            return false;
        }

        LongSparseArray unthemedEntries = null;
        try {
            unthemedEntries = (LongSparseArray)
                    sThemedResourceCache_mUnthemedEntriesField.get(cache);
        } catch (IllegalAccessException e) {
            HideLog.e(TAG, "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", e);
        }

        if (unthemedEntries != null) {
            unthemedEntries.clear();
            return true;
        }
        return false;
    }

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
