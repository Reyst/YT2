package gsihome.reyst.y2t.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import gsihome.reyst.y2t.adapters.IssueListAdapter;


public class Invoker implements IssueListAdapter.OnItemClickListener, AdapterView.OnItemClickListener {

    private Context mContext;

    public Invoker(Context context) {
        this.mContext = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        invoke();
    }

    @Override
    public void onItemClick() {
        invoke();
    }

    private void invoke() {
        Intent intent = new Intent(mContext, DetailActivity.class);
        mContext.startActivity(intent);
    }
}
