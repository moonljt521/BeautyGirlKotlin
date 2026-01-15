#!/usr/bin/env kotlin

/**
 * API 测试脚本
 * 用于在不运行 APP 的情况下测试网络请求
 * 
 * 使用方法：
 * 1. 安装 Kotlin: brew install kotlin (macOS)
 * 2. 运行脚本: kotlin api-test.kts
 */

@file:DependsOn("com.squareup.okhttp3:okhttp:4.12.0")
@file:DependsOn("com.google.code.gson:gson:2.10.1")

import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit

// API URLs
val GANK_BASE_URL = "http://gank.io/api/"
val DOUBAN_BASE_URL = "http://www.buxiuse.com/"
val YOUMEI_BASE_URL = "http://www.umei.cc/bizhitupian/meinvbizhi/"

// 创建 OkHttpClient
val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

// 创建 Gson
val gson = GsonBuilder().setPrettyPrinting().create()

// 测试 Gank API
fun testGankApi(number: Int = 10, page: Int = 1) {
    println("\n=== 测试 Gank API ===")
    println("URL: ${GANK_BASE_URL}data/福利/$number/$page")
    
    try {
        val request = Request.Builder()
            .url("${GANK_BASE_URL}data/福利/$number/$page")
            .build()
        
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val body = response.body?.string()
                println("✅ 请求成功!")
                println("状态码: ${response.code}")
                println("\n响应数据:")
                
                // 格式化 JSON 输出
                val jsonObject = gson.fromJson(body, Any::class.java)
                println(gson.toJson(jsonObject))
            } else {
                println("❌ 请求失败!")
                println("状态码: ${response.code}")
                println("错误信息: ${response.message}")
            }
        }
    } catch (e: Exception) {
        println("❌ 请求异常: ${e.message}")
        e.printStackTrace()
    }
}

// 测试豆瓣 API
fun testDoubanApi(cid: Int = 27, page: Int = 1) {
    println("\n=== 测试豆瓣 API ===")
    println("URL: ${DOUBAN_BASE_URL}?cid=$cid&page=$page")
    
    try {
        val request = Request.Builder()
            .url("${DOUBAN_BASE_URL}?cid=$cid&page=$page")
            .build()
        
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val body = response.body?.string()
                println("✅ 请求成功!")
                println("状态码: ${response.code}")
                println("\n响应数据 (前 500 字符):")
                println(body?.take(500))
                println("...")
            } else {
                println("❌ 请求失败!")
                println("状态码: ${response.code}")
                println("错误信息: ${response.message}")
            }
        }
    } catch (e: Exception) {
        println("❌ 请求异常: ${e.message}")
        e.printStackTrace()
    }
}

// 测试优美图库 API
fun testYoumeiApi() {
    println("\n=== 测试优美图库 API ===")
    println("URL: $YOUMEI_BASE_URL")
    
    try {
        val request = Request.Builder()
            .url(YOUMEI_BASE_URL)
            .build()
        
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val body = response.body?.string()
                println("✅ 请求成功!")
                println("状态码: ${response.code}")
                println("\n响应数据 (前 500 字符):")
                println(body?.take(500))
                println("...")
            } else {
                println("❌ 请求失败!")
                println("状态码: ${response.code}")
                println("错误信息: ${response.message}")
            }
        }
    } catch (e: Exception) {
        println("❌ 请求异常: ${e.message}")
        e.printStackTrace()
    }
}

// 主函数
fun main() {
    println("========================================")
    println("   BeautyGirl API 测试工具")
    println("========================================")
    
    // 测试所有 API
    testGankApi(number = 10, page = 1)
    testDoubanApi(cid = 27, page = 1)
    testYoumeiApi()
    
    println("\n========================================")
    println("   测试完成!")
    println("========================================")
}

// 运行主函数
main()
