package top.ish.foundationlibrary.foundationutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import top.ish.foundationlibrary.context.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * SharedPreferences工具类
 */
public class SPUtils {

    private static SharedPreferences mSharedPreferences;
    //SharedPreferences名
    private static final String SHARED_NAME = "wf_pref";
    private static SPUtils mPrefrenceUtil = new SPUtils();
    // String 默认值为空字符串
    private static final String STRING_DEFVALUE = "";
    // 布尔值默认值为false
    private static final boolean BOOLEAN_DEFVALUE = false;
    // int 默认值为-1
    private static final int INT_DEFVALUE = -1;
    // long 默认值为-1
    private static final int LONG_DEFVALUE = -1;
    // float 默认值为-1
    private static final float FLOAT_DEFVALUE = -1;
    private Editor mEditor;

    public static SPUtils getInstance() {
        return mPrefrenceUtil;
    }

    private SPUtils() {
        mSharedPreferences = App.INSTANCE.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public void setObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            mEditor = mSharedPreferences.edit();
            mEditor.putString(key, objectVal);
            mEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        if (mSharedPreferences.contains(key)) {
            String objectVal = mSharedPreferences.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                return (T) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bais.close();
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    public void setString(String key, String value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    /**
     * SP中写入boolean
     *
     * @param key   键
     * @param value 值
     */
    public void setBoolean(String key, boolean value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    public void setInt(String key, int value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */
    public void setLong(String key, long value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.apply();
    }

    /**
     * SP中写入float
     *
     * @param key   键
     * @param value 值
     */
    public void setFloat(String key, float value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putFloat(key, value);
        mEditor.apply();
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值空字符串
     */
    public String getString(String key) {
        return mSharedPreferences.getString(key, STRING_DEFVALUE);
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值false
     */
    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, BOOLEAN_DEFVALUE);
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(String key) {
        return mSharedPreferences.getInt(key, INT_DEFVALUE);
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(String key) {
        return mSharedPreferences.getLong(key, LONG_DEFVALUE);
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public Float getFloat(String key) {
        return mSharedPreferences.getFloat(key, LONG_DEFVALUE);
    }

    public boolean containsKey(String key) {
        return mSharedPreferences.contains(key);
    }

    /**
     * 清除指定key集合内容
     *
     * @param keys key值集合
     */
    public void clearValuesInFile(String[] keys) {
        if (keys != null) {
            mEditor = mSharedPreferences.edit();
            for (String key : keys) {
                mEditor.remove(key);
            }
            mEditor.apply();
        }
    }

    /**
     * 清除除指定key之外的所有内容
     *
     * @param keys key值集合
     */
    public void clearValuesWithOut(String[] keys) {
        if (keys != null) {
            Set<String> allKeys = mSharedPreferences.getAll().keySet();
            allKeys.removeAll(Arrays.asList(keys));
            Iterator<String> it = allKeys.iterator();
            mEditor = mSharedPreferences.edit();
            while (it.hasNext()) {
                mEditor.remove(it.next());
            }
            mEditor.apply();
        }
    }

}
