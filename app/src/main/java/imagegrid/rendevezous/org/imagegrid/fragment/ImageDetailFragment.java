package imagegrid.rendevezous.org.imagegrid.fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import imagegrid.rendevezous.org.imagegrid.R;
import imagegrid.rendevezous.org.imagegrid.common.ImageGridConstant;
import imagegrid.rendevezous.org.imagegrid.global.ImageGrid;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;
import imagegrid.rendevezous.org.imagegrid.uicomponents.ImageGalleryAdapter;
import android.support.v4.view.ViewPager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rahul on 27/3/15.
 */
public class ImageDetailFragment extends Fragment {
    public ViewPager imageGallery;
    ImageGalleryAdapter imageGalleryAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.imagedetailfragment, container, false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.renderDetailView();
    }

    public void fetchData(final Integer current) {
        String appUrl  = "http://rahulinaction.com/api/?fetch=fetch&range="+current;
        AsyncHttpClient client = new AsyncHttpClient();
        final ImageGrid imageGrid = (ImageGrid) getActivity().getApplicationContext();
        client.get(appUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray eventsArray = obj.getJSONArray("json");
                    ArrayList<ImageElement> events = new ArrayList<ImageElement>();
                    for(int i=0;i<eventsArray.length();i++){
                        JSONObject currentObject = eventsArray.getJSONObject(i);
                        ImageElement currentEvent = new ImageElement();
                        currentEvent.setId(Integer.parseInt(currentObject.getString("id")));
                        currentEvent.setTitle(currentObject.getString("title"));
                        currentEvent.setUrl(currentObject.getString("url"));
                        currentEvent.setHeight(Integer.parseInt(currentObject.getString("height")));
                        currentEvent.setWidth(Integer.parseInt(currentObject.getString("width")));
                        events.add(currentEvent);
                    }
                    if(events.size()== ImageGridConstant.MAX_LIMIT){
                        imageGrid.setEndStatus(false);
                    }else{
                        imageGrid.setEndStatus(true);
                    }
                    imageGrid.setPaginationIndex(current);
                    imageGalleryAdapter.addAllImages(events);
                    imageGalleryAdapter.notifyDataSetChanged();
                } catch (Throwable t) {
                    Log.e("ImageGrid Error", Log.getStackTraceString(t));
                }

            }
        });
    }


    public void renderDetailView() {
        imageGallery = (ViewPager) getView().findViewById(R.id.imageGallery);
        final ImageGrid imageGrid = (ImageGrid) getActivity().getApplicationContext();
        imageGalleryAdapter = new ImageGalleryAdapter(getActivity().getApplicationContext(),imageGrid.getImageElements());
        imageGallery.setAdapter(imageGalleryAdapter);
        imageGallery.setCurrentItem(imageGrid.getElementPosition());
        final ImageDetailFragment detailFragment = this;
        imageGallery.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                Boolean endStatus = imageGrid.getEndStatus();
                if(i>=imageGalleryAdapter.getCount()-1 && !endStatus){
                    System.out.println("came inside fetchdata");
                    Integer paginationIndex = imageGrid.getPaginationIndex()+1;
                    detailFragment.fetchData(paginationIndex);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


}
