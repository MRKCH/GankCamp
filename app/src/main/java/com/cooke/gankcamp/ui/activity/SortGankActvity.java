package com.cooke.gankcamp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.contsants.ContentType;
import com.cooke.gankcamp.ui.adapter.GankFragmentAdapter;
import com.cooke.gankcamp.ui.adapter.SortGankFragAdapter;

import butterknife.BindView;

public class SortGankActvity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private SortGankFragAdapter mAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_sort_gank_actvity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tab_layout.addTab(tab_layout.newTab().setText(ContentType.ALL));
        tab_layout.addTab(tab_layout.newTab().setText(ContentType.ANDROID));
        tab_layout.addTab(tab_layout.newTab().setText(ContentType.IOS));
        tab_layout.addTab(tab_layout.newTab().setText(ContentType.WEB));
        tab_layout.addTab(tab_layout.newTab().setText(ContentType.RECOMMEND));
        tab_layout.addTab(tab_layout.newTab().setText(ContentType.EXTENTAL_RESOURCE));

        mAdapter = new SortGankFragAdapter(getSupportFragmentManager());
        view_pager.setAdapter(mAdapter);
        view_pager.setOffscreenPageLimit(3);
        tab_layout.setupWithViewPager(view_pager);

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
