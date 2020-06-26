package net.esmaeil.explore;

public interface CoreEvents {
    String BEFORE_INSTALL_PLUGIN = "net.esmaeil.explore.core.plugin.install.before";
    String AFTER_INSTALL_PLUGIN = "net.esmaeil.explore.core.plugin.install.after";
    String BEFORE_UNINSTALL_PLUGIN = "net.esmaeil.explore.core.plugin.uninstall.before";
    String AFTER_UNINSTALL_PLUGIN = "net.esmaeil.explore.core.plugin.uninstall.after";
    String PLUGIN_ENABLED = "net.esmaeil.explore.core.plugin.enabled";
    String PLUGIN_DISABLED = "net.esmaeil.explore.core.plugin.disabled";
    String BEFORE_UPDATE_PLUGIN = "net.esmaeil.explore.core.plugin.update.before";
    String AFTER_UPDATE_PLUGIN = "net.esmaeil.explore.core.plugin.update.after";
    String BEFORE_MENU_BAR_CREATION = "net.esmaeil.explore.core.ui.menu_bar.creation.before";
    String BEFORE_TOOL_BOX_CREATION = "net.esmaeil.explore.core.ui.tool_box.creation.before";
    String BEFORE_SEARCH_HEAD_BOX_CREATION = "net.esmaeil.explore.core.ui.search_head_box.creation.before";
}
