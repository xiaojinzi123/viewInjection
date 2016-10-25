package com.xiaojinzi.viewinjectionproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xiaojinzi.viewinjection.annotation.Injection;
import com.xiaojinzi.viewinjection.annotation.ViewInjectionUtil;
import com.xiaojinzi.viewinjection.log.L;

@Injection(R.layout.act_main)
public class MainAct extends AppCompatActivity {

    @Injection(value = R.id.tv, click = "clickView")
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Long startTime = System.currentTimeMillis();

        ViewInjectionUtil.injectView(this);

        Long endTime = System.currentTimeMillis();

        System.out.println("注解花费的时间(单位:毫秒):" + (endTime - startTime));

    }

    public void clickView(View v) {
        L.s("哈哈哈");
    }

}
