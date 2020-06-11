package aslapov.android.study.pallada.kisuknd.raids.model;

import android.content.Context;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Entry;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Inspection;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.InspectionMessage;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaidRepository {

    private RaidDao mRaidDao;

    RaidRepository(Context applicationContext) {
        RaidRoomDatabase db = RaidRoomDatabase.getDatabase(applicationContext);
        mRaidDao = db.raidDao();
    }

    public Observable<List<RaidWithInspectors>> queryRaids(boolean isDraft) {
        return mRaidDao.queryRaidList(isDraft);
    }

    public Observable<RaidWithInspectors> queryRaidById(UUID raidId) {
        return mRaidDao.queryRaidById(raidId.toString());
    }

    public void updateRaid(RaidWithInspectors raid) {
        mRaidDao.updateRaidWithInspectors(raid.getRaid(), raid.getInspectors());
    }

    public void addRaid(RaidWithInspectors raid) {
        mRaidDao.insertRaidWithInspectors(raid.getRaid(), raid.getInspectors());
    }

    public void getRaid(UUID raidId) {
        RaidApiFactory.getRaidService()
                .queryRaidById(raidId)
                .enqueue(new Callback<Entry>() {
                    @Override
                    public void onResponse(Call<Entry> call, Response<Entry> response) {
                        Entry entry = response.body();
                        InspectionMessage body = entry.getInspectionMessage();
                        Inspection inspection = body.getInspection();
                    }

                    @Override
                    public void onFailure(Call<Entry> call, Throwable t) {
                        String s = t.getMessage();
                    }
                });
    }
}
