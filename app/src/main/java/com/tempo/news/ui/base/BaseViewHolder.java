/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tempo.news.ui.base;

import androidx.annotation.IdRes;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.tempo.news.BR;

/**
 * @ Created by Fawzy on 08/07/19.
 * @ ma7madfawzy@gmail.com
 */

public class BaseViewHolder<Binding extends ViewDataBinding, DM> extends RecyclerView.ViewHolder {
    public Binding binding;
    protected RowCLickListener cLickListener;
    protected ViewClickModel[] viewClickModels;
    protected int bindingVariable = -1;
    protected DM dataModel;
    private int itemCount;

    public BaseViewHolder(Binding binding) {
        this(binding, -1);
    }

    public BaseViewHolder(Binding binding, int bindingVariable) {
        this(binding, null, bindingVariable);
    }

    public BaseViewHolder(Binding binding, RowCLickListener cLickListener, int bindingVariable, ViewClickModel... viewClickModels) {
        super(binding.getRoot());
        this.binding = binding;
        this.cLickListener = cLickListener;
        this.bindingVariable = bindingVariable;
        this.bindingVariable = bindingVariable;
        setViewClickModels(viewClickModels);
    }

    public void setViewClickModels(ViewClickModel... viewClickModels) {
        this.viewClickModels = viewClickModels;
        setListeners();
    }

    private void setListeners() {
        if (cLickListener != null)
            itemView.setOnClickListener(v -> cLickListener.onRowClicked(dataModel, getAdapterPosition()));
        if (viewClickModels != null && viewClickModels.length > 0)
            for (ViewClickModel model : viewClickModels)
                if (itemView.findViewById(model.viewId) != null)
                    binding.getRoot().findViewById(model.viewId)
                            .setOnClickListener(v -> model.cLickListener.onRowClicked(dataModel, getAdapterPosition()));
    }

    public void onBind(int position, DM dm) {
        this.dataModel = dm;
        bindView(position);
    }

    public void onBind(int position, DM dm, int itemCount) {
        this.itemCount = itemCount;
        this.dataModel = dm;
        bindView(position);
    }

    private void bindView(int position) {
        try {
            binding.setVariable(BR.position, position);
//            binding.setVariable(BR.totalCount, itemCount);
            binding.setVariable(bindingVariable, dataModel);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface RowCLickListener<DM> {
        void onRowClicked(DM dataModel, int index);
    }

    public static class ViewClickModel {
        public RowCLickListener cLickListener;
        @IdRes
        public int viewId;

        public ViewClickModel(@IdRes int viewId, RowCLickListener cLickListener) {
            this.cLickListener = cLickListener;
            this.viewId = viewId;
        }
    }
}
