/*
 *   .
 */
package com.vk.libs.appcommon.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

/**
 * 文件类的方法 含SD卡操作,纯文件的操作.不包含assests的.主要针对Sd卡类型的文件 .<br>
 * @author liulongzhenhai <br>
 * @version 1.0.0 2012-8-2<br>
 * @see
 * @since JDK 1.4.2.6
 */
public final class FileUtil {

	/**
	 * 录音附件缓存路径.
	 */
	private final static String MYDOWNLOAD = "/ygsoft/cache/attachment/";

	/**
	 * 构造 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 */
	private FileUtil() {
	}

	/**
	 * 写入文件 SD卡的 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param data 数据内容
	 * @param path 完整路径
	 * @param unio 编码"utf-8"之类的
	 * @param append 是否追加
	 * @throws IOException IO错误
	 */
	public static void writeDataToFile(final String data, final String path, final String unio, final boolean append)
			throws IOException {
		OutputStreamWriter osw;
		final File file = new File(path);
		if (!append) {
			delFile(path);
		}
		if (!checkFileExists(path)) {
			file.createNewFile();
		}
		osw = new OutputStreamWriter(new FileOutputStream(file, append), unio);
		osw.write(data);
		osw.close();

	}

	/**
	 * 获取该路劲的后缀 <br>
	 * @author liulongzhenhai 2014年3月20日 下午5:33:14 <br>
	 * @param path 完整路径
	 * @return 后缀,不含.
	 */
	public static String getFileTypeString(final String path) {
		if (!TextUtils.isEmpty(path)) {
			int index_dian = path.lastIndexOf(".");
			int index_fen = path.lastIndexOf("/");
			if (index_dian > index_fen) {
				return path.substring(index_dian + 1);
			}
		}
		return "";
	}

	/**
	 * 多线程读写的时候需要注意，外部的方法需要同步 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param fileFullPath 写入的文件路径
	 * @param pBuf 写入文件的缓存
	 * @return 如果文件及路径不存在将创建文件
	 */
	public static boolean write(final String fileFullPath, final byte[] pBuf) {
		return write(fileFullPath, pBuf, 0, pBuf.length, 0);
	}

	/**
	 * 多线程读写的时候需要注意，外部的方法需要同步. <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param fileFullPath 写入的文件路径
	 * @param pBuf 写入文件的缓存
	 * @param pos 缓存起始位置
	 * @param size 写入字节数
	 * @param skip 文件跳跃字节数
	 * @return 如果文件及路径不存在将创建文件
	 */
	public static boolean write(final String fileFullPath, final byte[] pBuf, final int pos, final int size,
			final int skip) {
		final File file = new File(fileFullPath);
		RandomAccessFile randomAccessFile = null;
		try {
			// 文件存在
			if (file.exists()) {
				randomAccessFile = new RandomAccessFile(file, "rw");
			} else { // 文件不存在

				// 判断父目录是否存在
				final File p = file.getParentFile();
				boolean isOk = false;
				if (p != null && !p.exists()) { // 目录不存在创建

					isOk = p.mkdirs();
				} else if (p != null && p.exists()) { // 目录存在

					isOk = true;
				}
				if (isOk) {
					final boolean isSucess = file.createNewFile();
					if (isSucess) {
						randomAccessFile = new RandomAccessFile(file, "rw");
					}
				}
			}
			if (null != randomAccessFile) {
				randomAccessFile.seek(skip);
				randomAccessFile.write(pBuf, pos, size);
				randomAccessFile.close();
				return true;
			}
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
		return false;
	}

	/**
	 * 创建目录 <br>
	 * @author liulongzhenhai 2014年3月20日 下午4:28:33 <br>
	 * @param path 完整路劲
	 * @return 是否成功
	 */
	public static boolean createDir(final String path) {
		File f = getFileObject(path);
		if (f != null && !f.exists() && !f.isFile()) {
			return f.mkdirs();
		}
		return false;
	}

	/**
	 * 读取文件的内容,包括SD卡的. <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param path 完整路径
	 * @param unio 编码
	 * @return 读取的字符串
	 * @throws IOException IO错误
	 */
	public static String readFileData(final String path, final String unio) throws IOException {
		final InputStreamReader isr = new InputStreamReader(new FileInputStream(path), unio);
		final BufferedReader br = new BufferedReader(isr);
		final StringBuilder str = new StringBuilder();
		while (br.ready()) {
			str.append(br.readLine());
		}
		br.close();
		isr.close();
		return str.toString();

	}

	/**
	 * 通过完整路径读取文件的字节流 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param fileFullPath 读取文件路径
	 * @return 返回整个文件数据 流
	 */
	public static byte[] readFilebyte(final String fileFullPath) {
		final File file = getFileObject(fileFullPath);
		if (file != null && file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				final int readSize = fis.available();
				final byte[] buff = new byte[readSize];
				fis.read(buff);
				fis.close();
				fis = null;
				return buff;
			} catch (final Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return null;
	}

	/**
	 * 删除文件 如果要 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param path 完整路径
	 * @return 是否删除成功
	 */
	public static boolean delFile(final String path) {
		if (path != null && !"".equals(path)) {
			final File f = new File(path);
			if (f != null && f.isFile()) {
				return f.delete();
			}
		}
		return false;
	}

	/**
	 * 判断指定文件是否存在 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param filePath 文件路径
	 * @return 文件是否存在
	 */
	public static Boolean checkFileExists(final String filePath) {
		File f = getFileObject(filePath);
		try {
			if (f != null && f.exists() && f.isFile()) {
				return true;
			} else {
				return false;
			}
		} finally {
			f = null;
		}
	}

	/**
	 * 返回文件对像 . <br>
	 * @author liulongzhenhai 2012-8-2 上午8:49:55 <br>
	 * @param filePath 要创建文件的文件路径
	 * @return File 文件对像
	 */
	public static File getFileObject(final String filePath) {
		if (filePath == null || filePath.length() <= 0) {
			return null;
		}
		return new File(filePath);
	}

	/**
	 * 改文件的路径 <br>
	 * @author liulongzhenhai 2014年3月13日 上午8:39:28 <br>
	 * @param oldPath 旧路径
	 * @param newPath 新路径
	 * @return 是否成功
	 */
	public static boolean renamedFile(final String oldPath, final String newPath) {
		File oldFile = getFileObject(oldPath);
		if (oldFile != null && oldFile.exists() && oldFile.isFile()) {
			File newFile = getFileObject(newPath);
			if (newFile != null && !newFile.exists()) {
				return oldFile.renameTo(newFile);
			}
		}
		return false;
	}

	/**
	 * 创建文件保存图片文件 .<br>
	 * @param imageUri 下载地址
	 * @param folderName 保存路径名
	 * @return File
	 */
	public static File getCacheFile(final String imageUri, final String folderName) {
		File cacheFile = null;
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				final File sdCardDir = Environment.getExternalStorageDirectory();
				final String fileName = getFileName(imageUri);
				final File dir = new File(sdCardDir.getCanonicalPath() + folderName);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				cacheFile = new File(dir, fileName);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return cacheFile;
	}

	/**
	 * 根据下载地址截取文件名.<br>
	 * @param path 下载地址
	 * @return 文件名
	 */
	public static String getFileName(final String path) {
		final int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}

	/**
	 * 判断该请求地址是否是id.
	 * @author shaorulong 2013-4-27 上午10:30:40 <br>
	 * @param path 请求地址
	 * @return true：id，false：地址。
	 */
	public static boolean pathIsId(final String path) {
		try {
			Integer.parseInt(path);
			return true;
		} catch (final Exception e) {
			// 该请求是地址
		}
		return false;
	}


	/**
	 * 根据文件名获取文件保存的整体路径. <br>
	 * @author shaorulong 2013-6-9 下午4:10:18 <br>
	 * @param fileName 文件名
	 * @return 文件名保存在sd卡中的路径
	 */
	public static String getPathByFileName(final String fileName) {
		String filePath = "";
		final File sdCardDir = Environment.getExternalStorageDirectory();
		try {
			filePath = sdCardDir.getCanonicalPath() + MYDOWNLOAD + fileName;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return filePath;

	}

	/**
	 * 把一个文件转化为字节.
	 * @author shaorulong 2013-6-9 下午4:10:18 <br>
	 * @param filePath 文件路径
	 * @return byte[] 字节流
	 * @throws Exception
	 */
	public static byte[] getByte(final String filePath) throws Exception {
		byte[] bytes = null;
		final File file = new File(filePath);
		if (file.exists() && file != null) {
			final InputStream is = new FileInputStream(file);
			final int length = (int) file.length();
			// 当文件的长度超过了int的最大值
			if (length > Integer.MAX_VALUE) {
				// //System.out.println("this file is max ");
				return null;
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// 如果得到的字节长度和file实际的长度不一致就可能出错了
			if (offset < bytes.length) {
				// //System.out.println("file length is error");
				return null;
			}
			is.close();
		}
		return bytes;
	}


}
