package com.aliya.uimode.mode;

import android.view.View;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 自定义携带Key<code>{@link android.content.Context#hashCode()}</code> 弱引用
 *
 * @author a_liYa
 * @date 2017/6/27 22:47.
 */
final class KeyedWeakReference extends WeakReference<View> {

    private static final int NO_KEY = 0;

    private final int key;

    KeyedWeakReference(View referent, ReferenceQueue<? super View> q) {
        super(referent, q);
        if (referent != null && referent.getContext() != null) {
            key = referent.getContext().hashCode();
        } else {
            key = NO_KEY;
        }
    }

}
