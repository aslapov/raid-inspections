package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.annotation.NonNull;

public final class RepositoryProvider {

    private static RaidRepository sRaidRepository;

    private RepositoryProvider() {

    }

    @NonNull
    public static RaidRepository provideRaidRepository() {
        if (sRaidRepository == null) {
            sRaidRepository = new RaidRepository();
        }
        return sRaidRepository;
    }

    public static void setRaidRepository(@NonNull RaidRepository raidRepository) {
        sRaidRepository = raidRepository;
    }
}
