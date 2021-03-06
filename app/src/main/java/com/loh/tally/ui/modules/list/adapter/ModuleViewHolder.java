package com.loh.tally.ui.modules.list.adapter;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.loh.tally.R;
import com.loh.tally.domain.model.Module;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File: ModuleViewHolder.java
 * Date: 10/03/2017
 * Created By: Liam O'Hanlon
 */
public class ModuleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.container) View container;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.code) TextView code;
    @BindView(R.id.chat) View chatView;
    @BindView(R.id.circle) ImageView circle;

    public ModuleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Module module, OnModuleItemClickListener listener) {
        String firstLetter = module.getName().substring(0, 1);
        int color = ColorGenerator.MATERIAL.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder().buildRound(firstLetter, color);
        circle.setImageDrawable(textDrawable);

        name.setText(module.getName());
        code.setText(module.getCode());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            chatView.setBackgroundTintList(ColorStateList.valueOf(color));
        }

        container.setOnClickListener(v -> {
            if (listener != null) {
                listener.onModuleClicked(module);
            }
        });

        chatView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChatClicked(module.getId());
            }
        });

    }

    public interface OnModuleItemClickListener {
        void onModuleClicked(Module module);

        void onChatClicked(String moduleID);
    }

}
