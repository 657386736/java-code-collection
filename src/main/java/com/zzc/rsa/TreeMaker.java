package com.zzc.rsa;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: [ zhongzc ]
 * @Date: 2019/8/27 17:57
 * @Description: [ ]
 * @Version: [ v1.0.0 ]
 * @Copy:
 */
public abstract class TreeMaker<T> {

    //根节点标识
    String rootFlag;

    //对于没有父节点的节点，false被丢弃，true作为根节点
    boolean asRoot = false;

    public List<T> getTree(List<T> list) {
        List<T> rootList = new ArrayList<>();

        if (null == list || list.size() == 0) {
            return rootList;
        }

        list.forEach(e -> {
            if (isRootNode(e)) {
                rootList.add(e);
            } else {
                int i;
                for (i = 0; i < list.size(); i++) {
                    if (getNodeParentId(e).equals(getNodeId(list.get(i)))) {
                        //have find parent
                        if (null == getNodeChildren(list.get(i))) {
                            setChildren(list.get(i));
                        }
                        getNodeChildren(list.get(i)).add(e);

                        break;
                    }
                }
                //have not find parent
                if (i == list.size() && asRoot) {
                    rootList.add(e);
                }
            }
        });

        return rootList;
    }

    protected abstract void setChildren(T t);

    protected abstract String getNodeId(T t);

    protected abstract List<T> getNodeChildren(T t);

    private boolean isRootNode(T e) {
        String parentId = getNodeParentId(e);
        if ((null == rootFlag && null == parentId)
                || (null != rootFlag && rootFlag.equals(parentId))) {
            return true;
        }
        return false;
    }

    protected abstract String getNodeParentId(T e);


}

