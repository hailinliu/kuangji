package it.mbkj.lib.base;

import com.google.gson.JsonParseException;
import com.sskj.common.base.App;
import com.sskj.common.util.ToastUtil;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import it.mbkj.lib.exception.LogoutException;
import it.mbkj.lib.exception.ToastException;

/**
 * 作者 :吕志豪
 * 简书：https://www.jianshu.com/u/6e525b929aac
 * github：https://github.com/lvzhihao100
 * 描述：
 * 创建时间：2018-08-14 15:55
 */
public class ExceptionUtil {
    public static void dealException(Throwable e) {
        if (e instanceof JsonParseException) {
        } else if (e instanceof UnknownHostException) {
            ToastUtil.showShort("网络异常");
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            ToastUtil.showShort("网络连接超时");
        } else if (e instanceof ConnectException) {
            ToastUtil.showShort("网络异常");
        }
        if (e instanceof LogoutException) {
            ToastUtil.showShort("请先登录");
        } else if (e instanceof ToastException) {
            ToastUtil.showShort(e.getMessage());
        }
    }

    public static String getExceptionMessage(Throwable e) {
        if (e instanceof JsonParseException) {
        } else if (e instanceof UnknownHostException) {
            return "网络异常";
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            return "网络连接超时";
        } else if (e instanceof ConnectException) {
            return "网络异常";
        }
        if (e instanceof LogoutException) {
            return "请先登录";
        } else if (e instanceof ToastException) {
            return e.getMessage();

        } else {
            return "";
        }
    }
}