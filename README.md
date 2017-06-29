# AndroidUiMode
让日夜间模式实现起来更简单


#### 不支持的

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