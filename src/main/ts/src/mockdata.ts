import Mock from "mockjs";

export const mockYaml = Mock.mock(
`
metadata:
  vars:
    - name: "InstanceFieldNode{NewObj{<pascal.taie.TEST.Main: void main(java.lang.String[])>[1@L6] new pascal.taie.TEST.TMP}.str}"
      isGroup: false
      groupType: "node"
      nodeType: "node"
      color: ""
    - name: "VarNode{<pascal.taie.TEST.Main: void main(java.lang.String[])>/$r0}"
      isGroup: false
      groupType: "node"
      nodeType: "node"
      color: ""
    - name: "VarNode{<pascal.taie.TEST.Main: void main(java.lang.String[])>/$r2}"
      isGroup: false
      groupType: "node"
      nodeType: "node"
      color: ""
    - name: "<pascal.taie.TEST.Main: void main(java.lang.String[])>[0@L5] $r0 = invokestatic pascal.taie.TEST.SourceSink.source()/result"
      isGroup: false
      groupType: "node"
      nodeType: "source-point"
      color: "lightcoral"
    - name: "<pascal.taie.TEST.Main: void main(java.lang.String[])>[5@L8] invokestatic pascal.taie.TEST.SourceSink.sink($r2)/0"
      isGroup: false
      groupType: "node"
      nodeType: "sink-point"
      color: "lightgreen"
    - name: "{ kind: 'call', method: '<pascal.taie.TEST.SourceSink: java.lang.String source()>', index: 'result', type: 'java.lang.String' }"
      isGroup: false
      groupType: "node"
      nodeType: "source"
      color: "gold"
    - name: "{ method: '<pascal.taie.TEST.SourceSink: void sink(java.lang.String)>', index: '0' }"
      isGroup: false
      groupType: "node"
      nodeType: "sink"
      color: "aquamarine"
    - name: "<pascal.taie.TEST.Main: void main(java.lang.String[])>"
      isGroup: true
      groupType: "method"
      nodeType: ""
      color: ""
    - name: "<pascal.taie.TEST.SourceSink: java.lang.String source()>"
      isGroup: true
      groupType: "method"
      nodeType: ""
      color: ""
    - name: "<pascal.taie.TEST.SourceSink: void sink(java.lang.String)>"
      isGroup: true
      groupType: "method"
      nodeType: ""
      color: ""
    - name: "pascal.taie.TEST.Main"
      isGroup: true
      groupType: "class"
      nodeType: ""
      color: ""
    - name: "pascal.taie.TEST.SourceSink"
      isGroup: true
      groupType: "class"
      nodeType: ""
      color: ""
    - name: "pascal.taie.TEST.TMP"
      isGroup: true
      groupType: "class"
      nodeType: ""
      color: ""
    - name: "pascal.taie.TEST"
      isGroup: true
      groupType: "package"
      nodeType: ""
      color: ""
  groups:
    node:
      - 0
      - 1
      - 2
      - 3
      - 4
      - 5
      - 6
    package:
      - 13
    method:
      - 7
      - 8
      - 9
    class:
      - 10
      - 11
      - 12
parent:
  0: 12
  1: 7
  2: 7
  3: 7
  4: 7
  5: 8
  6: 9
  7: 10
  8: 11
  9: 11
  10: 13
  11: 13
  12: 13
children:
  7:
    - 1
    - 2
    - 3
    - 4
  8:
    - 5
  9:
    - 6
  10:
    - 7
  11:
    - 8
    - 9
  12:
    - 0
  13:
    - 10
    - 11
    - 12
graph:
  0:
    - 2
  1:
    - 0
  2:
    - 4
  3:
    - 1
  4:
    - 6
  5:
    - 3
`
);
