package wood.mike.sbstravaapi.controllers.activity;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum PaceZone {
    RECOVERY(1, "Recovery"),
    ENDURANCE(2, "Endurance"),
    TEMPO(3, "Tempo"),
    THRESHOLD(4, "Threshold"),
    VO2MAX(5, "VO2Max"),
    ANAEROBIC(6, "Anaerobic");

    private final Integer val;
    private final String display;
    private static Map<Integer, PaceZone> LOOKUP_MAP;

    static {
        LOOKUP_MAP = new HashMap<>();
        for(PaceZone z : PaceZone.values()) {
            LOOKUP_MAP.put(z.val, z);
        }
        LOOKUP_MAP = Collections.unmodifiableMap(LOOKUP_MAP);
    }
    
    public static PaceZone zoneForVal(Integer val) {
        return LOOKUP_MAP.get(val);
    }

    public static String zoneNameForVal(Integer val) {
        return LOOKUP_MAP.get(val).name();
    }

    PaceZone(final Integer val, final String display) {
        this.val = val;
        this.display = display;
    }

}
