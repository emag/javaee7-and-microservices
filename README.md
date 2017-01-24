# javaee7-microservices

Java EE 7 と Microservices の紹介用サンプル

## 構成

大きく Java EE 7 用のプロジェクト(javaee7)と Microservices 用のプロジェクト(microservices)にわかれています。

```
<this project>
├── javaee7                   # Java EE 7 サンプル(WebSocket、Concurrency、jBatch、JSON-P)
├── microservices
│   ├── hello-wfs             # はじめての WildFly Swarm
│   ├── logstash              # Logstash へのログ送信 
│   ├── ribbon-hystrix-consul # クライアントサイド ロードバランシング、サーキットブレーカ、サービスディスカバリ
│   └── zipkin                # サービスのトレーシング
└── README.md                 # この README
```

具体的な動作確認方法は各プロジェクトの README を参照ください。