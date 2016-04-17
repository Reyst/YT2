package gsihome.reyst.y2t.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import gsihome.reyst.y2t.R;
import gsihome.reyst.y2t.activities.Invoker;
import gsihome.reyst.y2t.data.DataUtil;
import gsihome.reyst.y2t.data.IssueEntity;
import gsihome.reyst.y2t.data.State;

public class ListViewFragment extends Fragment {

    public static final String STR_KEY_STATE = "state";

    private ListViewCompat mListView;
    private ListAdapter mAdapter;

    private Invoker mInvoker;

    public static Fragment getInstance(State state) {

        Fragment fragment = new ListViewFragment();

        Bundle params = new Bundle();
        params.putInt(STR_KEY_STATE, state.getValue());

        fragment.setArguments(params);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        State state = State.WAIT;
        Bundle params = getArguments();
        if (params != null) {
            state = State.getByValue(params.getInt(STR_KEY_STATE, -1));
        }

        List<IssueEntity> data = DataUtil.getModel(getContext(), state);

        mAdapter = new LVAdapter(getContext(), data);
        mInvoker = new Invoker(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mListView = (ListViewCompat) v.findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(mInvoker);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.attachToListView(mListView);

        return v;
    }

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

            holder.categoryTitle.setText(issueEntity.getCategory());
            holder.taskDesc.setText(issueEntity.getFullText());
            holder.likesAmount.setText(String.valueOf(issueEntity.getLikeAmount()));

            holder.categoryIcon.setImageDrawable(mContext.getResources().getDrawable(issueEntity.getIconId()));

            holder.dateCreated.setText(DataUtil.getFormatter().format(issueEntity.getCreated()));

            String days = mContext.getResources().getString(R.string.days);
            holder.daysAmount.setText(String.valueOf(issueEntity.getDaysAmount()).concat(" ").concat(days));

            v.setTag(holder);

            return v;
        }

        private class IssueViewHolder {

            TextView categoryTitle;
            TextView taskDesc;
            TextView daysAmount;
            TextView dateCreated;
            TextView likesAmount;
            ImageView categoryIcon;

            public IssueViewHolder(View itemView) {

                categoryTitle = (TextView) itemView.findViewById(R.id.category_title);
                categoryIcon = (ImageView) itemView.findViewById(R.id.category_icon);

                taskDesc = (TextView) itemView.findViewById(R.id.task_desc);
                daysAmount = (TextView) itemView.findViewById(R.id.amount_days);
                dateCreated = (TextView) itemView.findViewById(R.id.date_created);
                likesAmount = (TextView) itemView.findViewById(R.id.likes_amount);

            }
        }
    }
}
