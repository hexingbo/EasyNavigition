# EasyNavigition
几行代码实现Android底部导航栏（带加号、红点提示、数字消息）



简书地址:[https://www.jianshu.com/p/ce8e09cda486](https://www.jianshu.com/p/ce8e09cda486 "悬停显示")


# 前言
因为公司好多项目会用到底部导航栏，大都千篇一律，无非2-5个Tab（可能会有些点击动画、红点提示或者中间多个加号）总是重复相同的操作...所以...很懒的我希望几行代码就能实现这个效果（少敲一行是一行）


# 效果图

![image](https://github.com/forvv231/EasyNavigition/blob/master/screenshot/pre.gif)



# 实现
- 依赖

Step 1. Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
	 compile 'com.github.hexingbo:EasyNavigition:1.0.1'
```

##### 1、基础版
xml
```
<com.hxb.easynavigition.view.EasyNavigitionBar
     android:id="@+id/navigitionBar"
     android:layout_width="match_parent"
     android:layout_height="match_parent">
</com.hxb.easynavigition.view.EasyNavigitionBar>
```
**注：因EasyNavigitionBar包含ViewPager，需设置充满**

Activity
```
private String[] tabText = {"首页", "发现", "消息", "我的"};
//未选中icon
private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
//选中时icon
private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
```
```
navigitionBar = (EasyNavigitionBar)findViewById(R.id.navigitionBar);

fragments.add(new FirstFragment());
fragments.add(new SecondFragment());
fragments.add(new FirstFragment());
fragments.add(new SecondFragment());

navigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .build();
```
怎么样 是不是很Easy啊(￣▽￣)~*

##### 2、加号版本
```
  navigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .mode(EasyNavigitionBar.MODE_ADD)
                .addIcon(R.mipmap.add_image)
                .build();
```
- mode设置为EasyNavigitionBar.MODE_ADD
- 增加中间图片资源（addIcon属性）

---
# 属性
- 什么？不过瘾？看看下面给你提供可以更改的属性，满足你的需求（xml也可设置）
```
 navigitionBar.titleItems(tabText)      //必传  Tab文字集合
                .normalIconItems(normalIcon)   //必传  Tab未选中图标集合
                .selectIconItems(selectIcon)   //必传  Tab选中图标集合
                .fragmentList(fragments)       //必传  fragment集合
                .fragmentManager(getSupportFragmentManager())     //必传
                .iconSize(20)     //Tab图标大小
                .tabTextSize(10)   //Tab文字大小
                .tabTextTop(2)     //Tab文字距Tab图标的距离
                .normalTextColor(Color.parseColor("#666666"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#333333"))   //Tab选中时字体颜色
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)  //同 ImageView的ScaleType
                .navigitionBackground(Color.parseColor("#80000000"))   //导航栏背景色
                .onTabClickListener(new EasyNavigitionBar.OnTabClickListener() {   //Tab点击事件  return true 页面不会切换
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        return false;
                    }
                })
                .smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
                .canScroll(false)    //Viewpager能否左右滑动
                .mode(EasyNavigitionBar.MODE_ADD)   //默认MODE_NORMAL 普通模式  //MODE_ADD 带加号模式
                .anim(Anim.ZoomIn)                //点击Tab时的动画
                .addIcon(R.mipmap.add_image)      //当MODE_ADD模式下    中间加号图片资源
                .addIconSize(36)    //中间加号图片的大小
                .onAddClickListener(new EasyNavigitionBar.OnAddClickListener() {     //中间加号点击事件
                    @Override
                    public boolean OnAddClickEvent(View view) {
                        return false;
                    }
                })
                .addLayoutHeight(100)   //包含加号的布局高度 背景透明  所以加号看起来突出一块
                .navigitionHeight(40)  //导航栏高度
                .lineHeight(100)         //分割线高度  默认1px
                .lineColor(Color.parseColor("#ff0000"))
                .addIconRule(EasyNavigitionBar.RULE_CENTER) //RULE_CENTER 加号居中addLayoutHeight调节位置 EasyNavigitionBar.RULE_BOTTOM 加号在导航栏靠下
                .addIconBottom(10)   //加号到底部的距离
                .hasPadding(true)    //true ViewPager布局在导航栏之上 false有重叠
                .hintPointLeft(-3)  //调节提示红点的位置hintPointLeft hintPointTop（看文档说明）
                .hintPointTop(-3)
                .hintPointSize(6)    //提示红点的大小
                .msgPointLeft(-10)  //调节数字消息的位置msgPointLeft msgPointTop（看文档说明）
                .msgPointTop(-10)
                .msgPointTextSize(9)  //数字消息中字体大小
                .msgPointSize(18)    //数字消息红色背景的大小
                .build();
```

---
# 需求（简单列举几个）
##### 需求1：红点或数字消息提示
```
//数字消息大于99显示99+ 小于等于0不显示，取消显示则可以navigitionBar.setMsgPointCount(2, 0)
navigitionBar.setMsgPointCount(2, 109);
navigitionBar.setMsgPointCount(0, 5);
//红点提示 第二个参数控制是否显示
navigitionBar.setHintPoint(3, true);
```

数字消息的位置可通过msgPointLeft、msgPointTop来控制，默认这两个值为Tab图标的一半
![数字消息位置属性说明.png](https://upload-images.jianshu.io/upload_images/5739496-80a64e7541fe6dbd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**注：红点提示同理，通过hintPointLeft和hintPointTop两个属性调节位置**

##### 需求2：半开放式登录、点击“我的”Tab、不切换页面、需要进行登录操作
```
  .onTabClickListener(new EasyNavigitionBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        if (position == 3) {
                            Toast.makeText(AddActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                })
```
onTabClickEvent方法中return true则拦截事件、不进行页面切换  
    
**注：这种情况canScroll不能设置为true、否则滑动仍会切换页面**



##### 需求3：加号突出（以前这种比较多,现在好多都在导航栏里面、找了好多app...）

![简书和爱奇艺两种加号.png](https://upload-images.jianshu.io/upload_images/5739496-6c7102b32e13e417.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 像图中的两种需求，如何调整加号的位置呢？

提供两种解决方式、addIconRule设置属性RULE_CENTER和RULE_BOTTOM两种
![新建位图图像 (2).png](https://upload-images.jianshu.io/upload_images/5739496-bfa68daabfbf0b31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**1、RULE_CENTER**
中间加号（红色圆圈）相对红色框居中，可通过addLayoutHeight调节加号的位置，**此时addIconBottom属性无效**

**2、RULE_BOTTOM**
中间加号（红色圆圈）在红色框底部，可通过addIconBottom属性调节加号到底部的距离，从而改变加号的位置；
```
 <com.hxb.easynavigition.view.EasyNavigitionBar
        android:id="@+id/navigitionBar"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:Easy_addIconSize="40dp"
        app:Easy_navigitionHeight="60dp"
        app:Easy_addLayoutHeight="80dp"
        app:Easy_addIconBottom="10dp"
        app:Easy_addIconRule="RULE_CENTER">
```


##### 需求4：点击第一个页面中的按钮、跳转到第二个页面并执行其中的方法（部分人可选择无视）
第二个页面添加方法供Activity调用
```
 //提示消息
 public void showToast(String str) {
     Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
 }
```
第一个页面中调用代码
```
 //跳转第二个页面
 ((AddActivity) getActivity()).getNavigitionBar().selectTab(1);
 //调用第二个页面的方法
 ((SecondFragment) (((AddActivity) getActivity()).getNavigitionBar().getAdapter().getItem(1))).showToast("嘻嘻哈哈嗝");
```
---
# Demo
github：[https://github.com/forvv231/EasyNavigition](https://github.com/forvv231/EasyNavigition)

apk：[ https://fir.im/7r4d]( https://fir.im/7r4d)

![image.png](https://upload-images.jianshu.io/upload_images/5739496-92862d9212bbbdb1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

---
# By the way

- **本Demo中仿微博弹出菜单实现**
参考https://github.com/DuShuYuan/PlusMenu

---
# 点个赞吧
刚刚电梯里就自己，然后进来了一对情侣

男：只要长按按钮按错的楼层就会被取消

女：哇真的耶好厉害！！！

被取消掉楼层的我:？？？？
