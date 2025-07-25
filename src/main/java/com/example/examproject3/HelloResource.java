package com.example.examproject3;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}

/*
🛠️ **問題の概要**

Tomcatは正常に起動し、`ExamProject3:war exploded` アーティファクトも「正常にデプロイされた」とログには出ています。ところが、ブラウザで `http://localhost:8080/ExamProject3_war_exploded/` にアクセスしようとした際に **HTTP 404 エラー** が出ています。つまり、Tomcatはリクエストされたリソースを見つけられていません。

---

🔍 **考えられる原因と対処法**

| 原因 | 対処方法 |
|------|----------|
| 🌱 **URLが間違っている可能性** | `ExamProject3_war_exploded` がアプリケーションのコンテキストパスか確認しましょう。IntelliJで `<context>` の設定や `web.xml`、Tomcatの `server.xml` の `<Context>` 設定をチェックしてみてください。 |
| 📁 **アプリケーションのフォルダ構成が不正** | `WEB-INF` や `index.jsp` など、最低限のファイルが `webapps/ExamProject3_war_exploded` に存在しているか確認しましょう。 |
| ⚙️ **IntelliJ IDEAのTomcat設定ミス** | `war exploded` でデプロイすると、アプリのコンテキスト名が正しく設定されない場合があります。`Edit Configurations` → `Deployment` のところで、URLが何になるか確認できます。 |
| 🧩 **未使用のJARがTLDスキャンされている警告** | これは起動時間やJSPコンパイルに影響しますが、404には直接関係しません。必要なら `scanSkip` の設定を検討。 |
| 🧪 **アクセス先URLの検証不足** | ブラウザでは `http://localhost:8080/` にアクセスして、デフォルトのTomcatインデックスページが表示されるかどうかを確認してみましょう。その中に自分のアプリのリンクがあることがあります。 |

---

💡 **確認すべきポイント**

- IntelliJの設定 → Deploymentタブで「Context path」が `/ExamProject3_war_exploded` になっているか？
- 実際にTomcatの `webapps` ディレクトリにデプロイされているか？アプリのフォルダが存在するか？
- アプリケーションのルートに `index.jsp` や `web.xml` があるか？

---

もしよければ、`web.xml` やデプロイ設定のスクリーンショットを見せてくれると、もっと具体的に原因を絞り込めるよ 😄

やってみたら結果を教えてね！
 */