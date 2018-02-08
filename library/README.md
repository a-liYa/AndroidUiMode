# AndroidUiMode v1.x
让日夜间模式实现起来更简单

v1.x对于模块化配置比较繁琐，[v2.x](../README.md "v2.x")使用的新的方案并兼容v1.x的机制，建议使用[v2.x](../README.md "v2.x")

### 最新版本
https://bintray.com/a-liya/maven/android-uimode/_latestVersion


### 添加依赖

```
dependencies {
    compile 'com.aliya:android-uimode:1.0.0'
    compile 'com.android.support:appcompat-v7:x.x.x'
}
```

### 代码配置，具体用法参考示例app
* 初始化 Application#onCreate()

```
UiModeManager.init(this, R.styleable.SupportUiMode);
```
* BaseActivity#onCreate(Bundle),必须在super.onCreate(savedInstanceState);之前调用下面代码
```
UiModeManager.setInflaterFactor(getLayoutInflater());
```
* 拓展类型
```
UiModeManager.addSupportUiApply(String, UiApply);
```

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

### 有关属性

```
<declare-styleable name="UiMode">
    <attr name="iv_maskColor" format="color" />
    <attr name="iv_useMaskColor" format="boolean" />
    <attr name="invalidate" format="boolean" />
</declare-styleable>
```

* 1、iv_maskColor

`ImageView使用PorterDuffXfermode进行遮罩的颜色属性`
1. 若ImageView没有配iv_maskColor; 跟随当前Activity对应主题配置的iv_maskColor

```
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    ...
    <item name="iv_maskColor">@android:color/transparent</item>
    ...
</style>
<style name="NightAppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    ...
    <item name="iv_maskColor">#7f000000</item>
    ...
</style>
```

2. app:iv_maskColor="?attr/xxx"; 此时遮罩颜色值对应该ImageView所在Activity对应主题的xxx属性配置的值

 attrs.xml
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="SupportUiMode">
        <attr name="xxx" format="color|reference" />
    </declare-styleable>
</resources>
```
 styles.xml
```
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    ...
    <item name="xxx">#7f000000</item>
    ...
</style>
```

3. app:iv_maskColor="#f00"; 遮罩颜色值是#f00，但是不知道什么时候生效；此时需要配合iv_useMaskColor属性，详见iv_useMaskColor解释

* 2、iv_useMaskColor

```
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    ...
    <item name="iv_useMaskColor">false</item>
    ...
</style>
<style name="NightAppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    ...
    <item name="iv_useMaskColor">true</item>
    ...
</style>
```
依据上面配置，当前Activity对应主题NightAppTheme时，ImageView `app:iv_maskColor="#f00"` 才会生效；

* 3、invalidate

`app:invalidate="true"` 表示日、夜间模式切换会调用对应View.invalidate()来刷新

> 场景：RecyclerView的分割线，当日、夜间模式切换时，RecyclerView不刷新分割线的颜色就不会变化
