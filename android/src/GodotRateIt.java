
package org.godotengine.godot;

import android.net.Uri;

import android.os.Build;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.View;

public class GodotRateIt extends Godot.SingletonBase {

	private static Context context;
	private static Activity activity;

	static public Godot.SingletonBase initialize(Activity p_activity) {
		return new GodotRateIt(p_activity);
	}

	public GodotRateIt(Activity p_activity) {
		registerClass("GodotRateIt", new String[] {"rate", "get_version"});

		activity = p_activity;
		context = activity.getApplicationContext();
	}

	public void rate(final String url_prefix, final String url_prefix_fallback, final String id_format) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				String url_prefix_tmp = "market://details";
				String url_prefix_fallback_tmp = "https://play.google.com/store/apps/details";
				String id_format_tmp = "id";

				if (url_prefix.length() > 0) {
					url_prefix_tmp = url_prefix;
				}

				if (url_prefix_fallback.length() > 0) {
					url_prefix_fallback_tmp = url_prefix_fallback;
				}

				if (id_format.length() > 0) {
					id_format_tmp = id_format;
				}

				rateApp(url_prefix_tmp, url_prefix_fallback_tmp, id_format_tmp);
			}
		});
	}

	public int get_version() {
		int version = -1;

		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_META_DATA);

			version = pInfo.versionCode;
		} catch (NameNotFoundException e1) {
			Log.e(this.getClass().getSimpleName(), "Name not found", e1);
		}

		return version;
	}

	public void rateApp(final String url_prefix, final String url_prefix_fallback, final String id_format) {
		try {
			Intent rateIntent = rateIntentForUrl(url_prefix, id_format);
			activity.startActivity(rateIntent);
		} catch (ActivityNotFoundException e) {
			Intent rateIntent = rateIntentForUrl(url_prefix_fallback, id_format);

			activity.startActivity(rateIntent);
		}
	}

	private Intent rateIntentForUrl(final String url, final String id_format) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?%s=%s", url, id_format, activity.getPackageName())));
		int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

		if (Build.VERSION.SDK_INT >= 21) {
			flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
		} else {
			// noinspection deprecation
			flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
		}

		intent.addFlags(flags);

		return intent;
	}

	protected void onMainActivityResult(int requestCode, int resultCode, Intent data) {
		// Nothing to do
	}

	protected void onMainPause() {
		// Nothing to do
	}

	protected void onMainResume() {
		// Nothing to do
	}

	protected void onMainDestroy() {
		// Nothing to do
	}
}
