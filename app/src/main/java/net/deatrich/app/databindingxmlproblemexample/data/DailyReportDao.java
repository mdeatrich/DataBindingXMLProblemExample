/*
 * Copyright (C) 2018 Melanie Deatrich (github mdeatrich)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.deatrich.app.databindingxmlproblemexample.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
@TypeConverters(DateConverter.class)
public interface DailyReportDao {


    @Query("select * from DailyReport where reportDate = :reportDate")
    DailyReport loadReportByDate(Date reportDate);

    @Insert(onConflict = REPLACE)
    void insertReport(DailyReport dailyReport);

    @Insert (onConflict = REPLACE)
    void batchInsertReports(List<DailyReport> dailyReports);

    @Update(onConflict = REPLACE)
    void updateReport(DailyReport dailyReport);


    @Query("DELETE FROM DailyReport where reportDate = :reportDate")
    void deleteReport(Date reportDate);

    @Query("DELETE FROM DailyReport")
    void deleteAll();
}
