package it.mbkj.lib.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import it.mbkj.lib.R;

public class MyProgressDialogs {
    private AlertDialog dialog;
    private Context context;


    public MyProgressDialogs(Context context) {
        this.context = context;
    }


    public void showDialog() {
        this.showDialog("");
    }

    public void showDialog(String msg) {
        dialog=new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog).create();
        this.dialog.show();
        this.dialog.setCancelable(false);
        Window window = this.dialog.getWindow();
        window.setContentView(R.layout.lib_mvc_loading_view);
        window.setBackgroundDrawable(new BitmapDrawable());
        ImageView ivLoading = window.findViewById(R.id.ivLoading);
        RequestOptions ob = new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
        Glide.with(context)
                .load(R.mipmap.lib_loading)
                .apply(ob)
                .into(ivLoading);
        this.dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == 4 && event.getAction() == 0) {
                dialog.dismiss();
            }

            return false;
        });
    }

    public void closeDialog() {
        if (this.dialog != null && this.dialog.isShowing()) {
            try {
                this.dialog.dismiss();
                this.dialog = null;
            } catch (Exception e) {

            }
        }

    }

}
