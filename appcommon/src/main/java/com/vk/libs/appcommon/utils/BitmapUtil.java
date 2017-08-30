/*
 *   .
 */
package com.vk.libs.appcommon.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 图片工具 <br>
 * 获取assets下,或者非正常的资源文件,需要进行图片的op设置 . <br>
 * @author liulongzhenhai <br>
 * @version 1.0.0 2012-8-1<br>
 * @see
 * @since JDK 1.4.2.6
 */
public final class BitmapUtil {

	/**
	 * 构造 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:46:59 <br>
	 */
	private BitmapUtil() {
	}

	/**
	 * 计算一张图片的大小的size . <br>
	 * @author liulongzhenhai 2013-7-19 上午11:58:50 <br>
	 * @param bit 图片
	 * @return 大小
	 */
	public static int getBitmapSize(final Bitmap bit) {
		return bit.getHeight() * bit.getWidth();
	}

	/**
	 * 获取一张网路图片流.. <br>
	 * @author liulongzhenhai 2012-8-1 下午5:06:50 <br>
	 * @param url 网络完整地址
	 * @return 返回图片二进制
	 */
	public static byte[] getHttpImageByte(final String url) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		URL myFileUrl = null;
		try {
			myFileUrl = new URL(url);
		} catch (final MalformedURLException e) {
		}
		try {
			final HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			// conn.setRequestProperty("Cookie", "JSESSIONID=" + SessionManager.getCurrentSession().getId());
			conn.connect();
			final InputStream is = conn.getInputStream();
			final int length = conn.getContentLength();
			if (length != -1) {
				final byte[] imgData = new byte[length];
				final byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					destPos += readLen;
				}
				conn.disconnect();
				is.close();
				return imgData;
			}
		} catch (final IOException e) {

		}
		return null;
	}

	/**
	 * 获取网络图片转为Bitmap对象 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:08:06 <br>
	 * @param url 完整地址
	 * @return BImap对象
	 */
	public static Bitmap getHttpImageBitmap(final String url) {
		final byte[] data = getHttpImageByte(url);
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	/**
	 * 获取媒体数据库路径 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:09:27 <br>
	 * @param ct 上下文
	 * @param contentUri URI地址
	 * @return 返回相应的绝对路径
	 */
	public static String getUriImagePathQuery(final Context ct, final Uri contentUri) {
		// can post image
		if (contentUri == null) {
			return "";
		}

		final String[] proj = { MediaColumns.DATA };
		String path = "";
		final Cursor cursor = ct.getContentResolver().query(contentUri, proj, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndexOrThrow(MediaColumns.DATA));
			}
			cursor.close();
		}
		return path;
	}

	/**
	 * 获取bit的2进制 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:13:14 <br>
	 * @param bm 数据对象
	 * @return 返回二进制
	 */
	public static byte[] bitmap2Bytes(final Bitmap bm) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * Bitmap 转drawable对象 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:14:35 <br>
	 * @param bm 图片对象
	 * @return drawable
	 */
	public static Drawable bitmapToDrawable(final Bitmap bm) {
		if (bm == null) {
			return null;
		}
		return new BitmapDrawable(bm);
	}

	/**
	 * 二进制数组转为Btimap对象. <br>
	 * @author liulongzhenhai 2012-8-1 下午5:17:44 <br>
	 * @param b 二进制
	 * @return Bitmap
	 */
	public static Bitmap bytesToBitmap(final byte[] b) {
		if (b != null && b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	/**
	 * bitmap转换为二进制字节流 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:20:04 <br>
	 * @param bitmap 图片对象
	 * @return 图片流
	 */
	public static ByteArrayInputStream getInputStreamFromBitmap(final Bitmap bitmap) {
		try {
			final ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, oStream);
			final byte[] b = oStream.toByteArray();
			oStream.close();
			return new ByteArrayInputStream(b);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从系统的asset中读取一张图片 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:20:38 <br>
	 * @param context 上下文
	 * @param fileName 文件相对路劲
	 * @return 图片
	 */
	public static Bitmap getImageFromAssetFile(final Context context, final String fileName) {
		return getImageFromAssetFile(context, fileName, DisplayMetrics.DENSITY_LOW);
	}

	/**
	 * 从系统的asset中读取一张图片 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:21:12 <br>
	 * @param context 上下文
	 * @param fileName 文件相对路劲
	 * @param density Options.inDensity的设置,在一些情况下,需要制定.否则,会变形
	 * @return 返回图片
	 */
	public static Bitmap getImageFromAssetFile(final Context context, final String fileName, final int density) {
		if (TextUtils.isEmpty(fileName)) {
			return null;
		}
		Bitmap image = null;
		try {
			final AssetManager am = context.getAssets();
			final InputStream is = am.open(fileName);
			final Options o = new Options();
			o.inDensity = density;
			image = BitmapFactory.decodeStream(is, null, o);
			is.close();
		} catch (final Exception e) {
		}
		return image;
	}

	/**
	 * 从路径,SD卡中读取图片. <br>
	 * @author liulongzhenhai 2012-8-1 下午5:22:37 <br>
	 * @param path 完整路径
	 * @return 返回图片
	 */
	public static Drawable getFileDrawable(final String path) {
		return bitmapToDrawable(getFileBitmap(path));
	}

	/**
	 * 获取路径图片 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:22:59 <br>
	 * @param path 图片路径
	 * @return 图片
	 */
	public static Bitmap getFileBitmap(final String path) {
		byte[] b = null;
		try {
			b = FileUtil.getByte(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (b != null && b.length > 0) {
			return bytesToBitmap(b);
		}
		return null;
	}

	/**
	 * 缩放图片 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:23:28 <br>
	 * @param bmp 图片
	 * @param mScreenWidth 缩放的宽度
	 * @param mScreenHeight 缩放的高度
	 * @return 缩放后图片
	 */
	public static Bitmap createScaledBitmap(final Bitmap bmp, final int mScreenWidth, final int mScreenHeight) {
		return Bitmap.createScaledBitmap(bmp, mScreenWidth, mScreenHeight, true);
	}

	/**
	 * 将Drawable转化为Bitmap . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:24:10 <br>
	 * @param drawable 图片对象
	 * @return 图片
	 */
	public static Bitmap drawableToBitmap(final Drawable drawable) {
		// 取 drawable 的长宽
		final int w = drawable.getIntrinsicWidth();
		final int h = drawable.getIntrinsicHeight();
		// 取 drawable 的颜色格式
		final Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		final Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		final Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 获得圆角图片 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:24:57 <br>
	 * @param bitmap 图片
	 * @param roundPx 圆角度
	 * @return 图片
	 */
	public static Bitmap getRoundedCornerBitmap(final Bitmap bitmap, final float roundPx) {
		final int w = bitmap.getWidth();
		final int h = bitmap.getHeight();
		final Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片 . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:25:37 <br>
	 * @param bitmap 图片对象
	 * @return 图片
	 */
	public static Bitmap createReflectionImageWithOrigin(final Bitmap bitmap) {
		final int reflectionGap = 4;
		final int w = bitmap.getWidth();
		final int h = bitmap.getHeight();

		final Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		final Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);

		final Bitmap bitmapWithReflection = Bitmap.createBitmap(w, h + h / 2, Config.ARGB_8888);

		final Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		final Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		final Paint paint = new Paint();
		final LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
				+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * 获取一个selector XMLd对象,这里只需要默认和选中的图片对象 . <br>
	 * @author liulongzhenhai 2012-7-4 上午10:54:55 <br>
	 * @param defPic 默认图片对象
	 * @param selPic 选中图片对象
	 * @return selector图片
	 */
	public static StateListDrawable getNnewSelector(final Drawable defPic, final Drawable selPic) {
		return getNewSelector(defPic, selPic, selPic, defPic);
	}

	/**
	 * 通过代码配置一个selector XML对象 . <br>
	 * @author liulongzhenhai 2012-7-4 上午10:45:03 <br>
	 * @param normal 没有状态
	 * @param pressed 按下状态
	 * @param focused 获取焦点状态
	 * @param unable 无状态
	 * @return 返回selector 的对象布局
	 */
	public static StateListDrawable getNewSelector(final Drawable normal, final Drawable pressed,
                                                   final Drawable focused, final Drawable unable) {
		final StateListDrawable bg = new StateListDrawable();
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, focused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
		return bg;
	}

	/**
	 * 通过代码配置一个selector XML对象 . . <br>
	 * @author liulongzhenhai 2012-8-1 下午5:27:28 <br>
	 * @param normal 没有状态
	 * @param selected 选择状态
	 * @param pressed 按下状态
	 * @return 返回selector 的对象布局
	 */
	public static StateListDrawable getRadioButtonSelector(final Drawable normal, final Drawable selected,
                                                           final Drawable pressed) {
		final StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] { android.R.attr.state_focused, android.R.attr.state_enabled }, pressed);
		drawable.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, normal);
		drawable.addState(new int[] { android.R.attr.state_checked, android.R.attr.state_enabled }, pressed);
		drawable.addState(new int[] { android.R.attr.state_selected, android.R.attr.state_enabled }, pressed);
		drawable.addState(new int[] {}, normal);
		return drawable;
	}

	/**
	 * 根据自定义的弧度生成一张圆角图片 . <br>
	 * @author liulongzhenhai 2012-7-12 上午9:06:01 <br>
	 * @param bitmap 图片
	 * @param pixels 圆角
	 * @return 图片
	 */
	public static Bitmap toRoundCorner(final Bitmap bitmap, final int pixels) {
		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 按照宽的最终宽度,等比缩放 . <br>
	 * @author liulongzhenhai 2012-11-5 上午9:55:01 <br>
	 * @param bitmap 图片源
	 * @param widthSize 图片宽度的最终宽度
	 * @return BITMAP
	 */
	public static Bitmap zoomBitmap(final Bitmap bitmap, final int widthSize) {
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();

		// 定义预转换成的图片的宽度和高度

		// 计算缩放率，新尺寸除原始尺寸
		final float scaleWidth = (float) widthSize / width;
		final float scaleHeight = (float) widthSize / height;
		// 创建操作图片用的matrix对象
		final Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	/**
	 * 获取图片压缩的对象 <br>
	 * 先获取图片的参数,进行是否压缩的操作<br>
	 * 如果大于压缩的宽度,以压缩宽度比例进行压缩 .<br>
	 * @author liulongzhenhai 2013-7-19 下午4:38:57 <br>
	 * @param path 完整路径
	 * @param swidth 压缩宽度
	 * @param quality 压缩的比例 0-100
	 * @return Bitmap
	 */
	// public static Bitmap getBitmapCompression(final String path, final int swidth, final int quality) {
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	// BitmapFactory.decodeFile(path, options);
	// final int height = options.outHeight;
	// final int width = options.outWidth;
	// if (width > swidth) {
	// options.inSampleSize = swidth / width;
	// }
	// options.inJustDecodeBounds = false;
	//
	// Bitmap bitmap = BitmapFactory.decodeFile(path, options);
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//
	// bitmap.recycle();
	// bitmap = null;
	// return bytesToBitmap(baos.toByteArray());
	// }

	/**
	 * 压缩图片小雨100K的方法 . <br>
	 * @author liulongzhenhai 2013-7-22 上午10:22:44 <br>
	 * @param image Bitmap
	 * @return Bitmap
	 */
	public static Bitmap compressImage(final Bitmap image) {

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		final ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		final Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 进行压缩,先以比例压缩,然后精致压缩到等比距离,这种压缩方法,可以很大程度的降低内存溢出 . <br>
	 * @author liulongzhenhai 2013-7-22 下午2:06:07 <br>
	 * @param srcPath 路径
	 * @param swidth 压缩的宽度比例 0以下标识不压缩图片,只压缩质量
	 * @param quality 质量
	 * @return Bitmap
	 */
	public static Bitmap getBitmapCompression(final String srcPath, float swidth, final int quality) {
		final BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		final int w = newOpts.outWidth;
		final int h = newOpts.outHeight;
		Log.w("getBitmapCompression", swidth + "," + w);
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		if (swidth <= 0) {
			swidth = w;
		}
		final float scaleHeight = swidth / w; // 得到压缩比例,
		final float hh = newOpts.outHeight * scaleHeight;// 这里设置高度为800f
		// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		float be2 = 0;// 是否有遗留的缩放
		if (w > h && w > swidth) {// 如果宽度大的话根据宽度固定大小缩放
			be2 = newOpts.outWidth / swidth;
			be = (int) be2;
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be2 = newOpts.outHeight / hh;
			be = (int) be2;
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;// 设置缩放比例
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		if (bitmap == null) {
			return null;
		}
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Log.i("mylife", "qulity---" + quality);
		bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
		bitmap.recycle();
		bitmap = null;
		if (be2 > be) { // 如果存在遗漏,则进行再压缩
			return zoomBitmap(bytesToBitmap(baos.toByteArray()), (int) swidth);
		} else {
			return bytesToBitmap(baos.toByteArray());
		}
		// bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 对图片进行压缩处理,精确压缩,防止内存溢出 . <br>
	 * @author liulongzhenhai 2013-7-22 下午3:00:45 <br>
	 * @param image Bitmap
	 * @param swidth 压缩宽度
	 * @return Bitmap
	 */
	public static Bitmap getBitmapCompression(final Bitmap image, final float swidth) {

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 50, baos);
		if (baos.toByteArray().length > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.PNG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		final BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		final int w = newOpts.outWidth;
		final int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		final float scaleHeight = swidth / w; // 得到压缩比例,
		final float hh = newOpts.outHeight * scaleHeight;// 这里设置高度为800f
		// float ww = 480f;//这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		float be2 = 0;// 是否有遗留的缩放
		if (w > h && w > swidth) {// 如果宽度大的话根据宽度固定大小缩放
			be2 = newOpts.outWidth / swidth;
			be = (int) be2;
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be2 = newOpts.outHeight / hh;
			be = (int) be2;
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

		if (be2 > be) {
			return zoomBitmap(bitmap, (int) swidth);
		} else {
			return bitmap;
		}
	}

	/**
	 * 两张图片合成一张图片圆角的图片 . <br>
	 * @author liulongzhenhai 2013-10-28 上午11:24:15 <br>
	 * @param outBitmap1 背景图片
	 * @param bitmap 要生成圆角的图片
	 * @return Bitmap Bitmap
	 */
	public static Bitmap getRoundedCornerBitmap(final Bitmap outBitmap1, final Bitmap bitmap) {
		final int sm = 0;
		// Log.w("outHeight",outBitmap.getHeight()+","+outBitmap.getWidth());
		final Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		final Canvas canvas = new Canvas(outBitmap);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(sm, sm, bitmap.getWidth() - sm, bitmap.getHeight() - sm);
		final RectF rectF = new RectF(rect);
		final float roundPX = bitmap.getWidth() / 2;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return outBitmap;
	}

	/**
	 * 图片叠加 . <br>
	 * @author liulongzhenhai 2013-10-29 下午12:31:47 <br>
	 * @param bottom 底部
	 * @param top 上面的
	 * @return LayerDrawable
	 */
	public static LayerDrawable picStacked(final Bitmap bottom, final Bitmap top) {
		final Drawable[] array = new Drawable[2];
		array[0] = new BitmapDrawable(bottom);
		array[1] = new BitmapDrawable(top);
		return new LayerDrawable(array);
	}

	/**
	 * 图片是否可以使用的,没有被回收 <br>
	 * @author liulongzhenhai 2014年3月11日 下午7:45:39 <br>
	 * @param bit Bitmap
	 * @return 是否OK的
	 */
	public static boolean isRobust(final Bitmap bit) {
		if (bit != null && !bit.isRecycled()) {
			return true;
		}
		return false;
	}
}
