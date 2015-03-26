package imagegrid.rendevezous.org.imagegrid.uicomponents;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import java.util.List;

import imagegrid.rendevezous.org.imagegrid.R;
import imagegrid.rendevezous.org.imagegrid.fragment.ImageListFragment;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;

/**
 * Created by rahul on 21/3/15.
 */
public class ImageGridListView  extends ListView implements AbsListView.OnScrollListener {

    private boolean isLoading;
    public ImageGridListView(Context context) {
        super(context);
    }
    public Integer currentInteger;
    private ImageElementAdapter adapter;
    private View footer;
    private Context context;
    public static final int MAX_LIMIT = 20;
    public ImageGridListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.currentInteger = 0;
        this.context = context;
        this.setOnScrollListener(this);
    }

    public ImageGridListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.currentInteger = 0;
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(getAdapter()==null)
            return;

        if (getAdapter().getCount() == 0)
            return ;

        int l = visibleItemCount + firstVisibleItem;

        if (l >= totalItemCount && !isLoading) {
            isLoading = true;
            // It is time to add new data. We call the listener

            System.out.println("came inside here with value "+l);
            this.getData();
        }
    }

    public void getData() {
       this.setLoadingView(R.layout.loading_layout);
       ImageElementAdapter imageAdapter =  ((ImageElementAdapter)((HeaderViewListAdapter)this.getAdapter()).getWrappedAdapter());
       ImageListFragment currentFragment= imageAdapter.imageListFragment;
       this.currentInteger = this.currentInteger+1;
       currentFragment.fetchData(this.currentInteger);
    }


    public void addNewData(List<ImageElement> images) {
       /*stop pagination  if count of items is not equal to maximum indicating we have reached end*/
        if(images.size()<MAX_LIMIT) {
            isLoading = true;
        }else {
            isLoading = false;
        }
        ImageElementAdapter imageAdapter =  ((ImageElementAdapter)((HeaderViewListAdapter)this.getAdapter()).getWrappedAdapter());
        imageAdapter.addAllImages(images);
        imageAdapter.notifyDataSetChanged();
        this.removeFooterView(footer);
    }


    public void setAdapter(ImageElementAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
        this.removeFooterView(footer);
    }

    /*Loading view*/
    public void setLoadingView(int resId) {
        LayoutInflater inflater =  (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = (View) inflater.inflate(resId, null);
        this.addFooterView(footer);
    }

    /*End loading view*/
}
