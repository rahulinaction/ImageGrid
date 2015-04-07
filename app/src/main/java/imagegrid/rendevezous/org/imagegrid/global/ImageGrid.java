package imagegrid.rendevezous.org.imagegrid.global;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;
public class ImageGrid extends Application {
    public ArrayList<ImageElement> imageElements;
    public Integer position;
    public Integer paginationIndex;
    private Boolean endStatus;
    @Override
    public void onCreate() {
        super.onCreate();
        this.initImageLoader(getApplicationContext());
    }


    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public void setImageElements(JSONObject _contentObject) throws JSONException {
        this.imageElements = (ArrayList<ImageElement>) _contentObject.get("imageItems");
        this.position = _contentObject.getInt("position");
    }

    public ArrayList<ImageElement> getImageElements() {
        return  this.imageElements;
    }

    public Integer getElementPosition() {
        return this.position;
    }

    public Integer getPaginationIndex() {
        return this.paginationIndex;
    }

    public void setPaginationIndex(Integer _paginationIndex) {
        this.paginationIndex = _paginationIndex;
    }

    public Boolean getEndStatus() {
        return  this.endStatus;
    }

    public void setEndStatus(Boolean _endStatus) {
        this.endStatus = _endStatus;
    }
}