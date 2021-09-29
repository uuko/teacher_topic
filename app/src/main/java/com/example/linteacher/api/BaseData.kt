package com.example.linteacher.api

import com.example.linteacher.util.Config

abstract class BaseData {
    open var itemType:Int= Config.ORIGIN_VIEW_TYPE
}