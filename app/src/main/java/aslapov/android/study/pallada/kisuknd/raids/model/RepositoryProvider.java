package aslapov.android.study.pallada.kisuknd.raids.model;

import android.content.Context;

import androidx.annotation.NonNull;

public final class RepositoryProvider {

    private static RaidRepository sRaidRepository;

    private RepositoryProvider() {

    }

    @NonNull
    public static RaidRepository provideRaidRepository(Context applicationContext) {
        if (sRaidRepository == null) {
            sRaidRepository = new RaidRepository(applicationContext);
        }
        return sRaidRepository;
    }

    public static void setRaidRepository(@NonNull RaidRepository raidRepository) {
        sRaidRepository = raidRepository;
    }
}
