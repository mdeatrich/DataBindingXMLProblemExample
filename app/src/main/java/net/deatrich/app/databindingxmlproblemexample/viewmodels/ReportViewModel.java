
package net.deatrich.app.databindingxmlproblemexample.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import net.deatrich.app.databindingxmlproblemexample.data.DailyReport;
import net.deatrich.app.databindingxmlproblemexample.data.Repository;

import java.util.Date;



public class ReportViewModel extends AndroidViewModel {

    private static final String TAG = ReportViewModel.class.getSimpleName();

    private final Repository mReportRepository;

    // Our report will bind to this report via 2-way data binding
    private final DailyReport mDailyReport;

    private Date mReportDate;
    private boolean mReportIsAnEdit;
    private DailyReport mPreviousReportState;
    


    public ReportViewModel(Application application) {
        super(application);

        mReportRepository = new Repository();
        mDailyReport = new DailyReport();
    }

    public DailyReport getDailyReport() {
        return mDailyReport;
    }

    public Date getDailyReportDate() {
        return mDailyReport.getReportDate();
    }

    public void copyReport (DailyReport dailyReport){
        mDailyReport.copyValuesOf(dailyReport);
    }

    public void rollback(OnReportSaved callback){
        getDailyReport().copyValuesOf(mPreviousReportState);
        saveReport(callback);
    }

    //<editor-fold desc="Load Report">
    public void loadReport(OnReportLoaded callback, Date reportDate){

               new loadByDateTask(callback, mReportRepository, reportDate).execute();

    }

    // onPostExecute calls this with the results of a load
    //
    private void reportLoaded(OnReportLoaded callback, Date reportDate, DailyReport dailyReport) {
        // I copy the values into the existing structure to preserve data binding - it fires all
        // the setters which updates the data in the fragment
        //not sure if this is the right way to execute 2way binding in a view model

        mReportDate = reportDate;

        if (dailyReport != null) {
            // this triggers the setters and hence updates the data bindings
            mDailyReport.copyValuesOf(dailyReport);
            mReportIsAnEdit = true;


        }
        else {
            DailyReport report = new DailyReport();
            report.setReportDate(reportDate);

            mDailyReport.copyValuesOf(report);
            mReportIsAnEdit =false;

        }
        mPreviousReportState = dailyReport;
        callback.onReportLoaded(getDailyReportDate());

    }


    public interface OnReportLoaded{
         void onReportLoaded(Date reportDate);
    }

       private class loadByDateTask extends AsyncTask<Void, Void, DailyReport> {


        private final Date mDailyDBTReportDate;
        private final Repository mRepo;
        private final OnReportLoaded mCallback;

        loadByDateTask (OnReportLoaded callback, Repository repo, Date reportDate){

            mDailyDBTReportDate = reportDate;
            mRepo = repo;
            mCallback = callback;
        }

       @Override
       protected DailyReport doInBackground(Void... voids) {
           return mRepo.loadReportByDate(mDailyDBTReportDate);
       }


        @Override
        protected void onPostExecute(DailyReport dailyReport) {

            super.onPostExecute(dailyReport);

            reportLoaded(mCallback, mDailyDBTReportDate, dailyReport);


        }


       }
    //</editor-fold>


    //<editor-fold desc="Save Report">
    public void saveReport(OnReportSaved callback) {

        // stores resulting date in mSaved
        new insertTask(callback).execute();

    }

    public interface OnReportSaved {
        void onReportSaved();
    }


    private class insertTask extends AsyncTask<Void, Void, Void> {

        private final OnReportSaved mCallback;


        insertTask(OnReportSaved callback) {
            mCallback = callback;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            mReportRepository.insertReport(getDailyReport());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCallback.onReportSaved();
        }

    }
    //</editor-fold>



    //<editor-fold desc="Delete Report">
    public void deleteReport(OnReportDeleted callback, Date reportDate) {
        new deleteTask(callback, reportDate).execute();

    }
    public interface OnReportDeleted {
        void onReportDeleted();
    }

    private class deleteTask extends AsyncTask<Void, Void, Void> {

        private final OnReportDeleted mCallback;
        private final Date mDateToDelete;


        deleteTask(OnReportDeleted callback, Date reportDate) {
            mCallback = callback;
            mDateToDelete = reportDate;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            mReportRepository.deleteReport(mDateToDelete);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCallback.onReportDeleted();
        }
    }
    //</editor-fold>

}
