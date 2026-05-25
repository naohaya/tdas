<div id="top"></div>

## tdas (Tiny Distributed Algorithm Simulator)

<p align="left">
京都産業大学 情報理工学部/コンピュータ理工学部の専門科目「分散処理システム」において2025年度まで使用していた分散アルゴリズム実装用の簡易シミュレータです．<br/>
</p>

## 動作環境
<p align="left">
  java 11以上
</p>

## 準備
ディレクトリ内のjavaソースファイルを全てコンパイルしてください．

## シミュレータの使い方

<p align="left">
  <tt>java Simulator -c [クラス名] -p [プロセス数] </tt>
</p>

## シミュレータ上で動作するプログラムの作成について

<p align="left">
  クラスProcessを継承する形で実装し，コンパイルしたclassファイルのクラス名をSimulatorに引数として渡すことで動作します．<br/>
  詳細についてはTestAlgorithm.javaを参考にしてください．
</p>
