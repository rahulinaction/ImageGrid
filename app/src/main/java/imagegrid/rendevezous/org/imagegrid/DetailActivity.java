package imagegrid.rendevezous.org.imagegrid;
import android.app.Activity;
import android.os.Bundle;
import java.util.List;
import imagegrid.rendevezous.org.imagegrid.fragment.ImageDetailFragment;
import imagegrid.rendevezous.org.imagegrid.model.ImageElement;
/**
 * Created by rahul on 27/3/15.
 */
public class DetailActivity extends Activity {
    private List<ImageElement> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ImageDetailFragment())
                    .commit();
        }
    }

    public void onBackPressed () {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
