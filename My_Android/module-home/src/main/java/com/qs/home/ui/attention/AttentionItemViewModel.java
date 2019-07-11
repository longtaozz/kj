package com.qs.home.ui.attention;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.qs.home.entity.AttentionEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class AttentionItemViewModel extends ItemViewModel<AttentionViewModel> {

    public ObservableField<AttentionEntity.ItemsEntity> mItemEntity = new ObservableField<>();

    public AttentionItemViewModel(@NonNull AttentionViewModel viewModel, AttentionEntity.ItemsEntity itemsEntity) {
        super(viewModel);

        mItemEntity.set(itemsEntity);
    }

}
