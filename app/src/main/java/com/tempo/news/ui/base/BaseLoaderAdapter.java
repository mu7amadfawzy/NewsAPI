package com.tempo.news.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tempo.news.BR;
import com.tempo.news.R;
import com.tempo.news.databinding.LoadRowBinding;
import com.tempo.news.utils.EndlessScrollListener;

import java.util.List;

import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * @param <Binding>: defines the row binding implementation.</Binding> * @param <DM>: defines the data model class of rows.</DM> * @BaseLoaderAdapter : generic class to adapt recyclerView rows with Pagination . * @Created by Fawzy on 08/07/2019.
 */
public class BaseLoaderAdapter<Binding extends ViewDataBinding, DM extends Object> extends BaseAdapter<Binding, DM> {
    private final int LOADING_ROW = 88;
    private PaginationHandler paginationHandler;
    private boolean loading;
    private int totalItemCount = Integer.MAX_VALUE;
    private int currentPage = 1;

    public BaseLoaderAdapter(@LayoutRes int rowRes) {
        this(rowRes, null);
    }

    public BaseLoaderAdapter(@LayoutRes int rowRes, PaginationHandler paginationHandler) {
        this(rowRes, BR.dataModel, paginationHandler);
    }

    public BaseLoaderAdapter(@LayoutRes int rowRes, int bindingVariable, PaginationHandler paginationHandler) {
        this(rowRes, bindingVariable, paginationHandler, null);
    }

    public BaseLoaderAdapter(@LayoutRes int rowRes, PaginationHandler paginationHandler, BaseViewHolder.RowCLickListener<DM> rowClickListener) {
        this(rowRes, R.layout.layout_empty_data, BR.dataModel, paginationHandler, rowClickListener);
    }

    public BaseLoaderAdapter(@LayoutRes int rowRes, int bindingVariable, PaginationHandler paginationHandler, BaseViewHolder.RowCLickListener rowClickListener) {
        this(rowRes, R.layout.layout_empty_data, bindingVariable, paginationHandler, rowClickListener);
    }

    public BaseLoaderAdapter(@LayoutRes int rowRes, @LayoutRes int emptyViewRes, int bindingVariable, PaginationHandler paginationHandler, BaseViewHolder.RowCLickListener rowClickListener) {
        super(rowRes, emptyViewRes, bindingVariable, rowClickListener);
        setPaginationHandler(paginationHandler);
    }

    public void setDataList(List<DM> data) {
        disableLoading();
        super.setDataList(data);
    }

    public void updateDataList(List<DM> newData) {
        disableLoading();
        super.updateDataList(newData);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (paginationHandler != null) setupPagination(recyclerView);
    }

    private void setupPagination(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new EndlessScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int items) {
                if (showEmpty() || loading) return;
                if (paginationHandler != null && items < totalItemCount) {
                    paginationHandler.onLoadMore(++currentPage, items);
                    enableLoading();
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == LOADING_ROW) return new BaseViewHolder(getLoadingRowBinding(parent));
        else return super.onCreateViewHolder(parent, viewType);
    }

    /**
     * returns true if the current row wasn't loadingRow nor empty row
     **/
    @Override
    protected boolean isAdapterBusinessRow() {
        return super.isAdapterBusinessRow() || loading;
    }

    protected ViewDataBinding getLoadingRowBinding(ViewGroup parent) {
        LoadRowBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.load_row, parent, false);
        LayoutParams params = binding.progressContainer.getLayoutParams();
        if (isVertical()) params = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        else if (isHorizontal()) params = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        binding.progressContainer.setLayoutParams(params);
        return binding;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadingRow(position)) return LOADING_ROW;
        return super.getItemViewType(position);
    }

    @Override
    public void clearData() {
        super.clearData();
        currentPage = 1;
    }

    private boolean isLoadingRow(int position) {
        return loading && dataList != null && dataList.get(position) == null;
    }

    public void enableLoading() {
        if (dataList != null && dataList.size() > 0 && dataList.get(dataList.size() - 1) != null) {
            dataList.add(null);
            loading = true;
            notifyItemInserted(dataList.size() - 1);
        }
    }

    public void disableLoading() {
        if (dataList != null && dataList.size() >= 1 && dataList.get(dataList.size() - 1) == null) {
            dataList.remove(dataList.size() - 1);
            notifyItemRemoved(dataList.size());
            loading = false;
        }
    }

    public BaseLoaderAdapter<Binding, DM> setPaginationHandler(@NonNull PaginationHandler paginationHandler) {
        this.paginationHandler = paginationHandler;
        if (recyclerView != null) setupPagination(recyclerView);
        return this;
    }

    public BaseLoaderAdapter<Binding, DM> setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
        return this;
    }

    public BaseLoaderAdapter<Binding, DM> setTotalItemCount(ObservableInt observableInt) {
        observableInt.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                totalItemCount = observableInt.get();
            }
        });
        return this;
    }

    public interface PaginationHandler {
        void onLoadMore(int page, int totalRows);
    }
}