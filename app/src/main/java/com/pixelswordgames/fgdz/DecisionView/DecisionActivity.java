package com.pixelswordgames.fgdz.DecisionView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pixelswordgames.fgdz.POJO.Decision;
import com.pixelswordgames.fgdz.POJO.Task;
import com.pixelswordgames.fgdz.Parser.SiteParser;
import com.pixelswordgames.fgdz.R;

import es.dmoral.toasty.Toasty;

public class DecisionActivity extends AppCompatActivity {

    private int curDec;
    private String[] names;
    private String[] urls;
    private ViewPager pager;
    private ProgressBar progressBar;
    private TextView errorView;
    private DecPageAdapter adapter;

    private Task task;
    private SiteParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_decision);


        task = new Task();
        parser = new SiteParser(this);
        adapter = new DecPageAdapter(this);

        curDec = getIntent().getIntExtra("curDec",0);
        names = getIntent().getStringArrayExtra("decNames");
        urls = getIntent().getStringArrayExtra("decUrls");

        task.setName(names[curDec]);
        task.setUrl(urls[curDec]);

        setTitle(task.getName());

        pager = ((ViewPager)findViewById(R.id.decPager));
        progressBar = ((ProgressBar)findViewById(R.id.decProgressBar));
        errorView = ((TextView)findViewById(R.id.decErrorView));
        pager.setAdapter(adapter);

        loadDec();

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(task.getName() + "  " + adapter.getContentType(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadDec(){
        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        parser.loadDecision(task.getUrl(), new SiteParser.OnDecisionCompleteListener() {
            @Override
            public void onSuccess(Decision decision) {
                progressBar.setVisibility(View.GONE);
                adapter.setImages(decision.getImages());
                setTitle(task.getName() + "  " + adapter.getContentType(pager.getCurrentItem()));
            }

            @Override
            public void onFail(int errorCode) {
                progressBar.setVisibility(View.GONE);
                showError(errorCode);
            }
        });
    }

    private void showError(int errorCode){
        errorView.setVisibility(View.VISIBLE);
        switch (errorCode){
            case SiteParser.CONNECTION_ERROR:
                errorView.setText(R.string.error_connection);
                break;
            case SiteParser.NO_CONDITIONS:
                errorView.setText(R.string.error_no_decisions);
                break;
            case SiteParser.ERROR_LIST_NULL:
                errorView.setText(R.string.error_list_empty);
                break;
            case SiteParser.SUBJECT_EMPTY_ERROR:
                errorView.setText(R.string.error_no_subject);
                break;
            default:
                errorView.setText(R.string.error_some);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_next_dec:
                nextDec();
                break;

            case R.id.action_perv_dec:
                pervDec();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void nextDec(){
        if(curDec < names.length-1)
            curDec++;
        else {
            Toasty.info(DecisionActivity.this, R.string.thats_all).show();
            return;
        }

        finish();
        openDec();
    }

    private void openDec(){
        Intent intent = new Intent(DecisionActivity.this, DecisionActivity.class);

        intent.putExtra("curDec", curDec);
        intent.putExtra("decNames",names);
        intent.putExtra("decUrls",urls);

        startActivity(intent);
    }

    private void pervDec(){
        if(curDec > 0)
            curDec--;
        else {
            Toasty.info(DecisionActivity.this, R.string.thats_all).show();
            return;
        }

        finish();
        openDec();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dec_menu, menu);
        return true;
    }
}
