package com.xiaojinzi.viewinjection.annotation;

import android.app.Activity;
import android.view.View;

import com.xiaojinzi.viewinjection.log.L;

import java.lang.reflect.Field;

/**
 * activity的注入工具类
 *
 * @author xiaojinzi
 */
public class ViewInjectionUtil {

    /**
     * 类的标识
     */
    private static final String TAG = "ActivityInjectionUtil";

    /**
     * 对activity中的字段进行注入
     *
     * @param act
     */
    public static void injectView(final Activity act) {

        //如果Activity类中有注解,则可以设置Activity的对应的布局文件
        boolean b = act.getClass().isAnnotationPresent(Injection.class);
        if (b) {
            Injection injection = act.getClass().getAnnotation(Injection.class);
            //拿到布局文件id
            int value = injection.value();
            //设置视图
            act.setContentView(value);
        }

        injectView(act, new FindViewListener() {
            @Override
            public View findView(int resId) {
                return act.findViewById(resId);
            }
        });

    }

    /**
     * 对Fragment中的字段进行注入
     *
     * @param o
     * @param contentView
     */
    public static void injectView(Object o, final View contentView) {
        injectView(o, new FindViewListener() {
            @Override
            public View findView(int resId) {
                return contentView.findViewById(resId);
            }
        });
    }

    /**
     * 实现注解的功能
     *
     * @param o                需要注入属性的类
     * @param findViewListener 实现寻找控件的接口
     */
    private static void injectView(Object o, FindViewListener findViewListener) {
        // 获取这个activity中的所有字段
        Field[] fields = o.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            // 循环拿到每一个字段
            Field field = fields[i];
            if (!field.isAnnotationPresent(Injection.class))  // 如果这个字段没有注入的注解
                continue;
            // 获取注解对象
            Injection injection = field.getAnnotation(Injection.class);
            //拿到控件的id
            int value = injection.value();
            //声明返回值
            Object view = null;
            //尝试找到控件并设置到类的成员变量中
            try {
                view = findViewListener.findView(value);
                field.set(o, view);
            } catch (Exception e) {
                e.printStackTrace();
                L.s(TAG, "注入属性失败：" + field.getClass().getName() + ":" + field.getName());
            }

            //尝试实现事件
            try {
                if (view instanceof View == false)
                    continue;
                View v = (View) view;
                // 获取点击事件的触发的方法名称
                String methodName = injection.click();
                EventListener eventListener = null;
                // 如果不是空字符串
                if (!"".equals(methodName)) {
                    //创建事件的实现类
                    eventListener = new EventListener(o);
                    // 设置点击事件
                    v.setOnClickListener(eventListener);
                    //保存点击事件的方法名称,需要在后面点击的时候寻找这个方法
                    eventListener.setClickMethodName(methodName);
                }
                //获取长按事件的触发的方法名称
                methodName = injection.longClick();
                if (!"".equals(methodName)) {
                    if (eventListener == null) {
                        eventListener = new EventListener(o);
                    }
                    // 设置点击事件
                    v.setOnLongClickListener(eventListener);
                    eventListener.setLongClickMethodName(methodName);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface FindViewListener {
        View findView(int resId);
    }

}
