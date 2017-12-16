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
public class ThemeStore {

    private final static SparseArray<Set<Integer>> themes = new SparseArray();

    public static void putTheme(int keyMode, @StyleRes int resId) {
        Set<Integer> themeSet = themes.get(keyMode);
        if (themeSet == null) {
            themeSet = new HashSet<>();
            themes.put(keyMode, themeSet);
        }
        themeSet.add(resId);
    }

    public static Set<Integer> getTheme(int keyMode) {
        return themes.get(keyMode);
    }

}
