package net.deatrich.app.databindingxmlproblemexample.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.deatrich.app.databindingxmlproblemexample.R;
import net.deatrich.app.databindingxmlproblemexample.databinding.ContentReportConstraintBinding;
import net.deatrich.app.databindingxmlproblemexample.databinding.ContentReportLinearBinding;
import net.deatrich.app.databindingxmlproblemexample.viewmodels.ReportViewModel;

import java.util.Date;
import java.util.Objects;


public class ReportFragment extends Fragment {

    private static final String TAG = ReportFragment.class.getSimpleName();


    public final static String LAYOUT_ID_KEY = "layoutId";
    public final static String REPORT_DATE_KEY = "reportDate";


    // Store instance variables
    private int layoutId;


    //shared
    private static Date reportDate;

    //each fragment (viewpager page) shares the same reportDate and viewModel
    private ReportViewModel reportViewModel;

    //bindings
    //section data bindings
    private ViewDataBinding reportBinding = null;


    public ReportFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        layoutId = getArguments().getInt(LAYOUT_ID_KEY, R.layout.content_report_constraint);
        reportDate = (Date) getArguments().getSerializable(REPORT_DATE_KEY);


        Log.d(TAG, "onCreate, layoutId= " + layoutId);
        Log.d(TAG, "onCreate, reportDate= " + reportDate);

        reportViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ReportViewModel.class);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated, reportDate= " + reportViewModel.getDailyReportDate());


    }


    // newInstance constructor for creating fragment with arguments
    public static ReportFragment newInstance(int layoutResId, Date reportDate) {

        ReportFragment dbtFrag = new ReportFragment();

        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID_KEY, layoutResId);
        args.putSerializable(REPORT_DATE_KEY, reportDate);


        Log.d(TAG, "newInstance, reportDate= " + reportDate);

        dbtFrag.setArguments(args);
        return dbtFrag;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle args) {
        args.putInt(LAYOUT_ID_KEY, layoutId);
        super.onSaveInstanceState(args);


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        ContentReportConstraintBinding constraintBinding;
        ContentReportLinearBinding linearBinding;



            switch (layoutId) {
                case R.layout.content_report_constraint:
                    constraintBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);

                    if (constraintBinding != null) {
                        view = constraintBinding.getRoot();
                        constraintBinding.setReport(reportViewModel.getDailyReport());

                        Log.d(TAG, "onCreateView, constraint view,  reportDate= " + reportDate);
                    }
                    break;

                case R.layout.content_report_linear:
                    linearBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);

                    if (linearBinding != null) {
                        view = linearBinding.getRoot();
                        linearBinding.setReport(reportViewModel.getDailyReport());

                        Log.d(TAG, "onCreateView, linear view,  reportDate= " + reportDate);
                    }
                    break;

        }


        return view;

    }


}