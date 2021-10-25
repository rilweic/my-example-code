package com.lichao666.spark.core.framework.common

import com.lichao666.spark.core.framework.util.EnvUtil

trait TDao {

    def readFile(path:String) = {
        EnvUtil.take().textFile(path)
    }
}
