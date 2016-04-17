package gsihome.reyst.y2t.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

import gsihome.reyst.y2t.R;
import gsihome.reyst.y2t.adapters.ImageGalleryAdapter;
import gsihome.reyst.y2t.data.IssueEntity;
import gsihome.reyst.y2t.data.State;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, ImageGalleryAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private TextView mTextViewValueCreated;
    private TextView mTextViewValueRegistered;
    private TextView mTextViewValueDeadline;

    private TextView mTextViewValueCategory;
    private TextView mTextViewValueResponsible;
    private TextView mTextViewValueStatus;
    private TextView mTextViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTextViewValueCreated = (TextView) findViewById(R.id.tv_value_created);
        mTextViewValueRegistered = (TextView) findViewById(R.id.tv_value_registered);
        mTextViewValueDeadline = (TextView) findViewById(R.id.tv_value_deadline);

        mTextViewValueCategory = (TextView) findViewById(R.id.tv_section);
        mTextViewValueStatus = (TextView) findViewById(R.id.tv_status);
        mTextViewValueResponsible = (TextView) findViewById(R.id.tv_value_responsible);
        mTextViewDescription = (TextView)  findViewById(R.id.tv_description);

        Intent intent = getIntent();
        IssueEntity entity = (IssueEntity) intent.getSerializableExtra(getString(R.string.key_for_entity));

        if (entity != null) {
            setEntityData(entity, actionBar);
        } else {
            finish();
        }

//        ViewGroup vg = (ViewGroup) findViewById(R.id.main_container);
//        if (vg != null) {
//            int chCount = vg.getChildCount();
//            for (int i = 0; i < chCount; i++) {
//                View childView = vg.getChildAt(i);
//                childView.setOnClickListener(this);
//            }
//        }

    }

    private void setEntityData(IssueEntity entity, ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setTitle(entity.getNumber());
        }

        initRecyclerView(entity);
        initDates(entity);

        mTextViewValueCategory.setText(entity.getCategory());
        mTextViewValueResponsible.setText(entity.getResponsible());
        mTextViewDescription.setText(entity.getFullText());

        switch(entity.getState()){
            case IN_WORK:
                mTextViewValueStatus.setText(R.string.str_in_work);
                break;
            case DONE:
                mTextViewValueStatus.setText(R.string.str_done);
                break;
            case WAIT:
                mTextViewValueStatus.setText(R.string.str_wait);
                break;
            default:
                mTextViewValueStatus.setText(R.string.emptyString);
                break;
        }
    }

    private void initDates(IssueEntity entity) {

        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getApplicationContext());

        mTextViewValueRegistered.setText(dateFormat.format(entity.getRegistered()));
        mTextViewValueCreated.setText(dateFormat.format(entity.getCreated()));
        mTextViewValueDeadline.setText(dateFormat.format(entity.getDeadline()));
    }

    private void initRecyclerView(IssueEntity entity) {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_images);

        if (mRecyclerView != null) {
            ViewGroup.LayoutParams lp = mRecyclerView.getLayoutParams();
            lp.height = getResources().getDisplayMetrics().widthPixels / 2;
            mRecyclerView.setLayoutParams(lp);

            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ImageGalleryAdapter(this, entity.getImages(), this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

