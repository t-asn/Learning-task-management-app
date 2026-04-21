package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 学習タスク管理アプリケーションの起動クラス。
 * {@link SpringBootApplication} により、コンポーネントスキャンや自動設定が有効化されます。
 */
@SpringBootApplication
public class LearningTaskManagementAppApplication {

  /**
   * アプリケーションのエントリーポイント。
   *
   * @param args コマンドライン引数
   */
  public static void main(String[] args) {
    SpringApplication.run(LearningTaskManagementAppApplication.class, args);
  }
}
