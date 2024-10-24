package org.joinmastodon.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.webkit.WebView;

import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactHost;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactHost;
import com.facebook.react.defaults.DefaultReactNativeHost;
import com.facebook.react.soloader.OpenSourceMergedSoMapping;
import com.facebook.soloader.ExternalSoMapping;
import com.facebook.soloader.SoLoader;

import org.joinmastodon.android.api.PushSubscriptionManager;

import java.io.IOException;
import java.util.List;

import me.grishka.appkit.imageloader.ImageCache;
import me.grishka.appkit.utils.NetworkUtils;
import me.grishka.appkit.utils.V;

public class MastodonApp extends Application implements ReactApplication {

	@Override
	public ReactNativeHost getReactNativeHost(){
		return new DefaultReactNativeHost(this){
			@Override
			protected List<ReactPackage> getPackages(){
				return new PackageList(this).getPackages();
			}

			@Override
			protected String getJSMainModuleName(){
				return "index";
			}

			@Override
			public boolean getUseDeveloperSupport(){
				return BuildConfig.DEBUG;
			}

			@Override
			protected boolean isNewArchEnabled(){
				return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
			}

			@Override
			protected Boolean isHermesEnabled(){
				return BuildConfig.IS_HERMES_ENABLED;
			}
		};
	}

	@Override
	public ReactHost getReactHost() {
		return DefaultReactHost.getDefaultReactHost(getApplicationContext(), getReactNativeHost());
	}

	@SuppressLint("StaticFieldLeak") // it's not a leak
	public static Context context;

	@Override
	public void onCreate(){
		super.onCreate();
		try {
			ExternalSoMapping externalSoMapping = OpenSourceMergedSoMapping.INSTANCE;
			SoLoader.init(this, externalSoMapping);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
			DefaultNewArchitectureEntryPoint.load();
		}
		context=getApplicationContext();
		V.setApplicationContext(context);
		ImageCache.Parameters params=new ImageCache.Parameters();
		params.diskCacheSize=100*1024*1024;
		params.maxMemoryCacheSize=Integer.MAX_VALUE;
		ImageCache.setParams(params);
		NetworkUtils.setUserAgent("MastodonAndroid/"+BuildConfig.VERSION_NAME);

		PushSubscriptionManager.tryRegisterFCM();
		GlobalUserPreferences.load();
		if(BuildConfig.DEBUG){
			WebView.setWebContentsDebuggingEnabled(true);
		}
	}
}
