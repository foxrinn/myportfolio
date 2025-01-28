package com.artemsolovev.service;

import com.artemsolovev.model.Compliment;
import com.artemsolovev.model.DataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ComplimentServiceImpl implements ComplimentService {
    private DataList dataList;

    private HashMap<Long, ArrayList<Integer>> hashMap = new HashMap<>();

    @Autowired
    public void setDataList(DataList dataList) {
        this.dataList = dataList;
    }
    @Override
    public List<Compliment> get() {
        return this.dataList.getList();
    }

    @Override
    public Compliment get(int id) {
        return this.dataList.getList().get(id);
    }

    @Override
    public Compliment getRandom(long idUser) {
        ArrayList<Integer> arrayList = hashMap.getOrDefault(idUser, new ArrayList<>());
        if (arrayList.size() == this.dataList.getList().size()) {
            arrayList = new ArrayList<>();
            hashMap.put(idUser, arrayList);
        }
        int id = (int) (Math.random() * this.dataList.getList().size());
        while (arrayList.contains(id)) {
            id = (int) (Math.random() * this.dataList.getList().size());
        }
        Compliment compliment = this.dataList.getList().get(id);
        arrayList.add(id);
        hashMap.put(idUser, arrayList);
        return compliment;
    }
}
