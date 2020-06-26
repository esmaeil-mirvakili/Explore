package net.esmaeil.explore.config;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "configs")
@IdClass(ConfigEntity.ConfigId.class)
final class ConfigEntity {
    @Id
    private String pluginId;
    @Id
    private String key;

    @Column(length = 1024*1024)
    private Serializable config;

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Serializable getConfig() {
        return config;
    }

    public void setConfig(Serializable config) {
        this.config = config;
    }

    static class ConfigId implements Serializable{
        private String pluginId;
        private String key;

        public String getPluginId() {
            return pluginId;
        }

        public void setPluginId(String pluginId) {
            this.pluginId = pluginId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
