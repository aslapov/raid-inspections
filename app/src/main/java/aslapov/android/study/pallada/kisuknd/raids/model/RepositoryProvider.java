package aslapov.android.study.pallada.kisuknd.raids.model;

import android.content.Context;

import androidx.annotation.NonNull;

import aslapov.android.study.pallada.kisuknd.raids.repository.RaidRepository;

public final class RepositoryProvider {

    private static RaidRepository sRaidRepository;

    private RepositoryProvider() { }

    @NonNull
    public static RaidRepository provideRaidRepository(Context applicationContext) {
        if (sRaidRepository == null) {
            sRaidRepository = new RaidRepository(applicationContext);
        }
        return sRaidRepository;
    }
}
