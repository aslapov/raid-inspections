package aslapov.android.study.pallada.kisuknd.raids.model;

import android.content.Context;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Entry;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Inspection;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.InspectionMessage;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaidRepository {

    private RaidDao mRaidDao;

    RaidRepository(Context applicationContext) {
        RaidRoomDatabase db = RaidRoomDatabase.getDatabase(applicationContext);
        mRaidDao = db.raidDao();
    }

    public Observable<List<RaidWithInspectors>> queryRaids(Integer status) {
        return mRaidDao.queryRaidList(status);
    }

    public Observable<RaidWithInspectors> queryRaidById(UUID raidId) {
        return mRaidDao.queryRaidById(raidId.toString());
    }

    public void addRaid(RaidWithInspectors raidInspection) {
        mRaidDao.insertRaidWithInspectors(raidInspection);
    }

    public void updateRaid(RaidWithInspectors raidInspection) {
        mRaidDao.updateRaidWithInspectors(raidInspection);
    }

    public void updateRaids(List<RaidWithInspectors> raidInspections) {
        mRaidDao.updateRaids(raidInspections);
    }

    public void deleteRaid(RaidWithInspectors raidInspection) {
        mRaidDao.deleteRaid(raidInspection);
    }

    public void deleteRaids(List<RaidWithInspectors> raidInspections) {
        mRaidDao.deleteRaids(raidInspections);
    }

    public void getRaid(UUID raidId) {
        ApiFactory.getRaidService()
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
