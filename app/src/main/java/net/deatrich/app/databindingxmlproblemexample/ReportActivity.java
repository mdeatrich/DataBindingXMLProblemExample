package net.deatrich.app.databindingxmlproblemexample;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.deatrich.app.databindingxmlproblemexample.adapters.ReportViewPagerAdapter;
import net.deatrich.app.databindingxmlproblemexample.utilities.DateUtils;
import net.deatrich.app.databindingxmlproblemexample.viewmodels.ReportViewModel;

import java.util.Date;


public class ReportActivity extends AppCompatActivity
        implements ReportViewModel.OnReportSaved,
                ReportViewModel.OnReportLoaded{

    private static final String TAG = ReportActivity.class.getSimpleName();
    
    public final static String REPORT_DATE_KEY = "REPORT_DATE_KEY";

    //used in getting a result back from a loaded ReportActivity
    public final static int REPORT_RESULT_REQUEST = 100;
    public final static int SAVE_DATA_RESULT_SAVED = 101;
    public final static int SAVE_DATA_RESULT_CANCELED = 102;
    public final static int SAVE_DATA_RESULT_UNDONE = 103;
    public final static int DELETE_DATA_RESULT_DELETED = 104;
    public final static int DELETE_DATA_RESULT_UNDONE = 105;


    private ReportViewModel mReportViewModel;
    private ReportViewPagerAdapter mAdapterViewPager;
    private Date mReportDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar;

        Intent startingActivityIntent = getIntent();
        Date bundleDate = null;

        Bundle bundle = startingActivityIntent.getExtras();


        if (bundle != null)
             bundleDate = (Date) bundle.getSerializable(REPORT_DATE_KEY);

        //default to today
        mReportDate = DateUtils.getDateAtMidnightToday();


        if (bundleDate != null) {
            mReportDate = bundleDate;
        } else if (savedInstanceState != null) {
            Date savedDate = (Date) savedInstanceState.getSerializable(REPORT_DATE_KEY);
            if (savedDate != null)
                mReportDate = savedDate;
        }

        setContentView(R.layout.activity_report);

        mReportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);


        mReportViewModel.loadReport(this, mReportDate);



        Toolbar myToolbar = findViewById(R.id.report_toolbar);
        setSupportActionBar(myToolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(false);



        Log.d(TAG, "Activity Starting, bottom of onCreate" );

    }


    @Override
    protected void onDestroy() {
        if (!isFinishing()) {
            // the system is destroying us!
            saveReport();
        }

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:

                return true;

            case R.id.action_save:
                    saveReport();
                return true;

            case R.id.action_delete:
                deleteReport();
                return true;

            case R.id.action_cancel:
                finish();
                return true;

            default:
            // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    ////////////////////////////////////////////////////////////   Delete Report

    private void deleteReport() {

        Snackbar snackbar = Snackbar.make(findViewById(R.id.report_linear_layout),
                R.string.report_deleted_text, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.undo_text, new MyDeleteUndoListener());

        snackbar.addCallback(new DeleteCallback(mReportDate));


        snackbar.show();

    }

    @Override
    public void onReportLoaded(Date reportDate) {
        mReportDate = reportDate;


        ViewPager vpPager = findViewById(R.id.id_vp_dbt_view_pager);
        mAdapterViewPager = new ReportViewPagerAdapter(getSupportFragmentManager(), this, mReportDate);
        vpPager.setAdapter(mAdapterViewPager);


        PagerTitleStrip titleStrip = findViewById(R.id.id_dbt_pager_title_strip);
        titleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titleStrip.setTextColor(ContextCompat.getColor(this, R.color.colorPagerTitleStripText));
    }

    // This seems weird, right? But my intention is to only delete the report in the first place if
    // they didn't click UNDO

    private class DeleteCallback extends Snackbar.Callback implements ReportViewModel.OnReportDeleted{
        private final Date mDeleteDate;

        DeleteCallback (Date deleteDate){
            mDeleteDate = deleteDate;
        }
        @Override
        public void onDismissed(Snackbar snackbar, int event){

            // user clicked undo
            if (event == DISMISS_EVENT_ACTION) {
                setResult(DELETE_DATA_RESULT_UNDONE);
                //return without deleting the report
                finish();
            }
            else {
                //they didn't click UNDO, so do the delete
                mReportViewModel.deleteReport(this, mDeleteDate);
                setResult(DELETE_DATA_RESULT_DELETED);
            }

        }

        @Override
        public void onShown(Snackbar snackbar) {
        }

        @Override
        public void onReportDeleted() {
            // I'm waiting until the report is fully deleted before returning to the ReportCalendarActivity
            // in hopes that will keep the Calendar from updating 2 times
            finish();
        }
    }

    // this doesn't do anything I just want to know if they clicked undo
    public class MyDeleteUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Log.d(TAG, "Delete UNDO clicked. " + mReportDate);


        }

    }


    ////////////////////////////////////////////////////////////   Save Report

    private void saveReport() {
        mReportViewModel.saveReport(this);
    }

    public class MySaveUndoListener implements View.OnClickListener, ReportViewModel.OnReportDeleted {

        @Override
        public void onClick(View v) {
            mReportViewModel.deleteReport(this, mReportDate);

        }

        @Override
        public void onReportDeleted() {
            finish();
        }
    }

    @Override
    public void onReportSaved() {

        Snackbar snackbar = Snackbar.make(findViewById(R.id.report_linear_layout),
                R.string.report_saved_text, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.undo_text, new MySaveUndoListener());

        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {

                // user clicked undo
                if (event == DISMISS_EVENT_ACTION)
                    setResult(SAVE_DATA_RESULT_UNDONE);
                    // we're not calling finish because the callback from the UNDO delete
                    // does that. This way ReportCalendarActivity doesn't load until the data is
                    // right
                else {
                    setResult(SAVE_DATA_RESULT_SAVED);
                    finish();
                }

            }

            @Override
            public void onShown(Snackbar snackbar) {
            }
        });

        snackbar.show();

    }
}
