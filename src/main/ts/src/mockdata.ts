import Mock from "mockjs";

export const mockYaml = Mock.mock(`
metadata:
  packages:
    - "pascal.taie.TEST"
  classes:
    - "pascal.taie.TEST.Main"
    - "pascal.taie.TEST.SourceSink"
  methods:
    - "<pascal.taie.TEST.Main: void main(java.lang.String[])>"
    - "<pascal.taie.TEST.SourceSink: java.lang.String source()>"
    - "<pascal.taie.TEST.SourceSink: void sink(java.lang.String)>"
  nodes:
    - "VarNode{<pascal.taie.TEST.Main: void main(java.lang.String[])>/$r0}"
    - "<pascal.taie.TEST.Main: void main(java.lang.String[])>[0@L5] $r0 = invokestatic pascal.taie.TEST.SourceSink.source()/result"
    - "<pascal.taie.TEST.Main: void main(java.lang.String[])>[1@L6] invokestatic pascal.taie.TEST.SourceSink.sink($r0)/0"
    - "{ kind: 'call', method: '<pascal.taie.TEST.SourceSink: java.lang.String source()>', index: 'result', type: 'java.lang.String' }"
    - "{ method: '<pascal.taie.TEST.SourceSink: void sink(java.lang.String)>', index: '0' }"
  sourcePoints:
    - 1
  sinkPoints:
    - 2
  source:
    - 3
  sink:
    - 4
relation:
  packageToClasses:
    0:
      - 0
      - 1
  classToMethods:
    0:
      - 0
    1:
      - 1
      - 2
  classToFields: {}
  methodToNodes:
    0:
      - 0
      - 1
      - 2
    1:
      - 3
    2:
      - 4
graph:
  1:
    - 0
  0:
    - 2
  3:
    - 1
  2:
    - 4
`);
