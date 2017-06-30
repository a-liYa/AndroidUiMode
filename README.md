# AndroidUiMode
让日夜间模式实现起来更简单


### 不支持的

* 1、style样式里面的属性不支持动态更换主题   

res/values/styles.xml;
```
style样式：
<style name="TestStyle">
        <item name="android:background">?attr/bg_color</item>
</style>
布局中应用：
<View
     style="@style/TestStyle"
     android:layout_width="20dp"
     android:layout_height="20dp" />
```
此时```android:background```属性对应的?attr/bg_color值无法获取

* 2、Drawable resource 定义文件里面Android 5.0以下系统，不支持：?attr属性

res/drawable/bg_test_attr.xml
```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="?attr/colorPrimary" />
</shape>
```
以上写法不兼容低于Android 5.0的系统   


### ImageView夜间模式用法
* 1、theme的style样式配置全局
```
<style "AppTheme" parent="...">
    <item name="iv_maskColor">@android:color/transparent</item>
</style>
  
<style "NightAppTheme" parent="...">
    <item name="iv_maskColor">#7f000000</item>
</style> 
```

* 2、配置单个ImageView，attr/ic_customMaskColor是自定义属性
```
<ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:iv_maskColor="?attr/ic_customMaskColor" />
```