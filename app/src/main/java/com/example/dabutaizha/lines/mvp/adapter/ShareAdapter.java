package com.example.dabutaizha.lines.mvp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.database.SentencesObjectBox;

import java.util.List;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/31 下午7:07.
 */

public class ShareAdapter extends BaseQuickAdapter<SearchInfo.SentencesItem, BaseViewHolder> {

    private Context mContext;
    private SearchInfo.SentencesItem mSentencesItem;

    public ShareAdapter(Context context, @Nullable List<SearchInfo.SentencesItem> data) {
        super(R.layout.share_item, data);
        mContext = context;
        mSentencesItem = data.get(0);
    }

    public void startConvert(BaseViewHolder viewHolder, SearchInfo.SentencesItem item){
        convert(viewHolder, item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchInfo.SentencesItem item) {
        ImageLoader.loadRoundImageByRes(helper.getView(R.id.share_card_top_image).getContext(),
                helper.getView(R.id.share_card_top_image),
                R.drawable.card_default_bg,
                18,
                0);
        ImageLoader.loadTransformByRes(helper.getView(R.id.share_card_top_image_bg).getContext(),
                helper.getView(R.id.share_card_top_image_bg),
                R.drawable.card_default_bg);

        helper.setText(R.id.share_card_top_title, item.getArticle());
        helper.setText(R.id.share_card_top_author, item.getWriter());

        // 把中文字符串的空格改为换行
        String content = item.getContent().trim();
        String regex = ".*[a-zA-Z]+.*";
        if (!content.matches(regex)) {
            content = content.replace(" ", "\n");
        }
        helper.setText(R.id.share_card_top_content, content);

        helper.addOnClickListener(R.id.share_card_top_collect);
        ImageView imageView = helper.getView(R.id.share_card_top_collect);

        SentencesObjectBox
                .getInstance()
                .checkIsExistByRxJava(Integer.valueOf(mSentencesItem.getSentencesId()))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(mContext.getDrawable(R.drawable.collection));
                    } else {
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(mContext.getDrawable(R.drawable.no_collection));
                    }
                });
    }

}