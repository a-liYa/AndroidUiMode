package com.aliya.uimode.mode;

import android.support.annotation.StyleRes;
import android.util.SparseArray;

import java.util.HashSet;
import java.util.Set;

/**
 * a theme store
 *
 * @author a_liYa
 * @date 2017/10/22 10:36.
 */
public final class ThemeStore {

    private final SparseArray<Set<Integer>> mThemes = new SparseArray<>();

    public void putTheme(int keyMode, @StyleRes int resId) {
        Set<Integer> themeSet = mThemes.get(keyMode);
        if (themeSet == null) {
            themeSet = new HashSet<>();
            mThemes.put(keyMode, themeSet);
        }
        themeSet.add(resId);
    }

    public Set<Integer> getTheme(int keyMode) {
        return mThemes.get(keyMode);
    }

}
