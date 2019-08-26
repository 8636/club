package cn.duan.community.common.cache;


import cn.duan.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by codedrinker on 2019/8/2.
 */

@Component
@Data
public class HotTagCache {
    private List<HotTagDTO> hots = new ArrayList<>();
    public void updateTags(Map<String, Integer> map) {
        ArrayList<HotTagDTO> arrayList = new ArrayList<>();
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(entry.getKey());
            hotTagDTO.setPriority(entry.getValue());
            arrayList.add(hotTagDTO);
        }
        Collections.sort(arrayList, new Comparator<HotTagDTO>() {
            @Override
            public int compare(HotTagDTO o1, HotTagDTO o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });

        hots = arrayList.subList(0, arrayList.size() < 10? arrayList.size():10 );
    }
}
