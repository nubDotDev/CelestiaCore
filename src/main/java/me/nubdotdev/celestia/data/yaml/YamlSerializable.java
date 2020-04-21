package me.nubdotdev.celestia.data.yaml;

import java.util.Map;

public interface YamlSerializable {

    Map<String, Object> serialize();

}
