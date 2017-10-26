package com.ddtkj.projectformwork.common.CustomView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_AreaBean;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_AreaListBean;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.R;
import com.utlis.lib.WindowUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;


/**
 * 省 市 区3级联动选择地址
 *
 * @ClassName: AreaChoice
 * @Description: TODO
 * @author: Administrator杨重诚
 * @date: 2016-3-28 上午11:48:26
 */
public class Common_DialogAreaWheel implements OnWheelChangedListener {

    private Context context;
    private WheelView mViewProvince;// 省的滑动组件
    private WheelView mViewCity;// 市的滑动组件
    private WheelView mViewDistrict;// 区的滑动组件
    Dialog dialog;
    private int VisibleItems = 7;// 设置可见条数
    private TextView showTextView;// 显示地址的textView
    String showTextDefault = "";// 默认显示地址
    Common_Base_HttpRequest_Interface mCommon_base_httpRequest_interface;//请求数据接口
    //请求参数
    String datatype = "p";//区域类型
    String subid = "";//区域id
    /**
     * 所有省
     */
    // protected String[] mProvinceDatas;
    private List<Common_AreaBean> mProvinceList;
    /**
     * key - 省 value - 市
     */
    protected Map<String, List<Common_AreaBean>> mCitisDatasMap = new HashMap<String, List<Common_AreaBean>>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, List<Common_AreaBean>> mDistrictDatasMap = new HashMap<String, List<Common_AreaBean>>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 查询id
     */
    protected String mCodeId = "";

    private String proviceId = "";// 省id
    private String cityId = "";// 市id
    private String countyId = "";// 区id

    /**
     * 初始化组件
     *
     * @Title: setUpViews
     * @Description: TODO
     * @return: void
     */
    public void setUpViews(WheelView mViewProvince, WheelView mViewCity,
                           WheelView mViewDistrict) {
        this.mViewProvince = mViewProvince;
        this.mViewCity = mViewCity;
        this.mViewDistrict = mViewDistrict;
        setUpListener();// 添加change事件
        setUpData();
    }

    /**
     * 添加change事件
     *
     * @Title: setUpListener
     * @Description: TODO
     * @return: void
     */
    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
    }

    /**
     * 初始化数据
     *
     * @Title: setUpData
     * @Description: TODO
     * @return: void
     */
    private void setUpData() {
        // 初始化默认空数据
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context,
                new String[]{""}));
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context,
                new String[]{""}));
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context,
                new String[]{""}));

        // 设置可见条目数量
        mViewProvince.setVisibleItems(VisibleItems);
        mViewCity.setVisibleItems(VisibleItems);
        mViewDistrict.setVisibleItems(VisibleItems);

        regionListProvince(getUserArea_Params(datatype, subid));// 初始化省的信息
    }

    /**
     * wheel滑动类型
     */
    private enum wheelViewType {
        Province,
        City,
        District,
        All
    }

    /**
     * 滑动状态设置
     *
     * @param type
     */
    private void updateWheelState(wheelViewType type) {
        switch (type) {
            case Province:
                mViewProvince.setEnabled(true);
                mViewCity.setEnabled(false);
                mViewDistrict.setEnabled(false);
                break;
            case City:
                mViewProvince.setEnabled(false);
                mViewCity.setEnabled(true);
                mViewDistrict.setEnabled(false);
                break;
            case District:
                mViewProvince.setEnabled(false);
                mViewCity.setEnabled(false);
                mViewDistrict.setEnabled(true);
                break;
            case All:
                mViewProvince.setEnabled(true);
                mViewCity.setEnabled(true);
                mViewDistrict.setEnabled(true);
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue, boolean isUpdate) {
        if (!isUpdate) {
            if (wheel == mViewProvince) {
                updateWheelState(wheelViewType.Province);
            } else if (wheel == mViewCity) {
                updateWheelState(wheelViewType.City);
            } else if (wheel == mViewDistrict) {
                updateWheelState(wheelViewType.All);
            }
        }
        if (isUpdate) {
            // TODO Auto-generated method stub
            if (wheel == mViewProvince) {
                updateCities();
            } else if (wheel == mViewCity) {
                updateAreas();
            } else if (wheel == mViewDistrict) {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)
                        .get(newValue).getName();
                countyId = mDistrictDatasMap.get(mCurrentCityName).get(newValue)
                        .getId()
                        + "";// 城区id
                showTextView.setText(mCurrentProviceName + mCurrentCityName
                        + mCurrentDistrictName);
                showTextView.setTag(proviceId + "," + mCurrentProviceName + ";"
                        + cityId + "," + mCurrentCityName + ";"
                        + countyId + "," + mCurrentDistrictName);
            }
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)
                .get(pCurrent).getName();
        mCodeId = mCitisDatasMap.get(mCurrentProviceName).get(pCurrent).getId()
                + "";
        cityId = mCodeId;
        List<Common_AreaBean> areaList = mDistrictDatasMap.get(mCurrentProviceName);
        if (areaList == null) {
            // 获取区的数据
            datatype = "a";
            subid = String.valueOf(mCodeId);
            regionListDistrict(getUserArea_Params(datatype, subid));
        } else {
            String[] areas = new String[areaList.size()];

            for (int i = 0; i < areaList.size(); i++) {
                areas[i] = areaList.get(i).getName();
            }
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context,
                    areas));
            mViewDistrict.setCurrentItem(0);

            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)
                    .get(pCurrent).getName();// 城区
            countyId = mDistrictDatasMap.get(mCurrentCityName).get(pCurrent)
                    .getId()
                    + "";// 城区id
            showTextView.setText(mCurrentProviceName + mCurrentCityName
                    + mCurrentDistrictName);
            showTextView.setTag(proviceId + "," + mCurrentProviceName + ";"
                    + cityId + "," + mCurrentCityName + ";"
                    + countyId + "," + mCurrentDistrictName);

        }

        updateWheelState(wheelViewType.All);

    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceList.get(pCurrent).getName();
        mCodeId = mProvinceList.get(pCurrent).getId() + "";
        proviceId = mCodeId;
        List<Common_AreaBean> citiesList = mCitisDatasMap.get(mCurrentProviceName);
        if (citiesList == null) {
            // 获取市的数据
            datatype = "c";
            subid = String.valueOf(mCodeId);
            regionListCity(getUserArea_Params(datatype, subid));
        } else {
            String[] cities = new String[citiesList.size()];
            for (int i = 0; i < citiesList.size(); i++) {
                cities[i] = citiesList.get(i).getName();
            }
            mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context,
                    cities));
            mViewCity.setCurrentItem(0);
            updateAreas();
        }
    }

    /**
     * 显示Dialog
     *
     * @param context
     * @param showTextView
     * @Title: showDialog
     * @Description: TODO
     * @return: void
     */
    public Common_DialogAreaWheel(Context context, TextView showTextView) {
        this.context = context;
        this.showTextView = showTextView;
    }

    /**
     * 初始化
     *
     * @param context
     * @param showTextView
     * @param dialogStyle
     * @Title:DialogAreaWheel
     * @Description:TODO
     */
    public Common_DialogAreaWheel(Context context, TextView showTextView,
                                  int dialogStyle, String showTextDefault) {
        this.context = context;
        mCommon_base_httpRequest_interface = new Common_Base_HttpRequest_Implement();
        this.showTextView = showTextView;
        this.showTextDefault = showTextDefault;
        if (dialog == null) {
            dialog = new Dialog(context, dialogStyle);
            dialog.setContentView(R.layout.common_dialog_area_layout);

            //动态设置wheelView_Layout为屏幕1/3高度
            LinearLayout wheelView_Layout = (LinearLayout) dialog
                    .findViewById(R.id.wheelView_Layout);
            LayoutParams params = (LayoutParams) wheelView_Layout
                    .getLayoutParams();
            params.height = WindowUtils.getWindowHeight(context) / 3;
            wheelView_Layout.setLayoutParams(params);

            //确定按钮点击事件
            dialog.findViewById(R.id.query_Layout).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });

            mViewProvince = (WheelView) dialog.findViewById(R.id.id_province);
            mViewCity = (WheelView) dialog.findViewById(R.id.id_city);
            mViewDistrict = (WheelView) dialog.findViewById(R.id.id_district);

            Window window = dialog.getWindow();

            WindowManager windowManager = ((Activity) context)
                    .getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (display.getWidth()); // 设置宽度
            window.setAttributes(lp);
            window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        }
    }

    /**
     * 请求省市接口的Params
     */
    public Map<String, Object> getUserArea_Params(String type, String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);//请求区域类型
        params.put("id", id);//区域id
        return params;
    }

    /**
     * 获取省信息
     *
     * @Title: regionList
     * @Description: TODO
     * @return: void
     */
    private void regionListProvince(Map<String, Object> params) {
        mCommon_base_httpRequest_interface.requestData(context, Common_HttpPath.URL_API_AREA_INFO, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    Common_AreaListBean areaListBean = JSONObject.parseObject(request_bean.getData().toString(),
                            Common_AreaListBean.class);
                    mProvinceList = areaListBean.getProvince_list();
                    String[] mProvinceDatas = new String[mProvinceList.size()];
                    for (int i = 0; i < mProvinceList.size(); i++) {
                        mProvinceDatas[i] = mProvinceList.get(i).getName();
                    }
                    initProvinceDatas(mProvinceDatas);
                } else {
                    // 请求失败
                }
            }
        },false, Common_HttpRequestMethod.POST);

    }

    /**
     * 获取市信息
     *
     * @Title: regionList
     * @Description: TODO
     * @return: void
     */
    private void regionListCity(Map<String, Object> params) {
        mCommon_base_httpRequest_interface.requestData(context, Common_HttpPath.URL_API_AREA_INFO, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    Common_AreaListBean areaListBean = JSONObject.parseObject(request_bean.getData().toString(),
                            Common_AreaListBean.class);
                    List<Common_AreaBean> cityList = areaListBean.getCity_list();
                    mCitisDatasMap.put(mCurrentProviceName, cityList);
                    String[] mCityDatas = new String[cityList.size()];
                    for (int i = 0; i < cityList.size(); i++) {
                        mCityDatas[i] = cityList.get(i).getName();
                    }
                    initCityDatas(mCityDatas);
                } else {
                    // 请求失败
                }
            }
        },false, Common_HttpRequestMethod.POST);
    }

    /**
     * 获取区信息
     *
     * @Title: regionList
     * @Description: TODO
     * @return: void
     */
    private void regionListDistrict(Map<String, Object> params) {
        mCommon_base_httpRequest_interface.requestData(context, Common_HttpPath.URL_API_AREA_INFO, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    Common_AreaListBean areaListBean = JSONObject.parseObject(request_bean.getData().toString(),
                            Common_AreaListBean.class);
                    List<Common_AreaBean> areaList = areaListBean.getArea_list();

                    mDistrictDatasMap.put(mCurrentCityName, areaList);
                    String[] mAreaDatas = new String[areaList.size()];
                    String[] mAreaId = new String[areaList.size()];
                    for (int i = 0; i < areaList.size(); i++) {
                        mAreaDatas[i] = areaList.get(i).getName();
                        mAreaId[i] = areaList.get(i).getId() + "";
                    }
                    initAreaDatas(mAreaDatas, mAreaId);
                } else {
                    // 请求失败
                }
            }
        },false,Common_HttpRequestMethod.POST);
    }

    /**
     * 初始化省的信息
     *
     * @param mProvinceDatas
     * @Title: initProvinceDatas
     * @Description: TODO
     * @return: void
     */
    private void initProvinceDatas(String[] mProvinceDatas) {
        if (mProvinceDatas.length == 0) {
            mProvinceDatas = new String[]{""};
        }
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context,
                mProvinceDatas));
        mViewCity.setCurrentItem(0);
        // 更新市的信息
        updateCities();
    }

    /**
     * 初始化市的信息
     *
     * @Title: initCityDatas
     * @Description: TODO
     * @return: void
     */
    private void initCityDatas(String[] mCityDatas) {
        if (mCityDatas.length == 0) {
            mCityDatas = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context,
                mCityDatas));
        mViewCity.setCurrentItem(0);
        // 更新区的信息
        updateAreas();
    }

    /**
     * 初始化区的信息
     *
     * @param mAreaDatas
     * @Title: initAreaDatas
     * @Description: TODO
     * @return: void
     */
    private void initAreaDatas(String[] mAreaDatas, String[] mAreaId) {
        if (mAreaDatas.length == 0) {
            mAreaDatas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context,
                mAreaDatas));
        mViewDistrict.setCurrentItem(0);

        mCurrentDistrictName = mAreaDatas[0];// 城区名字
        countyId = mAreaId[0];// 区域id
        if (showTextDefault.isEmpty()) {
            showTextView.setText(mCurrentProviceName + mCurrentCityName
                    + mCurrentDistrictName);
            showTextView.setTag(proviceId + "," + mCurrentProviceName + ";"
                    + cityId + "," + mCurrentCityName + ";"
                    + countyId + "," + mCurrentDistrictName);
        } else {
            showTextView.setText(showTextDefault);
            showTextDefault = "";
        }

        updateWheelState(wheelViewType.All);
    }

    public void showDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
            setUpListener();// 添加change事件
            setUpData();
        }
    }

    private void closDialog() {
        if (dialog.isShowing()) {
            dialog.cancel();
        }
    }
}
