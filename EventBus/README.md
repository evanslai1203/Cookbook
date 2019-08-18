官方文檔：https://github.com/greenrobot/EventBus

一對一的程式互動應盡量使用interface等方式來處理，需要一對多時才考慮EventBus，因為EventBus散落在專案各處將使專案擴大後難以維護。

onEvent(Object event) 方法有四種 Thread Mode，分別說明如下 –

ThreadMode.MAIN :
表示無論事件是從哪個執行緒 post 出來的，onEvent() 都會在主執行緒執行。

ThreadMode.POSTING :
表示事件是從哪個執行緒 post 出來的，onEvent() 就會在哪個執行緒執行。

ThreadMode.BACKGROUND :
表示如果事件是從主執行緒 post 出來的，onEvent() 就會創建新的子執行緒執行，如果事件是從子執行緒 post 出來的，onEvent() 就會在該子執行中緒執行。

ThreadMode.AYSNC :
表示無論事件是從哪個執行緒 post 出來的，onEvent() 都會創建新的子執行緒執行。
