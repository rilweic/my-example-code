package com.lichao666.spark.core.framework.application

import com.lichao666.spark.core.framework.common.TApplication
import com.lichao666.spark.core.framework.controller.WordCountController


object WordCountApplication extends App with TApplication{

    // 启动应用程序
    start(){
        val controller = new WordCountController()
        controller.dispatch()
    }

}
