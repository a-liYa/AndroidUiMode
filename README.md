# AndroidUiMode v2.x
让日夜间模式实现起来更简单

#### 最新版本
https://bintray.com/a-liya/maven/android-uimode/_latestVersion

#### 简介

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

### 一、添加依赖

```
dependencies {
    compile 'com.aliya:android-uimode:2.0.7'
    compile 'com.android.support:appcompat-v7:x.x.x'
}
```

### 二、代码配置，具体用法参考示例 [ui-mode-2.x-simple](./ui-mode-2.x-simple)
* 初始化 Application#onCreate(); 参考示例代码 [UiMode.init(context)](./ui-mode-2.x-simple/src/main/java/com/aliya/uimode/simple/UiMode.java)

```java
/*
 * public static void init(Context context) {
 *     sContext = context.getApplicationContext();
 *     UiModeManager.init(sContext, null);
 *     UiModeManager.setDefaultUiMode(_get().uiMode);
 * }
 */
```

* BaseActivity#onCreate(Bundle);
```
protected void onCreate(Bundle savedInstanceState) {
    UiModeManager.setInflaterFactor(getLayoutInflater());
    super.onCreate(savedInstanceState);
}
```
* 实现日夜模式切换的Activity必须是AppCompatActivity的子类
```java
public class BaseActivity extends AppCompatActivity {
    
}
```
* 拓展类型
```
UiModeManager.addSupportUiApply(String, UiApply);
```

### 三、ImageView夜间模式用法

* 1. `<ImageView/>` 夜间模式有默认值，若想修改分别配置  

> res  
  |____values  
  |&nbsp;&nbsp;&nbsp;|____colors.xml

```xml
<resources>
    <color name="uiMode_maskColor">@android:color/transparent</color>
</resources>
```
      
> res  
  |____values-night  
  |&nbsp;&nbsp;&nbsp;|____colors.xml
```xml
<resources>
    <color name="uiMode_maskColor">#7f000000</color>
</resources>
```

* 2. 通过app:maskColor自定义属性配置遮罩颜色

```
<ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:maskColor="@color/ic_color" />
        
去掉遮罩
app:maskColor="@android:color/transparent"

```

### 四、uiMode_ignore属性

属性声明
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

### 五、invalidate属性
```xml
<declare-styleable name="UiMode">
    <attr name="invalidate" format="boolean" />
</declare-styleable>
```

`app:invalidate="true"` 表示日、夜间模式切换会调用对应View.invalidate()来刷新

> 场景：RecyclerView的分割线，当日、夜间模式切换时，RecyclerView不刷新分割线的颜色就不会变化

### 六、MaskImageView功能

#### 6.1 圆角相关
* 属性声明
```xml
<declare-styleable name="Round">
    <attr name="radius" format="dimension" />
    <attr name="radius_leftTop" format="dimension" />
    <attr name="radius_leftBottom" format="dimension" />
    <attr name="radius_rightTop" format="dimension" />
    <attr name="radius_rightBottom" format="dimension" />
    <attr name="radius_oval" format="boolean" />
    <attr name="border_width" format="dimension" />
    <attr name="border_color" format="color" />
</declare-styleable>
```

* 实现四个圆角半径均为5dp，xml代码如下
```xml
<ImageView
    android:layout_width="50dp"
    android:layout_height="50dp"
    app:radius="5dp" />
```

* 实现四个圆角分别为5dp、6dp、7dp、8dp，xml代码如下
```xml
<ImageView
    android:layout_width="50dp"
    android:layout_height="50dp"
    app:radius_leftTop="5dp"
    app:radius_rightTop="6dp"
    app:radius_rightBottom="7dp"
    app:radius_leftBottom="8dp" />
```

* 实现裁剪成椭圆，当宽高相等时即为圆
```xml
<ImageView
    android:layout_width="100dp"
    android:layout_height="50dp"
    app:radius_oval="true" />
```

* 实现裁剪成圆，并添加边框
```xml
<ImageView
    android:layout_width="50dp"
    android:layout_height="50dp"
    app:border_color="@color/color_border_color"
    app:border_width="2dp"
    app:radius_oval="true" />
```

#### 6.2 固定宽高比属性

* 属性声明
```xml
<declare-styleable name="RatioLayout">
    <attr name="ratio_w2h" format="string" />
</declare-styleable>
```

* 实现宽高比为1:1，注意：宽、高
```xml
<ImageView
    android:layout_width="100dp"
    android:layout_height="wrap_content"
    app:ratio_w2h="1:1" />
```

#### 6.3 夜间模式遮罩规则
* 属性声明
```xml
<declare-styleable name="MaskImageView">
    <attr name="maskColor" format="color" />
    <!--false:遮罩层与原始图片取并集; true:取交集; 默认值false -->
    <attr name="maskUnion" format="boolean" />
</declare-styleable>
```

* xml配置，默认为false
```xml
<ImageView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:maskUnion="true" />
```

### 七、自定义View日夜模式切换的实现

* 实现接口UiModeChangeListener，模版如下
```java
public class CustomView extends View implements UiModeChangeListener {
    
     @Override
     public void onUiModeChange() {
        // UiMode切换，在此处刷新属性
     }
    
}

```
* 参考自定义控件[MaskImageView](ui-mode-2.x/src/main/java/com/aliya/uimode/widget/MaskImageView.java)

### 八、其他相关用法

#### 8.1 Activity监听UiMode切换

Activity实现接口UiModeChangeListener
```java
public class MainActivity extends AppCompatActivity implements UiModeChangeListener {
    
     @Override
     public void onUiModeChange() {
        // UiMode切换在此回调
     }
     
}

```