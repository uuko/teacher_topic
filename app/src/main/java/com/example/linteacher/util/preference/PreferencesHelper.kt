package com.example.linteacher.util.preference

import android.content.Context
import java.util.*

abstract class PreferencesHelper(private val context: Context) {
    abstract val className: String?

    fun save(type: Type, key: String?, vale: Any?) {
        val store = context.getSharedPreferences(className, Context.MODE_PRIVATE)
        val editor = store.edit()
        if (type === Type.STRING) {
            editor.putString(key, vale as String?)
        } else if (type === Type.FLOAT) {
            editor.putFloat(key, (vale as Float?)!!)
        } else if (type === Type.INT) {
            editor.putInt(key, (vale as Int?)!!)
        } else if (type === Type.LONG) {
            editor.putLong(key, (vale as Long?)!!)
        } else if (type === Type.BOOLEAN) {
            editor.putBoolean(key, (vale as Boolean?)!!)
        } else if (type === Type.STRING_SET) {
            editor.putStringSet(key, vale as HashSet<String?>?)
        } else {
            throw RuntimeException("Must use base type(String, Float, Double, Integer, Long), type from input is " + type.javaClass.name + ".")
        }
        editor.commit()
    }

    operator fun get(type: Type, key: String): Any? {
        val store = context.getSharedPreferences(className, Context.MODE_PRIVATE)
        return if (type === Type.STRING) {
            store.getString(key, "")
        } else if (type === Type.FLOAT) {
            store.getFloat(key, 0f)
        } else if (type === Type.DOUBLE) {
            java.lang.Double.valueOf(store.getString(key, 0.0.toString()))
        } else if (type === Type.INT) {
            store.getInt(key, 0)
        } else if (type === Type.LONG) {
            store.getLong(key, 0)
        } else if (type === Type.BOOLEAN) {
            store.getBoolean(key, true)
        } else if (type === Type.STRING_SET) {
            store.getStringSet(key, HashSet())
        } else {
            throw RuntimeException("Must use base type(String, Float, Double, Integer, Long), type from input is " + type.javaClass.name + ".")
        }
    }

    object Type {
        val STRING: Type = Type
        val FLOAT: Type = Type
        val DOUBLE: Type = Type
        val INT: Type = Type
        val LONG: Type = Type
        val BOOLEAN: Type = Type
        val STRING_SET: Type = Type
    }
}