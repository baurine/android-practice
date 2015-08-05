# Embedded TabBar Demo
参考 [这篇博客](http://codethink.me/2014/09/14/2014-09-14-embedded-tab-bar/) 进行的练习。

但实现效果有所区别。我的例子中使用了 appcompat support library，而作者没有使用。

使用了 appcompat 库后，在设置 theme 时，有些属性不需要加 `android:` 前缀。具体原因看此 [链接](http://stackoverflow.com/questions/18726865/custom-style-action-bar-not-working-in-android-4)。如下所示：

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="actionBarStyle">@style/EmbeddedActionBar</item>
        <item name="actionBarTabStyle">@style/EmbeddedTabStyle</item>
        <item name="actionBarTabBarStyle">@style/customActionBarTabBarStyle</item>
    </style>

    <style name="EmbeddedActionBar" parent="Widget.AppCompat.Light.ActionBar.Solid">
        <item name="displayOptions">none</item>
        <item name="background">@color/actionbar_dark_grey</item>
    </style>

    <style name="EmbeddedTabStyle" parent="Widget.AppCompat.Light.ActionBar.TabView">
        <item name="android:padding">0dp</item>
        <item name="android:gravity">center</item>
        <item name="android:background">@drawable/actionbar_tabs_bg</item>
    </style>

    <style name="customActionBarTabBarStyle" parent="Widget.AppCompat.Light.ActionBar.TabBar">
        <item name="divider">@null</item>
    </style>

#### 截图

![embeddedtabbar](apk/embeddedtabbar.gif)

最后的效果并没有达到和原作者一样，不知道该怎么解决。