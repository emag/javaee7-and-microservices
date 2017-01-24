# hello-wfs

はじめての WildFly Swarm のサンプルです。

## 前提条件

* JDK 8

## 実行方法

``` sh
./mvnw clean package
java -jar target/helloworld-wfs-swarm.jar
```

起動後、下記のように API にアクセスすると

``` sh
curl localhost:8080/hello
```

以下のようなレスポンスが返ります。

``` json
{"message" : "Hello, WildFly Swarm!"}⏎
```

## project-stages.yml

WildFly Swarm では各種設定を `project-stages.yml` というファイルで行うことが可能です。

本サンプルでは以下設定で https でのアクセスを可能としています。

``` yml
swarm:
  http:
    certificate.alias: selfsigned
    keystore:
      password: password
      path: keystore.jks
```

以下のように https(8443 ポート)で同様にアクセス可能です。

``` sh
$ curl https://localhost:8443/hello -k
```

また、HTTP/2 が動作可能な環境であれば、自動的に HTTP/2 も有効化されます。

``` sh
curl --http2 https://localhost:8443/hello -kI
HTTP/2 200 
content-type: application/json
content-length: 37
date: Tue, 24 Jan 2017 05:07:49 GMT
```

project-stages.yml ではステージを複数定義可能です。

``` yml
project:
  stage: production
swarm:
  undertow:
    servers:
      default-server:
        http-listeners:
          default:
            enabled: false
```

ステージを記載していない定義はデフォルト扱いで、上書きしない限り有効になります。

上記では `production` ステージでは http リスナは無効としています。

下記のようにシステムプロパティ `swarm.project.stage` でステージを指定します。

``` sh
java -jar target/helloworld-wfs-swarm.jar -Dswarm.project.stage=production
```

8080 ポートがリスンされていないことが確認できます。

``` sh
$ ss -anlp | grep 1701
tcp LISTEN 0 128 :::8443 :::* users:(("java",pid=1701,fd=476))
tcp LISTEN 0  50 :::9990 :::* users:(("java",pid=1701,fd=475))
```

> 1701 はプロセス ID