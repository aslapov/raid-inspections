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

public final class ApiFactory {

	private static Retrofit sRetrofit;
	private static OkHttpClient sHttpClient;
	private static BasicAuthInterceptor sAuthInterceptor;

	private static volatile RaidApiService sRaidService;
	private static volatile AuthApiService sAuthService;

	private static final String BASE_URL = "http://10.1.3.106/";

	private ApiFactory() {
	}

	@NonNull
	public static RaidApiService getRaidService() {
		RaidApiService service = sRaidService;
		if (service == null) {
			synchronized (ApiFactory.class) {
				service = sRaidService;
				if (service == null) {
					service = sRaidService = getRetrofit().create(RaidApiService.class);
				}
			}
		}
		return service;
	}

	@NonNull
	public static AuthApiService getAuthService() {
		AuthApiService service = sAuthService;
		if (service == null) {
			synchronized (ApiFactory.class) {
				service = sAuthService;
				if (service == null) {
					service = sAuthService = getRetrofit().create(AuthApiService.class);
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
			synchronized (ApiFactory.class) {
				interceptor = sAuthInterceptor;
				if (interceptor == null)
					interceptor = sAuthInterceptor = new BasicAuthInterceptor();
			}
		}

		return interceptor;
	}

	@NonNull
	private static Retrofit getRetrofit() {
		Retrofit retrofit = sRetrofit;
		if (retrofit == null) {
			synchronized (ApiFactory.class) {
				retrofit = sRetrofit;
				if (retrofit == null) {
					retrofit = sRetrofit = buildRetrofit();
				}
			}
		}
		return retrofit;
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
		OkHttpClient client = sHttpClient;
		if (client == null) {
			synchronized (ApiFactory.class) {
				client = sHttpClient;
				if (client == null) {
					sAuthInterceptor = new BasicAuthInterceptor();
					client = sHttpClient = buildClient();
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
