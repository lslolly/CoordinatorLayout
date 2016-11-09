package com.example.administrator.myapplication1109;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mFeedlist;
    private RelativeLayout mRlbar;
    private CircleImageView mIvataver;
    private TextView mTvniclname;
    private int mCurrentPosition=0;
    private int mSuspensionHeight;

    private  FeedAdapter mFeedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
         final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mFeedAdapter = new FeedAdapter();
        mRlbar = (RelativeLayout) findViewById(R.id.suspension_bar);
        mIvataver = (CircleImageView) findViewById(R.id.iv_avatar);
        mTvniclname = (TextView) findViewById(R.id.tv_nickname);
        mFeedlist = (RecyclerView) findViewById(R.id.feed_list);
        mFeedlist.setLayoutManager(linearLayoutManager);
        mFeedlist.setAdapter(mFeedAdapter);
        mFeedlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight =mRlbar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View view =linearLayoutManager.findViewByPosition(mCurrentPosition+1);
                if(view==null){
                    return;
                }
                if(view.getTop()<=mSuspensionHeight){
                    mRlbar.setY(-(mSuspensionHeight-view.getTop()));
                }else {
                    mRlbar.setY(0);
                }
                if(mCurrentPosition!=linearLayoutManager.findFirstVisibleItemPosition()){
                    mCurrentPosition=linearLayoutManager.findFirstVisibleItemPosition();
                    mRlbar.setY(0);
                    updateSuspensionBar();
                }
            }
        });

    }

    private void updateSuspensionBar() {
        Picasso.with(MainActivity.this)
                .load(getAvatarResId(mCurrentPosition))
                .centerInside()
                .fit()
                .into(mIvataver);

        mTvniclname.setText("Taeyeon " + mCurrentPosition);
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }
}
