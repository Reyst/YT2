package gsihome.reyst.y2t.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gsihome.reyst.y2t.R;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder> {

    private Context mContext;
    private List<String> mModel;

    private OnItemClickListener mOnClickListener;

    private int mSideLength;

    public ImageGalleryAdapter(Context mContext, List<String> model, OnItemClickListener listener) {
        this.mContext = mContext;
        initModel(model);
        mOnClickListener = listener;
    }

    private void initModel(Collection<String> data) {
        mModel = new ArrayList<>(data.size());
        mModel.addAll(data);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.image_card, parent, false);

        mSideLength = parent.getLayoutParams().height;

        v.getLayoutParams().height = mSideLength;
        v.getLayoutParams().width = mSideLength;

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        String path = mModel.get(position);
        final int index = mModel.indexOf(path);

        Picasso.with(mContext)
                .load(path)
                .resize(mSideLength, mSideLength)
                .centerCrop()
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(mContext.getResources().getString(R.string.str_image) + " #" + String.valueOf(index));
                mOnClickListener.onClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public interface OnItemClickListener {
        void onClick(View view);
    }

    public static class ImageViewHolder extends ViewHolder {

        private ImageView imageView;

        public ImageViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.iv_image);
        }
    }
}
