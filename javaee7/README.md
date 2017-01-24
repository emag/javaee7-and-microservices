# javaee7

Java EE 7 のサンプル。以下の内容が含まれています。

* WebSocket
* Concurrency
* jBatch
* JSON-P(JSON Processing)

## 前提条件

* JBoss EAP 7.0.x
* JDK 8

## 利用方法

### JBoss EAP 7 の起動

``` sh
cd $JBOSS_HOME
bin/standalone.sh
```

### アプリケーションのデプロイ

``` sh
./mvnw clean wildfly:deploy
```

### WebSocket アプリケーションの確認

以下 URL に複数のブラウザでアクセスします。

http://localhost:8080/javaee7/websocket/

アクセス後、message フィールドにテキストを入力し Enter を押すことで
お互いのブラウザにメッセージが表示されます。

#### 実装ポイント

* サーバサイド:  javaee7.websocket.ChatEndpoint.java
* クライアントサイド: chat.js

### Concurrency

以下のように URL にアクセスすると

``` sh
curl -X POST localhost:8080/javaee7/api/concurrency/report
```

すぐにレスポンスが返って来ます。

```
Preparing the report...⏎   
```

しかし、サーバログ(server.log もしくは standalone.sh 実行プロセスの標準出力)を確認すると
以下のように 5 秒かかる処理が実行されていることがわかります。

``` sh
13:29:38,303 INFO  [javaee7.concurrency.ReportService] (EE-ManagedExecutorService-default-Thread-1) Start
13:29:43,404 INFO  [javaee7.concurrency.ReportService] (EE-ManagedExecutorService-default-Thread-1) connection: org.jboss.jca.adapters.jdbc.jdk8.WrappedConnectionJDK8@2c7a01c2
13:29:43,405 INFO  [javaee7.concurrency.ReportService] (EE-ManagedExecutorService-default-Thread-1) Done
```

Concurrency にはもう 1 つ計算用の API があります。

下記のようにアクセスすると

``` sh
curl 'localhost:8080/javaee7/api/concurrency/calc?a=3&b=4' -s | jq .
```

およそ 10 秒後にレスポンスが返って来ます。

``` sh
{
  "result": "*** 63 ***",
  "elapsedTime": "10001 msec."
}
```

この API は 5 秒かかる足し算と 10 秒かかる平方の 2 つの計算が含まれていますが、
それぞれを並行して計算することで 10 秒で処理が終わっています。

#### 実装ポイント

javaee7.api.ConcurrencyController.java


### jBatch

下記のようにアクセスすると

``` sh
curl -X POST localhost:8080/javaee7/api/batch -v
```

すぐにレスポンスが返って来ます。

``` sh
[...]
< Location: http://localhost:8080/javaee7/api/batch/1
[...]
```

> Location ヘッダに記載された URL はジョブ情報取得用 URL です。

サーバログ(server.log もしくは standalone.sh 実行プロセスの標準出力)を確認すると
以下のようにバッチ処理に見立てた 10 秒かかる処理が実行されていることがわかります。

```
13:40:03,596 INFO  [javaee7.batch.MyBatchlet] (Batch Thread - 2) process: Start
13:40:13,597 INFO  [javaee7.batch.MyBatchlet] (Batch Thread - 2) process: Done
```

#### 実装ポイント

* javaee7.api.BatchController.java
* javaee7.batch パッケージ

### JSON-P

上記 jBatch で作成したジョブの情報を取得します。

``` sh
curl localhost:8080/javaee7/api/batch/1 -s | jq .
```

以下のように JSON で情報を取得できます。

``` json
{
  "id": 1,
  "name": "simple_job",
  "status": "COMPLETED",
  "create_time": "Tue Jan 24 13:39:43 JST 2017",
  "end_time": "Tue Jan 24 13:39:53 JST 2017"
}
```

#### 実装ポイント

javaee7.api.BatchController.java
