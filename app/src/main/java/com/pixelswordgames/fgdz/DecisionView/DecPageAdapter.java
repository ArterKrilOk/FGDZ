package com.pixelswordgames.fgdz.DecisionView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ortiz.touchview.TouchImageView;
import com.pixelswordgames.fgdz.POJO.Image;
import com.pixelswordgames.fgdz.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DecPageAdapter extends PagerAdapter {
    private List<Image> images;
    private Context context;
    private LayoutInflater layoutInflater;


    public DecPageAdapter(Context context){
        this.context = context;
        images = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dec_page,container,false);

        final ProgressBar progressBar = ((ProgressBar)view.findViewById(R.id.dPageProgress));
        final TouchImageView imageView = ((TouchImageView)view.findViewById(R.id.dPageImage));
        TextView nameView = ((TextView)view.findViewById(R.id.dPageName));

        //nameView.setText(images.get(position).getAlt());
        if(images.get(position).getBitmap() != null) {
            progressBar.setVisibility(View.GONE);
            if(images.get(position).getBitmap().getByteCount() < 100000000)
                imageView.setImageBitmap(images.get(position).getBitmap());
            else
                imageView.setImageBitmap(
                                Bitmap.createScaledBitmap(images.get(position).getBitmap(),
                                images.get(position).getBitmap().getWidth()/2,
                                images.get(position).getBitmap().getHeight()/2,
                                false));
        } else
            Glide
                .with(context.getApplicationContext())
                .load(Uri.parse(images.get(position).getUrl()))
                .override(1000)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toasty.error(context,"Page Load ERROR").show();
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }


    public void setImages(List<Image> images){
        this.images = images;
        notifyDataSetChanged();
    }

    public String getContentType(int position){
        return images.get(position).getAlt();
    }
}
