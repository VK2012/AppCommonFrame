package com.vk.libs.appcommon.adapter;

/**
 * 多类型item布局适配辅助类
 * @param <T>
 */
public interface MultiItemTypeSupport<T> {

	int getLayoutId(int position, T t);

	int getViewTypeCount();

	int getItemViewType(int postion, T t);
}