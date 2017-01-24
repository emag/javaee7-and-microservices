# Logstash

Logstash にログを送信するサンプルです。

## Docker / Docker Compose のインストール

Docker および Docker Compose をインストールします。

それぞれのインストールは下記ドキュメントを参照ください。

* https://docs.docker.com/engine/installation/
* https://docs.docker.com/compose/install/

## Logstash の起動

Logstash を含む Elastic Stack(Elasticsearch, Kibana) を起動します。

``` sh
docker-compose up -d
```

## アプリケーションのビルドと実行

``` sh
./mvnw clean package
java -jar target/logstash-swarm.jar
```

## API へのアクセス

各種ログレベルをわけた [APIs](#apis) があるのでアクセスします。

``` sh
curl localhost:8080/info
```

Logstash は標準出力にも出力しているようにしているので、以下のようなログが確認できます。

``` sh
docker-compose logs -f logstash
```

```
{
     "loggerClassName" => "org.jboss.logging.Logger",
               "level" => "INFO",
             "message" => "This is INFO message",
                 "ndc" => "",
                 "mdc" => {},
          "threadName" => "default task-1",
                "tags" => [],
            "threadId" => 161,
            "sequence" => 26,
          "@timestamp" => 2017-01-24T05:30:12.086Z,
                "port" => 51842,
            "@version" => 1,
                "host" => "172.23.0.1",
    "wildflySwarmNode" => "your-host-name",
          "loggerName" => "microservices.logstash.MyResource"
}
```

## Kibana でのログ確認

Kibana(http://localhost:5601/) にアクセスします。
 
`Configure an index pattern` の画面に遷移するので、`Index name or pattern` に
`wildfly-swarm-logstash` と入力して `Create` をクリックします。

左のメニューから `Discover` をクリックし、ログが投入されていることを確認します。


### APIs

* /debug
* /info
* /warn
* /error
* /exception