package net.esmaeil.explore.plugin;

import java.security.*;

final class PluginPolicy extends Policy {
    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {
        Permissions permissions =  new Permissions();
        if(PluginUtils.isValid(codesource.getLocation().getPath()))
            permissions.add(new AllPermission());
        return permissions;
    }

    @Override
    public void refresh() {
    }
}
