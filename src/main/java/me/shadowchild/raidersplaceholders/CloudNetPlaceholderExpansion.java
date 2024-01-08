package me.shadowchild.raidersplaceholders;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;


public final class CloudNetPlaceholderExpansion extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "cloudnet";
    }

    @Override
    public String getAuthor() {
        return "ShadowChild";
    }

    @Override
    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }

    @Override
    public String getRequiredPlugin() {
        return "CloudNet-Bridge";
    }

    @Override
    public boolean canRegister() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("CloudNet-Bridge");
        return plugin != null && plugin.isEnabled();
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String rawParams) {
        ServiceInfoSnapshot serviceInfoSnapshot = Wrapper.getInstance().getCurrentServiceInfoSnapshot();
        String params = rawParams.toLowerCase();
        IPlayerManager players = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

        String host = serviceInfoSnapshot.getAddress().getHost();
        String port = Integer.toString(serviceInfoSnapshot.getAddress().getPort());

        switch (params.toLowerCase()) {
            case "service_name":
                return serviceInfoSnapshot.getServiceId().getName();
            case "service_static":
                return serviceInfoSnapshot.getConfiguration().isStaticService() ? "True" : "False";
            case "service_autodelete":
                return serviceInfoSnapshot.getConfiguration().isAutoDeleteOnStop() ? "True" : "False";
            case "service_port":
                return port;
            case "service_host":
                return host;
            case "service_address":
                return String.format("%s:%s", host, port);
            case "task_name":
                return serviceInfoSnapshot.getServiceId().getTaskName();
            case "total_online":
                return Integer.toString(players.getOnlineCount());
        }
        return null;
    }
}
