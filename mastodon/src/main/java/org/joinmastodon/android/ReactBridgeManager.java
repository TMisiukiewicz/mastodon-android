package org.joinmastodon.android;

import android.app.Activity;
import com.facebook.react.BuildConfig;
import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.soloader.SoLoader;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReactBridgeManager {
	private ReactBridgeManager() {}

	static {
		ReactBridgeManager instance = new ReactBridgeManager();
		shared = instance;
	}

	@Nullable
	private static ReactNativeHost reactNativeHost;
	@NotNull
	public static final ReactBridgeManager shared;

	@Nullable
	public ReactNativeHost getReactNativeHost() {
		return reactNativeHost;
	}

	public void loadReactNative(@NotNull final Activity activity) {
		if (reactNativeHost == null) {
			SoLoader.init(activity, false);
			reactNativeHost = new ReactNativeHost(activity.getApplication()) {
				public boolean getUseDeveloperSupport() {
					return BuildConfig.DEBUG;
				}

				@NotNull
				protected List getPackages() {
					return new PackageList(this.getApplication()).getPackages();
				}

				@NotNull
				protected String getJSMainModuleName() {
					return "index";
				}

				@NotNull
				protected String getBundleAssetName() {
					return "index.android.bundle";
				}
			};
		}

		if (reactNativeHost != null) {
			ReactInstanceManager instanceManager = reactNativeHost.getReactInstanceManager();
			if (instanceManager != null) {
				instanceManager.createReactContextInBackground();
			}
		}
	}
}