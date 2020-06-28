package com.be.positive.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.kirana.merchant.R;


import org.jetbrains.annotations.Nullable;


/**
 * Created by AND I5 on 05-02-2018.
 */

public class MessageUtils {
    public static String msg = "";

    /**
     * Show message using snack Bar
     */

    @SuppressLint("NewApi")
    public static Snackbar showSnackBar(FragmentActivity context, View view, String message) {

        Snackbar snackbar = null;
        try {
            if (view != null) {
                snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(context.getColor(R.color.colorBlack));
                TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
            /*Typeface custom_font = Typeface.createFromAsset(context.getAssets(), Fonts.REGULAR);
            textView.setTypeface(custom_font);*/
                textView.setTextSize(16);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
            return snackbar;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            MessageUtils.showToastMessage(context, e.getMessage(), false);
        }
        return snackbar;
    }


    /**
     * Show ERROR/SUCCESS  message using toast SHORT time
     *
     * @param context
     * @param msg
     */

    public static void showToastMessage(FragmentActivity context, String msg, boolean isShow) {
        try {
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            //toast.setText(msg); //Default
            /*Custom View Start*/
            LayoutInflater inflater = context.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, null);
            ImageView image = layout.findViewById(R.id.image);
            if (isShow) {
                image.setVisibility(View.VISIBLE);
            } else {
                image.setVisibility(View.GONE);
            }
            //image.setImageResource(R.drawable.ic_no_data);
            TextView text = layout.findViewById(R.id.text);
            text.setText(msg);
            toast.setView(layout);
            /*Custom View End*/

            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * * Show ERROR/SUCCESS message using toast LONG time
     *
     * @param context
     * @param msg
     */
    public static void showToastMessageLong(FragmentActivity context, String msg) {
        try {
            Toast toast = Toast.makeText(context, String.valueOf(msg), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show dialog on loading time
     *
     * @param activity // context
     * @return
     */

    public static Dialog showDialog(FragmentActivity activity) {

        Dialog dialog = new Dialog(activity);
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ProgressBar progressBar = new ProgressBar(activity);
            //progressBar.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.my_progress_indeterminate));
            ///dialog.getWindow().setWindowAnimations(R.style.grow);
            dialog.setContentView(progressBar);
            dialog.getWindow().setBackgroundDrawableResource(R.color.colorDialogTrans);
            dialog.setCancelable(false);
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    /**
     * Showed  Dialog for Network Not available
     *
     * @param activity
     * @return
     */
    public static Dialog showNetworkDialog(final FragmentActivity activity) {

        final Dialog netWorkDialog = new Dialog(activity);
        try {
            netWorkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            netWorkDialog.setContentView(R.layout.dialog_internet);
            netWorkDialog.setCancelable(false);
            netWorkDialog.getWindow().setBackgroundDrawableResource(R.color.colorDialogTrans);
            //netWorkDialog.getWindow().setWindowAnimations(R.style.upDownDialogAnimation);
            netWorkDialog.getWindow().setGravity(Gravity.BOTTOM);
            TextView done_permission = netWorkDialog.findViewById(R.id.btnSubmitInVertical);
            TextView cancel_permission = netWorkDialog.findViewById(R.id.btnCancelInVertical);
            TextView message_title_permission = netWorkDialog.findViewById(R.id.message_title_permission);

            message_title_permission.setText(activity.getString(R.string.no_network_connection));
            done_permission.setText(activity.getString(R.string.enable));
            cancel_permission.setText(activity.getString(R.string.cancel));
            done_permission.setOnClickListener(view -> {
                if (netWorkDialog != null) {
                    if (netWorkDialog.isShowing())
                        netWorkDialog.dismiss();
                }
                try {
                    Intent intent = new Intent();
                    //$DataUsageSummaryActivity
                    intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings"));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageUtils.showToastMessage(activity, "" + activity.getString(R.string.check_network_connection), true);
                }
            });

            cancel_permission.setOnClickListener(view -> {
                if (netWorkDialog != null) {
                    if (netWorkDialog.isShowing())
                        netWorkDialog.dismiss();
                }
                activity.finish();

                MessageUtils.showToastMessageLong(activity, activity.getResources().getString(R.string.no_internet_connection));
            });
            netWorkDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netWorkDialog;
    }

    /**
     * @param context
     * @param view
     * @param message
     * @return
     */

    @SuppressLint("NewApi")
    public static Snackbar showSnackBarAction(final FragmentActivity context, View view, String message) {

        final Snackbar snackbar;
        snackbar = Snackbar.make(view, String.valueOf(message), Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextSize(16);
        /*Typeface custom_font = Typeface.createFromAsset(context.getAssets(), Fonts.REGULAR);
        textView.setTypeface(custom_font);*/
        textView.setTextColor(Color.WHITE);
        textView.setText(message);
        snackbar.show();

        snackbar.setAction(Html.fromHtml("Go"), view1 -> {
            snackbar.dismiss();
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings"));
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                MessageUtils.showToastMessage(context, context.getString(R.string.check_network_connection), true);
            }
        });
        return snackbar;

    }
/*
    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) findViewById(R.id.usage);
                        if(txt.getVisibility() == View.VISIBLE){
                            txt.setVisibility(View.INVISIBLE);
                        }else{
                            txt.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }*/

    public static Typeface setType(FragmentActivity activity, String fontType) {
        return Typeface.createFromAsset(activity.getAssets(), String.valueOf(fontType));
    }


    @Nullable
    public static Typeface setType(@Nullable AppCompatActivity activity, int fontType) {
        Typeface typeface = ResourcesCompat.getFont(activity, fontType);
        return typeface;
    }

    /**
     * Checking Condition for Showing TSnack View
     *
     * @param response_code
     * @return
     */
    public static String getFailureCode(int response_code) {
        msg = "Something went wrong";
        try {
            switch (response_code) {

                case 201:
                    msg = "The request was successful and one or more resources was created";
                    break;
                case 204:
                    msg = "No content. When an action was executed successfully, but there is no content to return.";
                    break;
                case 206:
                    msg = "Partial content. Useful when you have to return a paginated list of resources.";
                    break;
                case 400:
                    msg = "Request not processed due to client error";
                    break;
                case 401:
                    msg = "Authentication is required and has failed";
                    break;
                case 403:
                    msg = "Forbidden. The user is authenticated.";
                    break;
                case 404:
                    msg = "The requested resource could not be found";
                    break;
                case 405:
                    msg = "A request method is not supported for the requested resource";
                    break;
                case 421:
                    msg = "The request was made to the wrong server";
                    break;
                case 422:
                    msg = "The request was well-formed but unable to process the contained instructions";
                    break;
                case 429:
                    msg = "Too many requests in a 60 second period";
                    break;
                case 500:
                    msg = "Something happened on our side, try again later";
                    break;
                case 503:
                    msg = "Service unavailable";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }


    /**
     * Check Condition for showing toast
     *
     * @param response
     * @return
     */
    public static String getFailureMessage(String response) {
        msg = "Something went wrong";
        try {
            if (response.toLowerCase().contains("Unable to resolve host".toLowerCase())) {
                msg = "Please check your connection";
            } else if (response.toLowerCase().contains("Server not found".toLowerCase())) {
                msg = "Server not found";
            } else if (response.toLowerCase().contains("No route to host".toLowerCase())) {
                msg = "No route to host";
            } else if (response.toLowerCase().contains("connect timed out".toLowerCase())) {
                msg = "Connection timed out";
            } else if (response.toLowerCase().equals("No Internet Connection".toLowerCase())) {
                msg = response;
            } else if (response.toLowerCase().contains("Expected BEGIN_ARRAY but was STRING at line".toLowerCase())) {
                msg = "Server profileInfo not found or not format";
            } else if (response.toLowerCase().contains("Software caused connection abort".toLowerCase())) {
                msg = response + ". Check Network Connection.";
            } else if (response.toLowerCase().contains("Failed to connect".toLowerCase()) || response.toLowerCase().contains("ssl handshake aborted".toLowerCase())) {
                msg = "Failed to connect to Server";
            }
        } catch (Exception e) {
            msg = e.getMessage();
        }
        return msg;

    }

    /**
     * Snackbar dismiss if Open
     *
     * @param snackBar
     */
    public static void dismissSnackBar(Snackbar snackBar) {
        try {
            if (snackBar != null) {
                if (snackBar.isShown()) {
                    snackBar.dismiss();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Dialog close if Open
     *
     * @param dialog
     * @return
     */
    public static Dialog dismissDialog(Dialog dialog) {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }


    @Nullable
    public static Typeface setType(@Nullable FragmentActivity activity, int fontType) {
        Typeface typeface = ResourcesCompat.getFont(activity, fontType);
        return typeface;
    }
}
