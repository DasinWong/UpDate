
cmake_minimum_required(VERSION 3.4.1)

# 查找文件系统中指定的路径
file(GLOB bzip_source ${CMAKE_SOURCE_DIR}/bzip/*.c)

# 添加/设置本地动态库，编译生成动态库
add_library(
        # 模块名
        native-lib

        # 动态库
        SHARED

        # 源文件
        native-lib.cpp
        bspatch.c
        ${bzip_source})

# 查找系统库，日志输出log
find_library(
        log-lib
        log)

# 需要链接或编译的库
target_link_libraries(
        native-lib
        ${log-lib})