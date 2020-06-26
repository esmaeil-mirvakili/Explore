package net.esmaeil.explore.plugin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "plugins")
public final class PluginEntity {
    @Id
    private String id;
    private String name;
    @Type(type = "text")
    private String description;
    @Column(length = 2000)
    private String developer;
    private String version;
    private String path;
    private String rootClass;
    @Enumerated(value = EnumType.STRING)
    @JsonIgnore
    private PluginStatus status;
    @Transient
    @JsonIgnore
    private boolean updateAvailable;
    @Transient
    @JsonIgnore
    private String nextAvailableVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public PluginStatus getStatus() {
        return status;
    }

    public void setStatus(PluginStatus status) {
        this.status = status;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }

    public String getNextAvailableVersion() {
        return nextAvailableVersion;
    }

    public void setNextAvailableVersion(String nextAvailableVersion) {
        this.nextAvailableVersion = nextAvailableVersion;
    }

    public String getRootClass() {
        return rootClass;
    }

    public void setRootClass(String rootClass) {
        this.rootClass = rootClass;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
