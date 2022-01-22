package de.mixelblocks.core.hack.defaults;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.mixelblocks.core.MixelCorePlugin;
import de.mixelblocks.core.hack.Hack;
import de.mixelblocks.core.hack.Hacky;

import java.util.List;

@Hacky(since = "22.02.2022", use = true, reason = "To translate chat color codes on packet source!")
public class HackChatProtocol implements Hack {
    private  PacketAdapter adapt;
    @Override
    public void activate() {

        adapt = new PacketAdapter(MixelCorePlugin.getInstance(),
                ListenerPriority.NORMAL, PacketType.Play.Server.CHAT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.CHAT) {
                    PacketContainer packet = event.getPacket();
                    List<WrappedChatComponent> components = packet.getChatComponents().getValues();
                    for (WrappedChatComponent component : components) {
                        // TODO: replace colorCode things ans style remaining codes
                    }
                }
            }
        };
        ProtocolLibrary.getProtocolManager().addPacketListener(adapt);
    }

    @Override
    public void unregister() {
        ProtocolLibrary.getProtocolManager().removePacketListener(adapt);
    }

}
