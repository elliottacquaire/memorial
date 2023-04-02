package com.exae.memorialapp.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exae.memorialapp.dialog.MMLoading;
import com.google.gson.Gson;


public abstract class CoreFragment extends Fragment {
//  protected TipDialog loadingDialog;
//  protected TipDialog tipDialog;

    protected MMLoading mLoadingDialog = null;
    private Gson gson;

    public CoreFragment() {
    }

    @ContentView
    public CoreFragment(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
    }

//  public void showLoading() {
//    if (loadingDialog != null) {
//      loadingDialog.show();
//    } else {
//      loadingDialog = crateLoadingDialog();
//      loadingDialog.show();
//    }
//  }

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new MMLoading.Builder(requireContext())
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(true)
                    .create();
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
    }

//  public void dismissLoading() {
//    if (loadingDialog != null) loadingDialog.dismiss();
//  }

    public void toast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

//  private TipDialog crateLoadingDialog() {
//    return new TipDialog.Builder(requireActivity()).setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
//        .setTipWord(getString(R.string.loading))
//        .create();
//  }

//  private TipDialog createTipDialog(String msg, int iconType) {
//    return new TipDialog.Builder(requireActivity())
//        .setIconType(iconType)
//        .setTipWord(msg)
//        .create();
//  }


    /**
     * 带图标的提示框
     */
//  public void showTip(String message, int iconType) {
//    if (tipDialog == null) {
//      tipDialog = createTipDialog(message, iconType);
//      tipDialog.setCanceledOnTouchOutside(true);
//    }
//    if (!tipDialog.isShowing()) {
//      tipDialog.show();
//    }
//  }
//
//  public void showNetError(Resource repos) {
//    if (repos.isFail()) {
//      toast(repos.getMessage());
//    } else {
//      toast(formatError(repos));
//    }
//  }


}
