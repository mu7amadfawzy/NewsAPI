package com.tempo.news.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tempo.news.BR;
import com.tempo.news.R;
import com.tempo.news.databinding.LayoutEmptyDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Created by Fawzy
 * @ ma7madfawzy@gmail.com
 */
public class BaseAdapter<Binding extends ViewDataBinding, DM extends Object> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int EMPTY_VIEW = 77, ROW = 0;
    protected BaseViewHolder.RowCLickListener<DM> rowClickListener;
    protected RecyclerView recyclerView;
    protected List<DM> dataList;
    protected LayoutInflater layoutInflater;
    protected boolean disableEmptyRow;
    @LayoutRes
    private int emptyViewRes;
    @DrawableRes
    private int emptyViewIconRes = R.drawable.ic_search_primary;
    @StringRes
    private int emptyViewMsg = R.string.emptyData, emptyViewMsgDesc = R.string.noDataFound;
    private int rowRes;
    private int bindingVariable;
    private PositionChangeListener positionChangeListener;
    protected BaseViewHolder.ViewClickModel[] viewClickModels;
    private ViewDataBinding emptyViewBinding;

    public BaseAdapter(@LayoutRes int rowRes) {
        this(rowRes, BR.dataModel, null);
    }

    public BaseAdapter(@LayoutRes int rowRes, int bindingVariable) {
        this(rowRes, bindingVariable, null);
    }

    public BaseAdapter(@LayoutRes int rowRes, BaseViewHolder.RowCLickListener<DM> rowCLickListener) {
        this(rowRes, BR.dataModel, rowCLickListener);
    }

    public BaseAdapter(@LayoutRes int rowRes, int bindingVariable, BaseViewHolder.RowCLickListener rowClickListener) {
        this(rowRes, R.layout.layout_empty_data, bindingVariable, rowClickListener);
    }

    public BaseAdapter(@LayoutRes int rowRes, BaseViewHolder.RowCLickListener rowClickListener, BaseViewHolder.ViewClickModel... viewClickModels) {
        this(rowRes, R.layout.layout_empty_data, BR.dataModel, rowClickListener, viewClickModels);
    }

    public BaseAdapter(@LayoutRes int rowRes, @LayoutRes int emptyViewRes, int bindingVariable
            , BaseViewHolder.RowCLickListener<DM> rowClickListener, BaseViewHolder.ViewClickModel... viewClickModels) {
        super();
        this.rowRes = rowRes;
        dataList = null;
        this.emptyViewRes = emptyViewRes;
        this.bindingVariable = bindingVariable;
        this.rowClickListener = rowClickListener;
        this.viewClickModels = viewClickModels;
    }

    public void addRow(DM dm) {
        if (dataList == null)
            setDataList(new ArrayList<>());
        dataList.add(dm);
        notifyItemInserted(dataList.size());
    }

    @CallSuper
    public void setDataList(@NonNull List<DM> data) {
        dataList = data;
        notifyDataSetChanged();
    }

    @CallSuper
    public void updateDataList(@NonNull List<DM> newData) {
        //in case dataList not set yet or the recent data is null(since refers to clear old data) then set dataList instead
        if (dataList == null || dataList.isEmpty() || newData == null)
            setDataList(newData);
        else {
            dataList.addAll(newData);
            notifyItemRangeInserted(dataList.size() - newData.size(), dataList.size());
        }
    }

    public List<DM> getData() {
        return dataList;
    }

    public DM get(int position) {
        return dataList != null && dataList.size() > position ? dataList.get(position) : null;
    }

    public boolean contains(DM dm) {
        return dataList != null && dataList.contains(dm);
    }

    public int positionOf(DM dm) {
        if (dataList == null)
            return -1;
        return dataList.indexOf(dm);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY_VIEW)
            return new BaseViewHolder<ViewDataBinding, DM>(getEmptyRow(parent));
        else
            return onHolderCreated(new BaseViewHolder<Binding, DM>(bindView(parent, rowRes), getRowClickListener(), bindingVariable, viewClickModels));
    }

    /**
     * implement to handle click listeners or any process that needs to be handled only once the holder created
     */
    protected RecyclerView.ViewHolder onHolderCreated(BaseViewHolder<Binding, DM> holder) {
        return holder;
    }

    protected ViewDataBinding getEmptyRow(ViewGroup parent) {
        if (emptyViewBinding == null)
            emptyViewBinding = bindView(parent, emptyViewRes);
        if (emptyViewBinding instanceof LayoutEmptyDataBinding)
            bindEmptyViewVariables((LayoutEmptyDataBinding) emptyViewBinding);
        return emptyViewBinding;
    }

    private void bindEmptyViewVariables(LayoutEmptyDataBinding binding) {
        binding.setDesc(binding.getRoot().getContext().getString(emptyViewMsgDesc));
        binding.setIcon(binding.getRoot().getContext().getDrawable(emptyViewIconRes));
        binding.setMsg(binding.getRoot().getContext().getString(emptyViewMsg));
    }

    @Override
    public int getItemViewType(int position) {
        if (showEmpty())
            return EMPTY_VIEW;
        return ROW;
    }

    protected Binding bindView(ViewGroup parent, int rowRes) {
        if (rowRes == -1) return null;
        return DataBindingUtil.inflate(layoutInflater, rowRes, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder) {
            ((BaseViewHolder) holder).onBind(position, getDm(position), getItemCount());
            if (!isAdapterBusinessRow())
                onHolderBound((BaseViewHolder) holder, position);
        }
        newPosition(position);
    }

    /**
     * returns true if the current row wasn't empty row
     **/
    protected boolean isAdapterBusinessRow() {
        return showEmpty();
    }

    /**
     * invoked after binding the viewHolder
     * *     implement to handle any process that needs to be handled each time holder being bind
     */
    protected void onHolderBound(BaseViewHolder<Binding, DM> holder, int position) {
        //TODO implement in your custom adapter to listen to holder binding

    }

    private void newPosition(int position) {
        if (positionChangeListener != null)
            positionChangeListener.onNewPosition(position);
    }

    private DM getDm(int position) {
        if (dataList.isEmpty()) return null;
        return dataList.get(position);
    }

    public BaseAdapter<Binding, DM> setRowClickListener(@NonNull BaseViewHolder.RowCLickListener<DM> rowClickListener, BaseViewHolder.ViewClickModel... viewClickModels) {
        this.rowClickListener = rowClickListener;
        setViewClickModels(viewClickModels);
        return this;
    }

    /**
     * takes array of ViewClickModel in which you can define the id view of the view and it's clickEvent
     */
    public BaseAdapter<Binding, DM> setViewClickModels(BaseViewHolder.ViewClickModel... viewClickModels) {
        this.viewClickModels = viewClickModels;
        return this;
    }

    public BaseAdapter<Binding, DM> setEmptyView(@LayoutRes int emptyViewRes) {
        this.emptyViewRes = emptyViewRes;
        return this;
    }

    public BaseAdapter<Binding, DM> setEmptyView(ViewDataBinding emptyViewBinding) {
        this.emptyViewBinding = emptyViewBinding;
        return this;
    }

    public BaseAdapter<Binding, DM> setEmptyView(@StringRes int msg, @StringRes int desc, @DrawableRes int iconRes) {
        emptyViewMsg = msg;
        emptyViewMsgDesc = desc;
        emptyViewIconRes = iconRes;
        return this;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : showEmpty() ? 1 : dataList.size();
    }

    protected boolean showEmpty() {
        return !disableEmptyRow && (dataList != null && dataList.isEmpty());
    }

    public boolean isEmpty() {
        return dataList == null || dataList.isEmpty();
    }

    public BaseAdapter<Binding, DM> addOnPositionChangeListener(PositionChangeListener positionChangeListener) {
        this.positionChangeListener = positionChangeListener;
        return this;
    }

    protected boolean isVertical() {
        return isOrientation(RecyclerView.VERTICAL);
    }

    protected boolean isHorizontal() {
        return isOrientation(RecyclerView.HORIZONTAL);
    }

    private boolean isOrientation(int orientation) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            return layoutManager.getOrientation() == orientation;
        }
        return false;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public BaseAdapter<Binding, DM> scrollTo(int position) {
        recyclerView.smoothScrollToPosition(position);
        return this;
    }

    public void remove(int index) {
        if (index >= getItemCount())
            return;
        dataList.remove(index);
        notifyItemRemoved(index);
//        notifyItemChanged(index);
    }

    protected BaseViewHolder.RowCLickListener<DM> getRowClickListener() {
        return rowClickListener;
    }

    public BaseAdapter<Binding, DM> setRowClickListener(@NonNull BaseViewHolder.RowCLickListener<DM> rowClickListener) {
        this.rowClickListener = rowClickListener;
        return this;
    }

    public void setRow(DM dm) {
        int index = dataList.indexOf(dm);
        if (index != -1)
            setRow(index, dm);
    }

    public void setRow(int index, DM dm) {
        if (index < dataList.size()) {
            dataList.set(index, dm);
            notifyItemChanged(index);
        } else addRow(dm);
    }

    public void remove(DM dm) {
        int index = dataList.indexOf(dm);
        if (index != -1)
            remove(index);
    }
    protected boolean nullOrEmpty(List<DM> data) {
        return data==null||data.isEmpty();
    }
    public void disableEmptyRow() {
        this.disableEmptyRow = true;
    }

    public void clearData() {
        setDataList(null);
    }

    public interface PositionChangeListener {
        void onNewPosition(int position);
    }
}