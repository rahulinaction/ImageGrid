package imagegrid.rendevezous.org.imagegrid.uicomponents;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import imagegrid.rendevezous.org.imagegrid.DetailActivity;
import imagegrid.rendevezous.org.imagegrid.R;
import imagegrid.rendevezous.org.imagegrid.fragment.ImageListFragment;
import imagegrid.rendevezous.org.imagegrid.global.ImageGrid;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;

public class ImageElementAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ImageElement> imageItems;
    private int lastPosition = -1;
    DisplayImageOptions options;
    //public Context currentContext;
    public ImageListFragment imageListFragment;
    public ImageElementAdapter(Activity activity, List<ImageElement> imageItems,ImageListFragment imageListFragment) {
        this.activity = activity;
        this.imageItems = imageItems;
        inflater = (LayoutInflater) this.activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.currentContext = this.activity.getApplicationContext();
        this.imageListFragment = imageListFragment;
        this.options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(700))
                .showImageOnLoading(R.color.golden_brown)
                .showImageForEmptyUri(R.color.golden_brown)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    public ImageElementAdapter(ImageListFragment imageListFragment, List<ImageElement> imageItems) {
        // TODO Auto-generated constructor stub
        this.imageListFragment = imageListFragment;
        this.imageItems = imageItems;
    }

    @Override
    public int getCount() {
//        System.out.println("the count obtained is "+this.imageItems.size());
        return this.imageItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = this.inflater.inflate(R.layout.simple_image_cell,viewGroup,false);
        }
        view.setBackgroundResource(R.color.golden_brown);
        ImageElement currentImageElement = this.imageItems.get(position);
        Map<String,Integer> currentDimension = this.computeHeight(currentImageElement);
        Integer width = currentDimension.get("width");
        Integer height = currentDimension.get("height");
        ImageView currentImageView = (ImageView)view.findViewById(R.id.elementImage);
        currentImageView.getLayoutParams().height = height;
        currentImageView.getLayoutParams().width = width;
        final ProgressBar progressView = (ProgressBar)view.findViewById(R.id.imageProgress);
        ImageLoader.getInstance().displayImage(currentImageElement.getUrl(),currentImageView, this.options,new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressView.setProgress(0);
                progressView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {
                progressView.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressView.setVisibility(View.GONE);
            }

        });

        final Activity a = this.activity;
        final ImageGrid applicationContext = (ImageGrid) a.getApplicationContext();
        final List<ImageElement> _imageItems = this.imageItems;
        currentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                Intent intent = new Intent(a, DetailActivity.class);
                JSONObject contentObject = new JSONObject();
                contentObject.putOpt("imageItems",_imageItems);
                contentObject.put("position",position);
                applicationContext.setImageElements(contentObject);
                a.startActivity(intent);
                a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        });


        if(view != null) {
            Animation animation = new TranslateAnimation(0, 0, (position > lastPosition) ? 100 : -100, 0);
            animation.setDuration(700);
            view.startAnimation(animation);
        }
        lastPosition = position;
        return view;
    }


    private Map<String,Integer> computeHeight(ImageElement imageElement) {
        Map<String,Integer> dimension = new HashMap<String,Integer>();
        Integer oldHeight = imageElement.getHeight();
        Integer oldWidth = imageElement.getWidth();
        Integer width=560;
        Float ratio = (float)oldHeight/oldWidth;
        Integer height = Math.round(ratio*560);
        dimension.put("width",width);
        dimension.put("height",height);
        return dimension;
    }

    public void addAllImages(List<ImageElement> imageElements){
        imageItems.addAll(imageElements);
    }

}