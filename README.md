# viewInjection

使用方式,针对As用户使用依赖:

compile 'com.xiaojinzi:viewInject:+'

使用范例
====

方式一
----

```
@Injection(R.layout.act_main)
public class MainAct extends AppCompatActivity {

    @Injection(R.id.tv)
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInjectionUtil.injectView(this);

        System.out.println("tv = " + tv);

    }

}
```

方式二
----

```
@Injection(R.layout.act_main)
public class MainAct extends AppCompatActivity {

    private MainActMemberVariable memberVariable = new MainActMemberVariable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInjectionUtil.injectView(memberVariable,this);

        System.out.println("memberVariable.tv = " + memberVariable.tv);

    }

}
```

方式三
---

xml
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.xiaojinzi.code.MainAct">

    <TextView
        android:id="@+id/tv1"
        android:layout_width="wrap_content"
        android:layout_height="100dp"
        android:gravity="center_vertical"
        android:text="hello android" />

    <TextView
        android:id="@+id/tv2"
        android:layout_width="wrap_content"
        android:layout_height="100dp"
        android:layout_below="@+id/tv1"
        android:gravity="center_vertical"
        android:text="hello android" />


</RelativeLayout>
```

```
@Injection(R.layout.act_main)
public class MainAct extends AppCompatActivity {

    @Injection(value = R.id.tv1, click = "clickView1")
    public TextView tv1;

    @Injection(value = R.id.tv2, click = "clickView2")
    public TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInjectionUtil.injectView(this);


    }

    public void clickView1() {
        System.out.println("TextView1.onclick");
    }

    public void clickView2(View view) {
        System.out.println("TextView2.onclick," + view);
    }

}
```

两个文本的点击结果
---------

![这里写图片描述](http://img.blog.csdn.net/20161026115854800)
