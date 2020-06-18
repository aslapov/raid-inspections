package aslapov.android.study.pallada.kisuknd.raids.model;

import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RaidApiFactory {

	private static OkHttpClient sClient;
	private static BasicAuthInterceptor sAuthInterceptor;

	private static volatile IRaidService sService;

	private static final String BASE_URL = "http://192.168.77.77:8082/";

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

	public static void setAuthTicket(@NotNull String authTicket) {
		getAuthInterceptor().setTicket(authTicket);
	}

	@NotNull
	public static BasicAuthInterceptor getAuthInterceptor() {
		BasicAuthInterceptor interceptor = sAuthInterceptor;
		if (interceptor == null) {
			synchronized (RaidApiFactory.class) {
				interceptor = sAuthInterceptor;
				if (interceptor == null)
					interceptor = sAuthInterceptor = new BasicAuthInterceptor();
			}
		}

		return interceptor;
	}

	@NonNull
	private static Retrofit buildRetrofit() {
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
				.create();

		return new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(getClient())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();
	}

	@NonNull
	private static OkHttpClient getClient() {
		OkHttpClient client = sClient;
		if (client == null) {
			synchronized (RaidApiFactory.class) {
				client = sClient;
				if (client == null) {
					sAuthInterceptor = new BasicAuthInterceptor();
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
				.addInterceptor(getAuthInterceptor())
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

	private static class BasicAuthInterceptor implements Interceptor {

        private String authHeader;

		public BasicAuthInterceptor() { }

		private Optional<String> getAuthHeader() {
			return Optional.ofNullable(authHeader);
		}

		public void setTicket(@NotNull String ticket) {
            authHeader = "Basic " + Base64.encodeToString(ticket.getBytes(), Base64.NO_WRAP);
        }

		@NotNull
		@Override
		public Response intercept(@NotNull Chain chain) throws IOException {

			Request request = getAuthHeader()
					.map(v -> chain.request().newBuilder()
							.header("Authorization", v).build())
					.orElse(chain.request());

            return chain.proceed(request);
		}
	}
}
