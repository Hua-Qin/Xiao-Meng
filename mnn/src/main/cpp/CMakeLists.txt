cmake_minimum_required(VERSION 3.22.1)
project("mnn")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_library(mnn SHARED)
target_include_directories(mnn PRIVATE ${CMAKE_CURRENT_SOURCE_DIR})

find_library(log-lib log)
target_link_libraries(mnn ${log-lib})
