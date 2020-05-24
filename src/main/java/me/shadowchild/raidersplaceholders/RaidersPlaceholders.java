package me.shadowchild.raidersplaceholders;

import de.dytanic.cloudnet.driver.provider.ServiceTaskProvider;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.wrapper.Wrapper;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;


public final class RaidersPlaceholders extends PlaceholderExpansion {

    private final String VERSION = getClass().getPackage().getImplementationVersion();
    private Wrapper cloudnet = Wrapper.getInstance();
    private ServiceTaskProvider task = cloudnet.getServiceTaskProvider();


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
        return VERSION;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String rawParams) {
        ServiceInfoSnapshot serviceInfoSnapshot = cloudnet.getCurrentServiceInfoSnapshot();
        String params = rawParams.toLowerCase();

        switch (params) {
            case "service_name":
                return serviceInfoSnapshot.getServiceId().getName();
            case "service_static":
                return ""+ serviceInfoSnapshot.getConfiguration().isStaticService();
            case "service_autodelete":
                return "" + serviceInfoSnapshot.getConfiguration().isAutoDeleteOnStop();
            case "service_port":
                return "" + serviceInfoSnapshot.getAddress().getPort();
            case "service_host":
                return "" + serviceInfoSnapshot.getAddress().getHost();
            case "service_address":
                return serviceInfoSnapshot.getAddress().getHost() + ":" + serviceInfoSnapshot.getAddress().getPort();
            case "task_name":
                return serviceInfoSnapshot.getServiceId().getTaskName();
        }
        return null;
    }
}
