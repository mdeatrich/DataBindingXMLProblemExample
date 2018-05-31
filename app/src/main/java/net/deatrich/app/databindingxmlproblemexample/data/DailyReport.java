package net.deatrich.app.databindingxmlproblemexample.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import net.deatrich.app.databindingxmlproblemexample.BR;
import net.deatrich.app.databindingxmlproblemexample.utilities.DateUtils;
import java.util.Date;


@Entity
public class DailyReport extends BaseObservable {


    @PrimaryKey
    @NonNull
    private Date reportDate;
    private int integer1;
    private int integer2;
    private int integer3;
    private String string1;
    




    public DailyReport(){

        this.reportDate = DateUtils.getDateAtMidnightToday();
        this.integer1 = 0;
        this.integer2 = 0;
        this.integer3 = 0;
        this.string1 = "";


    }



    @Ignore
    public void copyValuesOf (DailyReport report){
        setReportDate(report.getReportDate());
        setInteger1(report.getInteger1());
        setInteger2(report.getInteger2());
        setInteger3(report.getInteger3());
        setString1(report.getString1());

    }



    @NonNull
    @Bindable
    public Date getReportDate() {
        return reportDate;
    }


    public void setReportDate(Date reportDate) {

        this.reportDate = reportDate;
        notifyPropertyChanged(BR.reportDate);
    }

    @Bindable
    public int getInteger1() {
        return integer1;
    }

    public void setInteger1(int integer1) {
        this.integer1 = integer1;
        notifyPropertyChanged(BR.integer1);
    }

    @Bindable
    public int getInteger2() {
        return integer2;
    }

    public void setInteger2(int integer2) {
        this.integer2 = integer2;
        notifyPropertyChanged(BR.integer2);
    }

    @Bindable
    public int getInteger3() {
        return integer3;
    }


    public void setInteger3(int integer3) {
        this.integer3 = integer3;
        notifyPropertyChanged(BR.integer3);
    }

    @Bindable
    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
        notifyPropertyChanged(BR.string1);
    }

}

