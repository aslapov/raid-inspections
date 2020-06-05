package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.content.Context;
import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidInspectionMember;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateRaidViewModel extends ViewModel {

    private RaidRepository mRaidRepository;

    private MutableLiveData<CreateRaidViewModel> mViewModel = new MutableLiveData<>();
    private MutableLiveData<Raid> mRaid = new MutableLiveData<>();
    private MutableLiveData<RaidInspectionMember> mInspector = new MutableLiveData<>();
    private String mStartDateError;
    private String mEndDateError;
    private String mActDateError;
    private String mOrderDateError;
    private String mTaskDateError;
    private String mWarningDateError;
    private String mCreateError;
    private boolean isCreated;

    private Locale mLocaleRu = new Locale("ru");
    private DateFormat mDateFormatter = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, mLocaleRu);
    private DateFormat mDateTimeFormatter = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, mLocaleRu);

    CreateRaidViewModel(Context applicationContext) {
        mRaidRepository = RepositoryProvider.provideRaidRepository(applicationContext);
    }

    public void init() {
        Date currentDate = Calendar.getInstance(mLocaleRu).getTime();

        Raid raid = new Raid();
        raid.setRealStart(currentDate);
        raid.setRealEnd(currentDate);
        raid.setActDate(currentDate);
        raid.setOrderDate(currentDate);
        raid.setTaskDate(currentDate);
        raid.setWarningDate(currentDate);
        raid.setWarningCount(0);

        mRaid.setValue(raid);
        mInspector.setValue(new RaidInspectionMember());

        notifyViewModelChange();
    }

    public void saveRaid() {
        // TODO validate();

        UUID raidId = UUID.randomUUID();
        getRaid().setId(raidId.toString());
        getInspector().setRaidInspectionId(getRaid().getId());

        getRaid().setDraft(true);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date currentDate = calendar.getTime();
        getRaid().setCreateDate(currentDate);
        getRaid().setUpdateDate(currentDate);
        getRaid().setTimeZone(Calendar.getInstance().getTimeZone().getRawOffset());

        List<RaidInspectionMember> members = new ArrayList<>();
        members.add(getInspector());

        RaidWithInspectors raid = new RaidWithInspectors();
        raid.setRaid(getRaid());
        raid.setInspectors(members);

        Completable.fromAction(() -> mRaidRepository.addRaid(raid))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onComplete() {
                    isCreated = true;
                    notifyViewModelChange();
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    isCreated = false;
                    mCreateError = e.getMessage();
                    notifyViewModelChange();
                }
            });
    }

    public LiveData<CreateRaidViewModel> getViewModel() {
        return mViewModel;
    }

    public Raid getRaid() {
        return mRaid.getValue();
    }

    public RaidInspectionMember getInspector() { return mInspector.getValue(); }

    public String getStartDateError() {
        return mStartDateError;
    }

    public String getEndDateError() {
        return mEndDateError;
    }

    public String getActDateError() {
        return mActDateError;
    }

    public String getOrderDateError() {
        return mOrderDateError;
    }

    public String getTaskDateError() {
        return mTaskDateError;
    }

    public String getWarningDateError() {
        return mWarningDateError;
    }

    public String getCreateError() { return mCreateError; }

    public boolean isCreated() {
        return isCreated;
    }

    public void setStartDate(CharSequence date, CharSequence time) {
        try {
            String startDateString = date.toString()
                    .concat(" ")
                    .concat(time.toString());
            Date startDate = mDateTimeFormatter.parse(startDateString);
            getRaid().setRealStart(startDate);
        } catch (Exception e) {
            mStartDateError = e.getLocalizedMessage();
        }
    }

    public void setEndDate(CharSequence date, CharSequence time) {
        try {
            String endDateString = date.toString()
                    .concat(" ")
                    .concat(time.toString());
            Date endDate = mDateTimeFormatter.parse(endDateString);
            getRaid().setRealStart(endDate);
        } catch (Exception e) {
            mEndDateError = e.getLocalizedMessage();
        }
    }

    public void setActDate(CharSequence date) {
        try {
            Date startDate = mDateFormatter.parse(date.toString());
            getRaid().setActDate(startDate);
        } catch (Exception e) {
            mActDateError = e.getLocalizedMessage();
        }
    }

    public void setOrderDate(CharSequence date) {
        try {
            Date startDate = mDateFormatter.parse(date.toString());
            getRaid().setOrderDate(startDate);
        } catch (Exception e) {
            mOrderDateError = e.getLocalizedMessage();
        }
    }

    public void setTaskDate(CharSequence date) {
        try {
            Date startDate = mDateFormatter.parse(date.toString());
            getRaid().setTaskDate(startDate);
        } catch (Exception e) {
            mTaskDateError = e.getLocalizedMessage();
        }
    }

    public void setWarningDate(CharSequence date) {
        try {
            Date startDate = mDateFormatter.parse(date.toString());
            getRaid().setWarningDate(startDate);
        } catch (Exception e) {
            mWarningDateError = e.getLocalizedMessage();
        }
    }

    public void setDepartment(String department) {
        getRaid().setDepartment(department);
    }

    public void setTransportType(String transportType) {
        getRaid().setTransportType(transportType);
    }

    public void setPlaceAddress(Editable placeAddress) {
        getRaid().setPlaceAddress(placeAddress.toString());
    }

    public void setActNumber(Editable actNumber) {
        getRaid().setActNumber(actNumber.toString());
    }

    public void setOrderNumber(Editable orderNumber) {
        getRaid().setOrderNumber(orderNumber.toString());
    }

    public void setTaskNumber(Editable taskNumber) {
        getRaid().setTaskNumber(taskNumber.toString());
    }

    public void setWarningCount(Editable count) {
        int warningCount = Integer.parseInt(count.toString());
        getRaid().setWarningCount(warningCount);
    }

    public void setViolationsPresence(Boolean violationsPresence) {
        getRaid().setViolationsPresence(violationsPresence);
    }

    public void setVehicleInfo(Editable vehicleInfo) {
        getRaid().setVehicleInfo(vehicleInfo.toString());
    }

    public void setVehicleOwner(Editable vehicleOwner) {
        getRaid().setVehicleOwner(vehicleOwner.toString());
    }

    public void setOwnerInn(Editable ownerInn) {
        getRaid().setOwnerInn(ownerInn.toString());
    }

    public void setOwnerOgrn(Editable ownerOgrn) {
        getRaid().setOwnerOgrn(ownerOgrn.toString());
    }

    public void setInspector(Editable inspector) {
        getInspector().setContactName(inspector.toString());
    }

    private void notifyViewModelChange() {
        mViewModel.setValue(this);
    }
}
