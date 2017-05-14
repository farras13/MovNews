package id.sch.smktelkom_mlg.privateassignment.xirpl619.movnews.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.sch.smktelkom_mlg.privateassignment.xirpl619.movnews.R;

/**
 * Created by Mochammad Alauddin on 5/14/2017.
 */

public class DetailActivity extends AppCompatActivity {
    private static final String URL_DATA = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=74ce2c37a9f84c3da2f735d8dc538164";
    public TextView textViewHeadet;
    public TextView textViewDescet;
    public TextView textViewReview;
    public ImageView imageViewDetail;
    private Integer mPostkey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mPostkey = getIntent().getExtras().getInt("blog_id");
        loadRecyclerViewData();


        textViewHeadet = (TextView) findViewById(R.id.textViewHeadet);
        textViewDescet = (TextView) findViewById(R.id.textViewDescet);
        textViewReview = (TextView) findViewById(R.id.textViewReview);
        imageViewDetail = (ImageView) findViewById(R.id.imageViewDetail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("results");
                            JSONObject o = array.getJSONObject(mPostkey);

                            setTitle("Detail of Your Movie");

                            textViewHeadet.setText(o.getString("display_title"));
                            textViewDescet.setText(o.getString("display_title"));
                            textViewReview.setText(o.getString("summary_short"));

                            Glide
                                    .with(DetailActivity.this)
                                    .load(o.getString("src"))
                                    .into(imageViewDetail);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

