## HotFix Java热更新组件

## 代码分层

- level-1 ：Utils，HTTP组件，文件存储
- level-2 : Manager ，具有业务含义，封装底层的接口，以满足业务
- level-3 : Process，对Manager层进行管理（业务的组合拼装），以满足一个更加负责的业务
- level-4 : 控制对外异常，依赖Process层，将对外模型转成系统内部模型
