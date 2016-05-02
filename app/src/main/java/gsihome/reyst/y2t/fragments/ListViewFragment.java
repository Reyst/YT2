package gsihome.reyst.y2t.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import java.util.List;

import gsihome.reyst.y2t.R;
import gsihome.reyst.y2t.activities.FloatingActionButtonOwner;
import gsihome.reyst.y2t.activities.Invoker;
import gsihome.reyst.y2t.adapters.LVAdapter;
import gsihome.reyst.y2t.data.DataUtil;
import gsihome.reyst.y2t.data.IssueEntity;
import gsihome.reyst.y2t.data.State;

public class ListViewFragment extends Fragment {

    private static final String STR_KEY_STATE = "state";

    private FloatingActionButton mFab;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mFab = null;
        if (context instanceof FloatingActionButtonOwner) {
            mFab = ((FloatingActionButtonOwner) context).getFloatingActionButton();
        }
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

        ListViewCompat listView = (ListViewCompat) v.findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(mInvoker);

        if (mFab != null) {
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                private int mLastFirstVisibleItem = 0;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int fabVisibility = mFab.getVisibility();
                    if (firstVisibleItem > mLastFirstVisibleItem && fabVisibility == View.VISIBLE) {
                        mFab.hide();
                    } else if (firstVisibleItem < mLastFirstVisibleItem && fabVisibility == View.GONE) {
                        mFab.show();
                    }
                    mLastFirstVisibleItem = firstVisibleItem;
                }
            });
        }

        return v;
    }
}
