package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RaidApiFactory {

    private static OkHttpClient sClient;

    private static volatile IRaidService sService;

    private static final String BASE_URL = "http://10.1.3.106:8085/app/";

    private RaidApiFactory() {
    }

    @NonNull
    public static IRaidService getRaidService() {
        IRaidService service = sService;
        if (service == null) {
            synchronized (RaidApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(IRaidService.class);
                }
            }
        }
        return service;
    }

    public static void recreate() {
        sClient = null;
        sClient = getClient();
        sService = buildRetrofit().create(IRaidService.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (RaidApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new RaidCookieJar())
                .build();
    }

    private static class RaidCookieJar implements CookieJar {
        private List<Cookie> mCookies;

        @NotNull
        @Override
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            return (mCookies != null) ? mCookies : new ArrayList<>();
        }

        @Override
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            mCookies = list;
        }
    }
}
