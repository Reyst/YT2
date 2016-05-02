package gsihome.reyst.y2t.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gsihome.reyst.y2t.R;
import gsihome.reyst.y2t.data.DataUtil;
import gsihome.reyst.y2t.data.IssueEntity;

public class LVAdapter extends BaseAdapter {

    private List<IssueEntity> mModel;
    private Context mContext;

    public LVAdapter(Context context, List<IssueEntity> model) {

        mContext = context;
        this.mModel = new ArrayList<>();

        if (model != null) {
            this.mModel.addAll(model);
        }
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public IssueEntity getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mModel.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        IssueViewHolder holder;

        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.list_item_card, parent, false);
            holder = new IssueViewHolder(v);
        } else {
            holder = (IssueViewHolder) v.getTag();
        }

        IssueEntity issueEntity = mModel.get(position);

        holder.mTvCategoryTitle.setText(issueEntity.getCategory());
        holder.mTvTaskDesc.setText(issueEntity.getFullText());
        holder.mTvLikesAmount.setText(String.valueOf(issueEntity.getLikeAmount()));

        holder.mIvCategoryIcon.setImageDrawable(ContextCompat.getDrawable(mContext, issueEntity.getIconId()));

        holder.mTvDateCreated.setText(DataUtil.getFormatter().format(issueEntity.getCreated()));

        String days = mContext.getResources().getString(R.string.days);
        holder.mTvDaysAmount.setText(String.valueOf(issueEntity.getDaysAmount()).concat(" ").concat(days));

        v.setTag(holder);

        return v;
    }

    private class IssueViewHolder {

        TextView mTvCategoryTitle;
        TextView mTvTaskDesc;
        TextView mTvDaysAmount;
        TextView mTvDateCreated;
        TextView mTvLikesAmount;
        ImageView mIvCategoryIcon;

        public IssueViewHolder(View itemView) {

            mTvCategoryTitle = (TextView) itemView.findViewById(R.id.category_title);
            mIvCategoryIcon = (ImageView) itemView.findViewById(R.id.category_icon);

            mTvTaskDesc = (TextView) itemView.findViewById(R.id.task_desc);
            mTvDaysAmount = (TextView) itemView.findViewById(R.id.amount_days);
            mTvDateCreated = (TextView) itemView.findViewById(R.id.date_created);
            mTvLikesAmount = (TextView) itemView.findViewById(R.id.likes_amount);

        }
    }
}
