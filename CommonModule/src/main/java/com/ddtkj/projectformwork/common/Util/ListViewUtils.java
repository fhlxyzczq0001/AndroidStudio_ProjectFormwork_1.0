package com.ddtkj.projectformwork.common.Util;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewUtils {
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int a = listAdapter.getCount();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
			// Log.e("listItem.getMeasuredHeight()",
			// listItem.getMeasuredHeight()+"");
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		Log.e(listView.getClass().getSimpleName() + "params.height",
				listAdapter.getCount() + "--" + params.height + "");
		listView.setLayoutParams(params);
	}

	public static void setGridViewHeightBasedOnChildren(GridView gridView,
			float item_H) {
		ListAdapter gridAdapter = gridView.getAdapter();
		if (gridAdapter == null) {
			return;
		}
		
		int len = gridAdapter.getCount();
		if(len<=3){
			len=1;
		}else if(len<=6){
			len=2;
		}else if(len<=9){
			len=3;
		}
		float itemHeight = item_H + 10;
		int totalHeight = (int) (itemHeight * len);
		// }
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = (int) (totalHeight);
		gridView.setLayoutParams(params);

	}
}
