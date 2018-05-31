package net.deatrich.app.databindingxmlproblemexample.data;

import android.content.Context;

import net.deatrich.app.databindingxmlproblemexample.utilities.ApplicationContextProvider;

import java.util.Date;
import java.util.List;


public class Repository implements DailyReportDao {

    private static final String TAG = Repository.class.getSimpleName();

    private Context context = ApplicationContextProvider.getContext();
    private final DailyReportDao dailyReportDao = AppDatabase.getDatabase(context).reportDao();




    @Override
    public DailyReport loadReportByDate(Date reportDate) {
        return dailyReportDao.loadReportByDate(reportDate);
    }


    @Override
    public void insertReport(DailyReport dailyReport) {
         dailyReportDao.insertReport(dailyReport);
    }

    @Override
    public void batchInsertReports(List<DailyReport> dailyReports) {
        dailyReportDao.batchInsertReports (dailyReports);
    }

    @Override
    public void updateReport(DailyReport dailyReport) {
        dailyReportDao.updateReport(dailyReport);
    }

    @Override
    public void deleteReport(Date reportDate) {
       dailyReportDao.deleteReport(reportDate);
    }

    @Override
    public void deleteAll() {
        dailyReportDao.deleteAll();

    }


}
