package com.firework.client.Features.CommandsSystem;

import com.firework.client.Features.CommandsSystem.Command;
import com.firework.client.Features.CommandsSystem.Commands.Chat.ClearCommand;
import com.firework.client.Features.CommandsSystem.Commands.Chat.PrefixCommand;
import com.firework.client.Features.CommandsSystem.Commands.Chat.VClipCommand;
import com.firework.client.Features.CommandsSystem.Commands.Client.BookCommand;
import com.firework.client.Features.CommandsSystem.Commands.Client.CoordsCommand;
import com.firework.client.Features.CommandsSystem.Commands.Client.FakePlayerCommand;
import com.firework.client.Features.CommandsSystem.Commands.Client.GuiCommand;
import com.firework.client.Features.CommandsSystem.Commands.Client.NameMcCommand;
import com.firework.client.Features.CommandsSystem.Commands.Client.PitchCommand;
import com.firework.client.Features.CommandsSystem.Commands.Client.YawCommand;
import com.firework.client.Features.CommandsSystem.Commands.Dirs.ImgurCommand;
import com.firework.client.Features.CommandsSystem.Commands.Dirs.OpenDirCommand;
import com.firework.client.Features.CommandsSystem.Commands.Dirs.SaveConfigCommand;
import com.firework.client.Features.CommandsSystem.Commands.Dirs.WebhookCommand;
import com.firework.client.Features.CommandsSystem.Commands.FriendSys.FriendAdd;
import com.firework.client.Features.CommandsSystem.Commands.FriendSys.FriendDell;
import com.firework.client.Features.CommandsSystem.Commands.Fun.CowDupeCommand;
import com.firework.client.Features.CommandsSystem.Commands.Fun.DupeCommand;
import com.firework.client.Features.CommandsSystem.Commands.Fun.PenisCommand;
import com.firework.client.Features.CommandsSystem.Commands.Fun.TutorialCommand;
import com.firework.client.Features.CommandsSystem.Commands.GameSettings.FovCommand;
import com.firework.client.Features.CommandsSystem.Commands.GameSettings.GammaCommand;
import com.firework.client.Features.CommandsSystem.Commands.HelpCommand.Help1Command;
import com.firework.client.Features.CommandsSystem.Commands.HelpCommand.Help2Command;
import com.firework.client.Features.CommandsSystem.Commands.HelpCommand.Help3Command;
import com.firework.client.Features.CommandsSystem.Commands.HelpCommand.Help4Command;
import com.firework.client.Features.CommandsSystem.Commands.PeekCommand.PeekCommand;
import com.firework.client.Implementations.Events.PacketEvent;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommandManager {
    private final List<Command> commands = new ArrayList<Command>();
    public static String prefix = ".";

    public CommandManager() {
        this.init();
    }

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Send event) {
        String message;
        if (event.getPacket() instanceof CPacketChatMessage && (message = ((CPacketChatMessage)((Object)event.getPacket())).getMessage()).startsWith(prefix)) {
            String[] args = message.split(" ");
            String input = message.split(" ")[0].substring(1);
            for (Command command : this.commands) {
                if (!input.equalsIgnoreCase(command.getLabel()) && !this.checkAliases(input, command)) continue;
                event.setCanceled(true);
                command.execute(args);
            }
            if (!event.isCanceled()) {
                MessageUtil.sendClientMessage(ChatFormatting.RED + message + " was not found!", true);
                event.setCanceled(true);
            }
            event.setCanceled(true);
        }
    }

    private boolean checkAliases(String input, Command command) {
        for (String str : command.getAliases()) {
            if (!input.equalsIgnoreCase(str)) continue;
            return true;
        }
        return false;
    }

    public void init() {
        this.register(new Help1Command(), new Help2Command(), new Help3Command(), new Help4Command(), new TutorialCommand(), new CowDupeCommand(), new FriendAdd(), new FriendDell(), new BookCommand(), new PenisCommand(), new PeekCommand(), new CoordsCommand(), new WebhookCommand(), new FovCommand(), new SaveConfigCommand(), new NameMcCommand(), new DupeCommand(), new GammaCommand(), new YawCommand(), new PitchCommand(), new VClipCommand(), new ImgurCommand(), new PrefixCommand(), new ClearCommand(), new ClearCommand(), new OpenDirCommand(), new GuiCommand(), new FakePlayerCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void register(Command ... command) {
        Collections.addAll(this.commands, command);
    }
}
