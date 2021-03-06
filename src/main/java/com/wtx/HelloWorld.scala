package com.wtx

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wutianxiong on 2016/10/9.
  */
object HelloWorld {
  def main(args: Array[String]): Unit = {
    //SparkContext 是Spark应用的入口，它负责和这个集群的交互，包括创建RDD，accumulators and broadcast variables。
    //Spark默认的构造方法接受SparkConf，通过SparkConf我们可以自己定义本次提交的参数，这个参数会覆盖系统默认的设置
    val sc=new SparkContext(new SparkConf().setAppName("Spark Count"))
    //一般都是使用textfile来加载一个文件创建RDD，textfile的参数是一个path，这个path可以是以下
    // ①文件路径；②目录路径；③通过通配符的形式加载多个文件或者多个目录下面的所有文件
    //第1个参数为文本文件路径，sc从这里读入，返回文件类型
    val tokenizde = sc.textFile(args(0)).flatMap(_.split(" "))
    //对于读入的文件先将每个值设为（key，value），并对key相同的键值对进行reduce即将key不变，对应的value值相加再存入map中
    val wordCount = tokenizde.map((_,1)).reduceByKey(_+_)
    println(wordCount.collect().mkString(","))
  }

}
