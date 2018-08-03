package com.hxb.easynavigition.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hxb.easynavigition.R;
import com.hxb.easynavigition.adapter.ViewPagerAdapter;
import com.hxb.easynavigition.constant.Anim;
import com.hxb.easynavigition.utils.NavigitionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jue on 2018/6/1.
 */

public class EasyNavigitionBar extends LinearLayout {


    private RelativeLayout AddContainerLayout;

    //Tab数量
    private int tabCount = 0;

    private LinearLayout navigitionLayout;
    private RelativeLayout contentView;
    //分割线
    private View lineView;

    //红点集合
    private List<View> hintPointList = new ArrayList<>();

    //消息数量集合
    private List<TextView> msgPointList = new ArrayList<>();

    //底部Image集合
    private List<ImageView> imageViewList = new ArrayList<>();

    //底部Text集合
    private List<TextView> textViewList = new ArrayList<>();

    //底部TabLayout（除中间加号）
    private List<View> tabList = new ArrayList<>();

    private CustomViewPager mViewPager;
    //private GestureDetector detector;

    private ViewGroup addViewLayout;


    //文字集合
    private String[] titleItems;
    //未选择 图片集合
    private int[] normalIconItems;
    //已选择 图片集合
    private int[] selectIconItems;
    //fragment集合
    private List<Fragment> fragmentList = new ArrayList<>();

    private FragmentManager fragmentManager;

    //Tab点击动画效果
    private Techniques anim = null;
    //ViewPager切换动画
    private boolean smoothScroll = false;
    //图标大小
    private int iconSize = 20;

    //提示红点大小
    private float hintPointSize = 6;
    //提示红点距Tab图标右侧的距离
    private float hintPointLeft = -3;
    //提示红点距图标顶部的距离
    private float hintPointTop = -3;

    private OnTabClickListener onTabClickListener;
    //消息红点字体大小
    private float msgPointTextSize = 9;
    //消息红点大小
    private float msgPointSize = 18;
    //消息红点距Tab图标右侧的距离   默认为Tab图标的一半
    private float msgPointLeft = -10;
    //消息红点距图标顶部的距离  默认为Tab图标的一半
    private float msgPointTop = -10;
    //Tab文字距Tab图标的距离
    private float tabTextTop = 2;
    //Tab文字大小
    private float tabTextSize = 10;
    //未选中Tab字体颜色
    private int normalTextColor = Color.parseColor("#666666");
    //选中字体颜色
    private int selectTextColor = Color.parseColor("#333333");
    //分割线高度
    private float lineHeight = 1;
    //分割线颜色
    private int lineColor = Color.parseColor("#f7f7f7");

    private int navigitionBackground = Color.parseColor("#ffffff");
    private float navigitionHeight = 60;

    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;

    private boolean canScroll;
    private ViewPagerAdapter adapter;


    //Add
    private OnAddClickListener onAddClickListener;
    private float addIconSize = 36;
    private int addIcon;
    private float addLayoutHeight = navigitionHeight;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_ADD = 1;
    public static final int MODE_ADD2 = 2;

    private float addIconBottom = 10;

    //RULE_CENTER 居中只需调节addLayoutHeight 默认和navigitionHeight相等 此时addIconBottom属性无效
    //RULE_BOTTOM addLayoutHeight属性无效、自适应、只需调节addIconBottom距底部的距离
    private int addIconRule = RULE_CENTER;

    public static final int RULE_CENTER = 0;
    public static final int RULE_BOTTOM = 1;

    //true  ViewPager在Navigition上面
    //false  ViewPager和Navigition重叠
    private boolean hasPadding = true;


    //1、普通的Tab 2、中间带按钮（如加号）3、
    private int mode;


    public EasyNavigitionBar(Context context) {
        super(context);

        initViews(context, null);
    }

    public EasyNavigitionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {

        contentView = (RelativeLayout) View.inflate(context, R.layout.container_layout, null);
        addViewLayout = contentView.findViewById(R.id.add_view_ll);
        AddContainerLayout = contentView.findViewById(R.id.add_rl);
        navigitionLayout = contentView.findViewById(R.id.navigition_ll);
        mViewPager = contentView.findViewById(R.id.mViewPager);
        lineView = contentView.findViewById(R.id.common_horizontal_line);


        toDp();


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EasyNavigitionBar);
        parseStyle(attributes);

        addView(contentView);
    }

    private void parseStyle(TypedArray attributes) {
        if (attributes != null) {
            navigitionHeight = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_navigitionHeight, navigitionHeight);
            navigitionBackground = attributes.getColor(R.styleable.EasyNavigitionBar_Easy_navigitionBackground, navigitionBackground);


            tabTextSize = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_tabTextSize, tabTextSize);
            tabTextTop = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_tabTextTop, tabTextTop);
            iconSize = (int) attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_tabIconSize, iconSize);
            hintPointSize = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_hintPointSize, hintPointSize);
            msgPointSize = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_msgPointSize, msgPointSize);
            hintPointLeft = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_hintPointLeft, Math.abs(hintPointLeft));
            msgPointTop = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_msgPointTop, -iconSize / 2);
            hintPointTop = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_hintPointTop, Math.abs(hintPointTop));

            msgPointLeft = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_msgPointLeft, -iconSize / 2);
            msgPointTextSize = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_msgPointTextSize, msgPointTextSize);
            addIconSize = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_addIconSize, addIconSize);
            addIcon = attributes.getInteger(R.styleable.EasyNavigitionBar_Easy_addIconRes, addIcon);
            addIconBottom = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_addIconBottom, addIconBottom);


            lineHeight = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_lineHeight, lineHeight);
            lineColor = attributes.getColor(R.styleable.EasyNavigitionBar_Easy_lineColor, lineColor);


            addLayoutHeight = attributes.getDimension(R.styleable.EasyNavigitionBar_Easy_addLayoutHeight, navigitionHeight + lineHeight);

            normalTextColor = attributes.getColor(R.styleable.EasyNavigitionBar_Easy_tabNormalColor, normalTextColor);
            selectTextColor = attributes.getColor(R.styleable.EasyNavigitionBar_Easy_tabSelectColor, selectTextColor);

            int type = attributes.getInt(R.styleable.EasyNavigitionBar_Easy_scaleType, 0);
            if (type == 0) {
                scaleType = ImageView.ScaleType.CENTER_INSIDE;
            } else if (type == 1) {
                scaleType = ImageView.ScaleType.CENTER_CROP;
            } else if (type == 2) {
                scaleType = ImageView.ScaleType.CENTER;
            } else if (type == 3) {
                scaleType = ImageView.ScaleType.FIT_CENTER;
            } else if (type == 4) {
                scaleType = ImageView.ScaleType.FIT_END;
            } else if (type == 5) {
                scaleType = ImageView.ScaleType.FIT_START;
            } else if (type == 6) {
                scaleType = ImageView.ScaleType.FIT_XY;
            } else if (type == 7) {
                scaleType = ImageView.ScaleType.MATRIX;
            }

            addIconRule = attributes.getInt(R.styleable.EasyNavigitionBar_Easy_addIconRule, addIconRule);
            hasPadding = attributes.getBoolean(R.styleable.EasyNavigitionBar_Easy_hasPadding, hasPadding);

            attributes.recycle();
        }
    }

    //将dp、sp转换成px
    private void toDp() {
        navigitionHeight = NavigitionUtil.dip2px(getContext(), navigitionHeight);
        iconSize = NavigitionUtil.dip2px(getContext(), iconSize);
        hintPointSize = NavigitionUtil.dip2px(getContext(), hintPointSize);
        hintPointTop = NavigitionUtil.dip2px(getContext(), hintPointTop);
        hintPointLeft = NavigitionUtil.dip2px(getContext(), hintPointLeft);

        msgPointLeft = NavigitionUtil.dip2px(getContext(), msgPointLeft);
        msgPointTop = NavigitionUtil.dip2px(getContext(), msgPointTop);
        msgPointSize = NavigitionUtil.dip2px(getContext(), msgPointSize);
        msgPointTextSize = NavigitionUtil.sp2px(getContext(), msgPointTextSize);

        tabTextTop = NavigitionUtil.dip2px(getContext(), tabTextTop);
        tabTextSize = NavigitionUtil.sp2px(getContext(), tabTextSize);


        //Add
        addIconSize = NavigitionUtil.dip2px(getContext(), addIconSize);
        addLayoutHeight = NavigitionUtil.dip2px(getContext(), addLayoutHeight);
        addIconBottom = NavigitionUtil.dip2px(getContext(), addIconBottom);
    }


    public void build() {

        if (addLayoutHeight < navigitionHeight + lineHeight)
            addLayoutHeight = navigitionHeight + lineHeight;

        if (addIconRule == RULE_CENTER) {
            RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            addLayoutParams.height = (int) addLayoutHeight;
            AddContainerLayout.setLayoutParams(addLayoutParams);
        } else if (addIconRule == RULE_BOTTOM) {
            RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            if ((addIconSize + addIconBottom) > (navigitionHeight + 1))
                addLayoutParams.height = (int) (addIconSize + addIconBottom);
            else
                addLayoutParams.height = (int) (navigitionHeight + 1);
            AddContainerLayout.setLayoutParams(addLayoutParams);
        }


        navigitionLayout.setBackgroundColor(navigitionBackground);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) navigitionLayout.getLayoutParams();
        params.height = (int) navigitionHeight;
        navigitionLayout.setLayoutParams(params);


        if (hasPadding) {
            mViewPager.setPadding(0, 0, 0, (int) (navigitionHeight + lineHeight));
        }

        RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
        lineParams.height = (int) lineHeight;
        lineView.setBackgroundColor(lineColor);
        lineView.setLayoutParams(lineParams);

        if (mode == MODE_NORMAL) {
            buildNavigition();
        } else if (mode == MODE_ADD) {
            buildAddNavigition();
        } else if (mode == MODE_ADD2) {
            buildAdd2Navigition();
        }
        if (canScroll) {
            getmViewPager().setCanScroll(true);
        } else {
            getmViewPager().setCanScroll(false);
        }
        select(0, false);
    }

    public void buildNavigition() {
        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length))
            return;

        tabCount = titleItems.length;

        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();

        tabList.clear();

        navigitionLayout.removeAllViews();

        adapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < tabCount; i++) {
            View itemView = View.inflate(getContext(), R.layout.navigition_tab_layout, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.width = NavigitionUtil.getScreenWidth(getContext()) / tabCount;

            itemView.setLayoutParams(params);
            itemView.setId(i);

            TextView text = itemView.findViewById(R.id.tab_text_tv);
            ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
            icon.setScaleType(scaleType);
            LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
            iconParams.width = (int) iconSize;
            iconParams.height = (int) iconSize;
            icon.setLayoutParams(iconParams);

            View hintPoint = itemView.findViewById(R.id.red_point);

            //提示红点
            RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
            hintPointParams.bottomMargin = (int) Math.abs(hintPointTop);
            hintPointParams.width = (int) hintPointSize;
            hintPointParams.height = (int) hintPointSize;
            hintPointParams.leftMargin = (int) Math.abs(hintPointLeft);
            hintPoint.setLayoutParams(hintPointParams);

            //消息红点
            TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
            msgPoint.setTextSize(NavigitionUtil.px2sp(getContext(), msgPointTextSize));
            RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
            msgPointParams.bottomMargin = (int) Math.abs(msgPointTop);
            msgPointParams.width = (int) msgPointSize;
            msgPointParams.height = (int) msgPointSize;
            msgPointParams.leftMargin = (int) Math.abs(msgPointLeft);
            msgPoint.setLayoutParams(msgPointParams);


            hintPointList.add(hintPoint);
            msgPointList.add(msgPoint);

            imageViewList.add(icon);
            textViewList.add(text);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTabClickListener != null) {
                        if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    } else {
                        mViewPager.setCurrentItem(view.getId(), smoothScroll);
                    }
                }
            });

            LayoutParams textParams = (LayoutParams) text.getLayoutParams();
            textParams.topMargin = (int) tabTextTop;
            text.setLayoutParams(textParams);
            text.setText(titleItems[i]);
            text.setTextSize(NavigitionUtil.px2sp(getContext(), tabTextSize));


            tabList.add(itemView);
            navigitionLayout.addView(itemView);
        }

    }

    //构建中间带按钮的navigition
    public void buildAddNavigition() {
        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length))
            return;
        tabCount = titleItems.length + 1;
        int index = 0;


        hintPointList.clear();
        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();
        tabList.clear();

        navigitionLayout.removeAllViews();

        adapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < tabCount; i++) {

            if (i == tabCount / 2) {
                RelativeLayout addLayout = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                addParams.width = NavigitionUtil.getScreenWidth(getContext()) / tabCount;
                addLayout.setLayoutParams(addParams);
                navigitionLayout.addView(addLayout);
            } else {

                if (i > 1) {
                    index = i - 1;
                } else {
                    index = i;
                }

                View itemView = View.inflate(getContext(), R.layout.navigition_tab_layout, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.width = NavigitionUtil.getScreenWidth(getContext()) / tabCount;

                itemView.setLayoutParams(params);
                itemView.setId(index);

                TextView text = itemView.findViewById(R.id.tab_text_tv);
                ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
                icon.setScaleType(scaleType);
                LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);

                imageViewList.add(icon);
                textViewList.add(text);

                itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        } else {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    }
                });

                LayoutParams textParams = (LayoutParams) text.getLayoutParams();
                textParams.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams);
                text.setText(titleItems[index]);
                text.setTextSize(NavigitionUtil.px2sp(getContext(), tabTextSize));


                View hintPoint = itemView.findViewById(R.id.red_point);

                //提示红点
                RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
                hintPointParams.bottomMargin = (int) Math.abs(hintPointTop);
                hintPointParams.width = (int) hintPointSize;
                hintPointParams.height = (int) hintPointSize;
                hintPointParams.leftMargin = (int) Math.abs(hintPointLeft);
                hintPoint.setLayoutParams(hintPointParams);

                //消息红点
                TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
                msgPoint.setTextSize(NavigitionUtil.px2sp(getContext(), msgPointTextSize));
                RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
                msgPointParams.bottomMargin = (int) Math.abs(msgPointTop);
                msgPointParams.width = (int) msgPointSize;
                msgPointParams.height = (int) msgPointSize;
                msgPointParams.leftMargin = (int) Math.abs(msgPointLeft);
                msgPoint.setLayoutParams(msgPointParams);


                hintPointList.add(hintPoint);
                msgPointList.add(msgPoint);


                tabList.add(itemView);
                navigitionLayout.addView(itemView);
            }
        }


        RelativeLayout addLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addParams.width = NavigitionUtil.getScreenWidth(getContext()) / tabCount;
        if (addIconRule == RULE_CENTER) {
            addParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (addIconRule == RULE_BOTTOM) {
            addParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            addParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }

        ImageView addImage = new ImageView(getContext());
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.width = (int) addIconSize;
        imageParams.height = (int) addIconSize;

        if (addIconRule == RULE_CENTER) {
            imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (addIconRule == RULE_BOTTOM) {
            imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            imageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            imageParams.bottomMargin = (int) addIconBottom;
        }

        addImage.setImageResource(addIcon);
        addImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAddClickListener != null)
                    onAddClickListener.OnAddClickEvent(view);
            }
        });

        addLayout.addView(addImage, imageParams);
        AddContainerLayout.addView(addLayout, addParams);
    }


    //构建中间带按钮的navigition2
    public void buildAdd2Navigition() {
        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length))
            return;
        tabCount = titleItems.length + 1;
        int index = 0;


        hintPointList.clear();
        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();
        tabList.clear();

        navigitionLayout.removeAllViews();

        adapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < tabCount; i++) {

            if (i == tabCount / 2) {
                RelativeLayout addLayout = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                addParams.width = NavigitionUtil.getScreenWidth(getContext()) / tabCount;
                addLayout.setLayoutParams(addParams);
                navigitionLayout.addView(addLayout);
            } else {

                if (i > 1) {
                    index = i - 1;
                } else {
                    index = i;
                }

                View itemView = View.inflate(getContext(), R.layout.navigition_tab_layout, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.width = NavigitionUtil.getScreenWidth(getContext()) / tabCount;

                itemView.setLayoutParams(params);
                itemView.setId(index);

                TextView text = itemView.findViewById(R.id.tab_text_tv);
                ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
                icon.setScaleType(scaleType);
                LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);

                imageViewList.add(icon);
                textViewList.add(text);

                itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        } else {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    }
                });

                LayoutParams textParams = (LayoutParams) text.getLayoutParams();
                textParams.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams);
                text.setText(titleItems[index]);
                text.setTextSize(NavigitionUtil.px2sp(getContext(), tabTextSize));


                View hintPoint = itemView.findViewById(R.id.red_point);

                //提示红点
                RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
                hintPointParams.bottomMargin = (int) Math.abs(hintPointTop);
                hintPointParams.width = (int) hintPointSize;
                hintPointParams.height = (int) hintPointSize;
                hintPointParams.leftMargin = (int) Math.abs(hintPointLeft);
                hintPoint.setLayoutParams(hintPointParams);

                //消息红点
                TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
                msgPoint.setTextSize(NavigitionUtil.px2sp(getContext(), msgPointTextSize));
                RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
                msgPointParams.bottomMargin = (int) Math.abs(msgPointTop);
                msgPointParams.width = (int) msgPointSize;
                msgPointParams.height = (int) msgPointSize;
                msgPointParams.leftMargin = (int) Math.abs(msgPointLeft);
                msgPoint.setLayoutParams(msgPointParams);


                hintPointList.add(hintPoint);
                msgPointList.add(msgPoint);


                tabList.add(itemView);
                navigitionLayout.addView(itemView);
            }
        }


        RelativeLayout addLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addParams.width = NavigitionUtil.getScreenWidth(getContext()) / tabCount;
        if (addIconRule == RULE_CENTER) {
            addParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (addIconRule == RULE_BOTTOM) {
            addParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            addParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }

        ImageView addImage = new ImageView(getContext());
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.width = (int) addIconSize;
        imageParams.height = (int) addIconSize;

        if (addIconRule == RULE_CENTER) {
            imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (addIconRule == RULE_BOTTOM) {
            imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            imageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            imageParams.bottomMargin = (int) addIconBottom;
        }

        addImage.setImageResource(addIcon);
        addImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTabClickListener != null)
                    onTabClickListener.onTabClickEvent(view, tabCount / 2);
            }
        });

        addLayout.addView(addImage, imageParams);
        AddContainerLayout.addView(addLayout, addParams);
    }


    public CustomViewPager getmViewPager() {
        return mViewPager;
    }


    public void setAddViewLayout(View addViewLayout) {
        FrameLayout.LayoutParams addParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addViewLayout.addView(addViewLayout, addParams);
    }

    public ViewGroup getAddViewLayout() {
        return addViewLayout;
    }

    /**
     * tab图标、文字变换
     *
     * @param position
     */
    private void select(int position, boolean showAnim) {
        for (int i = 0; i < imageViewList.size(); i++) {
            if (i == position) {
                if (anim != null && showAnim)
                    YoYo.with(anim).duration(300).playOn(tabList.get(i));
                imageViewList.get(i).setImageResource(selectIconItems[i]);
                textViewList.get(i).setTextColor(selectTextColor);
            } else {
                imageViewList.get(i).setImageResource(normalIconItems[i]);
                textViewList.get(i).setTextColor(normalTextColor);
            }
        }
    }


    public void selectTab(int position) {
        getmViewPager().setCurrentItem(position, smoothScroll);
    }


    /**
     * 设置是否显示小红点
     *
     * @param position 第几个tab
     * @param isShow   是否显示
     */
    public void setHintPoint(int position, boolean isShow) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        if (isShow) {
            hintPointList.get(position).setVisibility(VISIBLE);
        } else {
            hintPointList.get(position).setVisibility(GONE);
        }
    }


    /**
     * 设置消息数量
     *
     * @param position 第几个tab
     * @param count    显示的数量  99个以上显示99+  少于1则不显示
     */
    public void setMsgPointCount(int position, int count) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        if (count > 99) {
            msgPointList.get(position).setText("99+");
            msgPointList.get(position).setVisibility(VISIBLE);
        } else if (count < 1) {
            msgPointList.get(position).setVisibility(GONE);
        } else {
            msgPointList.get(position).setText(count + "");
            msgPointList.get(position).setVisibility(VISIBLE);
        }
    }

    /**
     * 清空所有提示红点
     */
    public void clearAllHintPoint() {
        for (int i = 0; i < hintPointList.size(); i++) {
            hintPointList.get(i).setVisibility(GONE);
        }
    }

    /**
     * 清空所有消息红点
     */
    public void clearAllMsgPoint() {
        for (int i = 0; i < msgPointList.size(); i++) {
            msgPointList.get(i).setVisibility(GONE);
        }
    }


    public interface OnTabClickListener {
        boolean onTabClickEvent(View view, int position);
    }


    public interface OnAddClickListener {
        boolean OnAddClickEvent(View view);
    }


    public EasyNavigitionBar addLayoutHeight(int addLayoutHeight) {
        this.addLayoutHeight = NavigitionUtil.dip2px(getContext(), addLayoutHeight);
        return this;
    }

    public EasyNavigitionBar scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }


    public EasyNavigitionBar addIcon(int addIcon) {
        this.addIcon = addIcon;
        return this;
    }


    public EasyNavigitionBar mode(int mode) {
        this.mode = mode;
        return this;
    }

    public EasyNavigitionBar hasPadding(boolean hasPadding) {
        this.hasPadding = hasPadding;
        return this;
    }

    public EasyNavigitionBar addIconSize(int addIconSize) {
        this.addIconSize = NavigitionUtil.dip2px(getContext(), addIconSize);
        return this;
    }

    public EasyNavigitionBar onAddClickListener(OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
        return this;
    }


    public EasyNavigitionBar navigitionBackground(int navigitionBackground) {
        this.navigitionBackground = navigitionBackground;
        return this;
    }

    public EasyNavigitionBar navigitionHeight(int navigitionHeight) {
        this.navigitionHeight = NavigitionUtil.dip2px(getContext(), navigitionHeight);
        return this;
    }

    public EasyNavigitionBar normalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        return this;
    }

    public EasyNavigitionBar selectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        return this;
    }

    public EasyNavigitionBar lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public EasyNavigitionBar lineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public EasyNavigitionBar tabTextSize(int tabTextSize) {
        this.tabTextSize = NavigitionUtil.sp2px(getContext(), tabTextSize);
        return this;
    }

    public EasyNavigitionBar tabTextTop(int tabTextTop) {
        this.tabTextTop = NavigitionUtil.dip2px(getContext(), tabTextTop);
        return this;
    }

    public EasyNavigitionBar msgPointTextSize(int msgPointTextSize) {
        this.msgPointTextSize = NavigitionUtil.sp2px(getContext(), msgPointTextSize);
        return this;
    }

    public EasyNavigitionBar msgPointSize(int msgPointSize) {
        this.msgPointSize = NavigitionUtil.dip2px(getContext(), msgPointSize);
        return this;
    }

    public EasyNavigitionBar msgPointLeft(int msgPointLeft) {
        this.msgPointLeft = NavigitionUtil.dip2px(getContext(), msgPointLeft);
        return this;
    }

    public EasyNavigitionBar msgPointTop(int msgPointTop) {
        this.msgPointTop = NavigitionUtil.dip2px(getContext(), msgPointTop);
        return this;
    }


    public EasyNavigitionBar hintPointSize(int hintPointSize) {
        this.hintPointSize = NavigitionUtil.dip2px(getContext(), hintPointSize);
        return this;
    }

    public EasyNavigitionBar hintPointLeft(int hintPointLeft) {
        this.hintPointLeft = NavigitionUtil.dip2px(getContext(), hintPointLeft);
        return this;
    }

    public EasyNavigitionBar hintPointTop(int hintPointTop) {
        this.hintPointTop = NavigitionUtil.dip2px(getContext(), hintPointTop);
        return this;
    }


    public EasyNavigitionBar titleItems(String[] titleItems) {
        this.titleItems = titleItems;
        return this;
    }

    public EasyNavigitionBar normalIconItems(int[] normalIconItems) {
        this.normalIconItems = normalIconItems;
        return this;
    }

    public EasyNavigitionBar selectIconItems(int[] selectIconItems) {
        this.selectIconItems = selectIconItems;
        return this;
    }

    public EasyNavigitionBar fragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        return this;
    }

    public EasyNavigitionBar fragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public EasyNavigitionBar anim(Anim anim) {
        this.anim = anim.getYoyo();
        return this;
    }

    public EasyNavigitionBar addIconRule(int addIconRule) {
        this.addIconRule = addIconRule;
        return this;
    }

    public EasyNavigitionBar canScroll(boolean canScroll) {
        this.canScroll = canScroll;
        return this;
    }

    public EasyNavigitionBar smoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
        return this;
    }


    public EasyNavigitionBar onTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        return this;
    }


    public EasyNavigitionBar iconSize(int iconSize) {
        this.iconSize = NavigitionUtil.dip2px(getContext(), iconSize);
        return this;
    }

    public EasyNavigitionBar addIconBottom(int addIconBottom) {
        this.addIconBottom = NavigitionUtil.dip2px(getContext(), addIconBottom);
        return this;
    }


    public String[] getTitleItems() {
        return titleItems;
    }

    public int[] getNormalIconItems() {
        return normalIconItems;
    }

    public int[] getSelectIconItems() {
        return selectIconItems;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public Techniques getAnim() {
        return anim;
    }

    public boolean isSmoothScroll() {
        return smoothScroll;
    }

    public OnTabClickListener getOnTabClickListener() {
        return onTabClickListener;
    }

    public int getIconSize() {
        return iconSize;
    }


    public float getHintPointSize() {
        return hintPointSize;
    }

    public float getHintPointLeft() {
        return hintPointLeft;
    }

    public float getHintPointTop() {
        return hintPointTop;
    }


    public float getMsgPointTextSize() {
        return msgPointTextSize;
    }

    public float getMsgPointSize() {
        return msgPointSize;
    }

    public float getMsgPointLeft() {
        return msgPointLeft;
    }

    public float getMsgPointTop() {
        return msgPointTop;
    }

    public float getTabTextTop() {
        return tabTextTop;
    }

    public float getTabTextSize() {
        return tabTextSize;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public int getLineColor() {
        return lineColor;
    }

    public OnAddClickListener getOnAddClickListener() {
        return onAddClickListener;
    }

    public float getAddIconSize() {
        return addIconSize;
    }

    public int getAddIcon() {
        return addIcon;
    }

    public float getAddLayoutHeight() {
        return addLayoutHeight;
    }

    public int getNavigitionBackground() {
        return navigitionBackground;
    }

    public float getNavigitionHeight() {
        return navigitionHeight;
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public ViewPagerAdapter getAdapter() {
        return adapter;
    }


    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public int getMode() {
        return mode;
    }

    public LinearLayout getNavigitionLayout() {
        return navigitionLayout;
    }

    public RelativeLayout getContentView() {
        return contentView;
    }

    public View getLineView() {
        return lineView;
    }

    public ViewGroup getAddLayout() {
        return addViewLayout;
    }

    public float getAddIconBottom() {
        return addIconBottom;
    }

    public int getAddIconRule() {
        return addIconRule;
    }

    public RelativeLayout getAddContainerLayout() {
        return AddContainerLayout;
    }

    public boolean isHasPadding() {
        return hasPadding;
    }
}
