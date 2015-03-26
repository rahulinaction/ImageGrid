package imagegrid.rendevezous.org.imagegrid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import imagegrid.rendevezous.org.imagegrid.R;
import imagegrid.rendevezous.org.imagegrid.uicomponents.ImageElementAdapter;
import imagegrid.rendevezous.org.imagegrid.uicomponents.ImageGridListView;

public class  ImageListFragment extends Fragment {
    ImageGridListView imageList;
    ImageListFragment currentFragment;
    ImageElementAdapter imageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.imagelistfragment, container, false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentFragment =  this;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.fetchData(0);
    }

    public void fetchData(final Integer current) {

        String appUrl  = "http://rahulinaction.com/api/?fetch=fetch&range="+current;
        AsyncHttpClient client = new AsyncHttpClient();
        System.out.println("the client is"+client+"appurl is"+appUrl);
        client.get(appUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println("the response obtained is"+response);
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

                    if(current.intValue()==0) {
                        currentFragment.render(events);
                    }else{
                        imageList.addNewData(events);
                    }


                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                }

            }
        });
    }

    public void render ( List<ImageElement> imageItems){
        imageList = (ImageGridListView) getView().findViewById(R.id.imageList);
        imageAdapter = new ImageElementAdapter(getActivity(),imageItems,this);
        imageList.setAdapter(imageAdapter);
    }
}