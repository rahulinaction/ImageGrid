package imagegrid.rendevezous.org.imagegrid.uicomponents;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import imagegrid.rendevezous.org.imagegrid.R;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;

/**
 * Created by rahul on 4/4/15.
 */
public class ImageGalleryAdapter extends PagerAdapter {
    private List<ImageElement> imageItems;

    LayoutInflater mLayoutInflater;
    Context context;
    DisplayImageOptions options;
    public ImageGalleryAdapter(Context _context,List<ImageElement> _imageItems) {
        this.context = _context;
        this.imageItems = _imageItems;
        this.options =  new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(R.color.black)
                .showImageForEmptyUri(R.color.black)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return this.imageItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return  view == ((LinearLayout) o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_gallery_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.galleryImageItem);
        ImageElement currentImageElement = this.imageItems.get(position);
        ImageLoader.getInstance().displayImage(currentImageElement.getUrl(),imageView, this.options,new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }
            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void addAllImages(List<ImageElement> imageElements){
        imageItems.addAll(imageElements);
    }

}
