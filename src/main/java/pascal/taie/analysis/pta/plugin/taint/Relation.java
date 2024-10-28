package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.graph.flowgraph.*;

import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.MultiMap;

import java.util.List;
import java.util.Map;

class Relation {

    public final Map<Long, List<Long>> packageToClasses = Maps.newHybridMap();

    public final Map<Long, List<Long>> classToMethods = Maps.newHybridMap();

    public final Map<Long, List<Long>> classToFields = Maps.newHybridMap();

    public final Map<Long, List<Long>> methodToNodes = Maps.newHybridMap();

    public Relation(TFGElem tfgElem, MetaData md) {
        MultiMap<Long, Long> p2c = Maps.newMultiMap();
        MultiMap<Long, Long> c2m = Maps.newMultiMap();
        MultiMap<Long, Long> c2f = Maps.newMultiMap();
        MultiMap<Long, Long> m2n = Maps.newMultiMap();

        for(Node n : tfgElem.nodes){
            Long packageIndex;
            Long classIndex;
            Long methodIndex = -1L;
            Long nodeIndex = md.indexOfNode(n.toString());

            if(n instanceof VarNode vn){
                methodIndex = md.indexOfMethod(vn.getVar().getMethod().toString());
                classIndex = md.indexOfClass(vn.getVar().getMethod().getDeclaringClass().toString());
                packageIndex = md.packageFromClass(vn.getVar().getMethod().getDeclaringClass().toString());
            }
            else if(n instanceof InstanceFieldNode ifn){
                classIndex = md.indexOfClass(ifn.getField().getDeclaringClass().toString());
                packageIndex = md.packageFromClass(ifn.getField().getDeclaringClass().toString());
            }
            else if(n instanceof StaticFieldNode sfn){
                classIndex = md.indexOfClass(sfn.getField().getDeclaringClass().toString());
                packageIndex = md.packageFromClass(sfn.getField().getDeclaringClass().toString());
            }
            else if(n instanceof ArrayIndexNode ain){
                if(ain.getBase().getContainerMethod().isPresent()) {
                    methodIndex = md.indexOfMethod(ain.getBase().getContainerMethod().get().toString());
                    classIndex = md.indexOfClass(ain.getBase().getContainerMethod().get().getDeclaringClass().toString());
                    packageIndex = md.packageFromClass(ain.getBase().getContainerMethod().get().getDeclaringClass().toString());
                }
                else{
                    throw new RuntimeException("ArrayIndexNode not contained by a method");
                }
            }
            else{
                throw new RuntimeException("Can't process new Node");
            }

            if(methodIndex != -1){
                m2n.put(methodIndex, nodeIndex);
                c2m.put(classIndex, methodIndex);
            }
            else{
                c2f.put(classIndex, nodeIndex);
            }
            p2c.put(packageIndex, classIndex);
        }

        for (SourcePoint scp : tfgElem.sourcePoints) {
            JMethod m = scp.getContainer();
            JClass cls = m.getDeclaringClass();
            Long scpIndex = md.indexOfNode(scp.toString());
            Long methodIndex = md.indexOfMethod(m.toString());
            Long classIndex = md.indexOfClass(cls.toString());
            Long packageIndex = md.packageFromClass(cls.toString());
            m2n.put(methodIndex, scpIndex);
            c2m.put(classIndex, methodIndex);
            p2c.put(packageIndex, classIndex);
        }

        for (SinkPoint skp : tfgElem.sinkPoints) {
            JMethod m = skp.sinkCall().getContainer();
            JClass cls = m.getDeclaringClass();
            Long skpIndex = md.indexOfNode(skp.toString());
            Long methodIndex = md.indexOfMethod(m.toString());
            Long classIndex = md.indexOfClass(cls.toString());
            Long packageIndex = md.packageFromClass(cls.toString());
            m2n.put(methodIndex, skpIndex);
            c2m.put(classIndex, methodIndex);
            p2c.put(packageIndex, classIndex);
        }

        for (Source sc : tfgElem.sources) {
            Long packageIndex;
            Long classIndex;
            Long methodIndex = -1L;
            Long nodeIndex = md.indexOfNode(sc.toString());

            if (sc instanceof CallSource callSource){
                JMethod m = callSource.method();
                JClass cls = m.getDeclaringClass();
                methodIndex = md.indexOfMethod(m.toString());
                classIndex = md.indexOfClass(cls.toString());
                packageIndex = md.packageFromClass(cls.toString());
            } else if (sc instanceof FieldSource fieldSource){
                JClass cls = fieldSource.field().getDeclaringClass();
                classIndex = md.indexOfClass(cls.toString());
                packageIndex = md.packageFromClass(cls.toString());
            } else if (sc instanceof ParamSource paramSource){
                JMethod m = paramSource.method();
                JClass cls = m.getDeclaringClass();
                methodIndex = md.indexOfMethod(m.toString());
                classIndex = md.indexOfClass(cls.toString());
                packageIndex = md.packageFromClass(cls.toString());
            } else {
                throw new RuntimeException("Can't process new Source");
            }

            if(methodIndex != -1){
                m2n.put(methodIndex, nodeIndex);
                c2m.put(classIndex, methodIndex);
            }
            else{
                c2f.put(classIndex, nodeIndex);
            }
            p2c.put(packageIndex, classIndex);
        }

        for (Sink sk : tfgElem.sinks) {
            JMethod m = sk.method();
            JClass cls = m.getDeclaringClass();
            Long methodIndex = md.indexOfMethod(m.toString());
            Long classIndex = md.indexOfClass(cls.toString());
            Long packageIndex = md.packageFromClass(cls.toString());
            m2n.put(methodIndex, md.indexOfNode(sk.toString()));
            c2m.put(classIndex, methodIndex);
            p2c.put(packageIndex, classIndex);
        }

        p2c.keySet().forEach(k->this.packageToClasses.put(k, p2c.get(k).stream().sorted().toList()));
        c2m.keySet().forEach(k->this.classToMethods.put(k, c2m.get(k).stream().sorted().toList()));
        c2f.keySet().forEach(k->this.classToFields.put(k, c2f.get(k).stream().sorted().toList()));
        m2n.keySet().forEach(k->this.methodToNodes.put(k, m2n.get(k).stream().sorted().toList()));
    }
}
