package gsihome.reyst.y2t.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

    private OnItemClickListener mOnItemClickListener;
    private DateFormat mFormatter = DataUtil.getFormatter();

    public IssueListAdapter(Context mContext, List<IssueEntity> model, OnItemClickListener listener) {
        this.mContext = mContext;
        initModel(model);
        mOnItemClickListener = listener;
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

        holder.mTvCategoryTitle.setText(issueEntity.getCategory());
        holder.mTvTaskDesc.setText(issueEntity.getFullText());
        holder.mTvLikesAmount.setText(String.valueOf(issueEntity.getLikeAmount()));
        holder.mIvCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, issueEntity.getIconId()));
        holder.mTvDateCreated.setText(mFormatter.format(issueEntity.getCreated()));

        String days = mContext.getResources().getString(R.string.days);

        holder.mTvDaysAmount.setText(String.valueOf(issueEntity.getDaysAmount()).concat(" ").concat(days));

    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public interface OnItemClickListener {
        void onItemClick(IssueEntity entity);
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvCategoryTitle;
        private TextView mTvTaskDesc;
        private TextView mTvDaysAmount;
        private TextView mTvDateCreated;
        private TextView mTvLikesAmount;
        private ImageView mIvCategoryIcon;

        public IssueViewHolder(View itemView) {
            super(itemView);

            mTvCategoryTitle = (TextView) itemView.findViewById(R.id.category_title);
            mIvCategoryIcon = (ImageView) itemView.findViewById(R.id.category_icon);
            mTvTaskDesc = (TextView) itemView.findViewById(R.id.task_desc);
            mTvDaysAmount = (TextView) itemView.findViewById(R.id.amount_days);
            mTvDateCreated = (TextView) itemView.findViewById(R.id.date_created);
            mTvLikesAmount = (TextView) itemView.findViewById(R.id.likes_amount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mModel.get(position));
            }
        }
    }
}