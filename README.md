<div id="top"></div>

## tdas (Tiny Distributed Algorithm Simulator)

<p align="left">
京都産業大学 情報理工学部/コンピュータ理工学部の専門科目「分散処理システム」において2025年度まで使用していた分散アルゴリズム実装用の簡易シミュレータです．
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

## 免責事項
<p align="left">
  tdasの使用により生じたいかなる損害・損失・不利益（直接的・間接的を問わず）について当方は一切の責任を負いかねます．
</p>

## ライセンス

MIT License

Copyright (c) 2025 Naohiro Hayashibara

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
