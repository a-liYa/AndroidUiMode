# AndroidUiMode v2.x
让日夜间模式实现起来更简单

### 最新版本
https://bintray.com/a-liya/maven/android-uimode/_latestVersion

### 简介

为了方便升级过度，v2.x目前完全兼容v1.x的方案；v2.x是结合官方v7包日夜主题实现的(参考AppCompatDelegate.NightMode)。  
src/main/res目录文件夹规则：

```
res
|____color
|____color-night
|____drawable
|____drawable-night
|____layout
|____mipmap-night-xxhdpi
|____mipmap-xxhdpi
|____values
|____values-night
```

### 添加依赖

```
dependencies {
    compile 'com.aliya:android-uimode:2.0.0'
    compile 'com.android.support:appcompat-v7:x.x.x'
}
```

### 代码配置，具体用法参考示例[ui-mode-2.x-simple](./ui-mode-2.x-simple)
* 初始化 Application#onCreate()  

参考[UiMode](./ui-mode-2.x-simple/src/main/java/com/aliya/uimode/simple/UiMode.java)

```
public static void init(Context context) {
    sContext = context.getApplicationContext();
    UiModeManager.init(sContext, null);
    UiModeManager.setDefaultUiMode(_get().uiMode);
}
```
* BaseActivity#onCreate(Bundle),必须在super.onCreate(savedInstanceState);之前调用下面代码
```
UiModeManager.setInflaterFactor(getLayoutInflater());
```
* 拓展类型
```
UiModeManager.addSupportUiApply(String, UiApply);
```

### ImageView夜间模式用法

* 1、通过app:iv_maskColor自定义属性配置遮罩颜色
```
<ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:iv_maskColor="@color/ic_color" />  
        
 
去掉这遮罩
app:iv_maskColor="@android:color/transparent"

```

### uiMode_ignore属性
```xml
<declare-styleable name="UiMode">
    <!--uiMode忽略的属性，多个属性时属性名之间用'|'分割-->
    <attr name="uiMode_ignore" format="string" />
</declare-styleable>
```
如下配置，当日夜间模式切换时会忽略src属性
```xml
<ImageView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginRight="15dp"
    android:src="@mipmap/ic_nav_bar_back_dark"
    app:uiMode_ignore="src" />
```

### invalidate属性
```xml
<declare-styleable name="UiMode">
    <attr name="invalidate" format="boolean" />
</declare-styleable>
```

`app:invalidate="true"` 表示日、夜间模式切换会调用对应View.invalidate()来刷新

> 场景：RecyclerView的分割线，当日、夜间模式切换时，RecyclerView不刷新分割线的颜色就不会变化
