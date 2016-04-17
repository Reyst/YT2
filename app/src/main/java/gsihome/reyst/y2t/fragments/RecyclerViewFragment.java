package gsihome.reyst.y2t.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import gsihome.reyst.y2t.R;
import gsihome.reyst.y2t.activities.Invoker;
import gsihome.reyst.y2t.adapters.IssueListAdapter;
import gsihome.reyst.y2t.data.DataUtil;
import gsihome.reyst.y2t.data.IssueEntity;
import gsihome.reyst.y2t.data.State;

public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private IssueListAdapter mAdapter;

    public static Fragment getInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<IssueEntity> issueList = DataUtil.getModel(getContext(), State.IN_WORK);
        mAdapter = new IssueListAdapter(getContext(), issueList, new Invoker(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);

        return v;
    }
}
