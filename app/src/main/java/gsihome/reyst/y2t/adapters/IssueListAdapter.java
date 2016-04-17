package gsihome.reyst.y2t.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gsihome.reyst.y2t.R;
import gsihome.reyst.y2t.data.DataUtil;
import gsihome.reyst.y2t.data.IssueEntity;

public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.IssueViewHolder> {

    private Context mContext;
    private List<IssueEntity> mModel;

    private OnItemClickListener mOnClickListener;
    private DateFormat mFormatter = DataUtil.getFormatter();

    public IssueListAdapter(Context mContext, List<IssueEntity> model, OnItemClickListener listener) {
        this.mContext = mContext;
        initModel(model);

        mOnClickListener = listener;
    }

    private void initModel(Collection<IssueEntity> data) {
        mModel = new ArrayList<>(data.size());
        mModel.addAll(data);
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_card, parent, false);

        return new IssueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {

        IssueEntity issueEntity = mModel.get(position);

        holder.categoryTitle.setText(issueEntity.getCategory());
        holder.taskDesc.setText(issueEntity.getFullText());
        holder.likesAmount.setText(String.valueOf(issueEntity.getLikeAmount()));
        holder.categoryIcon.setImageDrawable(mContext.getResources().getDrawable(issueEntity.getIconId()));
        holder.dateCreated.setText(mFormatter.format(issueEntity.getCreated()));
        holder.daysAmount.setText(String.valueOf(issueEntity.getDaysAmount()));

    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public static class IssueViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTitle;
        private TextView taskDesc;
        private TextView daysAmount;
        private TextView dateCreated;
        private TextView likesAmount;
        private ImageView categoryIcon;

        public IssueViewHolder(View itemView) {
            super(itemView);

            categoryTitle = (TextView) itemView.findViewById(R.id.category_title);
            categoryIcon = (ImageView) itemView.findViewById(R.id.category_icon);
            taskDesc = (TextView) itemView.findViewById(R.id.task_desc);
            daysAmount = (TextView) itemView.findViewById(R.id.amount_days);
            dateCreated = (TextView) itemView.findViewById(R.id.date_created);
            likesAmount = (TextView) itemView.findViewById(R.id.likes_amount);

        }
    }

}
