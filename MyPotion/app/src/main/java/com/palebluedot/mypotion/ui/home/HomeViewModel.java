package com.palebluedot.mypotion.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.Intake;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.intake.IntakeRepository;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;
import com.palebluedot.mypotion.util.MyUtil;
import com.palebluedot.mypotion.util.TagManager;
import com.palebluedot.mypotion.util.WhenManager;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends AndroidViewModel {
    public LiveData<List<MyPotion>> mPotionList;
    public MutableLiveData<MyPotion> mPotion;
    public MutableLiveData<Intake> mTodayIntake;

    private MyPotionRepository potionRepository;
    private IntakeRepository intakeRepository;
    public HomeViewModel(Application application) {
        super(application);
        potionRepository = new MyPotionRepository(application.getApplicationContext());
        intakeRepository = new IntakeRepository(application.getApplicationContext());
        mPotion = new MutableLiveData<MyPotion>();
        mPotionList = potionRepository.getHomeList();
        mTodayIntake = new MutableLiveData<>();
    }

    public void intake() {
        if(mPotion.getValue() == null) return;
        Intake todayIntake = mTodayIntake.getValue();
        boolean update = todayIntake != null;
        Intake intake;
        String date = MyUtil.dateToString(new Date());
        String time = MyUtil.dateToTimeString(new Date());
        int totalTimes = 1;
        int whenFlag = WhenManager.now(getApplication().getSharedPreferences(WhenManager.SP_NAME, Context.MODE_PRIVATE));
        if(update) {
            totalTimes = todayIntake.totalTimes + 1;
            whenFlag |= todayIntake.whenFlag;
            time = todayIntake.time + time;
            intake = new Intake(todayIntake.id, date, time, totalTimes, whenFlag, mPotion.getValue().id);
        }
        else {
            intake = new Intake(date, time, totalTimes, whenFlag, mPotion.getValue().id);
        }
        intakeRepository.intake(intake, update);
        mTodayIntake.setValue(intake);
    }

    /* potion card data */
    public String getFactory() {
        return mPotion.getValue()!=null ? mPotion.getValue().factory : null;
    }
    public String getEffect() {
        if(mPotion.getValue() != null)
            return TagManager.getInstance().listToString(mPotion.getValue().effectTags);
        else
            return null;
    }
    public String getDday(MyPotion potion) {
        if(potion == null)  return null;

        Date beginDate = MyUtil.stringToDate(potion.beginDate);
        Date today = MyUtil.getFormattedToday();

        // ?????? ?????? ?????? ??????
        if(today.before(beginDate)){
            long msDiff = beginDate.getTime() - today.getTime();
            long dayDiff = msDiff / (24 * 60 * 60 * 1000);
            return dayDiff + "??? ???";
        }

        // intake_calendar - ????????? ?????????????????? ??? ??? ??? ???????????? ?????? ??? ??????
        Intake last = intakeRepository.getLastIntake(potion.id);
        //?????? ????????? ?????? ???
        if (last == null){
            return "TODAY";
        }

        Date lastDate = MyUtil.stringToDate(last.date);
        long lastDayMsDiff = today.getTime() - lastDate.getTime(); //?????? ??????
        long lastDayDiff = lastDayMsDiff / (24 * 60 * 60 * 1000);

        String dday = "";
        int day = potion.day;
        //????????? ????????? ????????? ???
        if (lastDayDiff == 0) {
            int totalTimes = last.totalTimes;
            if (totalTimes >= potion.times)
                dday = day + "??? ???";
            else
                // ?????? ?????? ?????? ????????? ??? ???????????? ???
                dday = "TODAY";
        }
        // ???????????? ??????????????? ????????? ???
        else if (lastDayDiff >= day) dday = "TODAY";

        // ????????? ??? ????????? ?????? ?????? ????????? ???
        else dday = (day - lastDayDiff) + "??? ???";

        return dday;
    }
    public String getDiffFromLast() {
        if(mPotion.getValue() == null)
            return null;
        // intake_calendar - ????????? ?????????????????? ?????? ??? ??? ??????
        Intake last = intakeRepository.getLastIntake(mPotion.getValue().id);
        if (last == null){
            return "??? ????????? ????????????";
        }
        Date lastDate = MyUtil.stringToDate(last.date);
        Date today = MyUtil.getFormattedToday();

        long lastDayMsDiff = today.getTime() - lastDate.getTime(); //?????? ??????
        long lastDayDiff = lastDayMsDiff / (24 * 60 * 60 * 1000);
        if(lastDayDiff == 0)
            return "??????";

        return lastDayDiff + "??? ???";
    }
    public String getIngDays() {
        if(mPotion.getValue() == null){
            return null;
        }

        String beginStr = mPotion.getValue().beginDate;
        Date today = MyUtil.getFormattedToday();
        Date beginDate = MyUtil.stringToDate(beginStr);
        long msDiff = today.getTime() - beginDate.getTime();
        long dayDiff = Math.abs(msDiff) / (24 * 60 * 60 * 1000);

        // ?????? ?????? ?????? ??????
        if(today.before(beginDate)){
            return dayDiff + "??? ??? ??????";
        }

        return dayDiff +"??? ??? ?????????";
    }
    public int getTotalTimes() {
        if(mPotion.getValue() == null || mTodayIntake.getValue() == null)
            return 0;

        return mTodayIntake.getValue().totalTimes;
    }

    public void finishSelectedPotion() {
        MyPotion potion = mPotion.getValue();
        if (potion == null) throw new AssertionError();
        potion.finishDate = MyUtil.dateToString(new Date());

        potionRepository.update(potion);
        mPotion.setValue(null);
        potionRepository.getHomeList();
    }

    public void deleteSelectedPotion() {
        MyPotion potion = mPotion.getValue();
        potionRepository.delete(potion);
        mPotion.setValue(null);
        potionRepository.getHomeList();
    }

    /* -- potion card data --*/

    //initiate the potion card
    public void onItemClick(int pos) {
        if(pos > -1 && mPotionList.getValue().size() > pos) {
            mPotion.setValue(mPotionList.getValue().get(pos));
            Intake todayIntake = intakeRepository.getTodayData(mPotion.getValue().id);
            mTodayIntake.setValue(todayIntake);
        }
    }
}