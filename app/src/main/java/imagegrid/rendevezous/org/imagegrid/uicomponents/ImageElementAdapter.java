package imagegrid.rendevezous.org.imagegrid.uicomponents;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import imagegrid.rendevezous.org.imagegrid.R;
import imagegrid.rendevezous.org.imagegrid.fragment.ImageListFragment;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;

public class ImageElementAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ImageElement> imageItems;
    private int lastPosition = -1;
    DisplayImageOptions options;
    public ImageListFragment imageListFragment;
    public ImageElementAdapter(Activity activity, List<ImageElement> imageItems,ImageListFragment imageListFragment) {
        this.activity = activity;
        this.imageItems = imageItems;
        inflater = (LayoutInflater) this.activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View view, ViewGroup viewGroup) {
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
        ImageLoader.getInstance().displayImage(currentImageElement.getUrl(),currentImageView, this.options,new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {
            }

            //				 @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

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
        //System.out.println("add images called");
//        for(int i=0;i<imageElements.size();i++) {
//            imageItems.add(imageElements.get(i));
//        }
        System.out.println("the end image items size is "+imageItems.size());
    }

}