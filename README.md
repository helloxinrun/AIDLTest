AIDLTest
=======
AIDL (Android Interface Definition Language)顾名思义就是android接口定义语言，用于生成可以在Android设备上两个进程之间进行进程间通信(interprocess communication, IPC)的代码。<br>
此demo采用计算器的例子实现了AIDL进程间通讯的工作机制，包含服务端的数据处理和客户端的数据采集并展示。运行时先运行服务器端,再运客户端，在客户端进行数据输入，背后的计算却在服务器端。<br>
AIDL的工作机制详细说明请见：http://blog.csdn.net/li0978/article/details/52075558
